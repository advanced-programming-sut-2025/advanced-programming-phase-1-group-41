package models;

import models.buildings.Building;
import models.foragings.Plant;

import java.util.ArrayList;
import java.util.Map;

public class App {
    private static Map map;
    private static ArrayList<Building> buildings;
    private static ArrayList<Plant> plants;
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
}
