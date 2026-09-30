package com.github.xaxvs.config;

import com.github.xaxvs.file.ConfigFile;
import com.github.xaxvs.validation.ConfigRule;
import com.github.xaxvs.validation.ValidationRule;
import com.github.xaxvs.validation.ValidationType;
import org.bukkit.configuration.file.FileConfiguration;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;
import java.util.List;

@NullMarked
public class EzConfig {

    private final ConfigFile configFile;
    private final ConfigValidator configValidator;
    private final ConfigMessageManager configMessageManager;
    private final ConfigSettingManager configSettingManager;
    private final FileConfiguration configuration;
    private boolean shouldSave = false;

    public EzConfig(ConfigFile configFile, ConfigRule... rules) {
        this.configFile = configFile;
        this.configuration = configFile.getConfiguration();
        this.configValidator = new ConfigValidator(configuration, rules);
        this.configMessageManager = new ConfigMessageManager(configuration);
        this.configSettingManager = new ConfigSettingManager(configuration);
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public List<ValidationResult> initialize(boolean saveAtomically) throws IOException {
        boolean setting = configSettingManager.initializeValues();
        boolean message = configMessageManager.initializeMessages();
        if (setting || message) {
            shouldSave = true;
        }
        List<ValidationResult> results = configValidator.validateConfig();
        if (results.stream().anyMatch(validationResult -> !validationResult.isResultValid())) {
            return results;
        }
        if (shouldSave) {
            configFile.saveConfig(saveAtomically);
            shouldSave = false;
        }
        return results;
    }

    public ConfigMessage addMessage(String path, String defaultMessage, ValidationRule... rule) {
        ConfigMessage message = configMessageManager.addMessage(path, defaultMessage);
        configValidator.addRule(ValidationType.STRING, path, rule);
        return message;
    }

    public ConfigValue<String> addString(String path, String defaultValue, ValidationRule... rules) {
        ConfigValue<String> value = configSettingManager.addString(path, defaultValue);
        configValidator.addRule(ValidationType.STRING, path, rules);
        return value;
    }

    public ConfigValue<Integer> addInt(String path, int defaultValue, ValidationRule... rules) {
        ConfigValue<Integer> value = configSettingManager.addInt(path, defaultValue);
        configValidator.addRule(ValidationType.INT, path, rules);
        return value;
    }

    public ConfigValue<Long> addLong(String path, long defaultValue, ValidationRule... rules) {
        ConfigValue<Long> value = configSettingManager.addLong(path, defaultValue);
        configValidator.addRule(ValidationType.LONG, path, rules);
        return value;
    }

    public ConfigValue<Double> addDouble(String path, double defaultValue, ValidationRule... rules) {
        ConfigValue<Double> value = configSettingManager.addDouble(path, defaultValue);
        configValidator.addRule(ValidationType.DOUBLE, path, rules);
        return value;
    }

    public ConfigValue<Boolean> addBoolean(String path, boolean defaultValue, ValidationRule... rules) {
        ConfigValue<Boolean> value = configSettingManager.addBoolean(path, defaultValue);
        configValidator.addRule(ValidationType.BOOLEAN, path, rules);
        return value;
    }

    public ConfigValue<List<?>> addList(String path, List<?> defaultValue, ValidationRule... rules) {
        ConfigValue<List<?>> value = configSettingManager.addList(path, defaultValue);
        configValidator.addRule(ValidationType.LIST, path, rules);
        return value;
    }

    public ConfigSettingManager getConfigSettingManager() {
        return configSettingManager;
    }

    public ConfigValidator getConfigValidator() {
        return configValidator;
    }

    public ConfigMessageManager getConfigMessageManager() {
        return configMessageManager;
    }

}
