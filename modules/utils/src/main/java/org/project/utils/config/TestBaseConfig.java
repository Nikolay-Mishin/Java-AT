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

    //Cucumber
    /**
     *
     * @return String
     */
    @Key("c.plugin.pkg")
    String getCPluginPkg();

    /**
     *
     * @return String
     */
    @Key("c.plugin")
    String getCPlugin();

    /**
     *
     * @return String
     */
    @Key("c.plugin.field")
    String getCPluginField();

    /**
     *
     * @return String
     */
    @Key("c.plugins.arg")
    String getCPluginsArg();

    /**
     *
     * @return String
     */
    @Key("c.plugins")
    String getCPlugins();

    //TestApi
    /**
     *
     * @return String
     */
    @Key("api.ver")
    String getApiVer();

    /**
     *
     * @return String
     */
    @Key("api.uri")
    String getApiUri();

    /**
     *
     * @return String
     */
    @Key("api.endpoint")
    String getEndpoint();

    /**
     *
     * @return String
     */
    @Key("api.endpoint.test")
    String getEndpointTest();

    /**
     *
     * @return String
     */
    @Key("api.auth")
    String getAuth();

    /**
     *
     * @return String
     */
    @Key("api.auth.test")
    String getAuthTest();

    //TestJson
    /**
     *
     * @return String
     */
    @Key("json.ver")
    String getJsonVer();

    /**
     *
     * @return String
     */
    @Key("json.ver.type")
    String getJsonVerType();

    /**
     *
     * @return String
     */
    @Key("json.downloads")
    String getJsonDownloads();

    /**
     *
     * @return String
     */
    @Key("json.get")
    String getJson();

    /**
     *
     * @return String
     */
    @Key("json.get.type")
    String getJsonType();

    /**
     *
     * @return String
     */
    @Key("json.k")
    String getJsonK();

    /**
     *
     * @return String
     */
    @Key("json.v")
    String getJsonV();

    /**
     *
     * @return String
     */
    @Key("json.url")
    String getJsonUrl();

    //TestZip
    /**
     *
     * @return String
     */
    @Key("zip.root")
    String getZipRoot();

    /**
     *
     * @return String
     */
    @Key("zip.out")
    String getZipOut();

    /**
     *
     * @return String
     */
    @Key("zip.name")
    String getZipName();

    /**
     *
     * @return String
     */
    @Key("zip.filename")
    String getZipFilename();

    /**
     *
     * @return String
     */
    @Key("zip.filename.full")
    String getZipFilenameFull();

    /**
     *
     * @return String
     */
    @Key("zip.filename.txt")
    String getZipFilenameTxt();

    /**
     *
     * @return String
     */
    @Key("zip.mkdir.root")
    String getZipMkdirRoot();

    /**
     *
     * @return String
     */
    @Key("zip.mkdir")
    String getZipMkdir();

    /**
     *
     * @return String
     */
    @Key("zip.read")
    String getZipReadDir();

    //TestFS
    /**
     *
     * @return String
     */
    @Key("chrome.driver.root")
    String getChromeDriverRoot();

    /**
     *
     * @return String
     */
    @Key("chrome.driver.file")
    String getChromeDriverFile();

    /**
     *
     * @return String
     */
    @Key("fs.delete")
    String getFsDelete();

    /**
     *
     * @return String
     */
    @Key("fs.file")
    String getFsFile();

    /**
     *
     * @return String
     */
    @Key("fs.attrs")
    String getFsAttrs();

    /**
     *
     * @return String
     */
    @Key("fs.attrs.name")
    String getFsAttrsK();

    /**
     *
     * @return String
     */
    @Key("fs.attrs.value")
    String getFsAttrsV();

    //TestReq
    /**
     *
     * @return String
     */
    @Key("req.body")
    String getReqBody();

    /**
     *
     * @return String
     */
    @Key("req.test")
    String getReqTest();

    //TestInvoke
    /**
     *
     * @return String
     */
    @Key("i.class")
    String getFs();

    /**
     *
     * @return String
     */
    @Key("i.field")
    String getFsField();

    /**
     *
     * @return String
     */
    @Key("i.method.name")
    String getFsMethodName();

    /**
     *
     * @return String
     */
    @Key("i.method")
    String getFsMethod();

    /**
     *
     * @return String
     */
    //TestUTF8
    @Key("test.utf8")
    String getUtf8();

}
