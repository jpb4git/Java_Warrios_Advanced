package warriors.client.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import warriors.contracts.WarriorsAPI;
import warriors.engine.Warriors;

import java.io.*;
import java.lang.reflect.Array;
import java.net.InetSocketAddress;

import io.vavr.jackson.datatype.VavrModule;

public class Application {

    public static void main(String[] args) throws IOException {
        int serverPort = 8111;


        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);

        WarriorsAPI warriors  = new Warriors();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VavrModule());

        //-----------------------------------------------------------------------------
        //-----------------------------------------------------------------------------
        server.createContext("/api/hello", (exchange -> {
            String respText = "Hello!";
            doExchange(exchange, respText,"/api/hello");
        }));

        //-----------------------------------------------------------------------------
        //-----------------------------------------------------------------------------
        server.createContext("/api/maps", (exchange -> {
            String respText = "";
            respText = mapper.writeValueAsString(warriors.availableMaps());
            doExchange(exchange, respText,"/api/hello");
        }));

        //-----------------------------------------------------------------------------
        //-----------------------------------------------------------------------------
        server.createContext("/api/heroes", (exchange -> {
            String respText = mapper.writeValueAsString(warriors.availableHeroes());
            doExchange(exchange, respText,"/api/hello");
        }));
        //-----------------------------------------------------------------------------
        //-----------------------------------------------------------------------------
        server.createContext("/api/games", (exchange -> {
            String respText = "Hello game! ";
            doExchange(exchange, respText,"/api/games");
        }));

        server.setExecutor(null); // creates a default executor
        server.start();
    }



    private static void doExchange(HttpExchange exchange, String respText,String EndPoint) throws IOException {

        if ("GET".equals(exchange.getRequestMethod())) {

            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();

        }
        if ("POST".equals(exchange.getRequestMethod())) {

            if (EndPoint.equals("/api/games")){

                InputStream input = exchange.getRequestBody();
                BufferedReader in = new BufferedReader(new InputStreamReader(input));


                in.lines().forEach(System.out::println);


                //System.out.println(exchange.getRequestBody());
                //System.out.println("entry Post methode");

                //exchange.sendResponseHeaders(200, exchange.getRequestBody().toString().getBytes().length);
               // OutputStream output = exchange.getResponseBody();
               // output.write(respText.getBytes());
               // output.flush();

            }else{
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }

        }

        exchange.close();
    }


}
