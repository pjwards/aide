package com.pjwards.aide.domain.builder;

import com.pjwards.aide.domain.ProgramDate;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.ParseException;

public class ProgramDateBuilder {

    private ProgramDate model;

    public ProgramDateBuilder() {
        model = new ProgramDate();
    }

    public ProgramDateBuilder id(Long id) {
        ReflectionTestUtils.setField(model, "id", id);
        return this;
    }

    public ProgramDateBuilder day(String day) throws ParseException {
        model.setFormattedDay(day);
        return this;
    }

    public ProgramDate build() {
        return model;
    }
}
