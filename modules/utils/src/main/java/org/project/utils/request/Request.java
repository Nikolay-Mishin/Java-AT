package org.project.utils.request;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import jdk.jfr.Description;

import static org.project.utils.Helper.concat;
import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.lastTrim;
import static org.project.utils.Helper.sb;
import static org.project.utils.reflection.Reflection.getPropStr;

import org.project.utils.Helper;
import org.project.utils.config.ApiConfig;
import org.project.utils.constant.RequestConstants.METHOD;

public class Request extends RequestAuth {

    @ConstructorProperties({})
    public Request() {}

    @ConstructorProperties({"method", "pathList"})
    public Request(METHOD method, Object... pathList) throws MalformedURLException, URISyntaxException {
        init(method, pathList);
    }

    public Request init(METHOD method, Object... pathList) throws MalformedURLException, URISyntaxException {
        return init(new ApiConfig(), method, pathList);
    }

    public Request init(ApiConfig apiConfig, METHOD method, Object... pathList) throws MalformedURLException, URISyntaxException {
        request = apiConfig.get();
        //debug("getHeaders: " + getHeaders());
        this.method = method;
        methodSend = method.toString().toLowerCase();
        baseUrl(pathList); // задаем базовый путь для запроса
        //debug("getHeaders: " + getHeaders());
        endpoint = this.method + " " + path;
        debug(endpoint);
        debug(URL);
        debug(URI);
        debug(path);
        printFullPath();
        return this;
    }

    public static String getParamsUri(String uri, Object... args) {
        return uri + getParams(args);
    }

    public static String getParamsUriSlash(String uri, Object... args) {
        return uri + getParamsSlash(args);
    }

    public static String getParams(Object... args) {
        return getParamsPrefix("", args);
    }

    public static String getParamsSlash(Object... args) {
        return getParamsPrefix("/", args);
    }

    public static String getParamsPrefix(String prefix, Object... args) {
        String[] k = {""};
        String[] map = Helper.map(args, String[]::new, a -> {
            if (k[0].isEmpty()) return k[0] = a.toString();
            else {
                String v = sb("=", a.toString(), "&");
                k[0] = "";
                return v;
            }
        });
        lastTrim(map, "&$");
        return sb(concat(new String[]{prefix + "?"}, map));
    }

    @Description("Print full path")
    public void printFullPath() {
        debug("Full PATH is: " + url);
    }

    @Description("Print endpoint")
    public void printEndpoint() {
        debug(endpoint);
    }

    @Description("Print method")
    public void printMethod() {
        debug(method);
    }

    @Description("Print url")
    public void printUrl() {
        debug(url);
    }

    @Description("Print uri")
    public void printUri() {
        debug(URI);
    }

    @Description("Print path")
    public void printPath() {
        debug(path);
    }

    @Description("Print method string")
    public METHOD printMethodStr() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return (METHOD) getPropStr(this, "method", true);
    }

    @Description("Print request")
    public void print() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        printEndpoint();
        printMethod();
        printUrl();
        printUri();
        printPath();
        //printMethodStr();
    }

}
