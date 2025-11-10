package org.project.utils.constant;

import static org.project.utils.windriver.DriverBaseConfig.*;

public class Capabilities {
    String app = BASE_CONFIG.getApp(); //если хотим сразу запускать какую-либо программу
    String launchDelay = BASE_CONFIG.getLaunchDelay(); //задержка после запуска программы
    String platformName = BASE_CONFIG.getPlatformName(); //платформа
    String deviceName = BASE_CONFIG.getDeviceName(); //устройство
}
