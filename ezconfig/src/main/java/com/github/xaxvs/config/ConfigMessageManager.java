package com.github.xaxvs.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ConfigMessageManager {

    private final FileConfiguration configuration;
    private final List<ConfigMessage> messages = new ArrayList<>();

    ConfigMessageManager(@NonNull FileConfiguration configuration) {
        this.configuration = configuration;
    }

    boolean initializeMessages() {
        boolean updated = false;
        for (ConfigMessage message : messages) {
            if (setMessage(message)) updated = true;
        }
        return updated;
    }

    private boolean setMessage(ConfigMessage configMessage) {
        if (!configuration.isSet(configMessage.getPath())) {
            configuration.set(configMessage.getPath(), configMessage.getValue());
            return true;
        }
        return false;
    }

    public List<ConfigMessage> getMessages() {
        return messages.stream().toList();
    }

    ConfigMessage addMessage(@NonNull String path, @NonNull String message) {
        String msg = configuration.getString(path, message);
        ConfigMessage configMessage = new ConfigMessage(path, msg);
        messages.add(configMessage);
        return configMessage;
    }

}
