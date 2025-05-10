package org.example.models.locations;

import dev.morphia.annotations.Entity;
import org.example.models.*;
import org.example.models.buildings.Building;
import org.example.models.buildings.Cottage;
import org.example.models.buildings.GreenHouse.Greenhouse;
import org.example.models.foragings.Foraging;
import org.example.models.foragings.Plant;
import org.example.models.foragings.Rock;
import org.example.models.foragings.Tree;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static org.example.models.App.MaxHeight;
import static org.example.models.App.MaxLength;


public class Farm {
    private FarmType farmType = FarmType.Mountain;
    private ArrayList<Cell> cells = new ArrayList<>();
    int id;
    private final ArrayList<Building> buildings = new ArrayList<>();
    private final ArrayList<Foraging> foragings = new ArrayList<>();
    private final ArrayList<Lake> lakes = new ArrayList<>();
    private Mine mine;

    public Farm(int id) {
        this.id = id;
        for(int i=0;i<MaxLength;i++){
            for(int j=0;j<MaxHeight;j++){
                cells.add(new Cell(new Grass(),i,j));
            }
        }
        //TODO Random in 4 ta
        buildings.add(new Greenhouse(0,0,this));
        buildings.add(new Cottage(10,10,this));
        mine = new Mine(20,20,this);
        int lakeCount = farmType.LakeCoefficient;
        for(int i = 0; i < lakeCount; i++){
            lakes.add(new Lake(30 + 10 * i, 30 + 10 * i, this));
        }

        int rockCount = 20 * farmType.rockCoefficient;
        int treeCount = 10 * farmType.treeCoefficient;
        int plantCount = 10 * farmType.treeCoefficient;
        for(int i = 0; i < rockCount ;i++){

            Random rand = new Random();
            int x = rand.nextInt(MaxLength);
            int y = rand.nextInt(MaxHeight);
            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).getObjectMap().getChar(), new Grass().getChar())){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).setObjectMap(new Rock(x, y, this));
            } else{
                i--;
            }
        }
        for(int i = 0; i < treeCount ;i++){
            Random rand = new Random();
            int x = rand.nextInt(MaxLength);
            int y = rand.nextInt(MaxHeight);
            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).getObjectMap().getChar(), new Grass().getChar())){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).setObjectMap(new Tree(x, y, this));
            } else{
                i--;
            }
        }
        for(int i = 0; i < plantCount ;i++){
            Random rand = new Random();
            int x = rand.nextInt(MaxLength);
            int y = rand.nextInt(MaxHeight);
            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).getObjectMap().getChar(), new Grass().getChar())){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).setObjectMap(new Plant(x, y, this));
            } else{
                i--;
            }
        }
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }
    public void printMap(){
        int counter = 0;
        for(Cell cell:cells){
            System.out.printf(cell.getObjectMap().getChar());
//            System.out.print(cell.getX()+""+cell.getY());
            if(counter%60 == 0){//TODO CHANGE APP FROM 75 TO 81 AND FROM 60 TO 41
                System.out.println("");
            }
            counter++;
        }
    }

    public ArrayList<Foraging> getForagings() {
        return foragings;
    }


//    public void setCells(ArrayList<Cell> cells) {
//        this.cells = cells;
//    }
}
