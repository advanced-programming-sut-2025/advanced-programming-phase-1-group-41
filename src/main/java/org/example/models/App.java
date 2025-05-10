package org.example.models;

import org.bson.types.ObjectId;
import org.example.models.buildings.Building;
import org.example.models.foragings.Plant;
import org.example.models.locations.Map;

import java.util.ArrayList;
import java.util.HashMap;

public class App {
    private static ArrayList<Building> buildings;
    private static ArrayList<Plant> plants;
    public final static ArrayList<User> users = new ArrayList<>();
    public final static ArrayList<Game> games = new ArrayList<>();
    public final static HashMap<ObjectId, User> userMap = new HashMap<>();
    private static User currentUser;
    private static Menu menu;
    private static Game game;
    public final static ArrayList<String> questions = new ArrayList<>();

    public static void setQuestions(ArrayList<String> questions){
        App.questions.clear();
        App.questions.addAll(questions);
    }

    public static Map getMap() {
        return map;
    }

    public static ArrayList<Building> getBuildings() {
        return buildings;
    }

    public static ArrayList<Plant> getPlants() {
        return plants;
    }

    public static Menu getMenu() {
        return menu;
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
}
