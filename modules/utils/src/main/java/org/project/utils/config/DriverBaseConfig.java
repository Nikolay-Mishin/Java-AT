package org.project.utils.config;

import static org.aeonbits.owner.Config.LoadPolicy;
import static org.aeonbits.owner.Config.LoadType.MERGE;
import static org.aeonbits.owner.Config.Sources;

import static org.project.utils.config.DriverConfig.config;
import static org.project.utils.config.DriverConfig.getConfig;

/**
 *
 */
@LoadPolicy(MERGE)
@Sources({
    "${props.env}",
    "${props.utils.win}"
})
public interface DriverBaseConfig extends BaseConfig {
    /**
     *
     */
    DriverBaseConfig BASE_CONFIG = config(DriverBaseConfig.class);
    /**
     *
     */
    boolean IS_WINIUM = getConfig().getIsWinium();
    /**
     *
     */
    int WINIUM_PORT = config().getWiniumPort();
    /**
     *
     */
    String WINIUM_HOST = config().getWiniumHost();
    /**
     *
     */
    String WINDRIVER_NAME = IS_WINIUM ? config().getWiniumName() : config().getWindriverName();
    /**
     *
     */
    String WINDRIVER = IS_WINIUM ? config().getWinium() : config().getWindriver();
    /**
     *
     */
    int WINDRIVER_PORT = IS_WINIUM ? WINIUM_PORT : config().getWindriverPort();
    /**
     *
     */
    String WINDRIVER_HOST = IS_WINIUM ? WINIUM_HOST : WINIUM_HOST.replaceAll(String.valueOf(WINIUM_PORT), String.valueOf(WINDRIVER_PORT));

    /**
     *
     * @return boolean
     */
    @Key("WIN_HOST")
    boolean getWinHost();

    /**
     *
     * @return boolean
     */
    @Key("IS_WINIUM")
    boolean getIsWinium();

    /**
     * Winium
     * @return String
     */
    @Key("WINIUM_PATH")
    String getWiniumPath();

    /**
     * Winium
     * @return String
     */
    @Key("WINIUM_NAME")
    String getWiniumName();

    /**
     * Winium
     * @return String
     */
    @Key("WINIUM")
    String getWinium();

    /**
     * Winium
     * @return int
     */
    @Key("WINIUM_PORT")
    int getWiniumPort();

    /**
     * Winium
     * @return String
     */
    @Key("WINIUM_HOST")
    String getWiniumHost();

    /**
     * WinAppDriver
     * @return String
     */
    @Key("WINDRIVER_PATH")
    String getWindriverPath();

    /**
     * WinAppDriver
     * @return String
     */
    @Key("WINDRIVER_NAME")
    String getWindriverName();

    /**
     * WinAppDriver
     * @return String
     */
    @Key("WINDRIVER")
    String getWindriver();

    /**
     * WinAppDriver
     * @return int
     */
    @Key("WINDRIVER_PORT")
    int getWindriverPort();

    /**
     * WinAppDriver
     * @return String
     */
    @Key("WINDRIVER_HOST")
    String getWindriverHost();

    /**
     * ChromeDriver
     * @return String
     */
    @Key("WEBDRIVER_PARAM")
    String getWebdriverParam();

    /**
     * ChromeDriver
     * @return String
     */
    @Key("CHROME_DRIVER_PATH")
    String getChromeDriverPath();

    /**
     * ChromeDriver
     * @return String
     */
    @Key("CHROME_DRIVER_NAME")
    String getChromeDriverName();

    /**
     * ChromeDriver
     * @return String
     */
    @Key("CHROME_DRIVER")
    String getChromeDriver();

    /**
     * app
     * @return String
     */
    @Key("APP")
    String getApp();

    /**
     * set the Handle as one of the capabilities
     * @return boolean
     */
    @Key("HANDLE")
    boolean getHandle();

    /**
     * app
     * @return String
     */
    @Key("LAUNCH_DELAY")
    String getLaunchDelay();

    /**
     * app
     * @return boolean
     */
    @Key("EXPERIMENTAL")
    boolean getExperimental();

    /**
     * app
     * @return boolean
     */
    @Key("JS")
    boolean getJS();

    /**
     * платформа
     * @return String
     */
    @Key("PLATFORM_NAME")
    String getPlatformName();

    /**
     * платформа
     * @return String
     */
    @Key("PLATFORM")
    String getPlatform();

    /**
     * платформа
     * @return String
     */
    @Key("DEVICE_NAME")
    String getDeviceName();

    /**
     *
     * @return String
     */
    @Key("notepad")
    String getNotepad();

    /**
     *
     * @return String
     */
    @Key("calc")
    String getCalc();

}
