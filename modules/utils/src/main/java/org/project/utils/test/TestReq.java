package org.project.utils.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.table;

public class TestReq extends TestApi {
    protected static String body;

    public TestReq() {
        body = c.getReqBody();
    }

    public static void main(String[] args) throws IOException, URISyntaxException, ReflectiveOperationException {
        testReq();
    }

    public static void testReq()
        throws IOException, URISyntaxException, ReflectiveOperationException {
        //testReqGet();
        testReqPost();
    }

    public static void testReqGet()
        throws IOException, URISyntaxException, ReflectiveOperationException {
        debug("testReqGet");
        new Step().getOrder(200, 0);
    }

    public static void testReqPost() throws IOException, URISyntaxException, ReflectiveOperationException {
        debug("testReqPost");
        //[[id, 0], [petId, 0], [quantity, 0], [shipDate, 2023-09-11T09:37:57.828Z], [status, placed], [complete, true]]
        List<List<String>> table = table(body);
        debug(table);
        new Step().postOrder(200, table);
    }

}
