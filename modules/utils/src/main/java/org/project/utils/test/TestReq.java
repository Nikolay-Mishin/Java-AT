package org.project.utils.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.project.utils.Helper.*;

public class TestReq {

    public static void main(String[] args)
        throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, URISyntaxException
    {
        testReq();
    }

    public static void testReq()
        throws IOException, URISyntaxException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException, InstantiationException
    {
        //testReqGet();
        testReqPost();
    }

    public static void testReqGet()
        throws IOException, URISyntaxException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException, InstantiationException
    {
        new Step().getOrder(200, 0);
    }

    public static void testReqPost()
        throws IOException, URISyntaxException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException, InstantiationException
    {
        //[[id, 0], [petId, 0], [quantity, 0], [shipDate, 2023-09-11T09:37:57.828Z], [status, placed], [complete, true]]
        Integer[][] arrayInt = {{1, 2}};
        String[][] arrayStr = {{"id", "0"}};
        String[][] array = {{"id", "0"}, {"petId", "0"}, {"quantity", "0"}, {"shipDate", "2023-09-11T09:37:57.828Z"}, {"status", "placed"}, {"complete", "true"}};
        debug(Arrays.toString(arrayInt));
        debug(Arrays.toString(arrayStr));
        debug(Arrays.toString(array));
        List<List<Integer>> tableInt = table(arrayInt);
        List<List<String>> tableStr = table(arrayStr);
        List<List<String>> table = table(array);
        debug(tableInt);
        debug(tableStr);
        debug(table);
        new Step().postOrder(200, table);
    }

}
