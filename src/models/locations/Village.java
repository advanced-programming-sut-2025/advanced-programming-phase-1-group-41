package models.locations;

import models.buildings.Building;

import java.util.ArrayList;

public class Village {
    public final ArrayList<Building> buildings;

    public Village(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }
}
