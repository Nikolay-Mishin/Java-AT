package utils.base;

import utils.Request;

public class Step<R extends Request, M extends Model<M>> {

    protected final R req;
    protected final Class<M> modelClass;

    public Step(R req, Class<M> modelClass) {
        this.req = req;
        this.modelClass = modelClass;
    }

}
