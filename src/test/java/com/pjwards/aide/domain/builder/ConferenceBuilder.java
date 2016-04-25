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
        model.setName(name);
        return this;
    }

    public ConferenceBuilder slogan(String slogan) {
        model.setSlogan(slogan);
        return this;
    }

    public ConferenceBuilder description(String description) {
        model.setDescription(description);
        return this;
    }

    public ConferenceBuilder location(String location, String locationUrl) {
        model.setLocation(location);
        model.setLocationUrl(locationUrl);
        return this;
    }

    public Conference build() {
        return model;
    }
}
