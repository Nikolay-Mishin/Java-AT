package org.project.utils.windriver;

import java.beans.ConstructorProperties;
import java.util.Map;

import org.openqa.selenium.remote.DesiredCapabilities;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.entries;
import static org.project.utils.Helper.isNull;
import static org.project.utils.Helper.notEquals;
import static org.project.utils.config.DriverConfig.config;

import org.project.utils.config.DriverBaseConfig;

/**
 *
 */
public class Capabilities extends DesiredCapabilities {
    /**
     * если хотим сразу запускать какую-либо программу
     */
    protected String app;
    /**
     * задержка после запуска программы
     */
    protected String launchDelay;
    /**
     * платформа
     */
    protected String platformName;
    /**
     * устройство
     */
    protected String deviceName;
    /**
     * квлючить экспериментальные функции
     */
    protected boolean experimental;

    /**
     *
     */
    @ConstructorProperties({})
    public Capabilities() {
        init();
    }

    /**
     *
     * @param app String
     */
    @ConstructorProperties({"token"})
    public Capabilities(String app){
        app(app).init();
    }

    /**
     *
     * @param config DriverBaseConfig
     */
    @ConstructorProperties({"token"})
    public Capabilities(DriverBaseConfig config) {
        init(config);
    }

    /**
     *
     * @param cap T
     * @param <T> T
     */
    @ConstructorProperties({"token"})
    public <T extends DesiredCapabilities> Capabilities(T cap) {
        init(cap);
    }

    /**
     *
     * @param app String
     * @return Capabilities
     */
    public Capabilities app(String app) {
        this.app = app;
        return this;
    }

    /**
     *
     * @return Capabilities
     */
    public Capabilities init() {
        return init(config());
    }

    /**
     *
     * @param config DriverBaseConfig
     * @return Capabilities
     */
    public Capabilities init(DriverBaseConfig config) {
        if (isNull(app)) app = config.getApp();
        launchDelay = config.getLaunchDelay();
        platformName = config.getPlatformName();
        deviceName = config.getDeviceName();
        experimental = config.getExperimental();
        return init(this);
    }

    /**
     *
     * @param cap T
     * @return T
     * @param <T> T
     */
    public static <T extends DesiredCapabilities> T init(T cap) {
        for (Map.Entry<String, Object> entry : entries(cap).entrySet()) {
            String k = entry.getKey();
            Object v = entry.getValue();
            if (v != "") cap.setCapability(notEquals(k, "experimental") ? k : "ms:experimental-webdriver", v);
        }
        debug("cap: " + cap);
        return cap;
    }

}
