package org.project.utils.test;

import io.cucumber.java.ru.Когда;
import org.project.utils.base.BaseStep;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.project.utils.config.WebBaseConfig.BASE_CONFIG;

public class Step extends BaseStep<Requests, Order> {

    @ConstructorProperties({})
    public Step() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        super(BASE_CONFIG);
    }

    public void getOrder(int statusCode, int id)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, URISyntaxException, IOException {
        assertEquals(statusCode, get(id).getStatusCode());
    }

    @Когда("создать заказ gen статус {int}")
    public void postOrder(int statusCode, List<List<String>> dataTable)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, URISyntaxException, IOException {
        assertEquals(statusCode, post(dataTable).getStatusCode());
    }

}
