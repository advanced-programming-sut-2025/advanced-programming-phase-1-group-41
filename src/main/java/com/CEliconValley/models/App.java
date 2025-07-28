package com.CEliconValley.models;

import com.CEliconValley.client.GameClient;
import com.CEliconValley.server.GameServer;
import org.bson.types.ObjectId;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

public class App {
    public static int MaxLength = 75;
    public static int MaxHeight = 60;
    public final static ArrayList<User> users = new ArrayList<>();
    public final static ArrayList<Game> games = new ArrayList<>();
    public final static HashMap<ObjectId, User> userMap = new HashMap<>();
    private static User currentUser;
    private static Menu menu;
    private static Game game;
    public final static ArrayList<String> questions = new ArrayList<>();
    private static GameServer server;
    private static GameClient client;

    public static void setupConnections(){
        server = new GameServer();
        server.start();
        System.out.println("GameServer started on port " + GameServer.PORT);
        try {
            Thread.sleep(100);
            String first = "ws://localhost:8080";
            URI serverUri = new URI(first);
            client = new GameClient(serverUri);
            client.connect();
            Thread.sleep(1000);
            client.send("hi");
            System.out.println("i sent hi!!!!!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setQuestions(ArrayList<String> questions){
        App.questions.clear();
        App.questions.addAll(questions);
    }

//    public static Map getMap() {
//        return map;
//    }

    public static Menu getMenu() {
        return App.menu;
    }
    public static void setMenu(Menu menu) {
        App.menu = menu;
    }

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
}
