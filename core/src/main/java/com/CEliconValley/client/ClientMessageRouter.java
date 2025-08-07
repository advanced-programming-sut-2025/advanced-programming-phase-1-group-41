package com.CEliconValley.client;

import com.CEliconValley.client.controller.handlers.ClientGameHandler;
import com.CEliconValley.client.controller.handlers.ClientLobbyHandler;
import com.CEliconValley.client.controller.handlers.GeneralHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ClientMessageRouter {
    public static void route(String type, JsonObject body, Gson gson,
                             long timestamp, String message) {
        switch (type) {
            case "app-data", "avatar-response", "handshake-data" -> {
                GeneralHandler.handle(type, body, gson, timestamp);
                System.out.println("Cmessage "+message);
            }
            case "new-lobby", "join-lobby", "leave-lobby",
                 "delete-lobby" -> {
                ClientLobbyHandler.handle(type, body, gson, timestamp);
                System.out.println("Cmessage "+message);
            }
            case "game-data", "new-game", "pre-start-request", "game-command",
                 "farm-data", "player-data", "new-vote" -> ClientGameHandler.handle(type, body, gson, timestamp);
        }
    }
    public static void route(String type, Gson gson,
                             long timestamp, String message){
        switch (type) {
            case "game-command" -> {
                ClientGameHandler.handle(type, message, gson, timestamp);
            }
        }
    }
}
