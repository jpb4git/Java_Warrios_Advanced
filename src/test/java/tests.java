
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.jackson.datatype.VavrModule;
import org.junit.jupiter.api.Test;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.handling.Handler;
import ratpack.test.embed.EmbeddedApp;
import warriors.client.rest.ApplicationRat;
import warriors.contracts.WarriorsAPI;
import warriors.engine.Warriors;

import static org.junit.Assert.assertEquals;
import static warriors.client.rest.ApplicationRat.getAllGames;
import static warriors.client.rest.ApplicationRat.getMaps;

public class tests {


    @Test
    void root_entry_point_slash_return_Hello() throws Exception {


        Handler myHandler = ctx -> {
            ctx.getResponse()
                    .send("Hello !!!"); //
        };


        Action<Chain> rootAction = chain -> {
                    chain.get( myHandler );

        };


        EmbeddedApp.fromHandlers(rootAction)
                .test(testHttpClient -> {
                    assertEquals("Hello !!!", testHttpClient.get("/")
                            .getBody()
                            .getText());

                });
    }


    @Test
    void root_get_games() throws Exception {

        WarriorsAPI warriors  = new Warriors();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VavrModule());

        Handler myHandler = ctx -> {
            ctx.getResponse()
                    .send(String.valueOf(warriors.listGames())); //
        };


        Action<Chain> rootAction = chain -> {
            chain.get( myHandler );

        };


        EmbeddedApp.fromHandlers(rootAction)
                .test(testHttpClient -> {
                    assertEquals(warriors.listGames(), testHttpClient.get("/games")
                            .getBody()
                            .getText());

                });
    }


}

