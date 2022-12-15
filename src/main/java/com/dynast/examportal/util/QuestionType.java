package com.dynast.examportal.util;

public enum QuestionType {

    SINGLE_ANSWER("S", "Single"),
    MULTIPLE("M", "Multiple");

    private final String label;
    private final String description;

    QuestionType(String label, String description) {
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
