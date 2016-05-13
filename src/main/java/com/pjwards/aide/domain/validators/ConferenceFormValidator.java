package com.pjwards.aide.domain.validators;

import com.pjwards.aide.domain.Conference;
import com.pjwards.aide.domain.Contact;
import com.pjwards.aide.domain.forms.ConferenceForm;
import com.pjwards.aide.service.conference.ConferenceService;
import org.apache.commons.validator.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ConferenceFormValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceFormValidator.class);
    private final EmailValidator emailValidator;
    private final UrlValidator urlValidator = new UrlValidator() {
        /** allow missing scheme. */
        @Override
        public boolean isValid(String value) {
            return super.isValid(value) || super.isValid("http://" + value);
        }
    };
    private ImageValidator imageValidator;

    @Autowired
    public ConferenceFormValidator(EmailValidator emailValidator,
                                   ImageValidator imageValidator) {
        this.emailValidator = emailValidator;
        this.imageValidator = imageValidator;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(ConferenceForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        ConferenceForm form = (ConferenceForm) target;
        validateName(errors, form);
        validateSlogan(errors, form);
        validateDescription(errors, form);
        validateLocation(errors, form);
        validateLocationUrl(errors, form);
        validateEmailAddress(errors, form);
        validateFacebookUrl(errors, form);
        validateTwitterUrl(errors, form);
        validateGithubUrl(errors, form);
        validateGooglePlusUrl(errors, form);
        validatePrice(errors, form);
        validateFiles(errors, form);
        validateDisqus(errors, form);
    }

    private void validateName(Errors errors, ConferenceForm form) {
        if (form.getName() == null || form.getName().equals("")) {
            errors.reject("name.empty", "Name is empty");
        } else if (form.getName().length() > Conference.MAX_LENGTH_NAME) {
            errors.reject("name.large", "Name is too large");
        }
    }

    private void validateSlogan(Errors errors, ConferenceForm form) {
        if (form.getSlogan() == null || form.getSlogan().equals("")) {
            errors.reject("slogan.empty", "Slogan is empty");
        } else if (form.getSlogan().length() > Conference.MAX_LENGTH_SLOGAN) {
            errors.reject("slogan.large", "Slogan is too large");
        }
    }

    private void validateDescription(Errors errors, ConferenceForm form) {
        if (form.getDescription() == null || form.getDescription().equals("")) {
            errors.reject("description.empty", "Description is empty");
        }
    }

    private void validateLocation(Errors errors, ConferenceForm form) {
        if (form.getLocation() != null && !form.getLocation().equals("")
                && form.getLocation().length() > Conference.MAX_LENGTH_LOCATION) {
            errors.reject("location.large", "Location is too large");
        }
    }

    private void validateLocationUrl(Errors errors, ConferenceForm form) {
        if (form.getLocationUrl() != null && !form.getLocationUrl().equals("")
                && !urlValidator.isValid(form.getLocationUrl())) {
            errors.reject("locationUrl.no_validate", "Location Url does not validate");
        } else if (form.getLocationUrl().length() > Conference.MAX_LENGTH_LOCATION_URL) {
            errors.reject("locationUrl.large", "Location Url is too large");
        }
    }

    private void validateEmailAddress(Errors errors, ConferenceForm form) {
        if (form.getEmail() != null && !form.getEmail().equals("")
                && !emailValidator.validate(form.getEmail())) {
            errors.reject("email.no_validate", "Email does not validate");
        } else if (form.getEmail().length() > Contact.MAX_LENGTH_URL) {
            errors.reject("email.large", "Email is too large");
        }
    }

    private void validateFacebookUrl(Errors errors, ConferenceForm form) {
        if (form.getFacebook() != null && !form.getFacebook().equals("")
                && (!urlValidator.isValid(form.getFacebook()) || !form.getFacebook().contains("facebook"))) {
            errors.reject("facebook.no_validate", "Facebook does not validate");
        } else if (form.getFacebook().length() > Contact.MAX_LENGTH_URL) {
            errors.reject("facebook.large", "Facebook is too large");
        }
    }

    private void validateTwitterUrl(Errors errors, ConferenceForm form) {
        if (form.getTwitter() != null && !form.getTwitter().equals("")
                && (!urlValidator.isValid(form.getTwitter()) || !form.getTwitter().contains("twitter"))) {
            errors.reject("twitter.no_validate", "Twitter does not validate");
        } else if (form.getTwitter().length() > Contact.MAX_LENGTH_URL) {
            errors.reject("twitter.large", "Twitter is too large");
        }
    }

    private void validateGithubUrl(Errors errors, ConferenceForm form) {
        if (form.getGithub() != null && !form.getGithub().equals("")
                && (!urlValidator.isValid(form.getGithub()) || !form.getGithub().contains("github"))) {
            errors.reject("github.no_validate", "Github does not validate");
        } else if (form.getGithub().length() > Contact.MAX_LENGTH_URL) {
            errors.reject("github.large", "Github is too large");
        }
    }

    private void validateGooglePlusUrl(Errors errors, ConferenceForm form) {
        if (form.getGooglePlus() != null && !form.getGooglePlus().equals("")
                && (!urlValidator.isValid(form.getGooglePlus()) || !form.getGooglePlus().contains("google"))) {
            errors.reject("google.no_validate", "Google Plus does not validate");
        } else if (form.getGooglePlus().length() > Contact.MAX_LENGTH_URL) {
            errors.reject("google.large", "Google Plus is too large");
        }
    }

    private void validatePrice(Errors errors, ConferenceForm form) {
        if (form.getPrice() < 0) {
            errors.reject("price.wrong", "Can not input the price under 0");
        }
    }

    private void validateFiles(Errors errors, ConferenceForm form) {
        form.getFiles().stream().filter(file -> !imageValidator.validate(file.getOriginalFilename())).forEach(file -> {
            if(!file.getOriginalFilename().equals("")) {
                errors.reject("files.no_validate", "Files do not validate: " + file.getOriginalFilename());
            }
        });
    }

    private void validateDisqus(Errors errors, ConferenceForm form) {
        if (form.getDisqus().length() > Conference.MAX_LENGTH_DISQUS) {
            errors.reject("disqus.large", "Disqus is too large");
        }
    }
}
