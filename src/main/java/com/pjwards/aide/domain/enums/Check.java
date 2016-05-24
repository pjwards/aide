package com.pjwards.aide.domain.enums;

public enum Check {
    PRESENCE("PRESENCE", "info"),
    ABSENCE("ABSENCE", "warning");

    private String title;
    private String color;

    Check(String title, String color) {
        this.title = title;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }
}
