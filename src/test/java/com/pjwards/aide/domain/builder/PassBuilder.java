package com.pjwards.aide.domain.builder;

import com.pjwards.aide.domain.Pass;
import org.springframework.test.util.ReflectionTestUtils;

public class PassBuilder {
    private Pass pass;

    public PassBuilder() {
        pass = new Pass();
    }

    public PassBuilder id(Long id) {
        ReflectionTestUtils.setField(pass, "id", id);
        return this;
    }

    public PassBuilder tag(String tag) {
        pass.setTag(tag);
        return this;
    }

    public Pass build() {
        return pass;
    }
}
