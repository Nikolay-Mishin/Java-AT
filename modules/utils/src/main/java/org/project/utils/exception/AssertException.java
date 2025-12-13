package org.project.utils.exception;

import java.beans.ConstructorProperties;

/**
 *
 */
public class AssertException {
    /**
     *
     */
    private final Object value;

    /**
     *
     * @param value Object
     */
    @ConstructorProperties({"value"})
    public AssertException(Object value) {
        this.value = value;
    }

    /**
     *
     * @param value
     * @param compare
     * @param equals
     */
    private void _assert(Object value, Object compare, Boolean equals) {
        assert equals == (value == compare);
    }

    /**
     *
     * @param compare Object
     * @param equals Boolean
     */
    private void _assert(Object compare, Boolean equals) {
        _assert(value, compare, equals);
    }

    /**
     *
     * @param value Object
     */
    private void notNull(Object value) {
        _assert(value, null, false);
    }

    /**
     *
     * @throws NullPointerException throws
     */
    public void notNull() throws NullPointerException {
        notNull(value);
    }

    /**
     *
     * @param compare Object
     */
    public void  _equals(Object compare) {
        notNull(compare);
        _assert(compare, true);
    }

    /**
     *
     * @param compare Object
     */
    public void notEquals(Object compare) {
        notNull(compare);
        _assert(compare, false);
    }

}
