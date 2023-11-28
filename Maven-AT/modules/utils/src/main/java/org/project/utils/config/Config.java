package org.project.utils.config;

import static java.lang.System.out;
import static org.aeonbits.owner.ConfigFactory.create;

public class Config {

    protected static IConfig config;

    @SuppressWarnings("unchecked")
    public static <T extends IConfig> T setConfig(Class<T> clazz) {
        config = create(clazz, System.getenv(), System.getProperties());
        return (T) config;
    }

    @SuppressWarnings("unchecked")
    public static <T extends IConfig> T config() {
        out.println("config: " + config);
        return (T) config;
    }

}
