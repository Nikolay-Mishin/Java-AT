package org.project.utils.base;

import static java.lang.Long.valueOf;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import io.restassured.response.Response;

import static org.project.utils.Helper.debug;
import static org.project.utils.config.WebConfig.config;
import static org.project.utils.reflection.Instance.create;
import static org.project.utils.reflection.Reflection.getGenericClass;

import org.project.utils.config.WebBaseConfig;
import org.project.utils.function.FunctionWithExceptions;
import org.project.utils.function.SupplierWithExceptions;
import org.project.utils.request.BaseRequests;

public class BaseStep<R extends BaseRequests<M>, M> {

    protected R req;
    protected Class<M> modelClass;
    protected Long id;
    protected HashMap<String, Class<?>> hashMap;

    public BaseStep()
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        init();
    }

    public BaseStep(Class<R> req, Class<M> modelClass)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        init(req, modelClass);
    }

    public BaseStep(R req, Class<M> modelClass) {
        init(req, modelClass);
    }

    public BaseStep(WebBaseConfig config)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        init(config);
    }

    public BaseStep(WebBaseConfig config, Class<R> req, Class<M> modelClass)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        init(config, req, modelClass);
    }

    public BaseStep(WebBaseConfig config, R req, Class<M> modelClass) {
        init(config, req, modelClass);
    }

    protected void init(WebBaseConfig config)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        init(config, getGenericClass(), getGenericClass(1));
    }

    protected void init(WebBaseConfig config, Class<R> req, Class<M> modelClass)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        init(config, create(req), modelClass);
    }

    protected void init(WebBaseConfig config, R req, Class<M> modelClass) {
        debug("BaseStep#initConfig");
        config(config);
        init(req, modelClass);
    }

    protected void init() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        init(getGenericClass(), getGenericClass(1));
    }

    protected void init(Class<R> req, Class<M> modelClass)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        init(create(req), modelClass);
    }

    protected void init(R req, Class<M> modelClass) {
        debug("BaseStep#init");
        this.req = req;
        this.modelClass = modelClass;
        debug("req: " + req);
        debug("modelClass: " + modelClass);
    }

    protected Response get() throws Exception {
        return get(id);
    }

    protected Response get(int id) throws Exception {
        //return get((long) id);
        return get(valueOf(id));
    }

    protected Response get(Long id) throws Exception {
        return req(() -> req.get(id));
    }

    protected Response post(List<List<String>> dataTable) throws Exception {
        return req(dataTable, m -> req.post(m));
    }

    protected Response put(List<List<String>> dataTable) throws Exception {
        return req(dataTable, m -> req.put(m));
    }

    protected Response patch(List<List<String>> dataTable) throws Exception {
        return req(dataTable, m -> req.patch(m));
    }

    protected Response delete() throws Exception {
        return delete(id);
    }

    protected Response delete(int id) throws Exception {
        //return delete((long) id);
        return delete(valueOf(id));
    }

    protected Response delete(Long id) throws Exception {
        return req(() -> req.delete(id));
    }

    protected <E extends Exception> Response req(List<List<String>> dataTable, FunctionWithExceptions<M, Response, E> cb) throws Exception {
        return req(() -> {
            M model = new Model<>(modelClass, dataTable, req.baseUrl()).get();
            Response resp = cb.apply(model);
            //id = resp.jsonPath().get("id");
            //id = parseLong(resp.path("id").toString());
            id = resp.path("id");
            debug(id);
            return resp;
        });
    }

    protected <E extends Exception> Response req(SupplierWithExceptions<Response, E> cb) throws IOException, URISyntaxException, ReflectiveOperationException, E {
        Response resp = cb.get();
        debug(resp.getStatusCode());
        return resp;
    }

}
