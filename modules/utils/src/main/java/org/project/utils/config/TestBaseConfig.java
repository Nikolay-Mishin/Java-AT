package org.project.utils.config;

import static org.aeonbits.owner.Config.Sources;
import static org.project.utils.config.TestConfig.config;

@Sources({"classpath:org.project.utils.test.properties"})
public interface TestBaseConfig extends BaseConfig {
    TestBaseConfig BASE_CONFIG = config(TestBaseConfig.class);

    //Cucumber
    @Key("c.plugin.pkg")
    String getCPluginPkg();

    @Key("c.plugin")
    String getCPlugin();

    @Key("c.plugin.field")
    String getCPluginField();

    @Key("c.plugins")
    String getCPlugins();

    //TestApi
    @Key("api.ver")
    String getApiVer();

    @Key("api.uri")
    String getApiUri();

    @Key("api.endpoint")
    String getEndpoint();

    @Key("api.endpoint.test")
    String getEndpointTest();

    @Key("api.auth")
    String getAuth();

    @Key("api.auth.test")
    String getAuthTest();

    //TestJson
    @Key("json.ver")
    String getJsonVer();

    @Key("json.ver.type")
    String getJsonVerType();

    @Key("json.downloads")
    String getJsonDownloads();

    @Key("json.get")
    String getJson();

    @Key("json.get.type")
    String getJsonType();

    @Key("json.k")
    String getJsonK();

    @Key("json.v")
    String getJsonV();

    @Key("json.url")
    String getJsonUrl();

    //TestZip
    @Key("zip.root")
    String getZipRoot();

    @Key("zip.out")
    String getZipOut();

    @Key("zip.name")
    String getZipName();

    @Key("zip.filename")
    String getZipFilename();

    @Key("zip.filename.full")
    String getZipFilenameFull();

    @Key("zip.filename.txt")
    String getZipFilenameTxt();

    @Key("zip.mkdir.root")
    String getZipMkdirRoot();

    @Key("zip.mkdir")
    String getZipMkdir();

    @Key("zip.read")
    String getZipReadDir();

    //TestFS
    @Key("chrome.driver.root")
    String getChromeDriverRoot();

    @Key("chrome.driver.file")
    String getChromeDriverFile();

    @Key("fs.delete")
    String getFsDelete();

    @Key("fs.file")
    String getFsFile();

    @Key("fs.attrs")
    String getFsAttrs();

    @Key("fs.attrs.name")
    String getFsAttrsK();

    @Key("fs.attrs.value")
    String getFsAttrsV();

    //TestReq
    @Key("req.body")
    String getReqBody();

    //TestInvoke
    @Key("i.class")
    String getFs();

    @Key("i.field")
    String getFsField();

    @Key("i.method.name")
    String getFsMethodName();

    @Key("i.method")
    String getFsMethod();
}
