package com.CEliconValley.server.handlers;

import com.CEliconValley.common.GameData;
import com.CEliconValley.common.messages.*;
import com.CEliconValley.models.*;
import com.CEliconValley.client.view.GameMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.java_websocket.WebSocket;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameHandler {
    private static final BlockingQueue<GameCommand> commandQueue = new LinkedBlockingQueue<>();
    static GameMenu gameView = new GameMenu();

    public static void handle(String type, String message, WebSocket conn, Gson gson) {
        switch (type) {
            case "game-popup" -> {
                GameMessage<String> msg = gson.fromJson(message, new TypeToken<GameMessage<String>>() {}.getType());
                // TODO: Broadcast popup to players in the game
            }
            case "new-game" -> {
                GameMessage<Lobby> msg = gson.fromJson(message, new TypeToken<GameMessage<Lobby>>() {}.getType());
                for (String playerName : msg.body.getPlayerNames()) {
                    GameMessage<PreStartRequest> request = new GameMessage<>("pre-start-request", new PreStartRequest());
                    App.getServer().sendToUsername(playerName, gson.toJson(request));
                }
                App.setPreGame(new PreGame(msg.body.getPlayerNames().size(), msg.body.getAdmin()));
            }
            case "pre-start-response" -> {
                GameMessage<PreStartResponse> msg = gson.fromJson(message, new TypeToken<GameMessage<PreStartResponse>>() {}.getType());
                App.getPreGame().addPlayer(msg.body.username, msg.body.farmType);
            }
            case "game-command" -> {
                GameMessage<GameCommand> msg = gson.fromJson(message, new TypeToken<GameMessage<GameCommand>>() {}.getType());
                commandQueue.offer(msg.body);
            }
        }
    }

    public static void newGame() {
        Game game = new Game(App.getPreGame().getPlayers(), App.getPreGame().getAdmin());
        App.setGame(game);

        GameMessage<GameData> response = new GameMessage<>("new-game", new GameData(game));
        App.getServer().sendToGroupByPlayers(game.getPlayers(), new Gson().toJson(response));

        // Start command processor thread
        game.commandThread =new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    GameCommand command = commandQueue.take();
                    processCommand(command);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        game.commandThread.start();

        // Start game tick scheduler
        game.scheduler = Executors.newSingleThreadScheduledExecutor();
        game.scheduler.scheduleAtFixedRate(() -> {
            try {
                game.getTime().advanceOneHour();
                GameMessage<GameData> msg = new GameMessage<>("game-data", new GameData(game));
                App.getServer().sendToGroupByPlayers(game.getPlayers(), new Gson().toJson(msg));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 1, 10, TimeUnit.SECONDS);
    }

    private static void processCommand(GameCommand command) {
        Game game = App.getGame();
        if (game == null) return;

        Player player = game.getPlayerByUsername(command.playerName);
        if (player == null) return;

        switch (command.command) {
            case "walk up" -> {
                player.setY(player.getY() + 1);
                return;
            }
            case "walk down" -> {
                player.setY(player.getY() - 1);
                return;
            }
            case "walk left" -> {
                player.setX(player.getX() - 1);
                return;
            }
            case "walk right" -> {
                player.setX(player.getX() + 1);
                return;
            }
        }


        gameView.check(command.command, command.playerName);

        GameMessage<GameData> msg = new GameMessage<>("game-data", new GameData(game));
        App.getServer().sendToGroupByPlayers(game.getPlayers(), new Gson().toJson(msg));
    }
}
