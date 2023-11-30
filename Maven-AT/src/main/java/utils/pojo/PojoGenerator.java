package utils.pojo;

import org.project.pojo.JsonSchemaToClass;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static config.WebConfig.BASE_CONFIG;

public class PojoGenerator {

    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        new JsonSchemaToClass(BASE_CONFIG);
        /*String path = BASE_CONFIG.getJsonSchemaRoot();
        out.println("listFiles");
        listFiles(path);
        out.println("readDir");
        readDir(path);*/
    }

}
