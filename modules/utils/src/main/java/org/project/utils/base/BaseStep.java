package org.project.utils.base;

import static java.lang.Long.valueOf;

import java.beans.ConstructorProperties;
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

/**
 *
 * @param <R>
 * @param <M>
 */
public class BaseStep<R extends BaseRequests<M>, M> {
    /**
     *
     */
    protected R req;
    /**
     *
     */
    protected Class<M> modelClass;
    /**
     *
     */
    protected Long id;
    /**
     *
     */
    protected HashMap<String, Class<?>> hashMap;

    /**
     *
     * @throws ClassNotFoundException throws
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     */
    @ConstructorProperties({})
    public BaseStep() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        init();
    }

    /**
     *
     * @param req Class R
     * @param modelClass Class M
     * @throws ClassNotFoundException throws
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     */
    @ConstructorProperties({"req", "modelClass"})
    public BaseStep(Class<R> req, Class<M> modelClass) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        init(req, modelClass);
    }

    /**
     *
     * @param req R
     * @param modelClass Class M
     */
    @ConstructorProperties({"req", "modelClass"})
    public BaseStep(R req, Class<M> modelClass) {
        init(req, modelClass);
    }

    /**
     *
     * @param config WebBaseConfig
     * @throws ClassNotFoundException throws
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     */
    @ConstructorProperties({"config"})
    public BaseStep(WebBaseConfig config) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        init(config);
    }

    /**
     *
     * @param config WebBaseConfig
     * @param req Class R
     * @param modelClass Class M
     * @throws ClassNotFoundException throws
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     */
    @ConstructorProperties({"config", "req", "modelClass"})
    public BaseStep(WebBaseConfig config, Class<R> req, Class<M> modelClass)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        init(config, req, modelClass);
    }

    /**
     *
     * @param config WebBaseConfig
     * @param req R
     * @param modelClass Class M
     */
    @ConstructorProperties({"config", "req", "modelClass"})
    public BaseStep(WebBaseConfig config, R req, Class<M> modelClass) {
        init(config, req, modelClass);
    }

    /**
     *
     * @param config WebBaseConfig
     * @throws ClassNotFoundException throws
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     */
    protected void init(WebBaseConfig config)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        init(config, getGenericClass(), getGenericClass(1));
    }

    /**
     *
     * @param config WebBaseConfig
     * @param req Class R
     * @param modelClass Class M
     * @throws ClassNotFoundException throws
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     */
    protected void init(WebBaseConfig config, Class<R> req, Class<M> modelClass)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        init(config, create(req), modelClass);
    }

    /**
     *
     * @param config WebBaseConfig
     * @param req R
     * @param modelClass Class M
     */
    protected void init(WebBaseConfig config, R req, Class<M> modelClass) {
        debug("BaseStep#initConfig");
        config(config);
        init(req, modelClass);
    }

    /**
     *
     * @throws ClassNotFoundException throws
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     */
    protected void init() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        init(getGenericClass(), getGenericClass(1));
    }

    /**
     *
     * @param req Class R
     * @param modelClass Class M
     * @throws ClassNotFoundException throws
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     */
    protected void init(Class<R> req, Class<M> modelClass)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        init(create(req), modelClass);
    }

    /**
     *
     * @param req R
     * @param modelClass Class M
     */
    protected void init(R req, Class<M> modelClass) {
        debug("BaseStep#init");
        this.req = req;
        this.modelClass = modelClass;
        debug("req: " + req);
        debug("modelClass: " + modelClass);
    }

    /**
     *
     * @return Response
     * @throws Exception throws
     */
    protected Response get() throws Exception {
        return get(id);
    }

    /**
     *
     * @param id int
     * @return Response
     * @throws Exception throws
     */
    protected Response get(int id) throws Exception {
        //return get((long) id);
        return get(valueOf(id));
    }

    /**
     *
     * @param id Long
     * @return Response
     * @throws Exception throws
     */
    protected Response get(Long id) throws Exception {
        return req(() -> req.get(id));
    }

    /**
     *
     * @param dataTable List of List {String}
     * @return Response
     * @throws Exception throws
     */
    protected Response post(List<List<String>> dataTable) throws Exception {
        return req(dataTable, m -> req.post(m));
    }

    /**
     *
     * @param dataTable List of List {String}
     * @return Response
     * @throws Exception throws
     */
    protected Response put(List<List<String>> dataTable) throws Exception {
        return req(dataTable, m -> req.put(m));
    }

    /**
     *
     * @param dataTable List of List {String}
     * @return Response
     * @throws Exception throws
     */
    protected Response patch(List<List<String>> dataTable) throws Exception {
        return req(dataTable, m -> req.patch(m));
    }

    /**
     *
     * @return Response
     * @throws Exception throws
     */
    protected Response delete() throws Exception {
        return delete(id);
    }

    /**
     *
     * @param id int
     * @return Response
     * @throws Exception throws
     */
    protected Response delete(int id) throws Exception {
        //return delete((long) id);
        return delete(valueOf(id));
    }

    /**
     *
     * @param id Long
     * @return Response
     * @throws Exception throws
     */
    protected Response delete(Long id) throws Exception {
        return req(() -> req.delete(id));
    }

    /**
     *
     * @param dataTable List of List {String}
     * @param cb FunctionWithExceptions {Response, E}
     * @return Response
     * @param <E> E
     * @throws Exception throws
     */
    protected <E extends Exception> Response req(List<List<String>> dataTable, FunctionWithExceptions<M, Response, E> cb) throws Exception {
        return req(() -> {
            M model = new Model<>(modelClass, dataTable, hashMap, req.baseUrl()).get();
            Response resp = cb.apply(model);
            //id = resp.jsonPath().get("id");
            //id = parseLong(resp.path("id").toString());
            id = resp.path("id");
            debug(id);
            return resp;
        });
    }

    /**
     *
     * @param cb SupplierWithExceptions {Response, E}
     * @return Response
     * @param <E> E
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     * @throws E throws
     */
    protected <E extends Exception> Response req(SupplierWithExceptions<Response, E> cb) throws IOException, URISyntaxException, ReflectiveOperationException, E {
        Response resp = cb.get();
        debug(resp.getStatusCode());
        return resp;
    }

}
