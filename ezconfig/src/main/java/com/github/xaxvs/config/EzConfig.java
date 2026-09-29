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

    public EzConfig(@NonNull ConfigFile configFile, ConfigRule... rules) throws IOException, InvalidConfigurationException {
        this.configFile = configFile;
        this.configuration = configFile.toConfiguration();
        this.configValidator = new ConfigValidator(configuration, rules);
        this.configMessageManager = new ConfigMessageManager(configuration);

    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public List<ValidationResult> initialize(boolean saveAtomically) throws IOException {
        if(configMessageManager.initializeMessages()) {
            configFile.saveConfig(saveAtomically);
        }
        return configValidator.validateConfig();
    }

    public ConfigValidator getConfigValidator() {
        return configValidator;
    }

    public ConfigMessageManager getConfigMessageManager() {
        return configMessageManager;
    }
}
