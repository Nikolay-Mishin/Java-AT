package org.project.utils.windriver;

import org.project.utils.config.Config;

public class DriverConfig extends Config {
    public static DriverBaseConfig config() {
        return config("win");
    }
}
