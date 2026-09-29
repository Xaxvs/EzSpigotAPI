package com.github.xaxvs.config;

import com.github.xaxvs.file.ConfigFile;
import com.github.xaxvs.validation.ConfigRule;
import org.bukkit.configuration.InvalidConfigurationException;
import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.util.List;

public class EzConfig {

    private final ConfigFile configFile;
    private final ConfigValidator configValidator;
    private final ConfigMessageManager configMessageManager;

    public EzConfig(@NonNull ConfigFile configFile, ConfigRule... rules) throws IOException, InvalidConfigurationException {
        this.configFile = configFile;
        this.configValidator = new ConfigValidator(configFile.toConfiguration(), rules);
        this.configMessageManager = new ConfigMessageManager(configFile.toConfiguration());

    }

    public List<ValidationResult> initialize() throws IOException {
        List<ValidationResult> results = configValidator.validateConfig();
        if(!results.isEmpty()) return results;
        if(configMessageManager.initializeMessages()) {
            configFile.saveConfig(false);
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
