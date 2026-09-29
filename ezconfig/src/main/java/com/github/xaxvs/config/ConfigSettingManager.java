package com.github.xaxvs.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
public class ConfigSettingManager {

    private final FileConfiguration configuration;
    private final List<ConfigValue<Object>> values = new ArrayList<>();

    ConfigSettingManager(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    boolean initializeValues() {
        boolean updated = false;
        for (ConfigValue<Object> setting : values) {
            if (setValue(setting)) updated = true;
        }
        return updated;
    }

    private boolean setValue(ConfigValue<Object> configSetting) {
        if (!configuration.isSet(configSetting.getPath())) {
            configuration.set(configSetting.getPath(), configSetting.getValue());
            return true;
        }
        return false;
    }

    public List<ConfigValue<Object>> getValues() {
        return values.stream().toList();
    }

    public ConfigValue<Object> addValue(String path, Object value) {
        Object val = configuration.get(path, value);
        ConfigValue<Object> configSetting = new ConfigValue<>(path, val);
        values.add(configSetting);
        return configSetting;
    }

}
