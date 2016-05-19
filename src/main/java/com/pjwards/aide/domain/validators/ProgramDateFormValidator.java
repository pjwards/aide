package com.pjwards.aide.domain.validators;

import com.pjwards.aide.domain.ProgramDate;
import com.pjwards.aide.domain.forms.ProgramDateForm;
import com.pjwards.aide.exception.ConferenceNotFoundException;
import com.pjwards.aide.service.conference.ConferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Component
public class ProgramDateFormValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramDateFormValidator.class);

    @Autowired
    ConferenceService conferenceService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(ProgramDateForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        ProgramDateForm form = (ProgramDateForm) target;
        validateName(errors, form);
        validateDay(errors, form);
        validateConference(errors, form);
    }

    private void validateName(Errors errors, ProgramDateForm form) {
        if (form.getName() == null || form.getName().equals("")) {
            errors.reject("name.empty", "Name is empty");
        } else if (form.getName().length() > ProgramDate.MAX_LENGTH_NAME) {
            errors.reject("name.large", "Name is too large");
        }
    }

    private void validateDay(Errors errors, ProgramDateForm form) {
        if (form.getDay() == null || form.getDay().equals("")) {
            errors.reject("day.empty", "Day is empty");
        }

        try {
            DateFormat formatter = new SimpleDateFormat(ProgramDate.DAY_FORMAT);
            formatter.parse(form.getDay());
        } catch (Exception e) {
            errors.reject("day.no_validate", "Day does not validate");
        }

    }

    private void validateConference(Errors errors, ProgramDateForm form) {
        if (form.getConferenceId() == null) {
            errors.reject("conference.empty", "Conference is empty");
        }

        try {
            conferenceService.findById(form.getConferenceId());
        } catch (ConferenceNotFoundException e) {
            errors.reject("conference.no_validate", "conference does not validate");
        }
    }
}
