package com.github.xaxvs.validation;

import java.util.List;

public interface ValidationRule {

    <T> boolean isValid(T value);

    String getErrReason();

    /**
     * Returns all failures with their paths, or an empty list when valid.
     * Implementations must keep this result consistent with isValid(value).
     */
    default List<String> validate(String path, Object value) {
        if (isValid(value)) {
            return List.of();
        }
        return List.of(path + ": " + getErrReason());
    }
}