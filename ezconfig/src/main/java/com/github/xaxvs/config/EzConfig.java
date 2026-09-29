package com.github.xaxvs.config;

import com.github.xaxvs.file.ConfigFile;
import com.github.xaxvs.validation.ConfigRule;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.util.List;

public class EzConfig {

    private final ConfigFile configFile;
    private final ConfigValidator configValidator;
    private final ConfigMessageManager configMessageManager;
    private final FileConfiguration configuration;

    public EzConfig(@NonNull ConfigFile configFile, ConfigRule... rules) {
        this.configFile = configFile;
        this.configuration = configFile.getConfiguration();
        this.configValidator = new ConfigValidator(configuration, rules);
        this.configMessageManager = new ConfigMessageManager(configuration);

    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public List<ValidationResult> initialize(boolean saveAtomically) throws IOException {
        boolean shouldSave = false;
        if(configMessageManager.initializeMessages()) {
            shouldSave = true;
        }
        List<ValidationResult> results = configValidator.validateConfig();
        if(results.stream().anyMatch(validationResult -> !validationResult.isResultValid())) {
            return results;
        }
        if(shouldSave) {
            configFile.saveConfig(saveAtomically);
        }
        return results;
    }

    public ConfigValidator getConfigValidator() {
        return configValidator;
    }

    public ConfigMessageManager getConfigMessageManager() {
        return configMessageManager;
    }
}
