package com.github.xaxvs.validation;

public record ConfigRule(ValidationType type, String path, ValidationRule... validation) {

}
