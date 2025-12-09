package utils.pojo;

import java.io.IOException;

import static config.WebConfig.BASE_CONFIG;

public class PojoGenerator {
    public static void main(String[] args) throws IOException {
        new org.project.utils.pojo.PojoGenerator(BASE_CONFIG);
    }
}
