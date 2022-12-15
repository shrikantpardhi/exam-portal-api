package com.dynast.examportal.util;

public enum AnswerStatus {
    TRUE("T"),
    FALSE("F");
    private char value;

    AnswerStatus(String f) {
    }

    public char getValue() {
        return value;
    }
}
