package org.example.models.locations;

import org.example.models.Cell;
import org.example.models.Player;
import org.example.models.Point;

import java.util.ArrayList;

public class Map {
    private ArrayList<Farm> farms = new ArrayList<>();
//    private ArrayList<Point> mapLoc;
//    private Village village;

    public Map() {
        this.farms = new ArrayList<>();
//        this.mapLoc = new ArrayList<>();
//        this.village = null;
    }

    public ArrayList<Farm> getFarms() {
        return farms;
    }

}
