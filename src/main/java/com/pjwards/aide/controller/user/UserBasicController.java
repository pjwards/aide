package com.pjwards.aide.controller.user;

import com.pjwards.aide.domain.User;
import com.pjwards.aide.domain.forms.SignUpForm;
import com.pjwards.aide.domain.validators.SignUpFormValidator;
import com.pjwards.aide.exception.UserNotFoundException;
import com.pjwards.aide.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserBasicController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserBasicController.class);
    private final UserService userService;
    private final SignUpFormValidator signUpFormValidator;

    @Autowired
    public UserBasicController(UserService userService, SignUpFormValidator signUpFormValidator){
        this.userService = userService;
        this.signUpFormValidator = signUpFormValidator;
    }

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(signUpFormValidator);
    }

    @RequestMapping("/sign_up")
    public ModelAndView getUserSignUpPage() {
        LOGGER.debug("Getting user sign_up form");
        return new ModelAndView("signin/signup", "form", new SignUpForm());
    }

    @RequestMapping(value = "/sign_up", method = RequestMethod.POST)
    public String handleUserSignUpForm(@Valid @ModelAttribute("form") SignUpForm form, BindingResult bindingResult) {
        LOGGER.debug("Processing user sign_up form={}, bindingResult={}", form, bindingResult);
        if (bindingResult.hasErrors()) {
            // failed validation
            return "signin/signup";
        }
        try {
            userService.create(form);
        } catch (DataIntegrityViolationException e) {
            // probably email already exists - very rare case when multiple admins are adding same user
            // at the same time and form validation has passed for more than one of them.
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
            bindingResult.reject("email.exists", "Email already exists");
            return "signin/signup";
        }
        // ok, redirect
        return "redirect:/";
    }

    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @RequestMapping("/{id}")
    public ModelAndView getUserPage(@PathVariable Long id) {
        LOGGER.debug("Getting user page for user={}", id);

        User user;

        try {
            user = userService.findById(id);
        }catch (UserNotFoundException e){
            LOGGER.warn("User not found", e);
            return new ModelAndView("redirect:/");
        }

        ModelAndView modelAndView = new ModelAndView("user/userprofile");
        modelAndView.addObject("user",user);

        return modelAndView;
    }
}
