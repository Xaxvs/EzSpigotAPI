package com.github.xaxvs.config;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class ValidationResult {

    private boolean pathIsSet;
    private boolean isTypeValid;
    private boolean isRuleValid;

    ValidationResult() {
    }

    void setPathIsSet(boolean pathIsSet) {
        this.pathIsSet = pathIsSet;
    }

    public boolean isPathSet() {
        return pathIsSet;
    }

    public boolean isRuleValid() {
        return isRuleValid;
    }

    void setRuleValid(boolean ruleValid) {
        this.isRuleValid = ruleValid;
    }

    public boolean isTypeValid() {
        return isTypeValid;
    }

    void setTypeValid(boolean typeValid) {
        this.isTypeValid = typeValid;
    }
}
