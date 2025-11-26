package org.project.utils.config;

import org.aeonbits.owner.Config.*;

import static org.project.utils.config.DriverConfig.*;

@Sources({"classpath:${env}.properties"})
public interface DriverBaseConfig extends BaseConfig {
    DriverBaseConfig BASE_CONFIG = config(DriverBaseConfig.class);
    boolean IS_WINIUM = config().getIsWinium();
    int WINIUM_PORT = config().getWiniumPort();
    String WINIUM_HOST = config().getWiniumHost();
    String WINDRIVER_NAME = IS_WINIUM ? config().getWiniumName() : config().getWindriverName();
    String WINDRIVER = IS_WINIUM ? config().getWinium() : config().getWindriver();
    int WINDRIVER_PORT = IS_WINIUM ? WINIUM_PORT : config().getWindriverPort();
    String WINDRIVER_HOST = IS_WINIUM ? WINIUM_HOST : WINIUM_HOST.replaceAll(String.valueOf(WINIUM_PORT), String.valueOf(WINDRIVER_PORT));

    // app
    @Key("WIN_HOST")
    boolean getWinHost();

    @Key("IS_WINIUM")
    boolean getIsWinium();

    // Winium
    @Key("WINIUM_PATH")
    String getWiniumPath();

    @Key("WINIUM_NAME")
    String getWiniumName();

    @Key("WINIUM")
    String getWinium();

    @Key("WINIUM_PORT")
    int getWiniumPort();

    @Key("WINIUM_HOST")
    String getWiniumHost();

    // WinAppDriver
    @Key("WINDRIVER_PATH")
    String getWindriverPath();

    @Key("WINDRIVER_NAME")
    String getWindriverName();

    @Key("WINDRIVER")
    String getWindriver();

    @Key("WINDRIVER_PORT")
    int getWindriverPort();

    @Key("WINDRIVER_HOST")
    String getWindriverHost();

    // ChromeDriver
    @Key("WEBDRIVER_PARAM")
    String getWebdriverParam();

    @Key("CHROME_DRIVER_PATH")
    String getChromeDriverPath();

    @Key("CHROME_DRIVER_NAME")
    String getChromeDriverName();

    @Key("CHROME_DRIVER")
    String getChromeDriver();

    @Key("APP")
    String getApp();

    @Key("LAUNCH_DELAY")
    String getLaunchDelay();

    @Key("EXPERIMENTAL")
    boolean getExperimental();

    // платформа
    @Key("PLATFORM_NAME")
    String getPlatformName();

    @Key("DEVICE_NAME")
    String getDeviceName();
}
