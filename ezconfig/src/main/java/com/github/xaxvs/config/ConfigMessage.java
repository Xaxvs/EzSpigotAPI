package com.github.xaxvs.config;

import com.github.xaxvs.placeholders.Placeholders;
import org.bukkit.ChatColor;
import org.jspecify.annotations.NullMarked;

import java.util.Map;

@NullMarked
public class ConfigMessage extends ConfigValue<String> {

    ConfigMessage(String path, String message) {
        super(path, message);
    }

    public ConfigMessage colorize() {
        return new ConfigMessage(path, ChatColor.translateAlternateColorCodes('&', value));
    }

    public ConfigMessage replace(Map<Placeholders, String> placeholders) {
        var values = Map.copyOf(placeholders);
        String replacedMessage = value;
        for (var entry : values.entrySet()) {
            replacedMessage = replacedMessage.replace(
                    entry.getKey().getPlaceholder(),
                    entry.getValue()
            );
        }
        return new ConfigMessage(path, replacedMessage);
    }

}
