package com.CEliconValley.server.handlers;

import com.CEliconValley.common.messages.GameMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.java_websocket.WebSocket;

public class GameHandler {
    public static void handle(String type, String message, WebSocket conn, Gson gson){
        switch (type) {
            case "game-popup" -> {
                GameMessage<String> msg = gson.fromJson(message, new TypeToken<GameMessage<String>>() {}.getType());
                // broadcast the msg.body to all of the players in the game
            }
        }
    }
}
