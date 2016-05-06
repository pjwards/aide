package com.pjwards.aide.domain.enums;

public enum Status {
    OPEN("OPEN", "success"),
    CLOSED("CLOSED", "default"),
    PROGRESS("PROGRESS", "danger");

    private String name;
    private String attribute;

    Status(String name, String attribute) {
        this.name = name;
        this.attribute = attribute;
    }

    public String getName() {
        return name;
    }

    public String getAttribute() {
        return attribute;
    }
}
