package org.project.utils.test;

import java.beans.ConstructorProperties;

import static org.project.utils.Helper._equals;
import static org.project.utils.Helper.debug;
import static org.project.utils.Thread.getSleep;
import static org.project.utils.Thread.timeout;
import static org.project.utils.config.Config.compare;
import static org.project.utils.config.Config.config;
import static org.project.utils.config.Config.configs;
import static org.project.utils.config.Config.env;
import static org.project.utils.config.Config.printEnvList;
import static org.project.utils.windriver.RemoteWebDriver.getTimeout;
import static org.project.utils.windriver.RemoteWebDriver.sleep;
import static org.project.utils.windriver.RemoteWebDriver.sleepStart;
import static org.project.utils.windriver.RemoteWebDriver.timeoutStart;

import org.project.utils.Helper;
import org.project.utils.base.HashMap;
import org.project.utils.config.BaseConfig;
import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.DriverConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;
import org.project.utils.config.WebConfig;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestConfig<T extends TestBaseConfig, W extends WebBaseConfig, D extends DriverBaseConfig> extends TestUTF8<T, W, D> {

    /**
     *
     */
    @ConstructorProperties({})
    public TestConfig() {
        debug("TestConfig:init");
    }

    /**
     *
     */
    public static void printConfig() {
        debug("TestConfig: " + org.project.utils.config.TestConfig.config());
        debug("WebConfig: " + WebConfig.config());
        debug("WinConfig: " + DriverConfig.config());
        debug("BaseConfig: " + config());
        debug("sleep: " + getSleep());
        debug("timeout: " + timeout());
        debug("sleepApp: " + sleep());
        debug("timeoutApp: " + getTimeout());
        debug("sleepStart: " + sleepStart());
        debug("timeoutStart: " + timeoutStart());
        debug("debugLevel: " + WebConfig.config().getDebugLevel());
        debug("env: " + env());
    }

    /**
     *
     * @throws ReflectiveOperationException throws
     */
    public static void testConfig() throws ReflectiveOperationException {
        debug("testConfig");
        debug(config().getClass());
        printConfigs();
        debug(config("config").getClass());
        debug(DriverConfig.config());
        debug(config("win"));
        debug(config("win").getClass());
        debug(config("win", DriverBaseConfig.class).getClass());
        printConfigs();
        //debug(BaseConfig.BASE_CONFIG);
        debug(WebConfig.config());
        //compare(BaseConfig.BASE_CONFIG);
        compare("web", WebConfig.config());
        //debug(BaseConfig.BASE_CONFIG.getBaseConfig());
        debug(WebConfig.config().getBaseConfig());
        Helper.debug(org.project.utils.config.TestConfig.config());
        printConfigs();
    }

    /**
     *
     * @throws ReflectiveOperationException throws
     */
    public static void printConfigs() throws ReflectiveOperationException {
        debug("printConfigs");
        HashMap<String, BaseConfig> map = configs();
        debug("map: " + map);
        for (String k : map.keys()) {
            debug(k);
            debug(map.get(k).getClass());
        }
    }

    /**
     *
     */
    public static void testWinDriverConfig() {
        debug("testWinDriverConfig");
        debug(WebConfig.config().getBaseUrl());
        debug(WebConfig.config().getDebugLevel());
        debug(DriverConfig.config().getIsWinium());
        debug(DriverConfig.config().getWinium());
        debug(DriverConfig.config().getWiniumHost());
        debug(DriverConfig.config().getWindriverPath());
        debug(DriverConfig.config().getWindriverName());
        debug(DriverConfig.config().getWindriver());
        debug(DriverConfig.config().getWindriverPort());
        debug(DriverConfig.config().getWindriverHost());
        debug(DriverConfig.config().getWebdriverParam());
        debug(DriverConfig.config().getChromeDriver());
        debug(DriverConfig.config().getExperimental());
        debug(DriverBaseConfig.IS_WINIUM);
        debug(DriverBaseConfig.WINDRIVER_NAME);
        debug(DriverBaseConfig.WINDRIVER);
        debug(DriverBaseConfig.WINDRIVER_PORT);
        debug(DriverBaseConfig.WINDRIVER_HOST);
        debug(_equals(DriverConfig.config().getWebdriverParam(), ""));
        printEnvList();
    }

}
