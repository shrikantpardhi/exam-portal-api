package com.dynast.examportal.util;

public enum Roles {
    USER("User", "for user"),
    EDUCATOR("Educator", "for educator"),
    ADMIN("Admin", "for admin");

    private final String label;
    private final String description;

    private Roles(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }
}
