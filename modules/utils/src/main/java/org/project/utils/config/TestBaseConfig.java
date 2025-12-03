package org.project.utils.config;

import static org.aeonbits.owner.Config.Sources;
import static org.project.utils.config.TestConfig.config;

@Sources({"classpath:org.project.utils.test.properties"})
public interface TestBaseConfig extends BaseConfig {
    TestBaseConfig BASE_CONFIG = config(TestBaseConfig.class);

    //testApi
    @Key("api.ver")
    String getApiVer();

    @Key("api.uri")
    String getApiUri();

    @Key("api.endpoint")
    String getEndpoint();

    //testFS
    @Key("chrome.driver.root")
    String getChromeDriverRoot();

    @Key("chrome.driver.file")
    String getChromeDriverFile();

    @Key("fs.attrs")
    String getFsAttrs();

    //testZip
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
}
