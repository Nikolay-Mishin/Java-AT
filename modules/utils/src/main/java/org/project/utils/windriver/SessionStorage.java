package org.project.utils.windriver;

import java.beans.ConstructorProperties;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.SessionId;

public class SessionStorage extends LocalStorage {
    private SessionId id;
    private org.openqa.selenium.html5.SessionStorage s;

    @ConstructorProperties({"s"})
    public SessionStorage(org.openqa.selenium.html5.SessionStorage s) {
        super();
        this.s = s;
    }

    @ConstructorProperties({"driver"})
    public SessionStorage(ChromeDriver driver) {
        super();
        this.driver = driver;
        id = driver.getSessionId();
        s = driver.getSessionStorage();
        executeMethod(driver);
    }

    public SessionId id() {
        return id;
    }
}
