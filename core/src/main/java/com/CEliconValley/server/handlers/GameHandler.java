package com.CEliconValley.server.handlers;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.view.screen.GameScreen;
import com.CEliconValley.common.GameData;
import com.CEliconValley.common.PlayerData;
import com.CEliconValley.common.messages.*;
import com.CEliconValley.database.UserDB;
import com.CEliconValley.models.*;
import com.CEliconValley.client.view.GameMenu;
import com.CEliconValley.models.npc.npcCharacters.NPCBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.java_websocket.WebSocket;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

public class GameHandler {
    private static final BlockingQueue<GameCommand> commandQueue = new LinkedBlockingQueue<>();
    static GameMenu gameView = new GameMenu();

    public static void handle(String type, String message, WebSocket conn, Gson gson) {
        switch (type) {
            case "game-popup" -> {
                GameMessage<String> msg = gson.fromJson(message, new TypeToken<GameMessage<String>>() {
                }.getType());
                // TODO: Broadcast popup to players in the game
            }
            case "new-game" -> {
                GameMessage<Lobby> msg = gson.fromJson(message, new TypeToken<GameMessage<Lobby>>() {
                }.getType());
                Lobby lobby = msg.body;
                if(lobby.isLoad()){
                    System.out.println("making game for "+lobby);
                    // for on games and their _ids and checkem up with current lobby_id
                    GameData gd = App.getGameDataById(lobby.getGameid());
                    if(gd == null){
                        System.out.println("couldn't find game "+lobby);
                        return;
                    }
                    for (PlayerData pd : gd.getPlayersData()) {
                        if(!lobby.getPlayerNames().contains(pd.getUsername())){
                            System.out.println(pd.getUsername()+" is not in lobby!");
                            return;
                        }
                    }
                    if(gd.getPlayersData().size() != lobby.getPlayerNames().size()){
                        System.out.println("someone new is in lobby ;)");
                        return;
                    }
                    Game game = gd.makeGame();
                    loadGame(game);

                }else{
                    System.out.println("making game for "+lobby);
                    for (String playerName : lobby.getPlayerNames()) {
                        GameMessage<PreStartRequest> request = new GameMessage<>("pre-start-request", new PreStartRequest());
                        App.getServer().sendToUsername(playerName, gson.toJson(request));
                    }
                    App.setPreGame(new PreGame(lobby.getPlayerNames().size(), lobby.getAdmin(), lobby));
                }
            }
            case "pre-start-response" -> {
                GameMessage<PreStartResponse> msg = gson.fromJson(message, new TypeToken<GameMessage<PreStartResponse>>() {
                }.getType());
                App.getPreGame().addPlayer(msg.body.username, msg.body.farmType);
            }
            case "game-command" -> {
                GameMessage<GameCommand> msg = gson.fromJson(message, new TypeToken<GameMessage<GameCommand>>() {
                }.getType());
                commandQueue.offer(msg.body);
            }
            case "pos-diff" -> {
                GameMessage<PosDiff> msg = gson.fromJson(message, new TypeToken<GameMessage<PosDiff>>() {
                }.getType());
                Player player = Finder.getPlayerByUsername(msg.body.playername);
//                player.setX(msg.body.x);
//                player.setY(msg.body.y);
//                GameMessage<PlayerData> response = new GameMessage<>("player-data",
//                    new PlayerData(player));
//                App.getServer().sendToGroupByPlayers(App.getGame().getPlayers(), gson.toJson(response));
            }
            case "new-vote" -> {
                GameMessage<VoteMessage> msg = gson.fromJson(message, new TypeToken<GameMessage<VoteMessage>>(){}.getType());
                App.getGame().setWhichToVote(msg.body.target);
                App.getGame().setHowManyForVote(0);
                App.getServer().sendToGroupByPlayers(App.getGame().getPlayers(), gson.toJson(msg));
            }
            case "update-vote" -> {
                App.getGame().incHowManyForVote();
                }
            case "terminate-vote" -> {
                App.getGame().setHowManyForVote(0);
                App.getGame().setWhichToVote("");
                GameMessage<String> response = new GameMessage<>("game-command",
                    "terminate-vote");
                App.getServer().sendToGroupByPlayers(App.getGame().getPlayers(), gson.toJson(response));
            }
            case "new-ter" -> {
                App.getGame().setHowManyForVote(0);
                App.getServer().sendToGroupByPlayers(App.getGame().getPlayers(), gson.toJson(
                    new GameMessage<>("game-command","new-ter")
                ));
            }
            case "update-ter" -> {
                App.getGame().incHowManyForTer();
                }
            case "terminate-ter" -> {
                App.getGame().setHowManyForVote(0);
                GameMessage<String> response = new GameMessage<>("game-command",
                    "terminate-ter");
                App.getServer().sendToGroupByPlayers(App.getGame().getPlayers(), gson.toJson(response));
            }
            case "save-game" -> {
                UserDB.saveGame(App.getGame());
                GameMessage<String> exiter = new GameMessage<>("game-command","exit-game");
                App.getServer().sendToGroupByPlayers(App.getGame().getPlayers(), gson.toJson(exiter));
                App.setGame(null);
            }
            case "load-game" -> {
                GameMessage<String> msg = gson.fromJson(message, new TypeToken<GameMessage<String>>(){}.getType());
                GameData gd = App.getGameDataByCustomName(msg.body);
                Lobby lobby = null;
                Lobby before = gd.getLobby();
                if(before.isPrivate()){
                    lobby = new Lobby(before.getLobbyName(), before.getPassword(),
                        App.getServer().getOnlineConnections().get(conn).getUsername(),
                        before.isVisible(), conn, true
                        );
                }else{
                    lobby = new Lobby(before.getLobbyName(),
                        App.getServer().getOnlineConnections().get(conn).getUsername(),
                    before.isVisible(), conn , true);
                }
                lobby.postLoad(gd.getLobby().getLobbyID(), gd.get_id(), conn);
            }
            case "position" -> {
                GameMessage<Position> msg = gson.fromJson(message, new TypeToken<GameMessage<Position>>() {}.getType());
                Position pos = msg.body;
                Player player = Finder.getPlayerByUsername(pos.playername);
                player.updatePos(pos);
                GameMessage<PlayerData> response = new GameMessage<>("player-data",
                    new PlayerData(player));
                App.getServer().sendToGroupByPlayers(App.getGame().getPlayers(), gson.toJson(response));
            }

            case "player-message" -> {
                GameMessage<PlayerMessage> playerMsg = gson.fromJson(message, new TypeToken<GameMessage<PlayerMessage>>() {}.getType());
                App.getGame().getPlayerMessages().add(playerMsg.body);
                GameMessage<MessageCred> msg = new GameMessage<>("message-cred",
                    new MessageCred(App.getGame().getPlayerMessages()));
                App.getServer().sendToGroupByPlayers(App.getGame().getPlayers(), new Gson().toJson(msg));
                Player mention = isMentioned(playerMsg.body.getMessage());
                if(mention != null){
                    System.out.println(new Result(true, "mentioned "+mention));
                    GameMessage<GameCommand> mmsg = new GameMessage<>("game-command",
                        new GameCommand("mention",playerMsg.body.getMessage()));
                    AtomicReference<WebSocket> mentionconn = new AtomicReference<>();
                    App.getServer().getOnlineConnections().forEach((k,v) -> {
                        if(v.getUsername().equalsIgnoreCase(mention.getUser().getUsername())){
                            mentionconn.set(k);
                        }
                    });
                    if(mentionconn.get() != null){
                        mentionconn.get().send(gson.toJson(mmsg));
                    }
                }else{
                    System.out.println(new Result(false, "no match found"));
                }
            }
        }
    }



