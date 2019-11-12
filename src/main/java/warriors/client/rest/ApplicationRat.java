package warriors.client.rest;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.jackson.datatype.VavrModule;
import ratpack.server.RatpackServer;
import warriors.contracts.WarriorsAPI;
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





public class ApplicationRat {



    public static void main(String... args) throws Exception {

        WarriorsAPI warriors  = new Warriors();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VavrModule());


        RatpackServer.start(server -> server
                        .handlers(chain -> chain

                            .get(ctx -> ctx.render("Hello !!! "))
                            .get("maps",    ctx -> ctx.render(getMaps(mapper, warriors)))
                            .get("heroes",  ctx -> ctx.render(getHeros(mapper, warriors)))
                            .post("games", ctx -> {
                                    ctx.parse(GameCreate.class).then(

                                            GameCreated -> {
                                                GameCreated.getName();
                                                ctx.render(postNewGame1(mapper ,warriors, GameCreated.getMap(), GameCreated.getHero(), GameCreated.getName()));
                                            }
                                    );
                            })

                            .get("games/:uuid", ctx -> ctx.render(getGameState(mapper,warriors,ctx.getPathTokens().get("uuid"))))
                            .post("games/:uuid/turns", ctx -> ctx.render(playTurn(mapper,warriors,ctx.getPathTokens().get("uuid"))))


        ));
    }


    /**
     *
     * @param mapper
     * @param warriors
     * @return
     * @throws JsonProcessingException
     */
    public static  String getMaps(ObjectMapper mapper,WarriorsAPI warriors) throws JsonProcessingException {
        return  mapper.writeValueAsString(warriors.availableMaps());
    }

    /**
     *
     * @param mapper
     * @param warriors
     * @return
     * @throws JsonProcessingException
     */
    public static  String getHeros(ObjectMapper mapper,WarriorsAPI warriors) throws JsonProcessingException {



        return  mapper.writeValueAsString(warriors.availableHeroes());
    }





    /**
     *
     * @return
     */
    public static  String postNewGame1( ObjectMapper mapper , WarriorsAPI warriors ,int map,int hero,String playerName) throws JsonProcessingException {
       
        BaseHero bh = (hero == 0 ) ? new Warrior() : new Magician();
        warriors.createGame(playerName,bh,warriors.availableMaps().iterator().next());
        
        return  mapper.writeValueAsString((warriors.createGame(playerName,bh,warriors.availableMaps().iterator().next())));
    }

    /**
     *
     * @param mapper
     * @param warriors
     * @param uuid
     * @return
     */
    public static String getGameState(ObjectMapper mapper, WarriorsAPI warriors, String uuid){
        
         String uuid = ctx.getPathTokens().get("uuid");
        // FROM GameId Class 
         Option<Game> game= this.warriors.show(GameId.parse(uuid));
     
        return  mapper.writeValueAsString(game);
    }
    
    /**
     *
     * @param mapper
     * @param warriors
     * @param uuid
     * @return
     */
    public static String playTurn(ObjectMapper mapper, WarriorsAPI warriors, String uuid){
     
        Option<GameState> gameState = this.warriors.nextTurn(GameId.parse(uuid));
        
        return mapper.writeValueAsString(gameState);
    }
}
