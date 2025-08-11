package com.CEliconValley.server;

import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.server.handlers.AuthRequestHandler;
import com.CEliconValley.server.handlers.GameHandler;
import com.CEliconValley.server.handlers.LobbyHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.java_websocket.WebSocket;


public class ServerMessageRouter {
    public static void route(String type, String rawJson, WebSocket conn, Gson gson) {
        switch (type) {
            case "login_request", "forgotpass_request", "fp_request",
                 "prereg_request", "register_request", "profile_request" ,
                 "logout_request", "avatar-request" -> AuthRequestHandler.handle(type, rawJson, conn, gson);
            case "game-popup", "new-game", "pre-start-response",
                 "game-command", "pos-diff", "new-vote",
                 "update-vote", "terminate-vote",
                 "new-ter" , "update-ter" , "terminate-ter",
                 "save-game", "load-game", "player-message",
                 "position" -> {
                GameHandler.handle(type, rawJson, conn, gson);
            }
            case "make-lobby", "join-lobby", "leave-lobby" -> {
                System.out.println(type);
                LobbyHandler.handle(type, rawJson, conn, gson);
            }
            default -> System.out.println("Unknown type: " + type);
        }
    }

}
