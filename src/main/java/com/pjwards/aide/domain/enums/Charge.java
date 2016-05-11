package com.pjwards.aide.domain.enums;

public enum Charge {

    FREE("FREE","info"),
    CHARGED("CHARGED","warning");

    private String title;
    private String color;

    Charge(String title, String color) {
        this.title = title;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public String getColor() {
        return color;
    }

    public static boolean contains(String test) {

        for (Charge c : Charge.values()) {
            if (c.toString().equals(test)) {
                return true;
            }
        }

        return false;
    }
}
