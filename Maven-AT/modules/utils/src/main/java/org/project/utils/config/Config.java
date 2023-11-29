package org.project.utils.config;

import static java.lang.System.*;
import static org.aeonbits.owner.ConfigFactory.create;
import static org.project.utils.Helper.isNull;
import static org.project.utils.config.WebBaseConfig.BASE_CONFIG;

public class Config {

    protected static WebBaseConfig config;

    @SuppressWarnings("unchecked")
    public static <T extends WebBaseConfig> T setConfig(Class<T> clazz) {
        out.println("setConfig");
        out.println(clazz);
        config = create(clazz, getenv(), getProperties());
        print();
        return (T) config;
    }

    @SuppressWarnings("unchecked")
    public static <T extends WebBaseConfig> T config() {
        if (isNull(config)) config = BASE_CONFIG;
        out.println("getConfig");
        print();
        return (T) config;
    }

    public static void print() {
        out.println("BASE_CONFIG: " + config.getTargetPackage());
    }

}
