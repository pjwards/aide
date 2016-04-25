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
        model.setTitle(title);
        return this;
    }

    public ProgramBuilder description(String description) {
        model.setDescription(description);
        return this;
    }

    public ProgramBuilder begin(String begin) {
        model.setBegin(begin);
        return this;
    }

    public ProgramBuilder end(String end) {
        model.setEnd(end);
        return this;
    }

    public Program build() {
        return model;
    }
}
