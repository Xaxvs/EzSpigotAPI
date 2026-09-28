package com.github.xaxvs.file;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

@NullMarked
public class ConfigFile {

    private final Path file;
    private final YamlConfiguration configuration = new YamlConfiguration();

    public ConfigFile(Path folder, String fileName) throws IOException {
        this(folder.resolve(fileName));
    }

    public ConfigFile(Path file) throws IOException {
        this.file = file;
        resolveDirs();
        resolveFile();
    }

    private void resolveFile() throws IOException {
        if (!Files.exists(file)) {
            Files.createFile(file);
        }
    }

    public YamlConfiguration toConfiguration() throws IOException, InvalidConfigurationException {
        configuration.load(file.toFile());
        return configuration;
    }

    public void deleteFile() throws IOException {
        Files.deleteIfExists(file);
    }

    public void saveConfig(boolean atomically) throws IOException {
        if(atomically) AtomicFileSaver.save(file, configuration.saveToString());
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
