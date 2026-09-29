package com.github.xaxvs.config;

import com.github.xaxvs.validation.Result;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class ValidationResult {

    private final String path;
    private Result pathIsSet = Result.VALID;
    private Result isTypeValid = Result.VALID;
    private Result isRuleValid = Result.VALID;

    ValidationResult(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    void pathNotSet() {
        pathIsSet = Result.INVALID;
        isTypeValid = Result.SKIPPED;
        isRuleValid = Result.SKIPPED;
    }

    public boolean isResultValid() {
        return pathIsSet == Result.VALID
                && isRuleValid == Result.VALID
                && isTypeValid == Result.VALID;
    }

    public Result isPathSet() {
        return pathIsSet;
    }

    public Result isRuleValid() {
        return isRuleValid;
    }

    void ruleNotValid() {
        isRuleValid = Result.INVALID;
    }

    public Result isTypeValid() {
        return isTypeValid;
    }

    void typeNotValid() {
        isTypeValid = Result.INVALID;
        isRuleValid = Result.SKIPPED;
    }

}
