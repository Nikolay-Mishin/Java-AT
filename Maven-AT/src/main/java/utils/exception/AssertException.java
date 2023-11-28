package utils.exception;

public class AssertException {

    private final Object value;

    public AssertException(Object value) {
        this.value = value;
    }

    private void _assert(Object value, Object compare, Boolean equals) {
        assert equals == (value == compare);
    }

    private void _assert(Object compare, Boolean equals) {
        _assert(value, compare, equals);
    }

    private void notNull(Object value) {
        _assert(value, null, false);
    }

    public void notNull() throws NullPointerException {
        notNull(value);
    }

    public void  _equals(Object compare) {
        notNull(compare);
        _assert(compare, true);
    }

    public void notEquals(Object compare) {
        notNull(compare);
        _assert(compare, false);
    }

}
