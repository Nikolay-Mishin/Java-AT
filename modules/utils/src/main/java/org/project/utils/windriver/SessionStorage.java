package org.project.utils.windriver;

import java.beans.ConstructorProperties;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.SessionId;

public class SessionStorage extends LocalStorage {
    private org.openqa.selenium.html5.SessionStorage s;
    private SessionId id;

    @ConstructorProperties({"driver"})
    public SessionStorage(ChromeDriver driver) {
        super(driver);
        s = s(driver);
        id = id(driver);
    }

    public org.openqa.selenium.html5.SessionStorage s() {
        return s;
    }

    public org.openqa.selenium.html5.SessionStorage s(org.openqa.selenium.html5.SessionStorage s) {
        return this.s = s;
    }

    public org.openqa.selenium.html5.SessionStorage s(ChromeDriver driver) {
        return s = driver.getSessionStorage();
    }

    public SessionId id() {
        return id;
    }

    public SessionId id(SessionId id) {
        return this.id = id;
    }

    public SessionId id(ChromeDriver driver) {
        return id = driver.getSessionId();
    }
}
