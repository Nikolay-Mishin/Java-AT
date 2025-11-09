package utils.pojo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static config.WebConfig.BASE_CONFIG;

public class PojoGenerator {

    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        new org.project.pojo.PojoGenerator(BASE_CONFIG);
    }

}
