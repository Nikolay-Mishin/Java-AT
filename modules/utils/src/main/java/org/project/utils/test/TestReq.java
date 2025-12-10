package org.project.utils.test;

import java.util.List;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.table;

public class TestReq extends BaseTest {
    protected static String body;

    public TestReq() {
        body = c.getReqBody();
    }

    public static void main(String[] args) throws Exception {
        testReq();
    }

    public static void testReq() throws Exception {
        //testReqGet();
        testReqPost();
    }

    public static void testReqGet() throws Exception {
        debug("testReqGet");
        new Step().getOrder(200, 0);
    }

    public static void testReqPost() throws Exception {
        debug("testReqPost");
        //[[id, 0], [petId, 0], [quantity, 0], [shipDate, 2023-09-11T09:37:57.828Z], [status, placed], [complete, true]]
        List<List<String>> table = table(body);
        debug(table);
        new Step().postOrder(200, table);
    }

}
