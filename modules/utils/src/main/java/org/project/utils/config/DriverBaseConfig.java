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

    // Winium
    /**
     *
     * @return String
     */
    @Key("WINIUM_PATH")
    String getWiniumPath();

    /**
     *
     * @return String
     */
    @Key("WINIUM_NAME")
    String getWiniumName();

    /**
     *
     * @return String
     */
    @Key("WINIUM")
    String getWinium();

    /**
     *
     * @return int
     */
    @Key("WINIUM_PORT")
    int getWiniumPort();

    /**
     *
     * @return String
     */
    @Key("WINIUM_HOST")
    String getWiniumHost();

    // WinAppDriver
    /**
     *
     * @return String
     */
    @Key("WINDRIVER_PATH")
    String getWindriverPath();

    /**
     *
     * @return String
     */
    @Key("WINDRIVER_NAME")
    String getWindriverName();

    /**
     *
     * @return String
     */
    @Key("WINDRIVER")
    String getWindriver();

    /**
     *
     * @return int
     */
    @Key("WINDRIVER_PORT")
    int getWindriverPort();

    /**
     *
     * @return String
     */
    @Key("WINDRIVER_HOST")
    String getWindriverHost();

    // ChromeDriver
    /**
     *
     * @return String
     */
    @Key("WEBDRIVER_PARAM")
    String getWebdriverParam();

    /**
     *
     * @return String
     */
    @Key("CHROME_DRIVER_PATH")
    String getChromeDriverPath();

    /**
     *
     * @return String
     */
    @Key("CHROME_DRIVER_NAME")
    String getChromeDriverName();

    /**
     *
     * @return String
     */
    @Key("CHROME_DRIVER")
    String getChromeDriver();

    /**
     *
     * @return String
     */
    @Key("APP")
    String getApp();

    /**
     *
     * @return boolean
     */
    @Key("HANDLE")
    boolean getHandle();

    /**
     *
     * @return String
     */
    @Key("LAUNCH_DELAY")
    String getLaunchDelay();

    /**
     *
     * @return boolean
     */
    @Key("EXPERIMENTAL")
    boolean getExperimental();

    /**
     *
     * @return boolean
     */
    @Key("JS")
    boolean getJS();

    // платформа
    /**
     *
     * @return String
     */
    @Key("PLATFORM_NAME")
    String getPlatformName();

    /**
     *
     * @return String
     */
    @Key("PLATFORM")
    String getPlatform();

    /**
     *
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
