package org.project.utils.request;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.net.*;

import jdk.jfr.Description;

import static org.project.utils.Helper.debug;
import static org.project.utils.reflection.Reflection.*;

import org.project.utils.config.ApiConfig;
import org.project.utils.constant.RequestConstants.METHOD;

public class Request extends RequestAuth<Request> {

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
        this.method = method;
        methodSend = method.toString().toLowerCase();
        url(pathList); // задаем базовый путь для запроса
        endpoint = this.method + " " + path;
        debug(endpoint);
        debug(URL);
        debug(URI);
        debug(path);
        printFullPath();
        return this;
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
