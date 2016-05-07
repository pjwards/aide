package com.pjwards.aide.controller.user;

import com.pjwards.aide.domain.ForgotPassword;
import com.pjwards.aide.domain.User;
import com.pjwards.aide.domain.enums.ValidEntity;
import com.pjwards.aide.domain.forms.ForgotPasswordForm;
import com.pjwards.aide.domain.validators.ForgotPasswordFormValidator;
import com.pjwards.aide.exception.ResourceNotFoundException;
import com.pjwards.aide.repository.ForgotPasswordRepository;
import com.pjwards.aide.repository.UserRepository;
import com.pjwards.aide.service.user.ForgotPasswordService;
import com.pjwards.aide.service.user.UserService;
import com.pjwards.aide.util.Mailing;
import com.pjwards.aide.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class SignInController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignInController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private Utils utils;

    @Autowired
    private Mailing mailing;

    private final ForgotPasswordFormValidator forgotPasswordFormValidator;

    @Autowired
    public SignInController(ForgotPasswordFormValidator forgotPasswordFormValidator) {
        this.forgotPasswordFormValidator = forgotPasswordFormValidator;
    }

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(forgotPasswordFormValidator);
    }


    @RequestMapping("/sign_in")
    public ModelAndView getSignInPage(@RequestParam Optional<String> error) {
        LOGGER.debug("Getting login page, error={}", error);
        return new ModelAndView("signin/signin", "error", error);
    }

    @RequestMapping("/forgot_password/new")
    public String forgotPasswordPage(){
        LOGGER.debug("Forgot password page");
        return "signin/password_found";
    }

    @RequestMapping(value = "/forgot_password/new", method = RequestMethod.POST)
    public ModelAndView handleForgotPassword(@RequestParam(value = "email", required = false) String email){
        LOGGER.debug("Forgot password handler, email={}", email);

        if(email == null){
            return  new ModelAndView("signin/password_found", "error", "Email is empty");
        }

        String error = validateEmail(email);

        if(error != null){
            return new ModelAndView("signin/password_found", "error", error);
        }

        User user = userService.findByEmail(email).get();

        ForgotPassword forgotPassword;

        forgotPassword = forgotPasswordService.create(user);

        user.setPassword(utils.getRandomHash(user.getPassword()));
        userRepository.save(user);

        mailing.sendForgotPasswordMail(user, forgotPassword.getKeyHash());

        return new ModelAndView("signin/password_found", "message", "Success! Check your email.");
    }

    private String validateEmail(String email){

        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
        } catch (AddressException ex){
            return "Email does not validate";
        }

        try {
            userService.findByEmail(email).isPresent();
        } catch (Exception ex){
            return "Email does not exists";
        }

        return null;
    }

    @RequestMapping("/forgot_password/reset/**")
    public ModelAndView resetPassword(@RequestParam(value = "k", required = true) String keyHash) {
        LOGGER.debug("Reset password page, keyHash={}", keyHash);

        ForgotPassword forgotPassword = forgotPasswordRepository.findOneByKeyHash(keyHash);
        validateForgotPassword(forgotPassword);

        return new ModelAndView("signin/password_reset", "form", new ForgotPasswordForm());
    }

    private void validateForgotPassword(ForgotPassword forgotPassword){
        if(forgotPassword == null) {
            throw new ResourceNotFoundException("Can not find forgot password request");
        }

        if(forgotPassword.getValidEntity() == ValidEntity.INVALID){
            throw new ResourceNotFoundException("This request is invalid");
        }

        long currentTime = System.currentTimeMillis();
        long time = forgotPassword.getExpiredDate().getTime();

        long differentTime = time - currentTime;

        if(differentTime < 0){
            throw new ResourceNotFoundException("Expired Time Over");
        }
        LOGGER.debug("Reset password page, differentTime={}", differentTime);
    }

    @RequestMapping(value = "/forgot_password/reset/**", method = RequestMethod.POST)
    public String handleResetPassword(@Valid @ModelAttribute("form") ForgotPasswordForm form, BindingResult bindingResult,
                                      @RequestParam(value = "k", required = true) String keyHash) {
        LOGGER.debug("Processing reset password form={}, bindingResult={}", form, bindingResult);

        ForgotPassword forgotPassword = forgotPasswordRepository.findOneByKeyHash(keyHash);
        validateForgotPassword(forgotPassword);

        if (bindingResult.hasErrors()) {
            // failed validation
            return "signin/password_reset";
        }

        forgotPasswordService.resetPassword(form, forgotPassword);

        // ok, redirect
        return "redirect:/";
    }
}
