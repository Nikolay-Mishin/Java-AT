package utils.pojo;

import org.project.pojo.JsonSchemaToClass;

import java.io.IOException;

import static config.WebConfig.BASE_CONFIG;
import static java.lang.System.out;
import static org.project.utils.fs.FS.*;

public class PojoGenerator {

    public static void main(String[] args) throws IOException {
        new JsonSchemaToClass(BASE_CONFIG);
        String path = BASE_CONFIG.getJsonSchemaRoot();
        out.println("listFilesForFolder");
        listFilesForFolder(path);
        out.println("listFiles");
        listFiles(path);
        out.println("readDir");
        readDir(path);
        /*readDir(path).forEach(file -> {
            out.println(file);
            out.println(file.getClass());
            out.println(file.toAbsolutePath());
            try {
                out.println(file.toRealPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            out.println(file.getFileName());
            out.println(file.getRoot());
            out.println(file.getParent());
            out.println(file.getNameCount());
            out.println(file.getFileSystem());
            out.println(file.getName(0));
        });*/
        /*listFiles(path).forEach(file -> {
            out.println(file);
            out.println(file.getClass());
            out.println(file.toAbsolutePath());
            try {
                out.println(file.toRealPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            out.println(file.getFileName());
            out.println(file.getRoot());
            out.println(file.getParent());
            out.println(file.getNameCount());
            out.println(file.getFileSystem());
            out.println(file.getName(0));
        });*/
    }

}
