package org.project.utils.windriver;

import java.beans.ConstructorProperties;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.SessionId;

/**
 *
 */
public class SessionStorage extends LocalStorage {
    /**
     *
     */
    private org.openqa.selenium.html5.SessionStorage s;
    /**
     *
     */
    private SessionId id;

    /**
     *
     * @param driver ChromeDriver
     */
    @ConstructorProperties({"driver"})
    public SessionStorage(ChromeDriver driver) {
        super(driver);
        s = s(driver);
        id = id(driver);
    }

    /**
     *
     * @return SessionStorage
     */
    public org.openqa.selenium.html5.SessionStorage s() {
        return s;
    }

    /**
     *
     * @param s SessionStorage
     * @return SessionStorage
     */
    public org.openqa.selenium.html5.SessionStorage s(org.openqa.selenium.html5.SessionStorage s) {
        return this.s = s;
    }

    /**
     *
     * @param driver ChromeDriver
     * @return SessionStorage
     */
    public org.openqa.selenium.html5.SessionStorage s(ChromeDriver driver) {
        return s = driver.getSessionStorage();
    }

    /**
     *
     * @return SessionId
     */
    public SessionId id() {
        return id;
    }

    /**
     *
     * @param id SessionId
     * @return SessionId
     */
    public SessionId id(SessionId id) {
        return this.id = id;
    }

    /**
     *
     * @param driver ChromeDriver
     * @return SessionId
     */
    public SessionId id(ChromeDriver driver) {
        return id = driver.getSessionId();
    }

}
