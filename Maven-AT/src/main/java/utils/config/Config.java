package utils.config;

import static org.aeonbits.owner.ConfigFactory.create;

public class Config {

    public static <T extends org.aeonbits.owner.Config> T setConfig(Class<T> clazz) {
        return create(clazz, System.getenv(), System.getProperties());
    }

}
