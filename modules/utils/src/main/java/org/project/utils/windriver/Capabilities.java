package org.project.utils.windriver;

import java.util.Map;

import org.openqa.selenium.remote.DesiredCapabilities;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.entries;
import static org.project.utils.Helper.isNull;
import static org.project.utils.Helper.notEquals;
import static org.project.utils.config.DriverConfig.config;

import org.project.utils.config.DriverBaseConfig;

public class Capabilities extends DesiredCapabilities {
    protected String app; //если хотим сразу запускать какую-либо программу
    protected String launchDelay; //задержка после запуска программы
    protected String platformName; //платформа
    protected String deviceName; //устройство
    protected boolean experimental;

    public Capabilities() {
        init();
    }

    public Capabilities(String app){
        app(app).init();
    }

    public Capabilities(DriverBaseConfig config) {
        init(config);
    }

    public <T extends DesiredCapabilities> Capabilities(T cap) {
        init(cap);
    }

    public Capabilities app(String app) {
        this.app = app;
        return this;
    }

    public Capabilities init() {
        return init(config());
    }

    public Capabilities init(DriverBaseConfig config) {
        if (isNull(app)) app = config.getApp(); //если хотим сразу запускать какую-либо программу
        launchDelay = config.getLaunchDelay(); //задержка после запуска программы
        platformName = config.getPlatformName(); //платформа
        deviceName = config.getDeviceName(); //устройство
        experimental = config.getExperimental();
        return init(this);
    }

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
