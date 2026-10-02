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

    public ConfigValue<Object> addValue(String path, Object value) {
        Object val = configuration.get(path, value);
        ConfigValue<Object> configSetting = new ConfigValue<>(path, val);
        values.add(configSetting);
        return configSetting;
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

    public ConfigValue<List<String>> addStringList(String path, List<String> value) {
        List<String> val = configuration.isList(path)
                ? configuration.getStringList(path)
                : value;
        ConfigValue<List<String>> configValue = new ConfigValue<>(path, val);
        values.add(configValue);
        return configValue;
    }

    public ConfigValue<List<Integer>> addIntList(String path, List<Integer> value) {
        List<Integer> val = configuration.isList(path)
                ? configuration.getIntegerList(path)
                : value;
        ConfigValue<List<Integer>> configValue = new ConfigValue<>(path, val);
        values.add(configValue);
        return configValue;
    }

    public ConfigValue<List<Long>> addLongList(String path, List<Long> value) {
        List<Long> val = configuration.isList(path)
                ? configuration.getLongList(path)
                : value;
        ConfigValue<List<Long>> configValue = new ConfigValue<>(path, val);
        values.add(configValue);
        return configValue;
    }

    public ConfigValue<List<Double>> addDoubleList(String path, List<Double> value) {
        List<Double> val = configuration.isList(path)
                ? configuration.getDoubleList(path)
                : value;
        ConfigValue<List<Double>> configValue = new ConfigValue<>(path, val);
        values.add(configValue);
        return configValue;
    }

    public ConfigValue<List<Boolean>> addBooleanList(String path, List<Boolean> value) {
        List<Boolean> val = configuration.isList(path)
                ? configuration.getBooleanList(path)
                : value;
        ConfigValue<List<Boolean>> configValue = new ConfigValue<>(path, val);
        values.add(configValue);
        return configValue;
    }

}
