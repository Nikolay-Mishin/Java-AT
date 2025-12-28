package org.project.utils.request;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.function.BiFunction;
import java.util.function.Function;

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
     * @throws ReflectiveOperationException throws
     */
    public static Request req(METHOD method, Object... pathList) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return req(new Request(method, pathList));
    }

    /**
     *
     * @param uri String
     * @param method METHOD
     * @param pathList Object[]
     * @return Request
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static Request req(String uri, METHOD method, Object... pathList) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return req(new Request(method, pathList).uri(uri));
    }

    /**
     *
     * @param method METHOD
     * @param query String
     * @param pathList Object[]
     * @return Request
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static Request query(METHOD method, String query, Object... pathList) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return req(new Request(method, pathList).query(query));
    }

    /**
     *
     * @param uri String
     * @param method METHOD
     * @param query String
     * @param pathList Object[]
     * @return Request
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static Request query(String uri, METHOD method, String query, Object... pathList) throws MalformedURLException, URISyntaxException, ReflectiveOperationException
    {
        return req(new Request(method, pathList).uri(uri).query(query));
    }

    /**
     *
     * @param req Request
     * @return Request
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    protected static Request req(Request req) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        debug("fullPath: " + req.fullPath());
        return req;
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
        return getParamsPrefix("?", args);
    }

    /**
     *
     * @param args Object[]
     * @return String
     */
    public static String getParamsSlash(Object... args) {
        return getParamsPrefix("/?", args);
    }

    /**
     *
     * @param prefix String
     * @param args Object[]
     * @return String
     */
    public static String getParamsPrefix(String prefix, Object... args) {
        return paramsStr(prefix, "", "&", a -> a, (a, sep) -> sb("=", a, sep), args);
    }

    /**
     *
     * @param before String
     * @param after String
     * @param sep String
     * @param k Function {Object, R}
     * @param v Function {Object, R}
     * @param args Object[]
     * @return String
     * @param <R> R
     */
    public static <R> String paramsStr(String before, String after, String sep, Function<Object, R> k, BiFunction<Object, String, R> v, Object... args) {
        return sb(params(before, after, sep, k, v, args));
    }

    /**
     *
     * @param before String
     * @param after String
     * @param sep String
     * @param k Function {Object, R}
     * @param v Function {Object, R}
     * @param args Object[]
     * @return Object[]
     * @param <R> R
     */
    public static <R> Object[] params(String before, String after, String sep, Function<Object, R> k, BiFunction<Object, String, R> v, Object... args) {
        Object[] _k = {""};
        Object[] map = Helper.map(args, Object[]::new, a -> {
            if (_k[0].toString().isEmpty()) return _k[0] = k.apply(a);
            else {
                _k[0] = "";
                return v.apply(a, sep);
            }
        });
        lastTrim(map, sep + "$");
        return concat(new Object[]{before}, concat(map, new Object[]{after}));
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
