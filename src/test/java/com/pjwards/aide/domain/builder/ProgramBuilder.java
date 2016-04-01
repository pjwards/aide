package com.pjwards.aide.domain.builder;

import com.pjwards.aide.domain.Program;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

public class ProgramBuilder {

    private Program model;

    public ProgramBuilder() {
        model = new Program();
    }

    public ProgramBuilder id(Long id) {
        ReflectionTestUtils.setField(model, "id", id);
        return this;
    }

    public ProgramBuilder title(String title) {
        model.update(title, model.getDescription(), model.getBegin(), model.getEnd());
        return this;
    }

    public ProgramBuilder description(String description) {
        model.update(model.getTitle(), description, model.getBegin(), model.getEnd());
        return this;
    }

    public ProgramBuilder begin(Date begin) {
        model.update(model.getTitle(), model.getDescription(), begin, model.getEnd());
        return this;
    }

    public ProgramBuilder end(Date end) {
        model.update(model.getTitle(), model.getDescription(), model.getBegin(), end);
        return this;
    }

    public Program build() {
        return model;
    }
}
