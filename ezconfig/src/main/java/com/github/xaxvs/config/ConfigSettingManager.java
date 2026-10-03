package com.github.xaxvs.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
public class ConfigSettingManager {

    private final FileConfiguration configuration;

    private final List<ConfigValue<?>> values = new ArrayList<>();

    ConfigSettingManager(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    boolean initializeValues() {
        boolean updated = false;
        for (ConfigValue<?> setting : values) {
            if (setValue(setting)) updated = true;
        }
        return updated;
    }

    private boolean setValue(ConfigValue<?> configSetting) {
        if (!configuration.isSet(configSetting.getPath())) {
            configuration.set(configSetting.getPath(), configSetting.getValue());
            return true;
        }
        return false;
    }

    public List<ConfigValue<?>> getValues() {
        return values.stream().toList();
    }

    ConfigValue<String> addString(String path, String value) {
        String val = configuration.getString(path, value);
        ConfigValue<String> configValue = new ConfigValue<>(path, val);
        values.add(configValue);
        return configValue;
    }

    ConfigValue<Integer> addInt(String path, int value) {
        int val = configuration.getInt(path, value);
        ConfigValue<Integer> configValue = new ConfigValue<>(path, val);
        values.add(configValue);
        return configValue;
    }

    ConfigValue<Long> addLong(String path, long value) {
        long val = configuration.getLong(path, value);
        ConfigValue<Long> configValue = new ConfigValue<>(path, val);
        values.add(configValue);
        return configValue;
    }

    ConfigValue<Double> addDouble(String path, double value) {
        double val = configuration.getDouble(path, value);
        ConfigValue<Double> configValue = new ConfigValue<>(path, val);
        values.add(configValue);
        return configValue;
    }

    ConfigValue<Boolean> addBoolean(String path, boolean value) {
        boolean val = configuration.getBoolean(path, value);
        ConfigValue<Boolean> configValue = new ConfigValue<>(path, val);
        values.add(configValue);
        return configValue;
    }

    ConfigValue<List<?>> addList(String path, List<?> value) {
        List<?> val = configuration.getList(path, value);
        ConfigValue<List<?>> configValue = new ConfigValue<>(path, val);
        values.add(configValue);
        return configValue;
    }

}
