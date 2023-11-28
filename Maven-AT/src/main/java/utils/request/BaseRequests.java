package utils.request;

import io.restassured.response.Response;
import jdk.jfr.Description;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static utils.constant.RequestConstants.METHOD.*;

public class BaseRequests<T> {

    protected String baseUrl;

    @Description("Add a new object")
    public Response post(T model) throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return new Request(POST, baseUrl)
            .body(model)
            .response();
    }

    @Description("Find object by ID")
    public Response get(Long id) throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Request request = new Request(GET, baseUrl, id);
        //request.print();
        return request.response();
    }

    @Description("Update object")
    public Response put(T model) throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return new Request(PUT, baseUrl)
            .body(model)
            .response();
    }

    @Description("Delete object")
    public Response delete(Long id) throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return new Request(DELETE, baseUrl, id).response();
    }

}
