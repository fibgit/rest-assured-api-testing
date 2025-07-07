import com.fasterxml.jackson.databind.ObjectMapper;
import config.FootballConfig;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class GpathJSONTests extends FootballConfig {

    // Using Basic Find
    @Test
    public void extractMapOfElements(){
        Response response = get("competitions/2021/teams");

        Map<String, ?> teamData  = response.path("teams.find{ it.name == 'Manchester United FC' }");

        ObjectMapper mapper = new ObjectMapper();
        try {
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(teamData);
            System.out.println("Manchester United FC Data:");
            System.out.println(prettyJson);
        } catch (Exception e) {
            System.out.println("Error formatting: " + e.getMessage());
        }
        //System.out.println("Map of team data: " + allTeamDataForSingleTeam);
    }

    @Test
    public void extractSingleValueWithFind(){
        Response response = get("teams/57");
        String certainPlayer = response.path("squad.find {it.id = 7778}.name ");
        System.out.println("Name of Player: " + certainPlayer );
    }

    // Using findALL to Extract Multiple Data
    @Test
    public void extractListOfValuesWithFindAll(){
        Response response = get("teams/57");
        List<String> playerNames = response.path("squad.findAll {it.id >= 7784}.name ");

        ObjectMapper mapper = new ObjectMapper();

        try {
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(playerNames);
             System.out.println(prettyJson);
        } catch (Exception e){
            System.out.println("Error formatting: " + e.getMessage());
        }
    }

    //Using MIN , MAX and COLLECT
    @Test
    public void extractSingleValueWithHighestNumber(){
        Response response = get("teams/57");
        String playerName = response.path("squad.max { it.id }.name");
        System.out.println("Player with highest id: " + playerName);
    }

    @Test
    public void extractMultipleValuesAndSumTheValues(){
        Response response = get("teams/57");
        int sumOfIds = response.path("squad.collect { it.id }.sum()");
        System.out.println("Sum of all Ids: " + sumOfIds);
    }
    // Using fnd and fndAll
    @Test
    public void extractMapWithFindAndFindAllWithParameters(){
        String position = "Right Winger";
        String nationality = "England";

        Response response = get("teams/57");

        Map<String, ?> playerOfCertainPosition = response.path(
                "squad.findAll { it.position == '%s' }.find { it.nationality == '%s' }",
                position, nationality
        );

        if (playerOfCertainPosition != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                String prettyJson = mapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(playerOfCertainPosition);
                System.out.println("Player Details (JSON):");
                System.out.println(prettyJson);
            } catch (Exception e) {
                System.out.println("Error converting to JSON: " + e.getMessage());
            }
        } else {
            System.out.println("No player found");
        }
    }

    @Test
    public void extractMultiplePlayers(){
        String position = "Central Midfield";
        String nationality = "Belgium";

        Response response = get("teams/57");

        ArrayList<Map<String, ?>> allplayersOfCertainPosition = response.path(
                "squad.findAll { it.position == '%s' }.findAll { it.nationality == '%s' }",
                position, nationality
        );

        if (allplayersOfCertainPosition != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                String prettyJson = mapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(allplayersOfCertainPosition);
                System.out.println("Players Details (JSON):");
                System.out.println(prettyJson);
            } catch (Exception e) {
                System.out.println("Error converting to JSON: " + e.getMessage());
            }
        } else {
            System.out.println("No players found");
        }
    }



}
