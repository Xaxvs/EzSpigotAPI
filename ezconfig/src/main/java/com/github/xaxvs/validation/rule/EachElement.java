package com.github.xaxvs.validation.rule;

import com.github.xaxvs.validation.ValidationRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EachElement implements ValidationRule {

    private final ValidationRule elementRule;

    public EachElement(ValidationRule elementRule) {
        this.elementRule = elementRule;
    }

    @Override
    public <T> boolean isValid(T value) {
        if (!(value instanceof List<?> list)) {
            return false;
        }

        boolean valid = true;
        for (Object element : list) {
            if (!elementRule.isValid(element)) {
                valid = false;
            }
        }
        return valid;
    }

    @Override
    public List<String> validate(String path, Object value) {
        if (!(value instanceof List<?> list)) {
            return List.of(path + ": Expected a list.");
        }

        List<String> errors = new ArrayList<>();
        int index = 0;

        for (Object element : list) {
            errors.addAll(elementRule.validate(path + "[" + index + "]", element));
            index++;
        }

        return List.copyOf(errors);
    }

    @Override
    public String getErrReason() {
        return "Expected a list where every element satisfies: "
                + elementRule.getErrReason();
    }
}