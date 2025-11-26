package org.project.utils.config;

public class DriverConfig extends Config {
    public static DriverBaseConfig config() {
        return config("win");
    }
    public static DriverBaseConfig config(DriverBaseConfig config) {
        return config("win", config);
    }
}
