package com.dynast.examportal.common;

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
