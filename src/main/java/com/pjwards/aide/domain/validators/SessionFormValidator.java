package com.pjwards.aide.domain.validators;

import com.pjwards.aide.domain.Session;
import com.pjwards.aide.domain.forms.SessionForm;
import com.pjwards.aide.exception.ProgramNotFoundException;
import com.pjwards.aide.exception.RoomNotFoundException;
import com.pjwards.aide.exception.UserNotFoundException;
import com.pjwards.aide.service.program.ProgramService;
import com.pjwards.aide.service.room.RoomService;
import com.pjwards.aide.service.user.UserService;
import org.apache.commons.validator.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SessionFormValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionFormValidator.class);

    @Autowired
    ProgramService programService;

    @Autowired
    RoomService roomService;

    @Autowired
    UserService userService;

    private final UrlValidator urlValidator = new UrlValidator() {
        /** allow missing scheme. */
        @Override
        public boolean isValid(String value) {
            return super.isValid(value) || super.isValid("http://" + value);
        }
    };

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(SessionForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        SessionForm form = (SessionForm) target;
        validateTitle(errors, form);
        validateDescription(errors, form);
        validateSlideUrl(errors, form);
        validateVideoUrl(errors, form);
        validateProgram(errors, form);
        validateRoom(errors, form);
        validateSpeakers(errors, form);
    }

    private void validateTitle(Errors errors, SessionForm form) {
        if (form.getTitle() == null || form.getTitle().equals("")) {
            errors.reject("title.empty", "Title is empty");
        } else if (form.getTitle().length() > Session.MAX_LENGTH_TITLE) {
            errors.reject("title.large", "Title is too large");
        }
    }

    private void validateDescription(Errors errors, SessionForm form) {
        if (form.getDescription() == null || form.getDescription().equals("")) {
            errors.reject("description.empty", "Description is empty");
        }
    }

    private void validateSlideUrl(Errors errors, SessionForm form) {
        if (form.getSlideUrl() != null && !form.getSlideUrl().equals("")){
            if(!urlValidator.isValid(form.getSlideUrl())) {
                errors.reject("slideUrl.no_validate", "Slide Url does not validate");
            }
            if(form.getSlideUrl().length() > Session.MAX_LENGTH_SLIDE_URL){
                errors.reject("slideUrl.large", "Slide Url is too large");
            }
        }
    }

    private void validateVideoUrl(Errors errors, SessionForm form) {
        if (form.getVideoUrl() != null && !form.getVideoUrl().equals("")){
            if(!urlValidator.isValid(form.getVideoUrl())) {
                errors.reject("videoUrl.no_validate", "Video Url does not validate");
            }
            if(form.getVideoUrl().length() > Session.MAX_LENGTH_VIDEO_URL){
                errors.reject("videoUrl.large", "Video Url is too large");
            }
        }
    }

    private void validateProgram(Errors errors, SessionForm form) {
        if (form.getProgramId() == null) {
            errors.reject("program.empty", "Program is empty");
        }
        if (form.getProgramId() != null) {
            try {
                programService.findById(form.getProgramId());
            } catch (ProgramNotFoundException e) {
                errors.reject("program.no_validate", "Program does not validate");
            }
        }
    }

    private void validateRoom(Errors errors, SessionForm form) {
        if (form.getRoomId() == null) {
            errors.reject("room.empty", "Room is empty");
        }
        if (form.getRoomId() != null) {
            try {
                roomService.findById(form.getRoomId());
            } catch (RoomNotFoundException e) {
                errors.reject("room.no_validate", "Room does not validate");
            }
        }
    }

    private void validateSpeakers(Errors errors, SessionForm form) {
        if (form.getSpeakers() != null) {

            try {
                for (Long id : form.getSpeakers()) {
                    userService.findById(id);
                }
            } catch (UserNotFoundException e) {
                errors.reject("speaker.no_validate", "Speaker does not validate");
            }
        }
    }
}
