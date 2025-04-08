package models;

import models.buildings.Building;
import models.entities.Entity;
import models.foragings.Plant;

import java.util.ArrayList;
import java.util.Map;

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
