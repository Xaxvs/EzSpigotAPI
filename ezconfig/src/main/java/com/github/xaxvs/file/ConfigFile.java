package com.github.xaxvs.file;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@NullMarked
public class ConfigFile {

    private final Path file;
    private final YamlConfiguration configuration;

    public ConfigFile(Path folder, String fileName) throws IOException, InvalidConfigurationException {
        file = folder.resolve(fileName);
        resolveDirs();
        resolveFile();
        configuration = new YamlConfiguration();
        configuration.load(file.toFile());
    }

    public YamlConfiguration getConfiguration() {
        return configuration;
    }

    private void resolveFile() throws IOException {
        if (!Files.exists(file)) {
            Files.createFile(file);
        }
    }

    public void deleteFile() throws IOException {
        Files.deleteIfExists(file);
    }

    public void saveConfig(boolean atomically) throws IOException {
        if (atomically) AtomicFileSaver.save(file, configuration.saveToString());
        else configuration.save(file.toFile());
    }

    private void resolveDirs() throws IOException {
        Path parent = file.getParent();
        if (parent == null) {
            throw new IllegalStateException(file.getFileName() + " has no parents!");
        }
        Files.createDirectories(parent);
    }

}
