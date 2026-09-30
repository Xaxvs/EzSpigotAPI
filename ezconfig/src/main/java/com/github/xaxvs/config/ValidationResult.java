package com.github.xaxvs.config;

import com.github.xaxvs.validation.Result;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
public class ValidationResult {

    private final String path;
    private Result pathResult = Result.VALID;
    private Result validTypeResult = Result.VALID;
    private Result validRuleResult = Result.VALID;
    private List<String> errs = new ArrayList<>();

    ValidationResult(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    void addError(String err) {
        errs.add(err);
    }

    void pathNotSet() {
        pathResult = Result.INVALID;
        validTypeResult = Result.SKIPPED;
        validRuleResult = Result.SKIPPED;
    }

    public List<String> getErrs() {
        return errs.stream().toList();
    }

    public boolean isResultValid() {
        return pathResult == Result.VALID
                && validRuleResult == Result.VALID
                && validTypeResult == Result.VALID;
    }

    public Result getPathResult() {
        return pathResult;
    }

    public Result getRuleResult() {
        return validRuleResult;
    }

    void ruleNotValid() {
        validRuleResult = Result.INVALID;
    }

    public Result getTypeResult() {
        return validTypeResult;
    }

    void typeNotValid() {
        validTypeResult = Result.INVALID;
        validRuleResult = Result.SKIPPED;
    }

}
