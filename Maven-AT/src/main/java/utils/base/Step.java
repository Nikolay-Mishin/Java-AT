package utils.base;

import utils.Request;

import java.lang.reflect.InvocationTargetException;

import static java.lang.System.out;
import static utils.reflections.Instance.create;
import static utils.reflections.Reflection.getGenericClass;

public class Step<R extends Request, M> {

    protected R req;
    protected Class<M> modelClass;

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

    private void init(R req, Class<M> modelClass) {
        this.req = req;
        this.modelClass = modelClass;
    }

}
