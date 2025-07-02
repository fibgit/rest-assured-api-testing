import config.VideoGameConfig;
import config.VideoGameEndpoints;
import org.junit.Test;
import static io.restassured.RestAssured.*;

public class VideoGameTests extends VideoGameConfig {

    @Test
    public void getAllGames(){
        given()
        .when()
                .get(VideoGameEndpoints.ALL_VIDEO_GAMES)
        .then();
    }


    @Test
    public void createNewGameByJSON(){
        String gameBodyJSON = """
                {
                  "category": "Platform",
                  "name": "Mario",
                  "rating": "Mature",
                  "releaseDate": "2012-05-04",
                  "reviewScore": 85
                }""";

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
}
