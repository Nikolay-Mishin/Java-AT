package org.project.utils.base;

import static java.lang.Long.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.HashMap;
import java.util.List;

import io.restassured.response.Response;

import static org.project.utils.Helper.debug;
import static org.project.utils.config.WebConfig.config;
import static org.project.utils.reflection.Instance.create;
import static org.project.utils.reflection.Reflection.getGenericClass;

import org.project.utils.config.WebBaseConfig;
import org.project.utils.request.BaseRequests;

public class BaseStep<R extends BaseRequests<M>, M> {

    protected R req;
    protected Class<M> modelClass;
    protected Long id;
    protected HashMap<String, Class<?>> hashMap;

    public BaseStep(WebBaseConfig config)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        config(config);
        init();
    }

    public BaseStep(WebBaseConfig config, Class<R> req, Class<M> modelClass)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        config(config);
        init(req, modelClass);
    }

    public BaseStep(WebBaseConfig config, R req, Class<M> modelClass) {
        config(config);
        init(req, modelClass);
    }

    protected void init() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class<R> t1 = getGenericClass();
        Class<M> t2 = getGenericClass(1);
        init(t1, t2);
    }

    protected void init(Class<R> req, Class<M> modelClass)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        init(create(req), modelClass);
    }

    protected void init(R req, Class<M> modelClass) {
        debug("BaseStep#init");
        this.req = req;
        this.modelClass = modelClass;
        debug("req: " + req);
        debug("modelClass: " + modelClass);
    }

    protected Response get() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        return get(id);
    }

    protected Response get(int id) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        //return get((long) id);
        return get(valueOf(id));
    }

    protected Response get(Long id) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        Response resp = req.get(id);
        debug(resp.getStatusCode());
        return resp;
    }

    protected Response post(List<List<String>> dataTable) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, URISyntaxException {
        M model = new Model<>(modelClass, dataTable, hashMap, req.baseUrl()).get();
        Response resp = req.post(model);
        //id = resp.jsonPath().get("id");
        id = parseLong(resp.path("id").toString());
        debug(id);
        debug(resp.getStatusCode());
        return resp;
    }

    protected Response put(List<List<String>> dataTable) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, URISyntaxException {
        M model = new Model<>(modelClass, dataTable, req.baseUrl()).get();
        Response resp = req.put(model);
        id = resp.path("id");
        debug(id);
        debug(resp.getStatusCode());
        return resp;
    }

    protected Response delete() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        return delete(id);
    }

    protected Response delete(int id) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        //return delete((long) id);
        return delete(valueOf(id));
    }

    protected Response delete(Long id) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        Response resp = req.delete(id);
        debug(resp.getStatusCode());
        return resp;
    }

}
