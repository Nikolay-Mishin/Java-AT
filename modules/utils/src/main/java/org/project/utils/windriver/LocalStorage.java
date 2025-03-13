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
    protected Set<String> values;
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
        //return (String) exec(String.format("return localStorage.getItem('%s');", k));
        return s.getItem(k);
    }

    public void set(String k, String v) {
        //exec(String.format("localStorage.setItem('%s', '%s');", k, v));
        s.setItem(k, v);
    }

    public String remove(String k) {
        //return (String) exec(String.format("return localStorage.removeItem('%s');", k));
        return s.removeItem(k);
    }

    public void clear() {
        //exec("localStorage.clear();");
        s.clear();
    }

    public boolean exist(String k) {
        //return (boolean) exec(String.format("return localStorage.getItem('%s') !== null;", k));
        return get(k)!= null;
    }

    public String str() {
        //return (String) exec("return JSON.stringify(localStorage);");
        return s.toString();
    }

    public long size() {
        //return (long) exec("return localStorage.length;");
        return s.size();
    }

    public String key(int i) {
        //return (String) exec(String.format("return localStorage.key(%d);", i));
        return keys().stream().toList().get(i);
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, String> items() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    //public Map<String, String> items() {
        /*
        return (Map<String, String>) exec(
            "let items = {}; " +
                "for (let i = 0; i < localStorage.length; i++) { " +
                "    items[localStorage.key(i)] = localStorage.getItem(localStorage.key(i)); " +
                "} " +
                "return items;"
        );
        */
        return new HashMap<String, String>(keys()).values(values());
    }

    @SuppressWarnings("unchecked")
    public Set<String> keys() {
    //public List<String> keys() {
        /*
        return (List<String>) exec(
            "let keys = []; " +
                "for (let i = 0; i < localStorage.length; i++) { " +
                "    keys.push(localStorage.key(i)); " +
                "} " +
                "return keys;"
        );
        */
        return keys = s.keySet();
    }

    @SuppressWarnings("unchecked")
    public Set<String> values() {
    //public List<String> values() {
        /*
        return (List<String>) exec(
            "let values = []; " +
                "for (let i = 0; i < localStorage.length; i++) { " +
                "    values.push(localStorage.getItem(localStorage.key(i))); " +
                "} " +
                "return values;"
        );
        */
        values = new HashSet<>();
        for (String k : keys()) values.add(get(k));
        return values;
    }
}
