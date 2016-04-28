package com.pjwards.aide.domain.enums;

public enum ContactType {
    EMAIL("Email", "envelope"),
    FACEBOOK("Facebook", "facebook"),
    TWITTER("Twitter", "twitter"),
    GITHUB("Github", "github"),
    GOOGLEPLUS("Google+", "google-plus");

    private String name;
    private String attribute;

    ContactType(String name, String attribute) {
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
