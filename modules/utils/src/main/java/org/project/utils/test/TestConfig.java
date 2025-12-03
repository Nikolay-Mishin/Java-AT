package org.project.utils.test;

import static org.project.utils.Helper._equals;
import static org.project.utils.Helper.debug;
import static org.project.utils.config.Config.compare;
import static org.project.utils.config.Config.config;
import static org.project.utils.config.Config.configs;
import static org.project.utils.config.Config.printEnvList;

import org.project.utils.base.HashMap;
import org.project.utils.config.BaseConfig;
import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.WebBaseConfig;

public class TestConfig extends App {

    public static void main(String[] args) throws ReflectiveOperationException {
        testConfig();
        testWinDriverConfig();
    }

    public static void testConfig() throws ReflectiveOperationException {
        debug("testConfig");
        debug(config().getClass());
        printConfigs();
        debug(config("config").getClass());
        debug(DriverBaseConfig.BASE_CONFIG);
        debug(config("win"));
        debug(config("win").getClass());
        debug(config("win", DriverBaseConfig.class).getClass());
        printConfigs();
        debug(BaseConfig.BASE_CONFIG);
        debug(WebBaseConfig.BASE_CONFIG);
        compare(BaseConfig.BASE_CONFIG);
        compare("web", WebBaseConfig.BASE_CONFIG);
        printConfigs();
        debug(BaseConfig.BASE_CONFIG.getConfig());
        debug(WebBaseConfig.BASE_CONFIG.getConfig());
    }

    public static void printConfigs() throws ReflectiveOperationException {
        debug("printConfigs");
        HashMap<String, BaseConfig> map = configs();
        debug("map: " + map);
        for (String k : map.keys()) {
            debug(k);
            debug(map.get(k).getClass());
        }
    }

    public static void testWinDriverConfig() {
        debug("testWinDriverConfig");
        debug(WebBaseConfig.BASE_CONFIG.getBaseUrl());
        debug(BaseConfig.BASE_CONFIG.getDebugLevel());
        debug(DriverBaseConfig.BASE_CONFIG.getIsWinium());
        debug(DriverBaseConfig.BASE_CONFIG.getWinium());
        debug(DriverBaseConfig.BASE_CONFIG.getWiniumHost());
        debug(DriverBaseConfig.BASE_CONFIG.getWindriverPath());
        debug(DriverBaseConfig.BASE_CONFIG.getWindriverName());
        debug(DriverBaseConfig.BASE_CONFIG.getWindriver());
        debug(DriverBaseConfig.BASE_CONFIG.getWindriverPort());
        debug(DriverBaseConfig.BASE_CONFIG.getWindriverHost());
        debug(DriverBaseConfig.BASE_CONFIG.getWebdriverParam());
        debug(DriverBaseConfig.BASE_CONFIG.getChromeDriver());
        debug(DriverBaseConfig.BASE_CONFIG.getExperimental());
        debug(DriverBaseConfig.IS_WINIUM);
        debug(DriverBaseConfig.WINDRIVER_NAME);
        debug(DriverBaseConfig.WINDRIVER);
        debug(DriverBaseConfig.WINDRIVER_PORT);
        debug(DriverBaseConfig.WINDRIVER_HOST);
        debug(_equals(DriverBaseConfig.BASE_CONFIG.getWebdriverParam(), ""));
        printEnvList();
    }

}
