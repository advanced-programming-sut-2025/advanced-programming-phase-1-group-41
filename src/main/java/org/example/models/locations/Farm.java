package org.example.models.locations;

import dev.morphia.annotations.Entity;
import org.example.models.Cell;
import org.example.models.Grass;
import org.example.models.buildings.Building;
import org.example.models.foragings.Foraging;

import java.util.ArrayList;


public class Farm {
    private FarmType farmType;
    private ArrayList<ArrayList<Cell>> cells;
    int id;
    private final ArrayList<Building> buildings = new ArrayList<>();
    private final ArrayList<Foraging> foragings = new ArrayList<>();

    public Farm(int id) {
        this.id = id;
        for(int i=0;i<75;i++){
            cells.add(new ArrayList<>());
            for(int j=0;j<60;j++){
                cells.get(i).add(new Cell(new Grass(),i,j));
            }
        }
    }
}
