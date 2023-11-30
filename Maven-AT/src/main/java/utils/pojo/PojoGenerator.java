package utils.pojo;

import org.project.pojo.JsonSchemaToClass;
import org.project.pojo.JsonToClass;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static config.WebConfig.BASE_CONFIG;

public class PojoGenerator {

    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        new JsonSchemaToClass(BASE_CONFIG);
        new JsonToClass(BASE_CONFIG);
    }

}
