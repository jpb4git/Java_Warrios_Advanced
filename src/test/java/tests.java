
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.vavr.jackson.datatype.VavrModule;
import org.junit.jupiter.api.Test;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.handling.Handler;
import ratpack.http.client.ReceivedResponse;
import ratpack.jackson.Jackson;
import ratpack.test.embed.EmbeddedApp;
import warriors.client.rest.ApplicationRat;
import warriors.contracts.GameId;
import warriors.contracts.WarriorsAPI;
import warriors.engine.Warriors;


import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static warriors.client.rest.ApplicationRat.*;

public class tests {

    ApplicationRat ar = new ApplicationRat();


    @Test
    void root_entry_point() throws Exception {


        WarriorsAPI warriors  = new Warriors();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VavrModule());


        // shortcut for complex setup
        Handler rootHandler = ctx -> {
            ctx.getResponse().send("Hello !!!");
        };

        Action<Chain> rootAction = chain -> {
                    chain.get( "" , rootHandler );
                    chain.get( "games/" ,ctx ->  ctx.getResponse().send(ar.getAllGames(ctx,mapper,warriors))  );
        };

        // starter here
        EmbeddedApp.fromHandlers(rootAction)

                .test(testHttpClient -> {
                    assertEquals("Hello !!!", testHttpClient.get("/")
                            .getBody()
                            .getText());

                    assertEquals("[]" , testHttpClient.get("games/")
                            .getBody()
                            .getText());
                });
    }



    @Test
    void Heroes_List() throws Exception {


        WarriorsAPI warriors  = new Warriors();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VavrModule());


        // starter here
        EmbeddedApp.fromHandlers(ApplicationRat.handler())
                .test(testHttpClient -> {
                    //heroes
                    assertEquals("[{\"name\":\"Guerrier\",\"life\":5,\"dead\":false,\"attackLevel\":10},{\"name\":\"Magicien\",\"life\":3,\"dead\":false,\"attackLevel\":6}]" , testHttpClient.get("heroes/")
                            .getBody()
                            .getText());
                });
    }

    @Test
    void maps_List() throws Exception {


        WarriorsAPI warriors  = new Warriors();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VavrModule());





        // starter here
        EmbeddedApp.fromHandlers(ApplicationRat.handler())
                .test(testHttpClient -> {
                    //heroes
                    assertEquals("[{\"name\":\"Default Map\",\"cases\":[{\"type\":\"EMPTY\"},{\"equipmentValue\":2,\"type\":\"EQUIPMENT\"},{\"equipmentValue\":1,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Gobelin\",\"life\":6,\"attack\":1},\"type\":\"ENEMY\"},{\"equipmentValue\":2,\"type\":\"EQUIPMENT\"},{\"equipmentValue\":3,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Gobelin\",\"life\":6,\"attack\":1},\"type\":\"ENEMY\"},{\"equipmentValue\":1,\"type\":\"EQUIPMENT\"},{\"equipmentValue\":2,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Gobelin\",\"life\":6,\"attack\":1},\"type\":\"ENEMY\"},{\"ennemy\":{\"name\":\"Sorcier\",\"life\":9,\"attack\":2},\"type\":\"ENEMY\"},{\"equipmentValue\":1,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Gobelin\",\"life\":6,\"attack\":1},\"type\":\"ENEMY\"},{\"equipmentValue\":1,\"type\":\"EQUIPMENT\"},{\"equipmentValue\":1,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Gobelin\",\"life\":6,\"attack\":1},\"type\":\"ENEMY\"},{\"type\":\"EMPTY\"},{\"equipmentValue\":2,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Gobelin\",\"life\":6,\"attack\":1},\"type\":\"ENEMY\"},{\"equipmentValue\":1,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Sorcier\",\"life\":9,\"attack\":2},\"type\":\"ENEMY\"},{\"ennemy\":{\"name\":\"Gobelin\",\"life\":6,\"attack\":1},\"type\":\"ENEMY\"},{\"equipmentValue\":3,\"type\":\"EQUIPMENT\"},{\"equipmentValue\":2,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Gobelin\",\"life\":6,\"attack\":1},\"type\":\"ENEMY\"},{\"ennemy\":{\"name\":\"Sorcier\",\"life\":9,\"attack\":2},\"type\":\"ENEMY\"},{\"equipmentValue\":1,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Gobelin\",\"life\":6,\"attack\":1},\"type\":\"ENEMY\"},{\"equipmentValue\":1,\"type\":\"EQUIPMENT\"},{\"equipmentValue\":1,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Gobelin\",\"life\":6,\"attack\":1},\"type\":\"ENEMY\"},{\"equipmentValue\":2,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Sorcier\",\"life\":9,\"attack\":2},\"type\":\"ENEMY\"},{\"equipmentValue\":1,\"type\":\"EQUIPMENT\"},{\"type\":\"EMPTY\"},{\"ennemy\":{\"name\":\"Sorcier\",\"life\":9,\"attack\":2},\"type\":\"ENEMY\"},{\"ennemy\":{\"name\":\"Sorcier\",\"life\":9,\"attack\":2},\"type\":\"ENEMY\"},{\"ennemy\":{\"name\":\"Sorcier\",\"life\":9,\"attack\":2},\"type\":\"ENEMY\"},{\"equipmentValue\":3,\"type\":\"EQUIPMENT\"},{\"equipmentValue\":2,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Sorcier\",\"life\":9,\"attack\":2},\"type\":\"ENEMY\"},{\"equipmentValue\":5,\"type\":\"EQUIPMENT\"},{\"equipmentValue\":5,\"type\":\"EQUIPMENT\"},{\"equipmentValue\":2,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Sorcier\",\"life\":9,\"attack\":2},\"type\":\"ENEMY\"},{\"ennemy\":{\"name\":\"Dragon\",\"life\":15,\"attack\":4},\"type\":\"ENEMY\"},{\"type\":\"EMPTY\"},{\"ennemy\":{\"name\":\"Sorcier\",\"life\":9,\"attack\":2},\"type\":\"ENEMY\"},{\"equipmentValue\":7,\"type\":\"EQUIPMENT\"},{\"equipmentValue\":7,\"type\":\"EQUIPMENT\"},{\"type\":\"EMPTY\"},{\"type\":\"EMPTY\"},{\"ennemy\":{\"name\":\"Dragon\",\"life\":15,\"attack\":4},\"type\":\"ENEMY\"},{\"equipmentValue\":5,\"type\":\"EQUIPMENT\"},{\"type\":\"EMPTY\"},{\"type\":\"EMPTY\"},{\"ennemy\":{\"name\":\"Dragon\",\"life\":15,\"attack\":4},\"type\":\"ENEMY\"},{\"type\":\"EMPTY\"},{\"type\":\"EMPTY\"},{\"type\":\"EMPTY\"},{\"type\":\"EMPTY\"},{\"type\":\"EMPTY\"},{\"ennemy\":{\"name\":\"Dragon\",\"life\":15,\"attack\":4},\"type\":\"ENEMY\"},{\"type\":\"EMPTY\"}],\"numberOfCase\":64}]" , testHttpClient.get("maps/")
                            .getBody()
                            .getText());
                });
    }

    @Test
    void games_List_empty() throws Exception {


        WarriorsAPI warriors  = new Warriors();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VavrModule());






        // starter here
        EmbeddedApp.fromHandlers(ApplicationRat.handler())
                .test(testHttpClient -> {
                    //games
                    assertEquals("[]" , testHttpClient.get("games/")
                            .getBody()
                            .getText());
                });
    }


    @Test
    void games() throws Exception {



        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VavrModule());






        // starter here
        EmbeddedApp.fromHandlers(ApplicationRat.handler())
                .test(testHttpClient -> {
                    //games
                    ReceivedResponse response = testHttpClient.request("games/",
                            requestSpec -> requestSpec.body(

                                    body -> body.type("application/json")
                                        .bytes("{\"playerName\":\"Toto\",\"hero\":0,\"map\":0}".getBytes(StandardCharsets.UTF_8)))
                            .post()
                    );


                    ObjectMapper mapperResponse = new ObjectMapper();
                    final JsonNode gameNode = mapperResponse.readTree(response.getBody().getText());
                    JsonNode gameId = gameNode.get("gameId");

                    JsonNode jsonNode = mapperResponse
                            .readTree(
                                    testHttpClient
                                            .get("games/" + gameId.asText())
                                            .getBody()
                                            .getText());

                    /*
                    assertEquals("{\"gameId\":\""+ gameId.asText()+"\",\"gameStatus\":\"IN_PROGRESS\",\"playerName\":\"Toto\",\"lastLog\":[\"Partie démarrée ! Vous etes sur la case Départ\"],\"hero\":{\"name\":\"Guerrier\",\"life\":5,\"dead\":false,\"attackLevel\":10},\"map\":{\"name\":\"Default Map\",\"cases\":[{\"type\":\"EMPTY\"},{\"equipmentValue\":2,\"type\":\"EQUIPMENT\"},{\"equipmentValue\":1,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Gobelin\",\"life\":6,\"attack\":1},\"type\":\"ENEMY\"},{\"equipmentValue\":2,\"type\":\"EQUIPMENT\"},{\"equipmentValue\":3,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Gobelin\",\"life\":6,\"attack\":1},\"type\":\"ENEMY\"},{\"equipmentValue\":1,\"type\":\"EQUIPMENT\"},{\"equipmentValue\":2,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Gobelin\",\"life\":6,\"attack\":1},\"type\":\"ENEMY\"},{\"ennemy\":{\"name\":\"Sorcier\",\"life\":9,\"attack\":2},\"type\":\"ENEMY\"},{\"equipmentValue\":1,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Gobelin\",\"life\":6,\"attack\":1},\"type\":\"ENEMY\"},{\"equipmentValue\":1,\"type\":\"EQUIPMENT\"},{\"equipmentValue\":1,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Gobelin\",\"life\":6,\"attack\":1},\"type\":\"ENEMY\"},{\"type\":\"EMPTY\"},{\"equipmentValue\":2,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Gobelin\",\"life\":6,\"attack\":1},\"type\":\"ENEMY\"},{\"equipmentValue\":1,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Sorcier\",\"life\":9,\"attack\":2},\"type\":\"ENEMY\"},{\"ennemy\":{\"name\":\"Gobelin\",\"life\":6,\"attack\":1},\"type\":\"ENEMY\"},{\"equipmentValue\":3,\"type\":\"EQUIPMENT\"},{\"equipmentValue\":2,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Gobelin\",\"life\":6,\"attack\":1},\"type\":\"ENEMY\"},{\"ennemy\":{\"name\":\"Sorcier\",\"life\":9,\"attack\":2},\"type\":\"ENEMY\"},{\"equipmentValue\":1,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Gobelin\",\"life\":6,\"attack\":1},\"type\":\"ENEMY\"},{\"equipmentValue\":1,\"type\":\"EQUIPMENT\"},{\"equipmentValue\":1,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Gobelin\",\"life\":6,\"attack\":1},\"type\":\"ENEMY\"},{\"equipmentValue\":2,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Sorcier\",\"life\":9,\"attack\":2},\"type\":\"ENEMY\"},{\"equipmentValue\":1,\"type\":\"EQUIPMENT\"},{\"type\":\"EMPTY\"},{\"ennemy\":{\"name\":\"Sorcier\",\"life\":9,\"attack\":2},\"type\":\"ENEMY\"},{\"ennemy\":{\"name\":\"Sorcier\",\"life\":9,\"attack\":2},\"type\":\"ENEMY\"},{\"ennemy\":{\"name\":\"Sorcier\",\"life\":9,\"attack\":2},\"type\":\"ENEMY\"},{\"equipmentValue\":3,\"type\":\"EQUIPMENT\"},{\"equipmentValue\":2,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Sorcier\",\"life\":9,\"attack\":2},\"type\":\"ENEMY\"},{\"equipmentValue\":5,\"type\":\"EQUIPMENT\"},{\"equipmentValue\":5,\"type\":\"EQUIPMENT\"},{\"equipmentValue\":2,\"type\":\"EQUIPMENT\"},{\"ennemy\":{\"name\":\"Sorcier\",\"life\":9,\"attack\":2},\"type\":\"ENEMY\"},{\"ennemy\":{\"name\":\"Dragon\",\"life\":15,\"attack\":4},\"type\":\"ENEMY\"},{\"type\":\"EMPTY\"},{\"ennemy\":{\"name\":\"Sorcier\",\"life\":9,\"attack\":2},\"type\":\"ENEMY\"},{\"equipmentValue\":7,\"type\":\"EQUIPMENT\"},{\"equipmentValue\":7,\"type\":\"EQUIPMENT\"},{\"type\":\"EMPTY\"},{\"type\":\"EMPTY\"},{\"ennemy\":{\"name\":\"Dragon\",\"life\":15,\"attack\":4},\"type\":\"ENEMY\"},{\"equipmentValue\":5,\"type\":\"EQUIPMENT\"},{\"type\":\"EMPTY\"},{\"type\":\"EMPTY\"},{\"ennemy\":{\"name\":\"Dragon\",\"life\":15,\"attack\":4},\"type\":\"ENEMY\"},{\"type\":\"EMPTY\"},{\"type\":\"EMPTY\"},{\"type\":\"EMPTY\"},{\"type\":\"EMPTY\"},{\"type\":\"EMPTY\"},{\"ennemy\":{\"name\":\"Dragon\",\"life\":15,\"attack\":4},\"type\":\"ENEMY\"},{\"type\":\"EMPTY\"}],\"numberOfCase\":64}}",
                                           testHttpClient.get("games/" + gameId.asText())
                            .getBody()
                            .getText());


                    */


                    assertEquals(gameId, jsonNode.get("gameId"));

                    assertEquals("IN_PROGRESS", jsonNode.get("gameStatus").asText());

                    assertEquals("64", jsonNode.get("numberOfCase").asText());

                });
    }

}

