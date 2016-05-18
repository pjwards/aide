package com.pjwards.aide.domain.validators;

import com.pjwards.aide.domain.Room;
import com.pjwards.aide.domain.forms.RoomForm;
import com.pjwards.aide.exception.ConferenceNotFoundException;
import com.pjwards.aide.exception.UserNotFoundException;
import com.pjwards.aide.service.conference.ConferenceService;
import com.pjwards.aide.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RoomFormValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomFormValidator.class);

    @Autowired
    ConferenceService conferenceService;

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(RoomForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        RoomForm form = (RoomForm) target;
        validateName(errors, form);
        validateDescription(errors, form);
        validateLocation(errors, form);
        validateConference(errors, form);
        validateManagers(errors, form);
    }

    private void validateName(Errors errors, RoomForm form) {
        if (form.getName() == null || form.getName().equals("")) {
            errors.reject("name.empty", "Name is empty");
        } else if (form.getName().length() > Room.MAX_LENGTH_NAME) {
            errors.reject("name.large", "Name is too large");
        }
    }

    private void validateDescription(Errors errors, RoomForm form) {
        if (form.getDescription() == null || form.getDescription().equals("")) {
            errors.reject("description.empty", "Description is empty");
        }
    }

    private void validateLocation(Errors errors, RoomForm form) {
        if (form.getLocation() == null || form.getLocation().equals("")) {
            errors.reject("location.empty", "Location is empty");
        } else if (form.getLocation().length() > Room.MAX_LENGTH_LOCATION) {
            errors.reject("location.large", "Location is too large");
        }
    }

    private void validateConference(Errors errors, RoomForm form) {
        if (form.getConferenceId() == null) {
            errors.reject("conference.empty", "Conference is empty");
        }

        try {
            conferenceService.findById(form.getConferenceId());
        } catch (ConferenceNotFoundException e) {
            errors.reject("conference.no_validate", "Conference does not validate");
        }
    }

    private void validateManagers(Errors errors, RoomForm form) {
        if (form.getManagers() != null) {

            try {
                for (Long id : form.getManagers()) {
                    userService.findById(id);
                }
            } catch (UserNotFoundException e) {
                errors.reject("manager.no_validate", "Manager does not validate");
            }
        }
    }
}
