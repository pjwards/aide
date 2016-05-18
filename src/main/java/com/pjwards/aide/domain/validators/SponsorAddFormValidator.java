package com.pjwards.aide.domain.validators;


import com.pjwards.aide.domain.Sponsor;
import com.pjwards.aide.domain.forms.SponsorAddForm;
import org.apache.commons.validator.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SponsorAddFormValidator implements Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(SponsorAddFormValidator.class);
    private final UrlValidator urlValidator = new UrlValidator() {
        /** allow missing scheme. */
        @Override
        public boolean isValid(String value) {
            return super.isValid(value) || super.isValid("http://" + value);
        }
    };
    private ImageValidator imageValidator;

    @Autowired
    public SponsorAddFormValidator(ImageValidator imageValidator){
        this.imageValidator = imageValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(SponsorAddForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        SponsorAddForm form = (SponsorAddForm) target;
        validateName(errors, form);
        validateSlug(errors, form);
        validateUrl(errors, form);
        validateRank(errors, form);
        validateAssets(errors, form);

    }

    private void validateAssets(Errors errors, SponsorAddForm form) {
        if(form.getAssets() == null || form.getAssets().getOriginalFilename().equals("") || !imageValidator.validate(form.getAssets().getOriginalFilename())){
            errors.reject("files.no_validate", "Files do not validate");
        }
    }

    private void validateRank(Errors errors, SponsorAddForm form) {
        if (form.getRank() < 0 || form.getRank() > 100) {
            errors.reject("rank.wrong", "Can not input the Rank under 0 and above 100");
        }

    }

    private void validateUrl(Errors errors, SponsorAddForm form) {
        if (form.getUrl() != null && !form.getUrl().equals("")
                && !urlValidator.isValid(form.getUrl())) {
            errors.reject("url.no_validate", "Url does not validate");
        } else if (form.getUrl().length() > Sponsor.MAX_LENGTH_URL) {
            errors.reject("url.large", "Url is too large");
        }
    }

    private void validateSlug(Errors errors, SponsorAddForm form) {
        if (form.getSlug() == null || form.getSlug().equals("")) {
            errors.reject("slug.empty", "Slug is empty");
        } else if (form.getSlug().length() > Sponsor.MAX_LENGTH_STRING) {
            errors.reject("slug.large", "Slug is too large");
        }
    }

    private void validateName(Errors errors, SponsorAddForm form) {
        if (form.getName() == null || form.getName().equals("")) {
            errors.reject("name.empty", "Name is empty");
        } else if (form.getName().length() > Sponsor.MAX_LENGTH_STRING) {
            errors.reject("name.large", "Name is too large");
        }
    }
}
