package utils.base;

import io.restassured.response.Response;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import static java.lang.System.out;
import static utils.reflections.Instance.create;
import static utils.reflections.Reflection.getGenericClass;

public class Step<R extends BaseRequests<M>, M> {

    protected R req;
    protected Class<M> modelClass;
    protected Long id;
    protected HashMap<String, Class<?>> hashMap;

    public Step(R req, Class<M> modelClass) throws ClassNotFoundException {
        init(req, modelClass);
    }

    public Step(Class<R> req, Class<M> modelClass)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        init(create(req), modelClass);
    }

    public Step() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class<R> t1 = getGenericClass();
        Class<M> t2 = getGenericClass(1);
        out.println("req: " + t1);
        out.println("modelClass: " + t2);
        init(create(t1), t2);
    }

    protected void init(R req, Class<M> modelClass) {
        this.req = req;
        this.modelClass = modelClass;
    }

    protected Response get() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        Response resp = req.get(id);
        out.println(resp.getStatusCode());
        return resp;
    }

    protected Response post(List<List<String>> dataTable) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, URISyntaxException {
        M order = new Model<>(modelClass, dataTable, hashMap).get();
        Response resp = req.post(order);
        //id = resp.jsonPath().get("id");
        id = resp.path("id");
        out.println(id);
        out.println(resp.getStatusCode());
        return resp;
    }

    protected Response put(List<List<String>> dataTable) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, URISyntaxException {
        M order = new Model<>(modelClass, dataTable).get();
        Response resp = req.put(order);
        id = resp.path("id");
        out.println(id);
        out.println(resp.getStatusCode());
        return resp;
    }

    protected Response delete() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        Response resp = req.delete(id);
        out.println(resp.getStatusCode());
        return resp;
    }

}
