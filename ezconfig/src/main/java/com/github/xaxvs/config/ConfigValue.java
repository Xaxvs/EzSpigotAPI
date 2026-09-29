package com.github.xaxvs.config;

public class ConfigValue<T> {

    protected final String path;
    protected final T value;

    protected ConfigValue(String path, T value) {
        this.path = path;
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public T getValue() {
        return value;
    }
}
