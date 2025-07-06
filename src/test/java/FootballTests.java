import config.FootballConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;


public class FootballTests extends FootballConfig {


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

    @Test
    public void getDateFounded(){
        given()
        .when()
                .get("teams/57")
        .then()
                .log().body()
                .body("founded", equalTo(1886));
    }

    // Assert on HTTP response WITH logging to screen
    @Test
    public void getFirstTeam(){
        given()
        .when()
                .get("competitions/2021/teams")
        .then()
                .log().body()
                .body("teams.name[1]",equalTo("Aston Villa FC"));
    }

    // Assert on HTTP response WITHOUT logging to screen
    @Test
    public void getAnotherFirstTeam(){
        given()
        .when()
                .get("competitions/2021/teams")
        .then()
                .statusCode(200)
                .body("teams.name[1]", equalTo("Aston Villa FC"));
    }


    // Assert Body of HTTP Response
    @Test
    public void getTheFirstTeam(){
        String teamName = given()
                            .when()
                                .get("competitions/2021/teams")
                            .then()
                                .statusCode(200)
                                .extract()
                                .path("teams.name[]");

                            assertEquals("Aston Villa FC", teamName);
    }

    // Extract Body of HTTP response as a string
    @Test
    public void getTeamData(){
        String responseBody = get("teams/57").asString();
        System.out.println(responseBody);

    }

    // Extract Body of HTTP Response
    @Test
    public void getAllTeamData_DoCheckFirst(){
        Response response =
                given()
                .when()
                        .get("teams/57")
                .then()
                        .contentType(ContentType.JSON)
                        .extract().response();

        String jsonResponseAsString = response.asString();
        System.out.println(jsonResponseAsString);
    }

    //Extract Headers of HTTP Response
    @Test
    public void extractHeaders(){
        Response response =
                get("teams/57")
                        .then()
                        .extract().response();
        String contentTypeHeader = response.contentType();
        System.out.println(contentTypeHeader);
        String apiVersionHeader = response.getHeader("X-API-Version");
        System.out.println(apiVersionHeader);
    }

    @Test
    public void extractFirstTeamName(){
        String firstTeamName = get("competitions/2021/teams").jsonPath().getString("teams.name[0]");
        System.out.println(firstTeamName);

    }

    @Test
    public void extractAllTeamName(){
        Response response = get("competitions/2021/teams")
                .then()
                .extract().response();

        List<String> teamNames = response.path("teams.name");

        // Print table header
        System.out.println("+" + "-".repeat(5) + "+" + "-".repeat(30) + "+");
        System.out.println("| " + String.format("%-3s", "No.") + " | " + String.format("%-28s", "Team Name") + " |");
        System.out.println("+" + "-".repeat(5) + "+" + "-".repeat(30) + "+");

        // Print each team with index
        for(int i = 0; i < teamNames.size(); i++){
            System.out.println("| " + String.format("%-3d", (i + 1)) + " | " + String.format("%-28s", teamNames.get(i)) + " |");
        }

        // Print table footer
        System.out.println("+" + "-".repeat(5) + "+" + "-".repeat(30) + "+");
    }



}
