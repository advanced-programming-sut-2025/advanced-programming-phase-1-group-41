package org.example.models.locations;

import org.example.models.Cell;
import org.example.models.Player;
import org.example.models.Point;

import java.util.ArrayList;

public class Map {
    private ArrayList<Farm> farms = new ArrayList<>();
    private ArrayList<Point> mapLoc;
    private Village village;

    public Map(ArrayList<Farm> farms, ArrayList<Point> mapLoc, Village village) {
        this.farms = farms;
        this.mapLoc = mapLoc;
        this.village = village;
    }

    public ArrayList<Farm> getFarms() {
        return farms;
    }

    public ArrayList<Point> getMapLoc() {
        return mapLoc;
    }

    public Village getVillage() {
        return village;
    }
}
