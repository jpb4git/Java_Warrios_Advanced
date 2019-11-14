package warriors.client.rest;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Option;
import io.vavr.jackson.datatype.VavrModule;
import ratpack.handling.Context;

import ratpack.handling.Handler;
import ratpack.http.MutableHeaders;

import ratpack.server.RatpackServer;
import warriors.contracts.GameId;
import warriors.contracts.GameState;

import warriors.contracts.WarriorsAPI;
import warriors.engine.Game;
import warriors.engine.Warriors;
import warriors.model.*;

class GameCreate {
    private final String playerName;
    private final int map;
    private final int hero;

    public GameCreate(@JsonProperty("playerName") String name,@JsonProperty("map") int map,@JsonProperty("hero") int hero) {
        this.playerName = name;
        this.hero   = hero;
        this.map = map;
    }
    public String getName() {
        return playerName;
    }

    public int getHero() {
        return hero;
    }
    public int getMap() {
        return map;
    }

}





public class ApplicationRat  {



    public static void main(String... args) throws Exception {

        WarriorsAPI warriors  = new Warriors();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VavrModule());


        RatpackServer.start(server -> server

                        .handlers(  chain -> chain


                        // rewriting Headers CORS limitation
                        .all(ctx -> {
                                    ctx.getResponse().getHeaders().add("Access-Control-Allow-Origin", "*");
                                    ctx.getResponse().getHeaders().add("Access-Control-Allow-Headers", "*");
                                    ctx.next();
                        })

                            .get(ctx -> ctx.render("Hello !!! "))

                            .path("games", context ->
                            context.byMethod(
                                    byMethodSpec -> byMethodSpec
                                        .get(ctx -> getAllGames(ctx ,mapper, warriors))
                                        .post(ctx ->  postNewGame(ctx, mapper ,warriors))
                            ))
                            .get("maps", ctx -> getMaps(ctx ,mapper, warriors))

                            .get("heroes",  ctx -> ctx.render(getHeros(ctx, mapper, warriors)))

                            .get("games/:uuid", ctx -> ctx.render(getGameState(ctx,mapper,warriors,ctx.getPathTokens().get("uuid"))))

                            .post("games/:uuid/turns", ctx -> ctx.render(playTurn(ctx,mapper,warriors,ctx.getPathTokens().get("uuid"))))


        ));
    }


    /**
     *
     * @param mapper
     * @param warriors
     * @return
     * @throws JsonProcessingException
     */
    public static void  getMaps(Context ctx, ObjectMapper mapper, WarriorsAPI warriors) throws JsonProcessingException {



        ctx.render(mapper.writeValueAsString(warriors.availableMaps()));
    }

    /**
     *
     * @param mapper
     * @param warriors
     * @return
     * @throws JsonProcessingException
     */
    public static  String getHeros( Context ctx,ObjectMapper mapper,WarriorsAPI warriors) throws JsonProcessingException {



        return  mapper.writeValueAsString(warriors.availableHeroes());
    }

    /**
     *
     * @return
     */
    public static  String postNewGame1(Context ctx, ObjectMapper mapper , WarriorsAPI warriors ,int map,int hero,String playerName) throws JsonProcessingException {


        BaseHero bh = (hero == 0 ) ? new Warrior() : new Magician();

        return  mapper.writeValueAsString((warriors.createGame(playerName,bh,warriors.availableMaps().iterator().next())));
    }

    public static  void postNewGame(Context ctx, ObjectMapper mapper , WarriorsAPI warriors) throws JsonProcessingException {

        ctx.parse(GameCreate.class).then(
                GameCreated -> {
                    BaseHero bh = (GameCreated.getHero() == 0 ) ? new Warrior() : new Magician();
                    ctx.render(
                                mapper.writeValueAsString(
                                                            warriors.createGame(
                                                                    GameCreated.getName(),
                                                                    bh,
                                                                    warriors.availableMaps().iterator().next()
                                                            )
                                )
                    );
                }
        );

    }


    /**
     *
     * @param mapper
     * @param warriors
     * @param uuid
     * @return
     */
    public static String getGameState(Context ctx, ObjectMapper mapper, WarriorsAPI warriors, String uuid) throws JsonProcessingException {



        // FROM GameId Class
         Option<Game> game = warriors.show(GameId.parse(uuid));
     
        return  mapper.writeValueAsString(game);
    }
    
    /**
     *
     * @param mapper
     * @param warriors
     * @param uuid
     * @return
     */
    public static String playTurn(Context ctx, ObjectMapper mapper, WarriorsAPI warriors, String uuid) throws JsonProcessingException {



        Option<GameState> gameState = warriors.nextTurn(GameId.parse(uuid));
        
        return mapper.writeValueAsString(gameState);
    }

   public static void getAllGames(Context ctx, ObjectMapper mapper, WarriorsAPI warriors) throws JsonProcessingException {

       ctx.render( mapper.writeValueAsString(warriors.listGames()));

   }



}


