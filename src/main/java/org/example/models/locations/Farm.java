package org.example.models.locations;

import dev.morphia.annotations.Entity;
import org.example.models.Cell;
import org.example.models.Grass;
import org.example.models.ObjectMap;
import org.example.models.buildings.Building;
import org.example.models.buildings.GreenHouse.Greenhouse;
import org.example.models.foragings.Foraging;

import java.util.ArrayList;

import static org.example.models.App.MaxHeight;
import static org.example.models.App.MaxLength;


public class Farm {
    private FarmType farmType;
    private ArrayList<Cell> cells;
    int id;
    private final ArrayList<Building> buildings = new ArrayList<>();
    private final ArrayList<Foraging> foragings= new ArrayList<>();

    public Farm(int id) {
        this.id = id;
        for(int i=0;i<MaxLength;i++){
            for(int j=0;j<MaxHeight;j++){
                cells.add(new Cell(new Grass(),i,j));
            }
        }
        buildings.add(new Greenhouse(0,0,this));
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

//    public void setCells(ArrayList<Cell> cells) {
//        this.cells = cells;
//    }
}
