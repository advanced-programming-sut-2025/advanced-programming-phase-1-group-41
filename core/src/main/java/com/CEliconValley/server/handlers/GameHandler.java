package com.CEliconValley.server.handlers;

import com.CEliconValley.common.GameData;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.models.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.java_websocket.WebSocket;

import java.util.ArrayList;

public class GameHandler {
    public static void handle(String type, String message, WebSocket conn, Gson gson){
        switch (type) {
            case "game-popup" -> {
                    GameMessage<String> msg = gson.fromJson(message, new TypeToken<GameMessage<String>>() {}.getType());
                    // broadcast the msg.body to  players in the game
            }
            case "new-game" -> {
                GameMessage<Lobby> msg = gson.fromJson(message, new TypeToken<GameMessage<Lobby>>() {}.getType());
                ArrayList<Player> players = new ArrayList<>();
                Player admin = null;
                for (String playerName : msg.body.getPlayerNames()) {
                    Player player = new Player(Finder.getUserByUsername(playerName));
                    players.add(player);
                    if(playerName.equals(msg.body.getAdmin())) {
                        admin = player;
                    }
                }
                Game game = new Game(players, admin);
                App.setGame(game);
                GameMessage<GameData> response = new GameMessage<>("new-game", new GameData(game));
                // change broadcast
                App.getServer().broadcast(gson.toJson(response));
            }
        }
    }
}
