package org.project.utils.test;

import java.util.List;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.table;

import org.project.utils.function.ConsumerWithExceptions;

/**
 *
 */
public class TestReq extends TestProps {
    /**
     *
     */
    protected static String body;
    /**
     *
     */
    protected static String bodyTest;

    /**
     *
     */
    public static void init() {
        debug("TestReq:init");
        TestProps.init();
        body = c.getReqBody();
        bodyTest = c.getReqTest();
    }

    /**
     *
     * @throws Exception throws
     */
    public static void testReqGet() throws Exception {
        debug("testReqGet");
        new Step().getOrder(200, 0);
    }

    /**
     *
     * @throws Exception throws
     */
    public static void testReqPost() throws Exception {
        debug("testReqPost");
        //[[id, 0], [petId, 0], [quantity, 0], [shipDate, 2023-09-11T09:37:57.828Z], [status, placed], [complete, true]]
        testReq(body, table -> new Step().postOrder(200, table));
    }

    /**
     *
     * @throws Exception throws
     */
    public static void testReqTest() throws Exception {
        debug("testReqTest");
        //[[id, 0], [category.id, 0], [category.name, string], [photoUrls, string], [name, doggie], [tags.id, 0], [tags.name, string], [status, available]]
        testReq(bodyTest, table -> new Step().postPet(200, table));
    }

    /**
     *
     * @param body String
     * @param cb ConsumerWithExceptions
     * @param <E> E
     * @throws Exception throws
     */
    public static <E extends Exception> void testReq(String body, ConsumerWithExceptions<List<List<String>>, E> cb) throws Exception {
        List<List<String>> table = table(body);
        debug(table);
        cb.accept(table);
    }

}
