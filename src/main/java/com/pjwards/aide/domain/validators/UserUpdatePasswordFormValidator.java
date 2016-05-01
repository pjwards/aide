package com.pjwards.aide.domain.validators;


import com.pjwards.aide.domain.forms.UserUpdatePasswordForm;
import com.pjwards.aide.repository.UserRepository;
import com.pjwards.aide.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserUpdatePasswordFormValidator implements Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserUpdatePasswordFormValidator.class);
    private final UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserUpdatePasswordFormValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserUpdatePasswordForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        UserUpdatePasswordForm form = (UserUpdatePasswordForm) target;
        validateOldPasswordEmpty(errors, form);
        validatePasswordEmpty(errors, form);
        validatePasswordRepeatedEmpty(errors, form);
        validatePasswordLength(errors, form);
        validatePasswordRepeatedLength(errors, form);
        validateOldPasswords(errors, form);
        validatePasswords(errors, form);
    }

    private void validateOldPasswordEmpty(Errors errors, UserUpdatePasswordForm form){
        if (form.getPassword() == null || form.getPassword().equals("")) {
            errors.reject("oldPassword.empty", "OldPassword is empty");
        }
    }

    private void validatePasswordEmpty(Errors errors, UserUpdatePasswordForm form){
        if (form.getPassword() == null || form.getPassword().equals("")) {
            errors.reject("password.empty", "Password is empty");
        }
    }

    private void validatePasswordRepeatedEmpty(Errors errors, UserUpdatePasswordForm form){
        if (form.getPasswordRepeated() == null || form.getPasswordRepeated().equals("")) {
            errors.reject("passwordRepeated.empty", "PasswordRepeated is empty");
        }
    }

    private void validatePasswordLength(Errors errors, UserUpdatePasswordForm form){
        if (form.getPassword().length() < 7) {
            errors.reject("password.empty", "Password is too short (minimum is 7 characters)");
        }
    }

    private void validatePasswordRepeatedLength(Errors errors, UserUpdatePasswordForm form){
        if (form.getPasswordRepeated().length() < 7) {
            errors.reject("passwordRepeated.empty", "PasswordRepeated is too short (minimum is 7 characters)");
        }
    }

    private void validateOldPasswords(Errors errors, UserUpdatePasswordForm form) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(form.getOldPassword(), userRepository.getOne(form.getId()).getPassword())) {
            errors.reject("oldPassword.no_match", "Old Password do not match");
        }
    }

    private void validatePasswords(Errors errors, UserUpdatePasswordForm form) {
        if (!form.getPassword().equals(form.getPasswordRepeated())) {
            errors.reject("password.no_match", "Passwords do not match");
        }
    }
}
