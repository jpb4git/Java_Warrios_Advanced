package warriors;

import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.handling.Handler;
import ratpack.test.embed.EmbeddedApp;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;


public class tests {


    @Test
    void givenAnyUri_GetEmployeeFromSameRegistry() throws Exception {


        Handler myHandler = ctx -> {
            ctx.getResponse()
                    .send("");
        };

        Action<Chain> rootAction = chain -> chain.prefix("/", rootChain -> {
            rootChain.all(myHandler)
                    .get("/" , myHandler );

        });


        EmbeddedApp.fromHandlers(rootAction)
                .test(testHttpClient -> {
                    assertEquals("Hello ...", testHttpClient.get("/")
                            .getBody()
                            .getText());

                });
    }



}



