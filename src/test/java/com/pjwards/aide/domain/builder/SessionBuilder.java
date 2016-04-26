package com.pjwards.aide.domain.builder;

import com.pjwards.aide.domain.Session;
import org.springframework.test.util.ReflectionTestUtils;

public class SessionBuilder {

    private Session model;

    public SessionBuilder() {
        model = new Session();
    }

    public SessionBuilder id(Long id) {
        ReflectionTestUtils.setField(model, "id", id);
        return this;
    }

    public SessionBuilder title(String title) {
        model.setTitle(title);
        return this;
    }

    public SessionBuilder description(String description) {
        model.setDescription(description);
        return this;
    }

    public Session build() {
        return model;
    }
}
