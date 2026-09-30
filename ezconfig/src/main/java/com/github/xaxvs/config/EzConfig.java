package com.github.xaxvs.config;

import com.github.xaxvs.file.ConfigFile;
import com.github.xaxvs.validation.ConfigRule;
import com.github.xaxvs.validation.ValidationRule;
import com.github.xaxvs.validation.ValidationType;
import org.bukkit.configuration.file.FileConfiguration;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

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

    public ConfigMessage addMessage(String path, String defaultMessage, @Nullable ValidationRule... rule) {
        ConfigMessage message = configMessageManager.addMessage(path, defaultMessage);
        if (rule.length > 0) {
            configValidator.addRules(ValidationType.STRING, path, rule);
        }
        return message;
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