    public static void loadGame(Game game){
        App.setGame(game);
        GameMessage<GameData> response = new GameMessage<>("new-game", new GameData(game));
        App.getServer().sendToGroupByPlayers(game.getPlayers(), new Gson().toJson(response));
        game.commandThread = new Thread(() -> {
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


        game.startScheduler();
    }

    public static void newGame() {
        Game game = new Game(App.getPreGame().getPlayers(), App.getPreGame().getAdmin(), App.getPreGame().getLobby());
        System.out.println("game length is " + game.getPlayers().size());
        App.setGame(game);
        new NPCBuilder();

        GameMessage<GameData> response = new GameMessage<>("new-game", new GameData(game));
        App.getServer().sendToGroupByPlayers(game.getPlayers(), new Gson().toJson(response));
        App.lobbies.remove(App.getPreGame().getLobby());
        // Start command processor thread
        game.commandThread = new Thread(() -> {
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

        game.getVillage().getNPCs().forEach(npc -> {
            npc.setRandomPoint();
        });

        game.startScheduler();
    }

    private static void processCommand(GameCommand command) {
        Game game = App.getGame();
        if (game == null) return;

        Player player = game.getPlayerByUsername(command.playerName);
        if (player == null) return;

        switch (command.command) {
            case "walk up" -> {
                player.setY(player.getY() + 1);
                player.decEnergy(0.2f);
                GameMessage<PlayerData> response = new GameMessage<>("player-data",
                    new PlayerData(player));
                App.getServer().sendToGroupByPlayers(game.getPlayers(), new Gson().toJson(response));
                return;
            }
            case "walk down" -> {
                player.setY(player.getY() - 1);
                player.decEnergy(0.2f);
                GameMessage<PlayerData> response = new GameMessage<>("player-data",
                    new PlayerData(player));
                App.getServer().sendToGroupByPlayers(game.getPlayers(), new Gson().toJson(response));
                return;
            }
            case "walk left" -> {
                player.setX(player.getX() - 1);
                player.decEnergy(0.2f);
                GameMessage<PlayerData> response = new GameMessage<>("player-data",
                    new PlayerData(player));
                App.getServer().sendToGroupByPlayers(game.getPlayers(), new Gson().toJson(response));
                return;
            }
            case "walk right" -> {
                player.setX(player.getX() + 1);
                player.decEnergy(0.2f);
                GameMessage<PlayerData> response = new GameMessage<>("player-data",
                    new PlayerData(player));
                App.getServer().sendToGroupByPlayers(game.getPlayers(), new Gson().toJson(response));
                return;
            }
            case "at home" -> {
                App.getGame().incHowManyInHome();
            }
            case "go-to-village" -> {
                Finder.getPlayerByUsername(command.playerName).setPlayerIsInVillage(true);
                GameMessage<GameCommand> response = new GameMessage<>("game-command",
                    new GameCommand("go-to-village", ":)"));
                App.getServer().sendToPlayername(command.playerName, new Gson().toJson(response));

                GameMessage<GameCommand> updateVillage = new GameMessage<>("game-command",
                    new GameCommand("update-village", ":)"));
                App.getServer().sendToPlayername(command.playerName, new Gson().toJson(updateVillage));
            }
            case "go-to-farm" -> {
                Finder.getPlayerByUsername(command.playerName).setPlayerIsInVillage(false);
                GameMessage<GameCommand> response = new GameMessage<>("game-command",
                    new GameCommand("go-to-farm", ":)"));
                App.getServer().sendToPlayername(command.playerName, new Gson().toJson(response));
                GameMessage<GameCommand> updateVillage = new GameMessage<>("game-command",
                    new GameCommand("update-village", ":)"));
                App.getServer().sendToPlayername(command.playerName, new Gson().toJson(updateVillage));
            }
        }


        gameView.check(command.command, command.playerName);
        gameView.check("print map", command.playerName);
//        System.out.println("printing inventory: ");
//        gameView.check("inventory show", command.playerName);

        GameMessage<GameData> msg = new GameMessage<>("game-data", new GameData(game));
        App.getServer().sendToGroupByPlayers(game.getPlayers(), new Gson().toJson(msg));
    }

    private static Player isMentioned(String message){
        for (String sub : message.split("\\s+")) {
            System.out.println("checking for {"+sub+"}");
            if(sub.startsWith("@")){
                System.out.println("  ["+sub+"]");
                for (Player player : App.getGame().getPlayers()) {
                    if(sub.substring(1,sub.length()).equalsIgnoreCase(player.getUser().getUsername())){
                        return player;
                    }
                }
            }
        }
        return null;
    }
}
