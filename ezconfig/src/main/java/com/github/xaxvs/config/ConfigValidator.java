package com.github.xaxvs.config;

import com.github.xaxvs.validation.ConfigRule;
import com.github.xaxvs.validation.ValidationRule;
import com.github.xaxvs.validation.ValidationType;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigValidator {

    private final FileConfiguration configuration;
    private final List<ConfigRule> rules;

    ConfigValidator(FileConfiguration configuration, ConfigRule... rules) {
        this.configuration = configuration;
        this.rules = new ArrayList<>(Arrays.stream(rules).toList());
    }

    public List<ValidationResult> validateConfig() {
        List<ValidationResult> results = new ArrayList<>();
        for (ConfigRule rule : rules) {
            String path = rule.path();
            ValidationResult result = new ValidationResult(path);
            results.add(result);
            if(!configuration.isSet(path)) {
                result.pathNotSet();
                continue;
            }
            if(!checkType(path, rule.type())) {
                result.typeNotValid();
                continue;
            }
            if(!rule.validation().isValid(configuration.get(path))) {
                result.ruleNotValid();
            }
        }
        return results.stream().toList();
    }

    public ConfigRule addRule(ValidationType type, String path, ValidationRule rule) {
        ConfigRule configRule = new ConfigRule(type, path, rule);
        rules.add(configRule);
        return configRule;
    }
    //                                             interface ValidationRule
    //                                             Bound implements interface
    // ConfigRule addRule(EntryType.LONG, "some.path", new Bound(250000, 529999));

    private boolean checkType(String path, ValidationType type) {
        return switch (type) {
            case STRING -> configuration.isString(path);
            case LONG -> configuration.isLong(path);
            case INT -> configuration.isInt(path);
            case DOUBLE -> configuration.isDouble(path);
            case BOOLEAN -> configuration.isBoolean(path);
            case LIST -> configuration.isList(path);
        };
    }

}
