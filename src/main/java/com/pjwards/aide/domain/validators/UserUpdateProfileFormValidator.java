package com.pjwards.aide.domain.validators;


import com.pjwards.aide.domain.forms.UserUpdateProfileForm;
import com.pjwards.aide.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserUpdateProfileFormValidator implements Validator{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserUpdateProfileFormValidator.class);
    private final UserService userService;

    @Autowired
    public UserUpdateProfileFormValidator(UserService userService){
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserUpdateProfileForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        UserUpdateProfileForm form = (UserUpdateProfileForm) target;
        validateNameEmpty(errors, form);
        validateNameLength(errors, form);
    }

    private void validateNameEmpty(Errors errors, UserUpdateProfileForm form) {
        if (form.getName() == null || form.getName().equals("")) {
            errors.reject("name.empty", "name is empty");
        }
    }

    private void validateNameLength(Errors errors, UserUpdateProfileForm form) {
        if (form.getName().length() < 2) {
            errors.reject("name.empty", "Name is too short (minimum is 2 characters)");
        }
    }
}
