package org.project.utils.request;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static org.project.utils.Helper.concat;
import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.lastTrim;
import static org.project.utils.Helper.sb;
import static org.project.utils.reflection.Reflection.getPropStr;

import org.project.utils.Helper;
import org.project.utils.config.ApiConfig;
import org.project.utils.constant.RequestConstants.METHOD;

/**
 *
 */
public class Request extends RequestAuth {

    /**
     *
     */
    @ConstructorProperties({})
    public Request() {}

    /**
     *
     * @param method METHOD
     * @param pathList Object[]
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     */
    @ConstructorProperties({"method", "pathList"})
    public Request(METHOD method, Object... pathList) throws MalformedURLException, URISyntaxException {
        init(method, pathList);
    }

    /**
     *
     * @param method METHOD
     * @param pathList Object[]
     * @return Request
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     */
    public Request init(METHOD method, Object... pathList) throws MalformedURLException, URISyntaxException {
        return init(new ApiConfig(), method, pathList);
    }

    /**
     *
     * @param apiConfig ApiConfig
     * @param method METHOD
     * @param pathList Object[]
     * @return Request
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     */
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

    /**
     *
     * @param uri String
     * @param args Object[]
     * @return String
     */
    public static String getParamsUri(String uri, Object... args) {
        return uri + getParams(args);
    }

    /**
     *
     * @param uri String
     * @param args Object[]
     * @return String
     */
    public static String getParamsUriSlash(String uri, Object... args) {
        return uri + getParamsSlash(args);
    }

    /**
     *
     * @param args Object[]
     * @return String
     */
    public static String getParams(Object... args) {
        return getParamsPrefix("", args);
    }

    /**
     *
     * @param args Object[]
     * @return String
     */
    public static String getParamsSlash(Object... args) {
        return getParamsPrefix("/", args);
    }

    /**
     *
     * @param prefix String
     * @param args Object[]
     * @return String
     */
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
        return sb((Object[]) concat(new String[]{prefix + "?"}, map));
    }

    /**
     * Print full path
     */
    public void printFullPath() {
        debug("Full PATH is: " + url);
    }

    /**
     * Print endpoint
     */
    public void printEndpoint() {
        debug(endpoint);
    }

    /**
     * Print method
     */
    public void printMethod() {
        debug(method);
    }

    /**
     * Print url
     */
    public void printUrl() {
        debug(url);
    }

    /**
     * Print uri
     */
    public void printUri() {
        debug(URI);
    }

    /**
     * Print path
     */
    public void printPath() {
        debug(path);
    }

    /**
     * Print method string
     * @return METHOD
     * @throws InvocationTargetException throws
     * @throws IllegalAccessException throws
     * @throws NoSuchMethodException throws
     */
    public METHOD printMethodStr() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return (METHOD) getPropStr(this, "method", true);
    }

    /**
     * Print request
     * @throws InvocationTargetException throws
     * @throws IllegalAccessException throws
     * @throws NoSuchMethodException throws
     */
    public void print() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        printEndpoint();
        printMethod();
        printUrl();
        printUri();
        printPath();
        //printMethodStr();
    }

}
