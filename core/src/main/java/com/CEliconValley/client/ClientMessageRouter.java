package com.CEliconValley.client;

import com.CEliconValley.client.controller.handlers.ClientGameHandler;
import com.CEliconValley.client.controller.handlers.ClientLobbyHandler;
import com.CEliconValley.client.controller.handlers.GeneralHandler;
import com.google.gson.Gson;

public class ClientMessageRouter {
    public static void route(String type, String rawJson, Gson gson) {
        switch (type) {
            case "app-data", "avatar-response", "handshake-data" -> GeneralHandler.handle(type, rawJson, gson);
            case "new-lobby", "join-lobby", "leave-lobby",
                 "delete-lobby" -> ClientLobbyHandler.handle(type, rawJson, gson);
            case "game-data", "new-game", "pre-start-request", "game-command" -> ClientGameHandler.handle(type, rawJson, gson);
        }
    }
}
