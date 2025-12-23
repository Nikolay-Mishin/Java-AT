package org.project.utils.config;

import static org.aeonbits.owner.Config.LoadPolicy;
import static org.aeonbits.owner.Config.LoadType.MERGE;
import static org.aeonbits.owner.Config.Sources;

import static org.project.utils.config.TestConfig.config;

/**
 *
 */
@LoadPolicy(MERGE)
@Sources({
    "${props.env}",
    "${props.utils.test}"
})
public interface TestBaseConfig extends BaseConfig {
    /**
     *
     */
    TestBaseConfig BASE_CONFIG = config(TestBaseConfig.class);

    /**
     * System
     * @return String
     */
    @Key("user.name")
    String getUserName();

    /**
     * System
     * @return String
     */
    @Key("user")
    String getUser();

    /**
     * Cucumber
     * @return String
     */
    @Key("c.plugin.pkg")
    String getCPluginPkg();

    /**
     *Cucumber
     * @return String
     */
    @Key("c.plugin")
    String getCPlugin();

    /**
     *Cucumber
     * @return String
     */
    @Key("c.plugin.field")
    String getCPluginField();

    /**
     *Cucumber
     * @return String
     */
    @Key("c.plugins.arg")
    String getCPluginsArg();

    /**
     *Cucumber
     * @return String
     */
    @Key("c.plugins")
    String getCPlugins();

    /**
     * TestApi
     * @return String
     */
    @Key("api.auth")
    String getAuth();

    /**
     * TestApi
     * @return String
     */
    @Key("api.auth.test")
    String getAuthTest();

    /**
     * TestApi
     * @return String
     */
    @Key("api.endpoint.test")
    String getEndpointTest();

    /**
     * TestZip
     * @return String
     */
    @Key("zip.root")
    String getZipRoot();

    /**
     * TestZip
     * @return String
     */
    @Key("zip.out")
    String getZipOut();

    /**
     * TestZip
     * @return String
     */
    @Key("zip.name")
    String getZipName();

    /**
     * TestZip
     * @return String
     */
    @Key("zip.filename")
    String getZipFilename();

    /**
     * TestZip
     * @return String
     */
    @Key("zip.filename.full")
    String getZipFilenameFull();

    /**
     * TestZip
     * @return String
     */
    @Key("zip.filename.txt")
    String getZipFilenameTxt();

    /**
     * TestZip
     * @return String
     */
    @Key("zip.mkdir.root")
    String getZipMkdirRoot();

    /**
     * TestZip
     * @return String
     */
    @Key("zip.mkdir")
    String getZipMkdir();

    /**
     * TestZip
     * @return String
     */
    @Key("zip.read")
    String getZipReadDir();

    /**
     * TestFS
     * @return String
     */
    @Key("chrome.driver.root")
    String getChromeDriverRoot();

    /**
     * TestFS
     * @return String
     */
    @Key("chrome.driver.file")
    String getChromeDriverFile();

    /**
     * TestFS
     * @return String
     */
    @Key("fs.delete")
    String getFsDelete();

    /**
     * TestFS
     * @return String
     */
    @Key("fs.file")
    String getFsFile();

    /**
     * TestFS
     * @return String
     */
    @Key("fs.attrs")
    String getFsAttrs();

    /**
     * TestFS
     * @return String
     */
    @Key("fs.attrs.name")
    String getFsAttrsK();

    /**
     * TestFS
     * @return String
     */
    @Key("fs.attrs.value")
    String getFsAttrsV();

    /**
     * TestReq
     * @return String
     */
    @Key("req.body")
    String getReqBody();

    /**
     * TestReq
     * @return String
     */
    @Key("req.test")
    String getReqTest();

    /**
     * TestInvoke
     * @return String
     */
    @Key("i.class")
    String getFs();

    /**
     * TestInvoke
     * @return String
     */
    @Key("i.field")
    String getFsField();

    /**
     * TestInvoke
     * @return String
     */
    @Key("i.method.name")
    String getFsMethodName();

    /**
     * TestInvoke
     * @return String
     */
    @Key("i.method")
    String getFsMethod();

    /**
     * TestUTF8
     * @return String
     */
    @Key("test.utf8")
    String getUtf8();

    /**
     * TestWeb
     * @return boolean
     */
    @Key("auth.init")
    boolean getAuthInit();

    /**
     * TestWeb
     * @return String
     */
    @Key("endpoint")
    String getEndpoint();

    /**
     * TestWeb
     * @return int
     */
    @Key("endpoint.id")
    int getEndpointId();

    /**
     * TestWeb
     * @return String
     */
    @Key("endpoint.token")
    String getEndpointToken();

}
