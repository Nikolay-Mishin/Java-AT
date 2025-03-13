package org.project.utils.windriver;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.ExecuteMethod;
import org.openqa.selenium.remote.RemoteExecuteMethod;

import org.project.utils.base.HashMap;

public class LocalStorage {
    protected ChromeDriver driver;
    protected org.openqa.selenium.html5.LocalStorage s;
    protected ExecuteMethod executeMethod;
    protected Set<String> keys;
    protected List<String> values;
    private JavascriptExecutor js;

    @ConstructorProperties({})
    public LocalStorage() {}

    @ConstructorProperties({"js"})
    public LocalStorage(JavascriptExecutor js) {
        this.js = js;
    }

    @ConstructorProperties({"driver"})
    public LocalStorage(ChromeDriver driver) {
        this.driver = driver;
        s = driver.getLocalStorage();
        executeMethod(driver);
    }

    public void executeMethod(ChromeDriver driver) {
        executeMethod = new RemoteExecuteMethod(driver);
    }

    public Object exec(String script) {
        return js.executeScript(script);
    }

    public String get(String k) {
        return s.getItem(k);
    }

    public void set(String k, String v) {
        s.setItem(k, v);
    }

    public String remove(String k) {
        return s.removeItem(k);
    }

    public void clear() {
        s.clear();
    }

    public boolean exist(String k) {
        return get(k)!= null;
    }

    public String str() {
        return s.toString();
    }

    public long size() {
        return s.size();
    }

    public String key(int i) {
        return keys().stream().toList().get(i);
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, String> items() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return new HashMap<String, String>(keys()).values(values());
    }

    @SuppressWarnings("unchecked")
    public Set<String> keys() {
        return keys = s.keySet();
    }

    @SuppressWarnings("unchecked")
    public List<String> values() {
        values = new ArrayList<>();
        for (String k : keys()) values.add(get(k));
        return values;
    }
}
