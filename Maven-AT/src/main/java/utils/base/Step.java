package utils.base;

import utils.Request;

public class Step<R extends Request, M extends Model<M>> {

    protected final R req;
    protected final Class<M> modelClass;

    public Step(R _req, Class<M> _modelClass) {
        req = _req;
        modelClass = _modelClass;
    }

}
