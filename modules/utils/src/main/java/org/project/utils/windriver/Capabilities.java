package org.project.utils.windriver;

import java.beans.ConstructorProperties;

import org.openqa.selenium.remote.DesiredCapabilities;

import static org.project.utils.Helper._equals;
import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.isNull;
import static org.project.utils.Helper.notEquals;
import static org.project.utils.base.HashMap.forEachSort;
import static org.project.utils.config.DriverConfig.config;

import org.project.utils.config.DriverBaseConfig;

/**
 *
 */
public class Capabilities extends DesiredCapabilities {
    /**
     * если хотим захватывать какую-либо запущенную программу и прикреплять к текущему сеансу
     */
    protected static boolean handle;
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
     * платформа
     */
    protected String platform;
    /**
     * устройство
     */
    protected String deviceName;
    /**
     * включить экспериментальные функции
     */
    protected boolean experimental;
    /**
     * включить JS
     */
    protected boolean javascriptEnabled;

    /**
     *
     * @throws ReflectiveOperationException throws
     */
    @ConstructorProperties({})
    public Capabilities() throws ReflectiveOperationException {
        init();
    }

    /**
     *
     * @param app String
     * @throws ReflectiveOperationException throws
     */
    @ConstructorProperties({"app"})
    public Capabilities(String app) throws ReflectiveOperationException {
        app(app).init();
    }

    /**
     * You set the Handle as one of the capabilities
     * @param app String
     * @param handle boolean
     * @throws ReflectiveOperationException throws
     */
    @ConstructorProperties({"app", "handle"})
    public Capabilities(String app, boolean handle) throws ReflectiveOperationException {
        app(app, handle).init();
    }

    /**
     *
     * @param config DriverBaseConfig
     * @throws ReflectiveOperationException throws
     */
    @ConstructorProperties({"config"})
    public Capabilities(DriverBaseConfig config) throws ReflectiveOperationException {
        init(config);
    }

    /**
     *
     * @param cap T
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    @ConstructorProperties({"cap"})
    public <T extends DesiredCapabilities> Capabilities(T cap) throws ReflectiveOperationException {
        init(cap);
    }

    /**
     *
     * @param app String
     * @return Capabilities
     */
    public Capabilities app(String app) {
        return app(app, false);
    }

    /**
     * You set the Handle as one of the capabilities
     * @param app String
     * @param handleApp boolean
     * @return Capabilities
     */
    public Capabilities app(String app, boolean handleApp) {
        this.app = app;
        handle = handleApp;
        return this;
    }

    /**
     * You set the Handle as one of the capabilities
     * @return boolean
     */
    public static boolean handle() {
        return handle;
    }

    /**
     *
     * @return Capabilities
     * @throws ReflectiveOperationException throws
     */
    public Capabilities init() throws ReflectiveOperationException {
        return init(config());
    }

    /**
     *
     * @param config DriverBaseConfig
     * @return Capabilities
     * @throws ReflectiveOperationException throws
     */
    public Capabilities init(DriverBaseConfig config) throws ReflectiveOperationException {
        if (isNull(app)) app = config.getApp();
        if (isNull(handle)) handle = config.getHandle();
        launchDelay = config.getLaunchDelay();
        platformName = config.getPlatformName();
        platform = config.getPlatform();
        if (platform.isEmpty()) platform = platformName.toUpperCase();
        deviceName = config.getDeviceName();
        experimental = config.getExperimental();
        javascriptEnabled = config.getJS();
        return init(this);
    }

    /**
     *
     * @param cap T
     * @return T
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    public static <T extends DesiredCapabilities> T init(T cap) throws ReflectiveOperationException {
        forEachSort(cap, (k, v) -> {
            if (!v.equals("") && !k.equals("handle")) {
                if (_equals(k, "app") && handle()) k = "appTopLevelWindow"; // You set the Handle as one of the capabilities
                cap.setCapability(notEquals(k, "experimental") ? k : "ms:experimental-webdriver", v);
            }
        });
        debug("cap: " + cap);
        return cap;
    }

}
