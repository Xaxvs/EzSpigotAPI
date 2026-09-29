package com.github.xaxvs.validation;

public interface ValidationRule {

    <T> boolean isValid(T value);
}
