package com.pjwards.aide.domain.validators;

import com.pjwards.aide.domain.forms.ForgotPasswordForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ForgotPasswordFormValidator implements Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ForgotPasswordFormValidator.class);

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(ForgotPasswordForm.class);
    }


    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        ForgotPasswordForm form = (ForgotPasswordForm) target;
        validatePasswordEmpty(errors, form);
        validatePasswordRepeatedEmpty(errors, form);
        validatePasswordLength(errors, form);
        validatePasswordRepeatedLength(errors, form);
        validatePasswords(errors, form);
    }

    private void validatePasswordEmpty(Errors errors, ForgotPasswordForm form){
        if (form.getPassword() == null || form.getPassword().equals("")) {
            errors.reject("password.empty", "Password is empty");
        }
    }

    private void validatePasswordRepeatedEmpty(Errors errors, ForgotPasswordForm form){
        if (form.getPasswordRepeated() == null || form.getPasswordRepeated().equals("")) {
            errors.reject("passwordRepeated.empty", "PasswordRepeated is empty");
        }
    }

    private void validatePasswordLength(Errors errors, ForgotPasswordForm form){
        if (form.getPassword().length() < 7) {
            errors.reject("password.empty", "Password is too short (minimum is 7 characters)");
        }
    }

    private void validatePasswordRepeatedLength(Errors errors, ForgotPasswordForm form){
        if (form.getPasswordRepeated().length() < 7) {
            errors.reject("passwordRepeated.empty", "PasswordRepeated is too short (minimum is 7 characters)");
        }
    }

    private void validatePasswords(Errors errors, ForgotPasswordForm form) {
        if (!form.getPassword().equals(form.getPasswordRepeated())) {
            errors.reject("password.no_match", "Passwords do not match");
        }
    }

}
