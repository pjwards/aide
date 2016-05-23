package com.pjwards.aide.domain.validators;

import com.pjwards.aide.domain.Program;
import com.pjwards.aide.domain.forms.ProgramForm;
import com.pjwards.aide.exception.ProgramDateNotFoundException;
import com.pjwards.aide.exception.RoomNotFoundException;
import com.pjwards.aide.exception.UserNotFoundException;
import com.pjwards.aide.service.programdate.ProgramDateService;
import com.pjwards.aide.service.room.RoomService;
import com.pjwards.aide.service.user.UserService;
import org.apache.commons.validator.UrlValidator;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProgramFormValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramFormValidator.class);

    @Autowired
    ProgramDateService programDateService;

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
        return clazz.equals(ProgramForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        ProgramForm form = (ProgramForm) target;
        validateTitle(errors, form);
        validateDescription(errors, form);
        validateBegin(errors, form);
        validateEnd(errors, form);
        validateBeginEnd(errors, form);
        validateSlideUrl(errors, form);
        validateVideoUrl(errors, form);
        validateProgramDate(errors, form);
        validateRoom(errors, form);
        validateSpeakers(errors, form);
    }

    private void validateTitle(Errors errors, ProgramForm form) {
        if (form.getTitle() == null || form.getTitle().equals("")) {
            errors.reject("title.empty", "Title is empty");
        } else if (form.getTitle().length() > Program.MAX_LENGTH_TITLE) {
            errors.reject("title.large", "Title is too large");
        }
    }

    private void validateDescription(Errors errors, ProgramForm form) {
        if (form.getDescription() == null || form.getDescription().equals("")) {
            errors.reject("description.empty", "Description is empty");
        }
    }

    private void validateBegin(Errors errors, ProgramForm form) {
        if (form.getBegin() == null || form.getBegin().equals("")) {
            errors.reject("begin.empty", "Begin is empty");
        }
        try {
            new LocalTime(form.getBegin());
        } catch (Exception e) {
            errors.reject("begin.no_validate", "Begin does not validate");
        }
    }

    private void validateEnd(Errors errors, ProgramForm form) {
        if (form.getEnd() == null || form.getEnd().equals("")) {
            errors.reject("end.empty", "End is empty");
        }
        try {
            new LocalTime(form.getEnd());
        } catch (Exception e) {
            errors.reject("end.no_validate", "End does not validate");
        }
    }

    private void validateBeginEnd(Errors errors, ProgramForm form) {
        if (form.getBegin() == null || form.getBegin().equals("")) {
            return;
        }
        if (form.getEnd() == null || form.getEnd().equals("")) {
            return;
        }

        try {
            LocalTime begin = new LocalTime(form.getBegin());
            LocalTime end = new LocalTime(form.getEnd());
            if (begin.compareTo(end) > 0) {
                errors.reject("time.no_validate", "End is earlier than Begin");
            }
        } catch (Exception ignored) {
        }
    }

    private void validateSlideUrl(Errors errors, ProgramForm form) {
        if (form.getSlideUrl() != null && !form.getSlideUrl().equals("")){
            if(!urlValidator.isValid(form.getSlideUrl())) {
                errors.reject("slideUrl.no_validate", "Slide Url does not validate");
            }
            if(form.getSlideUrl().length() > Program.MAX_LENGTH_SLIDE_URL){
                errors.reject("slideUrl.large", "Slide Url is too large");
            }
        }
    }

    private void validateVideoUrl(Errors errors, ProgramForm form) {
        if (form.getVideoUrl() != null && !form.getVideoUrl().equals("")){
            if(!urlValidator.isValid(form.getVideoUrl())) {
                errors.reject("videoUrl.no_validate", "Video Url does not validate");
            }
            if(form.getVideoUrl().length() > Program.MAX_LENGTH_VIDEO_URL){
                errors.reject("videoUrl.large", "Video Url is too large");
            }
        }
    }

    private void validateProgramDate(Errors errors, ProgramForm form) {
        if (form.getProgramDateId() == null) {
            errors.reject("day.empty", "Day is empty");
        }
        if (form.getProgramDateId() != null) {
            try {
                programDateService.findById(form.getProgramDateId());
            } catch (ProgramDateNotFoundException e) {
                errors.reject("day.no_validate", "Day does not validate");
            }
        }
    }

    private void validateRoom(Errors errors, ProgramForm form) {
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

    private void validateSpeakers(Errors errors, ProgramForm form) {
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
