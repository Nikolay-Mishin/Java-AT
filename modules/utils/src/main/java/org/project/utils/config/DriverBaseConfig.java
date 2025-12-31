package org.project.utils.config;

import static java.lang.String.valueOf;
import static org.aeonbits.owner.Config.LoadPolicy;
import static org.aeonbits.owner.Config.LoadType.MERGE;
import static org.aeonbits.owner.Config.Sources;

import static org.project.utils.config.DriverConfig.config;
import static org.project.utils.config.DriverConfig.setConfig;

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
    //DriverBaseConfig BASE_CONFIG = config(DriverBaseConfig.class);
    DriverBaseConfig BASE_CONFIG = setConfig();
    /**
     *
     */
    boolean IS_WINIUM = config().getIsWinium();
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
    String WINDRIVER_HOST = IS_WINIUM ? WINIUM_HOST : WINIUM_HOST.replaceAll(valueOf(WINIUM_PORT), valueOf(WINDRIVER_PORT));

    /**
     * app
     * @return long
     */
    @Key("SLEEP_START")
    long getSleepStart();

    /**
     * app
     * @return long
     */
    @Key("TIMEOUT_START")
    long getTimeoutStart();

    /**
     * Library
     * @return String
     */
    @Key("api.milestone")
    String getApiMilestone();

    /**
     * Library
     * @return String
     */
    @Key("api.build")
    String getApiBuild();

    /**
     * Library
     * @return String
     */
    @Key("api.ver")
    String getApiVer();

    /**
     * Library
     * @return String
     */
    @Key("api.uri")
    String getApiUri();

    /**
     * Library
     * @return String
     */
    @Key("api.endpoint")
    String getApiEndpoint();

    /**
     * Library
     * @return String
     */
    @Key("api.endpoint.ver")
    String getApiEndpointVer();

    /**
     * Library
     * @return String
     */
    @Key("lib.endpoint")
    String getLibEndpoint();

    /**
     * Library
     * @return String
     */
    @Key("lib.endpoints")
    String getLibEndpoints();

    /**
     * Library
     * @return String
     */
    @Key("json.ver")
    String getJsonVer();

    /**
     * Library
     * @return String
     */
    @Key("json.downloads")
    String getJsonDownloads();

    /**
     * Library
     * @return String
     */
    @Key("json.downloads.get")
    String getJsonDownloadsK();

    /**
     * Library
     * @return String
     */
    @Key("json.get")
    String getJson();

    /**
     * Library
     * @return String
     */
    @Key("json.k")
    String getJsonK();

    /**
     * Library
     * @return String
     */
    @Key("json.v")
    String getJsonV();

    /**
     * Library
     * @return String
     */
    @Key("json.url")
    String getUrlK();

    /**
     * Library
     * @return String
     */
    @Key("lib.root")
    String getLibRoot();

    /**
     * Library
     * @return String
     */
    @Key("lib.out")
    String getLibOut();

    /**
     * Library
     * @return boolean
     */
    @Key("lib.write")
    boolean getLibWrite();

    /**
     *
     * @return boolean
     */
    static boolean isWinium() { return config().getIsWinium(); };

    /**
     *
     * @return int
     */
    static int winiumPort() { return config().getWiniumPort(); };

    /**
     *
     * @return String
     */
    static String winiumHost() { return config().getWiniumHost(); };

    /**
     *
     * @return String
     */
    static String windriverName() { return windriverName(isWinium()); };

    /**
     *
     * @return String
     */
    static String winDriver() { return winDriver(isWinium()); };

    /**
     *
     * @return int
     */
    static int winDriverPort() { return winDriverPort(isWinium()); };

    /**
     *
     * @return String
     */
    static String winDriverHost() {
        return winDriverHost(isWinium());
    };

    /**
     *
     * @param isWinium boolean
     * @return String
     */
    static String windriverName(boolean isWinium) { return isWinium ? config().getWiniumName() : config().getWindriverName(); };

    /**
     *
     * @param isWinium boolean
     * @return String
     */
    static String winDriver(boolean isWinium) { return isWinium ? config().getWinium() : config().getWindriver(); };

    /**
     *
     * @param isWinium boolean
     * @return int
     */
    static int winDriverPort(boolean isWinium) { return isWinium ? winiumPort() : config().getWindriverPort(); };

    /**
     *
     * @param isWinium boolean
     * @return String
     */
    static String winDriverHost(boolean isWinium) {
        String winiumHost = winiumHost();
        return isWinium ? winiumHost : winiumHost.replaceAll(valueOf(winiumPort()), valueOf(winDriverPort(isWinium)));
    };

    /**
     *
     * @return boolean
     */
    @Key("WIN_HOST")
    boolean getWinHost();

    /**
     * Winium
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
    @Key("CHROME_PATH")
    String getChrome();

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
