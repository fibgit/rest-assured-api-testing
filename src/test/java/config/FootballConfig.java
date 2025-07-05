package config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import org.junit.BeforeClass;

public class FootballConfig {
    @BeforeClass

    public static void setup(){
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://api.football-data.org")
                .setBasePath("/v4")
                .addHeader("X-Auth-Token","8bc08ef18bd94d368d0ce87548018691")
                .addHeader("X-Response-Control", "minified")
                .addFilter(new RequestLoggingFilter())
                .build();


        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();

    }
}
