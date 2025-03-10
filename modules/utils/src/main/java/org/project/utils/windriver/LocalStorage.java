package org.project.utils.windriver;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;

public class LocalStorage {
    protected JavascriptExecutor js;

    @ConstructorProperties({})
    public LocalStorage(JavascriptExecutor js) {
        this.js = js;
    }

    public Object exec(String script) {
        return js.executeScript(script);
    }

    public String get(String key) {
        return (String) exec(String.format("return localStorage.getItem('%s');", key));
    }

    public void set(String key, String value) {
        exec(String.format("localStorage.setItem('%s', '%s');", key, value));
    }

    public void remove(String key) {
        exec(String.format("localStorage.removeItem('%s');", key));
    }

    public void clear() {
        exec("localStorage.clear();");
    }

    public boolean exist(String key) {
        return (boolean) exec(String.format("return localStorage.getItem('%s') !== null;", key));
    }

    public Map<String, String> items() {
        return (Map<String, String>) exec(
            "let items = {}; " +
                "for (let i = 0; i < localStorage.length; i++) { " +
                "    items[localStorage.key(i)] = localStorage.getItem(localStorage.key(i)); " +
                "} " +
                "return items;");
    }

    public String itemsStr() {
        return (String) exec("return JSON.stringify(localStorage);");
    }

    public long size() {
        return (long) exec("return localStorage.length;");
    }

    public String getKey(int index) {
        return (String) exec(String.format("return localStorage.key(%d);", index));
    }

    public List<String> keys() {
        return (List<String>) exec(
            "let keys = []; " +
                "for (let i = 0; i < localStorage.length; i++) { " +
                "    keys.push(localStorage.key(i)); " +
                "} " +
                "return keys;");
    }

    public List<String> values() {
        return (List<String>) exec(
            "let values = []; " +
                "for (let i = 0; i < localStorage.length; i++) { " +
                "    values.push(localStorage.getItem(localStorage.key(i))); " +
                "} " +
                "return values;");
    }
}
