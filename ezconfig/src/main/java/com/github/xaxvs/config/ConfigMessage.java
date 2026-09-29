package com.github.xaxvs.config;

import com.github.xaxvs.placeholders.Placeholders;
import org.bukkit.ChatColor;
import org.jspecify.annotations.NullMarked;

import java.util.Map;

@NullMarked
public class ConfigMessage {

    private final String path;
    private final String message;

    ConfigMessage(String path, String message) {
        this.path = path;
        this.message = message;
    }

    public ConfigMessage colorize() {
        return new ConfigMessage(path, ChatColor.translateAlternateColorCodes('&', message));
    }

    public ConfigMessage replace(Map<Placeholders, String> placeholders) {
        var values = Map.copyOf(placeholders);
        String replacedMessage = message;
        for (var entry : values.entrySet()) {
            replacedMessage = replacedMessage.replace(
                    entry.getKey().getPlaceholder(),
                    entry.getValue()
            );
        }
        return new ConfigMessage(path, replacedMessage);
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }

}
