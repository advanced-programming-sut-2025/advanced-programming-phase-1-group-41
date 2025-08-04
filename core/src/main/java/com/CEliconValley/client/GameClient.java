package com.CEliconValley.client;

import com.CEliconValley.Main;
import com.CEliconValley.client.view.LobbyScreen;
import com.CEliconValley.client.view.MainMenuView;
import com.CEliconValley.client.view.ProfileMenuView;
import com.CEliconValley.client.view.screen.FarmScreen;
import com.CEliconValley.common.AppData;
import com.CEliconValley.common.GameData;
import com.CEliconValley.common.HandshakeData;
import com.CEliconValley.common.UserData;
import com.CEliconValley.common.messages.*;
import com.CEliconValley.models.*;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.models.locations.FarmType;
import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashSet;

public class GameClient extends WebSocketClient {

    public GameClient(URI serverUri) {
        super(serverUri);
    }

    public static void main(String[] args) {
        try {
            String first = "ws://localhost:6969";
            String second = "wss://5d92bdf5fa08.ngrok-free.app";
            URI serverUri = new URI(first);
            GameClient client = new GameClient(serverUri);
            client.connect(); // Starts async connection
            while (!client.isOpen()) {
                Thread.sleep(50);
            }
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            if (this.isOpen()) {
                this.close(); // Gracefully close the connection
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to server!");
    }

    @Override
    public void onMessage(String message) {
        Gson gson = new Gson();
        try {
            GameMessage<Object> genericMsg = gson.fromJson(message, new TypeToken<GameMessage<Object>>() {
            }.getType());
            JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
            JsonElement bodyElement = jsonObject.get("body");
            GameMessage<SuccessMessage> successMsg = null;
            GameMessage<ErrorMessage> errorMsg = null;
            try{
                if (bodyElement.getAsJsonObject().has("success")) {
                    successMsg = gson.fromJson(message, new TypeToken<GameMessage<SuccessMessage>>() {
                    }.getType());
                } else if (bodyElement.getAsJsonObject().has("error")) {
                    errorMsg = gson.fromJson(message, new TypeToken<GameMessage<ErrorMessage>>() {
                    }.getType());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (successMsg != null) {
                System.out.println("success is "+message);
                Response.successResponse(successMsg.body);
            } else if (errorMsg != null) {
                System.out.println("error is "+message);
                Response.errorResponse(errorMsg.body);
            } else {
                ClientMessageRouter.route(genericMsg.type, message, new Gson());
                switch (genericMsg.type) {
                    case "login_response", "forgotpass_response",
                         "fp_response", "profile_response" -> {
                        System.out.println("Cmessage: " + message);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("----------------------------------");
            System.out.println("CmessageE: " + message);
            System.out.println("----------------------------------");
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
        if(Main.getMain() != null){
            Gdx.app.postRunnable(() -> {
               Gdx.app.exit();
            });
            System.out.println("released the beast");
        }else{
            System.out.println("main is null");
        }
        System.out.println("Connection closed: " + reason + " (Code: " + code + ")");
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("WebSocket Error:");
        ex.printStackTrace();
    }
}
