package utils.base;

import org.project.utils.base.BaseStep;
import org.project.utils.request.BaseRequests;

import java.lang.reflect.InvocationTargetException;

import static config.WebConfig.BASE_CONFIG;

public class Step<R extends BaseRequests<M>, M> extends BaseStep<R, M> {

    public Step() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        super(BASE_CONFIG);
    }

}
