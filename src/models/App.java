package models;

import enums.Map;
import enums.Plant;
import enums.buildings.Building;
import models.entities.Entity;

import java.util.ArrayList;

public class App {
    Map map;
    ArrayList<Building> buildings;
    ArrayList<Plant> plants;
    ArrayList<Entity> entities;

    Game game;

    static Menu menu;

    public static Menu getMenu() {
        return menu;
    }
}
