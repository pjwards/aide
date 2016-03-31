package com.pjwards.aide.domain.builder;

import com.pjwards.aide.domain.Conference;
import org.springframework.test.util.ReflectionTestUtils;

public class ConferenceBuilder {

    private Conference model;

    public ConferenceBuilder() {
        model = new Conference();
    }

    public ConferenceBuilder id(Long id) {
        ReflectionTestUtils.setField(model, "id", id);
        return this;
    }

    public ConferenceBuilder name(String name) {
        model.update(name, model.getDescription());
        return this;
    }

    public ConferenceBuilder description(String description) {
        model.update(model.getName(), description);
        return this;
    }

    public Conference build() {
        return model;
    }
}
