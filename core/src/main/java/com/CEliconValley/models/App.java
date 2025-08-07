package com.CEliconValley.models;

import com.CEliconValley.client.GameClient;
import com.CEliconValley.client.view.LobbyScreen;
import com.CEliconValley.common.AppData;
import com.CEliconValley.common.OnlineData;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.server.GameServer;
import com.google.gson.Gson;
import org.bson.types.ObjectId;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class App {
    public static int MaxLength = 75;
    public static int MaxHeight = 60;
    public final static ArrayList<User> users = new ArrayList<>();
    public final static ArrayList<Game> games = new ArrayList<>();
    public final static HashMap<ObjectId, User> userMap = new HashMap<>();
    public static ArrayList<Lobby> lobbies = new ArrayList<>();
    public static Set<OnlineData> onlinePlayers = new HashSet<>();
    private static User currentUser;
    private static PreGame preGame;
    private static Game game;
    private static GameServer server;
    private static GameClient client;

    public static void setupServer(){
        server = new GameServer();
        server.start();
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
}
