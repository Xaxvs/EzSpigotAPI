package com.github.xaxvs.config;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class ValidationResult {

    private boolean pathIsSet = true;
    private boolean isTypeValid = true;
    private boolean isRuleValid = true;
    private final String path;

    ValidationResult(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    void pathNotSet() {
        pathIsSet = false;
    }

    public boolean isResultValid() {
        return pathIsSet && isRuleValid && isTypeValid;
    }

    public boolean isPathSet() {
        return pathIsSet;
    }

    public boolean isRuleValid() {
        return isRuleValid;
    }

    void ruleNotValid() {
        isRuleValid = false;
    }

    public boolean isTypeValid() {
        return isTypeValid;
    }

    void typeNotValid() {
        isTypeValid = false;
    }

}
