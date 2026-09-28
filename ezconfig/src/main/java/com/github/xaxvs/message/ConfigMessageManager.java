package com.github.xaxvs.message;

import org.bukkit.configuration.file.FileConfiguration;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ConfigMessageManager {

    private final FileConfiguration configuration;
    private final List<ConfigMessage> messages = new ArrayList<>();

    public ConfigMessageManager(@NonNull FileConfiguration configuration) {
        this.configuration = configuration;
    }

    public List<ConfigMessage> getMessages() {
        return messages.stream().toList();
    }

    public ConfigMessage addMessage(String path, String defaultMessage) {
        if(!configuration.isSet(path)) {
            configuration.set(path, defaultMessage);
            return new ConfigMessage(path, defaultMessage);
        }
        String message = defaultMessage;
        if(configuration.isString(path)) message = configuration.getString(path);
        ConfigMessage configMessage = new ConfigMessage(path, message);
        messages.add(configMessage);
        return configMessage;
    }

    public String getMessage(ConfigMessage message) {
        return message.colorize().getMessage();
    }

}
