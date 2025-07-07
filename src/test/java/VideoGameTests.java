import config.VideoGameConfig;
import config.VideoGameEndpoints;
import static io.restassured.matcher.RestAssuredMatchers.*;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import objects.VideoGame;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;

import static io.restassured.RestAssured.*;


public class VideoGameTests extends VideoGameConfig {

    String gameBodyJSON = """
                {
                  "category": "Platform",
                  "name": "Mario",
                  "rating": "Mature",
                  "releaseDate": "2012-05-04",
                  "reviewScore": 85
                }""";


    @Test
    public void getAllGames(){
        given()
        .when()
                .get(VideoGameEndpoints.ALL_VIDEO_GAMES)
        .then();
    }

    @Test
    public void createNewGameByJSON(){

        given()
                .body((gameBodyJSON))
        .when()
                .post(VideoGameEndpoints.ALL_VIDEO_GAMES)
        .then();
    }

    @Test
    public void createNewGameByXML(){
        String gameBodyXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<VideoGameRequest>\n" +
                "\t<category>Platform</category>\n" +
                "\t<name>Mario</name>\n" +
                "\t<rating>Mature</rating>\n" +
                "\t<releaseDate>2012-05-04</releaseDate>\n" +
                "\t<reviewScore>85</reviewScore>\n" +
                "</VideoGameRequest>";

        given()
                .body((gameBodyXML))
                .contentType("application/xml")
                .accept("application/xml")
        .when()
                .post(VideoGameEndpoints.ALL_VIDEO_GAMES)
        .then();
    }

    @Test
    public void updateGameByJSON(){
        given()
                    .body((gameBodyJSON))
                .when()
                    .put("videogame/3")
                .then();
    }

    @Test
    public void deleteGameByJSON(){
        given()
                .accept("text/plain")
                .when()
                    .delete("videogame/5")
                .then();
    }

    @Test
    public void getSingleGame(){
        given()
                .pathParam("videoGameId", 5)
        .when()
                .get(VideoGameEndpoints.SINGLE_VIDEO_GAME)
        .then();
    }

    // Using Object serialization to create a new game
    @Test
    public void testVideoGameSerializationByJson(){
        VideoGame videoGame = new VideoGame("Shooter", "ShipDestroyerGame", "Mature", "2018-01-09", 75);
        given()
                .body(videoGame)
        .when()
                .post(VideoGameEndpoints.ALL_VIDEO_GAMES)
        .then();
    }

    @Test
    public void convertJsonToPojo(){
        Response response =
                given()
                        .pathParam("videoGameId", 5)
                        .when()
                        .get(VideoGameEndpoints.SINGLE_VIDEO_GAME);

        VideoGame videoGame = response.getBody().as(VideoGame.class);
        System.out.println(videoGame.toString());

    }

    // Validating against XML Schema
    @Test
    public void testVideoGamesSchemaXML(){
        File xsdFile = new File("src/main/java/resources/VideoGameXSD.xsd");
        given()
                .pathParam("videoGameId", 5)
                .accept("application/xml")
        .when()
                .get(VideoGameEndpoints.SINGLE_VIDEO_GAME)
        .then()
                .body(matchesXsd(xsdFile));

    }

    // Validating against JSON Schema
    @Test
    public void testVideoGamesSchemaJSON(){
        File jsonFile = new File("src/main/java/resources/VideoGameJsonSchema.json");
        given()
                .pathParam("videoGameId", 5)
                .accept("application/json")
        .when()
                .get(VideoGameEndpoints.SINGLE_VIDEO_GAME)
        .then()
                .body(JsonSchemaValidator.matchesJsonSchema(jsonFile));
    }

    // Measure server response time
    @Test
    public void captureResponseTime(){
        long responseTime = get(VideoGameEndpoints.ALL_VIDEO_GAMES).time();
        // Multiple assertions
        Assert.assertTrue("Response time too slow: " + responseTime + "ms", responseTime < 900);
        Assert.assertTrue("Response time suspiciously fast: " + responseTime + "ms", responseTime > 500);
        System.out.println("Response time in ms: " + responseTime);

    }









}
