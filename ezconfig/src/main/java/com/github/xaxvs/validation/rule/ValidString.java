package com.github.xaxvs.validation.rule;

import com.github.xaxvs.validation.ValidationRule;

public class ValidString implements ValidationRule {

    private final int maxLength;

    public ValidString() {
        this.maxLength = 4096;
    }

    public ValidString(int maxLength) {
        if(maxLength < 1) throw new IllegalArgumentException("Max length cannot be less than 1");
        this.maxLength = maxLength;
    }

    @Override
    public <T> boolean isValid(T value) {
        if (!(value instanceof String str)) return false;

        if (str.isBlank()) return false;

        return str.length() <= maxLength;
    }

    @Override
    public String getErrReason() {
        return "String must not be blank and contain at most " + maxLength + " characters.";
    }
}
