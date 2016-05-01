package com.pjwards.aide.controller.user;


import com.pjwards.aide.domain.Assets;
import com.pjwards.aide.domain.CurrentUser;
import com.pjwards.aide.domain.User;
import com.pjwards.aide.domain.forms.UserUpdatePasswordForm;
import com.pjwards.aide.domain.validators.ImageValidator;
import com.pjwards.aide.domain.validators.UserUpdatePasswordFormValidator;
import com.pjwards.aide.repository.UserRepository;
import com.pjwards.aide.service.user.UserService;
import com.pjwards.aide.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/settings")
public class UserSettingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserSettingController.class);
    private final UserService userService;
    private final UserUpdatePasswordFormValidator userUpdatePasswordFormValidator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageValidator imageValidator;

    @Autowired
    private Utils utils;

    @Autowired
    public UserSettingController(UserService userService, UserUpdatePasswordFormValidator userUpdatePasswordFormValidator) {
        this.userService = userService;
        this.userUpdatePasswordFormValidator = userUpdatePasswordFormValidator;
    }

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userUpdatePasswordFormValidator);
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public String handleSettingsPassword(@Valid @ModelAttribute("form") UserUpdatePasswordForm form, BindingResult bindingResult) {
        LOGGER.debug("Processing user register form={}, bindingResult={}", form, bindingResult);
        if (bindingResult.hasErrors()) {
            // failed validation
            return "user/settings";
        }

        userService.updatePassword(form);

        SecurityContextHolder.clearContext();

        // ok, redirect
        return "redirect:/";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView handleSettingsDelete(@ModelAttribute("currentUser")CurrentUser currentUser,
                                             @RequestParam(value = "password", required = true) String password){

        LOGGER.debug("Delete User id={}", currentUser.getId());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, currentUser.getUser().getPassword())) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("user/settings");
            modelAndView.addObject("form", new UserUpdatePasswordForm());
            modelAndView.addObject("deleteError","Passwords do not match");
            return modelAndView;
        }


        userRepository.delete(currentUser.getId());

        SecurityContextHolder.clearContext();

        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/update")
    public ModelAndView getSettingsUserUpdate() {
        LOGGER.debug("Getting settings user update form");
        return new ModelAndView("user/settings", "form", new UserUpdatePasswordForm());
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateSettingsUser(@ModelAttribute("currentUser")CurrentUser currentUser,
                                            @RequestParam(value = "file",required = false) MultipartFile file,
                                            @RequestParam(value = "name", required = false) String name,
                                            @RequestParam(value = "company", required = false) String company,
                                            @RequestParam(value = "description", required = false) String description) {
        LOGGER.debug("Getting settings user update, name={}", name);

        ModelAndView modelAndView = new ModelAndView("user/settings", "form", new UserUpdatePasswordForm());

        User user = currentUser.getUser();

        /*
        Set name
         */
        if(name != null && !name.equals("")){
            user.update(name, user.getEmail(), user.getPassword(), user.getCreatedDate(), user.getLastDate(),
                    user.getCompany(), user.getRole(), user.getDescription());
            userRepository.save(user);
            modelAndView.addObject("nameSuccess", "Changed successfully");
        } else {
            modelAndView.addObject("nameError", "Name is empty");
        }

        /*
        Set user thumbnail
         */
        if(file != null && imageValidator.validate(file.getOriginalFilename())){
            LOGGER.debug("File name={}, validated={}", file.getOriginalFilename(), imageValidator.validate(file.getOriginalFilename()));
            Assets assets = utils.profileSaveHelper(file, user);

            if(assets != null){
                modelAndView.addObject("avatarSuccess", "The avatar has been changed successfully");
            }
        }

        /*
        Set company
         */
        if(company != null && !company.equals("")){
            user.update(user.getName(), user.getEmail(), user.getPassword(), user.getCreatedDate(), user.getLastDate(),
                    company, user.getRole(), user.getDescription());
            userRepository.save(user);
            modelAndView.addObject("companySuccess", "Changed successfully");
        }

        /*
        Set description
         */
        if(description!= null && !description.equals("")){
            user.update(user.getName(), user.getEmail(), user.getPassword(), user.getCreatedDate(), user.getLastDate(),
                    user.getCompany(), user.getRole(), description);
            userRepository.save(user);
            modelAndView.addObject("descriptionSuccess", "Changed successfully");
        }

        return modelAndView;
    }
}
