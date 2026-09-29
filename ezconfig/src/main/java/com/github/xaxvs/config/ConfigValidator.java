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
            ValidationResult result = new ValidationResult();
            result.setPathIsSet(configuration.isSet(rule.path()));
            result.setRuleValid(rule.validation().isValid(configuration.get(rule.path())));
            result.setTypeValid(checkType(rule.path(), rule.type()));
            results.add(result);
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
