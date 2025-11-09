package org.project.utils.windriver;

import org.project.utils.config.BaseConfig;

import static org.project.utils.config.Config.config;

// список endpoits's
// константы пишутся camelCase + postfix = "URL"
public interface DriverBaseConfig extends BaseConfig {
    DriverBaseConfig BASE_CONFIG = config("win", DriverBaseConfig.class);

    // app
    boolean IS_WINIUM = false;
    // Winium
    String WINIUM_PATH = "lib\\Winium\\";
    String WINIUM_NAME = "Winium.Desktop.Driver.exe";
    String WINIUM = WINIUM_PATH + WINIUM_NAME;
    int WINIUM_PORT = 9999;
    String WINIUM_HOST = "http://localhost:" + WINIUM_PORT;
    // WinAppDriver
    String WINDRIVER_PATH = IS_WINIUM ? WINIUM_PATH : "C:\\Program Files\\Windows Application Driver\\";
    String WINDRIVER_NAME = IS_WINIUM ? WINIUM_NAME : "WinAppDriver.exe";
    String WINDRIVER = WINDRIVER_PATH + WINDRIVER_NAME;
    int WINDRIVER_PORT = IS_WINIUM ? WINIUM_PORT : 4723;
    String WINDRIVER_HOST = IS_WINIUM ? WINIUM_HOST : WINIUM_HOST.replaceAll(String.valueOf(WINIUM_PORT), String.valueOf(WINDRIVER_PORT));
    // ChromeDriver
    String WEBDRIVER_PARAM = "";
    String CHROME_DRIVER_PATH = "lib\\chromedriver\\";
    String CHROME_DRIVER_NAME = "chromedriver.exe";
    String CHROME_DRIVER = CHROME_DRIVER_PATH + CHROME_DRIVER_NAME;
    // "" - Если мы не хотим запускать какое-то приложение, а просто прикрепляться к какому-либо уже запущенному
    String APP = "C:\\Windows\\System32\\notepad.exe"; //если хотим сразу запускать какую-либо программу
    String LAUNCH_DELAY = "5"; //задержка после запуска программы
    boolean EXPERIMENTAL = true;
    // Дополнительно можно (а если запускаете Appium, то даже нужно) указывать платформу и устройство
    String PLATFORM_NAME = "Windows"; //платформа
    String DEVICE_NAME = "Windows10Machine"; //устройство
    // Если вы запустили WinAppDriver, то строка URL: "http://127.0.0.1:4723"
    // Ну а если запустили Appium, то: "http://127.0.0.1:4723/wd/hub"

    // app
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
    String getPlatformNameform();

    @Key("DEVICE_NAME")
    String getDeviceName();
}
