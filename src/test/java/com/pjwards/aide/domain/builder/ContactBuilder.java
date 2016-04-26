package com.pjwards.aide.domain.builder;

import com.pjwards.aide.domain.Contact;
import com.pjwards.aide.domain.enums.ContactType;
import org.springframework.test.util.ReflectionTestUtils;

public class ContactBuilder {

    private Contact model;

    public ContactBuilder() {
        model = new Contact();
    }

    public ContactBuilder id(Long id) {
        ReflectionTestUtils.setField(model, "id", id);
        return this;
    }

    public ContactBuilder type(ContactType type) {
        model.setType(type);
        return this;
    }

    public ContactBuilder url(String url) {
        model.setUrl(url);
        return this;
    }

    public Contact build() {
        return model;
    }
}
