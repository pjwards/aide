package com.pjwards.aide.domain.enums;

public enum Role {
    PARTICIPANT("PARTICIPANT", "info"),
    HOST("HOST", "warning"),
    MANAGER("MANAGER", "primary"),
    SPEAKER("SPEAKER", "success"),
    ADMIN("ADMIN", "primary"),
    USER("USER", "info"),
    DUMMY("DUMMY", "info");

    private String title;
    private String color;

    Role(String title, String color) {
        this.title = title;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public String getColor() {
        return color;
    }
}
