package com.CEliconValley.server.handlers;

import com.CEliconValley.common.GameData;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.common.messages.PreStartRequest;
import com.CEliconValley.common.messages.PreStartResponse;
import com.CEliconValley.models.*;
import com.badlogic.gdx.utils.Timer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.java_websocket.WebSocket;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameHandler {
    public static void handle(String type, String message, WebSocket conn, Gson gson){
        switch (type) {
            case "game-popup" -> {
                    GameMessage<String> msg = gson.fromJson(message, new TypeToken<GameMessage<String>>() {}.getType());
                    // broadcast the msg.body to  players in the game
            }
            case "new-game" -> {
                GameMessage<Lobby> msg = gson.fromJson(message, new TypeToken<GameMessage<Lobby>>() {}.getType());
                for (String playerName : msg.body.getPlayerNames()) {
                    GameMessage<PreStartRequest> request = new GameMessage<>("pre-start-request",
                        new PreStartRequest());
                    App.getServer().sendToUsername(playerName, gson.toJson(request));
                }
                App.setPreGame(new PreGame(msg.body.getPlayerNames().size(), msg.body.getAdmin()));
            }
            case "pre-start-response" -> {
                GameMessage<PreStartResponse> msg = gson.fromJson(message, new TypeToken<GameMessage<PreStartResponse>>() {}.getType());
                App.getPreGame().addPlayer(msg.body.username, msg.body.farmType);
            }
        }
    }

    public static void newGame(){
        Game game = new Game(App.getPreGame().getPlayers(), App.getPreGame().getAdmin());
        App.setGame(game);
        GameMessage<GameData> response = new GameMessage<>("new-game", new GameData(game));
        App.getServer().sendToGroupByPlayers(game.getPlayers(),new Gson().toJson(response));
        game.scheduler = Executors.newSingleThreadScheduledExecutor();
        game.scheduler.scheduleAtFixedRate(() -> {
            try{
                game.getTime().advanceOneHour();
                GameMessage<GameData> msg = new GameMessage<>("game-data", new GameData(game));
                App.getServer().sendToGroupByPlayers(game.getPlayers(),new Gson().toJson(msg));
            } catch (Exception e){
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }, 1, 1, TimeUnit.SECONDS);
    }
}
