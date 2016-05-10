package com.pjwards.aide.domain.enums;

public enum Status {
    OPEN("OPEN", "success"),
    CLOSED("CLOSED", "default"),
    PROGRESS("PROGRESS", "danger");

    private String title;
    private String attribute;

    Status(String title, String attribute) {
        this.title = title;
        this.attribute = attribute;
    }

    public String getTitle() {
        return title;
    }

    public String getAttribute() {
        return attribute;
    }

    public static boolean contains(String test) {

        for (Status c : Status.values()) {
            if (c.toString().equals(test)) {
                return true;
            }
        }

        return false;
    }
}
