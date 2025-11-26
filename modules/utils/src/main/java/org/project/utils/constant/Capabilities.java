package org.project.utils.constant;

import static org.project.utils.config.DriverConfig.config;

public class Capabilities {
    String app = config().getApp(); //если хотим сразу запускать какую-либо программу
    String launchDelay = config().getLaunchDelay(); //задержка после запуска программы
    String platformName = config().getPlatformName(); //платформа
    String deviceName = config().getDeviceName(); //устройство
}
