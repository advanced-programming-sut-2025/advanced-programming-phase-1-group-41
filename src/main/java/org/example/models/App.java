package org.example.models;

import org.example.models.buildings.Building;
import org.example.models.foragings.Plant;

import java.util.ArrayList;
import java.util.Map;

public class App {
    private static Map map;
    private static ArrayList<Building> buildings;
    private static ArrayList<Plant> plants;
    private static ArrayList<User> users;
    private static Menu menu;
    private static Game game;

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

    public static Game getGame() {
        return game;
    }

    public static ArrayList<User> getUsers() {return users;}
}
