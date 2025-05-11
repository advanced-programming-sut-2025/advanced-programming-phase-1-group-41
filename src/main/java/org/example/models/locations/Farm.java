package org.example.models.locations;

import org.example.models.*;
import org.example.models.buildings.Building;
import org.example.models.buildings.Cottage;
import org.example.models.buildings.GreenHouse.Greenhouse;
import org.example.models.buildings.Nature.Bush;
import org.example.models.buildings.Nature.Rock;
import org.example.models.buildings.Nature.Tree;
import org.example.models.foragings.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static org.example.models.App.MaxHeight;
import static org.example.models.App.MaxLength;


public class Farm {
    private FarmType farmType;
    private ArrayList<Cell> cells = new ArrayList<>();
    private int id;
    private final ArrayList<Building> buildings = new ArrayList<>();
    private final ArrayList<Foraging> foragings = new ArrayList<>();
    private final ArrayList<Lake> lakes = new ArrayList<>();
    private final ArrayList<Bush> bushes = new ArrayList<>();
    private Mine mine;

    public Farm(int id) {
        this.id = id;
        for(int i=0;i<MaxLength;i++){
            for(int j=0;j<MaxHeight;j++){
                cells.add(new Cell(new Grass(),j,i));
            }
        }
        //TODO Random in 4 ta
        Random rand = new Random();
        FarmType farmType = FarmType.values()[rand.nextInt(FarmType.values().length)];
        buildings.add(new Greenhouse(4,24 + rand.nextInt(4),this));
        buildings.add(new Cottage(30 + rand.nextInt(4), 4,this));
        mine = new Mine(3,3,this);

        int lakeCount = farmType.LakeCoefficient;
        int bushCount = 2;

        for(int i = 0; i < lakeCount; i++){
            lakes.add(new Lake(25 + 10 * i + rand.nextInt(5 + 5 * i), 25 + 10 * i + rand.nextInt(5 + 5 * i), this));
        }
        for(int i = 0; i < bushCount; i++){
            bushes.add(new Bush(20 + rand.nextInt(10) + i * 20, 20 + rand.nextInt(10), this));
        }

        int rockCount = 40 * farmType.rockCoefficient;
        int treeCount = 40 * farmType.treeCoefficient;
        int plantCount = 40 * farmType.treeCoefficient;
        for(int i = 0; i < rockCount ;i++){
            int y = rand.nextInt(MaxLength - 4) + 2;
            int x = rand.nextInt(MaxHeight - 4) + 2;
            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).getObjectMap().getChar(), new Grass().getChar())){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).setObjectMap(new Rock(x, y, this));
            } else{
                i--;
            }
        }
        for(int i = 0; i < treeCount ;i++){
            int y = rand.nextInt(MaxLength - 4) + 4;
            int x = rand.nextInt(MaxHeight - 4) + 4;
            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).getObjectMap().getChar(), new Grass().getChar())){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).setObjectMap(new Tree(x, y, this));
            } else{
                i--;
            }
        }
        for(int i = 0; i < plantCount ;i++){
            int y = rand.nextInt(MaxLength - 4) + 4;
            int x = rand.nextInt(MaxHeight - 4) + 4;
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
            counter++;
            if(counter % 60 == 0){//TODO CHANGE APP FROM 75 TO 81 AND FROM 60 TO 41
                System.out.println();
            }
        }
    }

    public ArrayList<Foraging> getForagings() {
        return foragings;
    }

    public FarmType getFarmType() {
        return farmType;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public ArrayList<Lake> getLakes() {
        return lakes;
    }

    public ArrayList<Bush> getBushes() {
        return bushes;
    }

    public Mine getMine() {
        return mine;
    }

    public void setFarmType(FarmType farmType) {
        this.farmType = farmType;
    }

    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMine(Mine mine) {
        this.mine = mine;
    }

    //    public void setCells(ArrayList<Cell> cells) {
//        this.cells = cells;
//    }
}
