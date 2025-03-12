package org.project.utils.windriver;

import org.project.utils.config.BaseConfig;

// список endpoits's
// константы пишутся camelCase + postfix = "URL"
public interface Config extends BaseConfig {
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
    String WINDRIVER_PARAM = "";
    int WINDRIVER_PORT = IS_WINIUM ? WINIUM_PORT : 4723;
    String WINDRIVER_HOST = IS_WINIUM ? WINIUM_HOST : WINIUM_HOST.replaceAll(String.valueOf(WINIUM_PORT), String.valueOf(WINDRIVER_PORT));
    // ChromeDriver
    String CHROME_DRIVER_PATH = "lib\\chromedriver\\";
    String CHROME_DRIVER_NAME = "chromedriver.exe";
    String CHROME_DRIVER = CHROME_DRIVER_PATH + CHROME_DRIVER_NAME;
    // "" - Если мы не хотим запускать какое-то приложение, а просто прикрепляться к какому-либо уже запущенному
    boolean EXPERIMENTAL = true;
    String APP = "C:\\Windows\\System32\\notepad.exe"; //если хотим сразу запускать какую-либо программу
    String LAUNCH_DELAY = "5"; //задержка после запуска программы
    // Дополнительно можно (а если запускаете Appium, то даже нужно) указывать платформу и устройство
    String PLATFORM_NAME = "Windows"; //платформа
    String DEVICE_NAME = "Windows10Machine"; //устройство
    // Если вы запустили WinAppDriver, то строка URL: "http://127.0.0.1:4723"
    // Ну а если запустили Appium, то: "http://127.0.0.1:4723/wd/hub"
}
