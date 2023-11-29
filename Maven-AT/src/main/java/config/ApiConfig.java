package config;

import io.restassured.specification.RequestSpecification;
import org.project.utils.config.ApiBaseConfig;

public class ApiConfig extends ApiBaseConfig {

    public static RequestSpecification getRequestSpec() {
        //out.println("ApiConfig: " + BASE_CONFIG);
        return ApiBaseConfig.getRequestSpec();
    }

}
