import config.FootballConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import static io.restassured.RestAssured.*;


public class FootballTests extends FootballConfig {

    private static final Log log = LogFactory.getLog(FootballTests.class);

    @Test
    public void getDetailsOfOneArea(){
        given()
                .queryParam("areas", 2076)
        .when()
                .get("/areas")
                .then()
                .log().body();

    }

    @Test
    public void getDetailsOfMultipleAreas(){
        String areaIds = "2076,2077,2080";

        given()
                .urlEncodingEnabled(false)
                .queryParam("areas", areaIds)
        .when()
                .get("/areas")
        .then()
                .log().body();

    }
}
