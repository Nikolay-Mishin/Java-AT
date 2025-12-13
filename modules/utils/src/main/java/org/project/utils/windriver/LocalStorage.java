package org.project.utils.windriver;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.ExecuteMethod;
import org.openqa.selenium.remote.RemoteExecuteMethod;

import static org.project.utils.Helper.notNull;

import org.project.utils.base.HashMap;

/**
 *
 */
public class LocalStorage {
    /**
     *
     */
    protected ChromeDriver driver;
    /**
     *
     */
    protected org.openqa.selenium.html5.LocalStorage s;
    /**
     *
     */
    private JavascriptExecutor js;
    /**
     *
     */
    protected ExecuteMethod executeMethod;
    /**
     *
     */
    protected Set<String> keys;
    /**
     *
     */
    protected List<String> values;

    /**
     *
     * @param driver ChromeDriver
     */
    @ConstructorProperties({"driver"})
    public LocalStorage(ChromeDriver driver) {
        this.driver = driver;
        s = ls(driver);
        executeMethod(driver);
    }

    /**
     *
     * @return LocalStorage
     */
    public org.openqa.selenium.html5.LocalStorage ls() {
        return s;
    }

    /**
     *
     * @param s LocalStorage
     * @return LocalStorage
     */
    public org.openqa.selenium.html5.LocalStorage ls(org.openqa.selenium.html5.LocalStorage s) {
        return this.s = s;
    }

    /**
     *
     * @param driver ChromeDriver
     * @return LocalStorage
     */
    public org.openqa.selenium.html5.LocalStorage ls(ChromeDriver driver) {
        return s = driver.getLocalStorage();
    }

    /**
     *
     * @return JavascriptExecutor
     */
    public JavascriptExecutor js() {
        return js;
    }

    /**
     *
     * @param js JavascriptExecutor
     * @return JavascriptExecutor
     */
    public JavascriptExecutor js(JavascriptExecutor js) {
        return this.js = js;
    }

    /**
     *
     * @param driver ChromeDriver
     */
    public void executeMethod(ChromeDriver driver) {
        executeMethod = new RemoteExecuteMethod(driver);
    }

    /**
     *
     * @param script String
     * @return Object
     */
    public Object exec(String script) {
        return js.executeScript(script);
    }

    /**
     *
     * @param k String
     * @return String
     */
    public String get(String k) {
        return s.getItem(k);
    }

    /**
     *
     * @param k String
     * @param v String
     */
    public void set(String k, String v) {
        s.setItem(k, v);
    }

    /**
     *
     * @param k String
     * @return String
     */
    public String remove(String k) {
        return s.removeItem(k);
    }

    /**
     *
     */
    public void clear() {
        s.clear();
    }

    /**
     *
     * @param k String
     * @return boolean
     */
    public boolean exist(String k) {
        return notNull(get(k));
    }

    /**
     *
     * @return String
     */
    public String str() {
        return s.toString();
    }

    /**
     *
     * @return long
     */
    public long size() {
        return s.size();
    }

    /**
     *
     * @param i int
     * @return String
     */
    public String key(int i) {
        return keys().stream().toList().get(i);
    }

    /**
     *
     * @return HashMap {String, String}
     * @throws ReflectiveOperationException throws
     */
    public HashMap<String, String> items() throws ReflectiveOperationException {
        return new HashMap<String, String>(keys()).values(values());
    }

    /**
     *
     * @return Set {String}
     */
    public Set<String> keys() {
        return keys = s.keySet();
    }

    /**
     *
     * @return List {String}
     */
    public List<String> values() {
        values = new ArrayList<>();
        for (String k : keys()) values.add(get(k));
        return values;
    }
}
