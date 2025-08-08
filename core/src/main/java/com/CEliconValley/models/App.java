package com.CEliconValley.models;

import com.CEliconValley.client.GameClient;
import com.CEliconValley.client.view.screen.Playeracts;
import com.CEliconValley.common.AppData;
import com.CEliconValley.common.GameData;
import com.CEliconValley.common.OnlineData;
import com.CEliconValley.common.PlayerData;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.database.UserDB;
import com.CEliconValley.server.GameServer;
import com.CEliconValley.server.handlers.LobbyHandler;
import com.google.gson.Gson;
import org.bson.types.ObjectId;
import org.java_websocket.WebSocket;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class App {
    public static int MaxLength = 75;
    public static int MaxHeight = 60;
    public final static ArrayList<User> users = new ArrayList<>();
    public final static ArrayList<GameData> gamesdata = new ArrayList<>();
    public static ArrayList<Lobby> lobbies = new ArrayList<>();
    public static Set<OnlineData> onlinePlayers = new HashSet<>();
    private static User currentUser;
    private static PreGame preGame;
    private static Game game;
    private static GameServer server;
    private static GameClient client;
    private static Set<DCguy> dcguys = new HashSet<>();
    public static long dcTimestamp = System.currentTimeMillis();
    private static Thread backgroundWorker = new Thread(() -> {
        while (true) {
            try {
                for (Lobby lobby : lobbies) {
                    if(System.currentTimeMillis() - lobby.lastTimeJoined > 60_000 * 5){
                        System.out.println(lobby.getLobbyName()+" is going to be deleted?");
                        System.out.println("current size "+lobby.getPlayerNames().size());
                        int maxAttempts = lobby.getPlayerNames().size();
                        int attempts = 0;
                        while(lobby.getPlayerNames().size() > 0 && attempts < maxAttempts){
                            String playerName = lobby.getPlayerNames().iterator().next();
                            AtomicReference<WebSocket> conn = new AtomicReference<>();
                            App.getServer().getOnlineConnections().forEach((k,v) -> {
                                if(v.getUsername().equals(playerName)){
                                    conn.set(k);
                                }
                            });
                            LobbyHandler.handleLeaveLobby(lobby, conn.get(), new Gson(), playerName);
                            attempts++;
                        }
                        System.out.println("after size "+lobby.getPlayerNames().size());
                    }
                }


                if(!dcguys.isEmpty() && App.getGame() != null){
                    System.out.println("not empty "+ (System.currentTimeMillis() - dcTimestamp) / 1000);
                    if(System.currentTimeMillis() - dcTimestamp > 60_000 * 2){
                        // handle save and quit for all
                        UserDB.saveGame(App.getGame());
                        GameMessage<String> exiter = new GameMessage<>("game-command","exit-game");
                        ArrayList<Player> senders = new ArrayList<>();
                        for (Player player : App.getGame().getPlayers()) {
                            if(containsDC(player.getUser()) == null){
                                senders.add(player);
                            }
                        }
                        App.getServer().sendToGroupByPlayers(senders, new Gson().toJson(exiter));
                        dcguys.clear();
                        App.setGame(null);
                    }
                }
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted, shutting down.");
                break;
            }
        }
    });

    static {
        backgroundWorker.setDaemon(true);
    }

    public static void setupServer(){
        server = new GameServer();
        server.start();
        backgroundWorker.start();
        System.out.println("GameServer started on port " + GameServer.PORT);
    }
    public static void setupClient(){
        try {
            Thread.sleep(100);
            String first = "ws://localhost:6969";
            String second ="ws://192.168.43.131:6969";
            URI serverUri = new URI(first);
            client = new GameClient(serverUri);
            client.connect();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    public static void setupConnections(){
        server = new GameServer();
        server.start();
        System.out.println("GameServer started on port " + GameServer.PORT);
        try {
            Thread.sleep(100);
            String first = "ws://localhost:6969";
            URI serverUri = new URI(first);
            client = new GameClient(serverUri);
            client.connect();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

//    public static Map getMap() {
//        return map;
//    }


    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) {
        App.game = game;
    }

    public static ArrayList<User> getUsers() {return users;}

    public static void addUser(User user){
        users.add(user);
    }

    public static User getCurrentUser(){
        return currentUser;
    }
    public static void setCurrentUser(User currentUser){
        App.currentUser = currentUser;
    }


    public static GameServer getServer() {
        return server;
    }

    public static GameClient getClient() {
        return client;
    }

    public static void putOnlinePlayer(OnlineData onlinePlayer){
        onlinePlayers.remove(onlinePlayer);
        onlinePlayers.add(onlinePlayer);
        sendData();
    }
    public static void removeOnlinePlayer(OnlineData onlinePlayer){
        onlinePlayers.remove(onlinePlayer);
        sendData();
    }
    public static void removeOnlinePlayer(String username){
        removeOnlinePlayer(new OnlineData(username, false));
    }

    public static void sendData(){

        GameMessage<AppData> msg = new GameMessage<>("app-data",new AppData(onlinePlayers));
        String json = new Gson().toJson(msg);
        App.getServer().broadcast(json);
        System.out.println("broadcasted "+json);
    }


    public static PreGame getPreGame() {
        return preGame;
    }

    public static void setPreGame(PreGame preGame) {
        App.preGame = preGame;
    }

    public static ArrayList<String> getGameByUsername(String username){
        ArrayList<String> gameNames = new ArrayList<>();
        for (GameData gd : App.gamesdata) {
            String namerr = gd.getLobby().getLobbyName()+" "+gd.getLobby().getLobbyID();
            for (PlayerData pd : gd.getPlayersData()) {
                if(pd.getUsername().equals(username)) {
                    gameNames.add(namerr);
                    break;
                }
            }
        }
        return gameNames;
    }
    public static GameData getGameDataByCustomName(String cn){
        for (GameData gd : App.gamesdata) {
            String namerr = gd.getLobby().getLobbyName()+" "+gd.getLobby().getLobbyID();
            if(cn.equals(namerr)) {
                return gd;
            }
        }
        return null;
    }
    public static GameData getGameDataById(ObjectId id){
        for (GameData gd : App.gamesdata) {
            if(gd.get_id().equals(id)) {
                return gd;
            }
        }
        return null;
    }

    public static Set<DCguy> getDcguys() {
        return dcguys;
    }

    public static DCguy containsDC(User user){
        for (DCguy dcguy : dcguys) {
            if(dcguy.getUser().getUsername().equals(user.getUsername())) {
                return dcguy;
            }
        }
        return null;
    }
}
