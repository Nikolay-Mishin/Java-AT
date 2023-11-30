package utils.pojo;

import org.project.pojo.JsonSchemaToClass;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static config.WebConfig.BASE_CONFIG;
import static java.lang.System.out;
import static org.project.utils.fs.FS.listFiles;
import static org.project.utils.fs.FS.readDir;

public class PojoGenerator {

    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        new JsonSchemaToClass(BASE_CONFIG);
        String path = BASE_CONFIG.getJsonSchemaRoot();
        out.println("listFiles");
        listFiles(path);
        out.println("readDir");
        readDir(path);
    }

}
