package utils.pojo;

import org.project.pojo.JsonSchemaToClass;

import java.io.IOException;

import static config.WebConfig.BASE_CONFIG;

public class PojoGenerator {

    public static void main(String[] args) throws IOException {
        new JsonSchemaToClass(BASE_CONFIG);
    }

}
