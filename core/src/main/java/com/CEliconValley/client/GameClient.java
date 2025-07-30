package com.CEliconValley.client;

import com.CEliconValley.common.GameData;
import com.CEliconValley.common.messages.*;
import com.CEliconValley.models.Game;
import com.CEliconValley.models.Result;
import com.CEliconValley.models.locations.Farm;
import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import net.bytebuddy.description.method.MethodDescription;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class GameClient extends WebSocketClient {

    public GameClient(URI serverUri) {
        super(serverUri);
    }

    public static void main(String[] args) {
        try {
            String first = "ws://localhost:8080";
            String second = "wss://5d92bdf5fa08.ngrok-free.app";
            URI serverUri = new URI(first);
            GameClient client = new GameClient(serverUri);
            client.connect(); // Starts async connection
            while (!client.isOpen()) {
                Thread.sleep(50);
            }
            client.send("sup");
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to server!");
        send("Hello from client!");
    }

    @Override
    public void onMessage(String message) {
        Gson gson = new Gson();
        try{
            GameMessage<Object> genericMsg = gson.fromJson(message, new TypeToken<GameMessage<Object>>() {}.getType());
            JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
            JsonElement bodyElement = jsonObject.get("body");
            GameMessage<SuccessMessage> successMsg = null;
            GameMessage<ErrorMessage> errorMsg = null;
            if(bodyElement.getAsJsonObject().has("success")) {
                successMsg = gson.fromJson(message, new TypeToken<GameMessage<SuccessMessage>>() {}.getType());
            }
            else if(bodyElement.getAsJsonObject().has("error")) {
                errorMsg = gson.fromJson(message, new TypeToken<GameMessage<ErrorMessage>>() {}.getType());
            }
            if(successMsg != null){
                Response.successResponse(successMsg.body);
            }else if(errorMsg != null){
                Response.errorResponse(errorMsg.body);
            }
            switch (genericMsg.type){
                case "login_response", "forgotpass_response" ,
                     "fp_response","profile_response" -> {
                    System.out.println("Cmessage: "+message);
                }

                case "gamedata" -> {
                    GameMessage<GameData> gameDataMessage = gson.fromJson(message, new TypeToken<GameMessage<GameData>>() {}.getType());
                    Gdx.app.postRunnable(() -> {
                        AppClient.setGameData(gameDataMessage.body);
                    });
                    System.out.println("Cmessage: updated gamedata");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("CmessageE: "+message);
        }
//        try {
//            GameData data = gson.fromJson(message, GameData.class);
//            System.out.println("received the data :D");
//            System.out.println("client code:");
//            Game game = data.makeGame();
//            for (Farm farm : game.getFarms()) {
//                farm.printMap();
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
////            System.out.println("received message: "+message);
//        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed: " + reason + " (Code: " + code + ")");
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("WebSocket Error:");
        ex.printStackTrace();
    }
}
