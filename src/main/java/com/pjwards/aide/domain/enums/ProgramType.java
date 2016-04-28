package com.pjwards.aide.domain.enums;

public enum ProgramType {
    SESSION(""),
    KEYNOTE("keynote"),
    REGISTER("data_on"),
    LUNCH("data_on"),
    BOF("data_on");

    private String attribute;

    ProgramType(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }
}
