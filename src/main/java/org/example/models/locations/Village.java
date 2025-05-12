package org.example.models.locations;

import org.example.models.*;
import org.example.models.buildings.Building;
import org.example.models.buildings.Cottage;
import org.example.models.buildings.GreenHouse.Greenhouse;
import org.example.models.buildings.Nature.*;
import org.example.models.buildings.animalContainer.Barn;
import org.example.models.buildings.animalContainer.BarnType;
import org.example.models.buildings.animalContainer.Coop;
import org.example.models.buildings.animalContainer.CoopType;
import org.example.models.foragings.*;

import javax.xml.stream.Location;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static org.example.models.App.MaxHeight;
import static org.example.models.App.MaxLength;


public class Village {
    private FarmType farmType;
    private ArrayList<Cell> cells = new ArrayList<>();

    private final ArrayList<Building> buildings = new ArrayList<>();
    private final ArrayList<Lake> lakes = new ArrayList<>();
    private final ArrayList<Bush> bushes = new ArrayList<>();


    public Village() {

        for(int i=0;i<MaxLength+20;i++){
            for(int j=0;j<MaxHeight-5;j++){
                cells.add(new Cell(new Grass(),j,i));
            }
        }
        Random rand = new Random();
        FarmType farmType = FarmType.values()[rand.nextInt(FarmType.values().length)];

        int lakeCount = farmType.LakeCoefficient;
        int bushCount = 2;//TODO IDK

        for(int i = 0; i < lakeCount; i++){
//            lakes.add(new Lake(25 + 10 * i + rand.nextInt(5 + 5 * i), 25 + 10 * i + rand.nextInt(5 + 5 * i), this));
        }
        for(int i = 0; i < bushCount; i++){
//            bushes.add(new Bush(20 + rand.nextInt(10) + i * 20, 20 + rand.nextInt(10), this));
        }

        int rockCount = (5 + rand.nextInt(10)) * farmType.rockCoefficient;
        int treeCount = (10 + rand.nextInt(10)) * farmType.treeCoefficient;
        int plantCount = (5 + rand.nextInt(10)) * farmType.treeCoefficient;
        int cropCount = (0 + rand.nextInt(5));
        for(int i = 0; i < rockCount ;i++){
            int y = rand.nextInt(MaxLength + 16) + 2;
            int x = rand.nextInt(MaxHeight - 9) + 2;
//            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).getObjectMap().getChar(), new Grass().getChar())){
//                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).setObjectMap(new Rock(x, y, this));
//            } else{
//                i--;
//            }
        }
        for(int i = 0; i < treeCount ;i++){
//            int y = rand.nextInt(MaxLength +16) + 4;
//            int x = rand.nextInt(MaxHeight - 9) + 4;
//            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).getObjectMap().getChar(), new Grass().getChar())){
//                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).setObjectMap(new Tree(x, y, this));
//            } else{
//                i--;
//            }
        }
        for(int i = 0; i < plantCount ;i++){
//            int y = rand.nextInt(MaxLength +16) + 4;
//            int x = rand.nextInt(MaxHeight - 9) + 4;
//            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).getObjectMap().getChar(), new Grass().getChar())){
//                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).setObjectMap(new Plant(x, y, this));
//            } else{
//                i--;
//            }
        }
        for(int i = 0; i < cropCount ;i++){
//            int y = rand.nextInt(MaxLength +16) + 4;
//            int x = rand.nextInt(MaxHeight - 9) + 4;
//            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).getObjectMap().getChar(), new Grass().getChar())){
//                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).setObjectMap(new ForagingCrop(x, y, this));
//            } else{
//                i--;
//            }
        }
    }


    public ArrayList<Cell> getCells() {
        return cells;
    }



    public Cell getCell(int x , int y){
        for(Cell cell : cells){
            if(cell.getX() == x && cell.getY() == y){
                return cell;
            }
        }
        return null;
    }


    public void printMap(){
        int counter = 0;
        for(Cell cell:cells){
//            if(cell.getX()==App.getGame().getCurrentPlayer().getX()&&cell.getY()==App.getGame().getCurrentPlayer().getY()){
//                System.out.printf(App.getGame().getCurrentPlayer().getChar());
//            }
             {
                System.out.printf(cell.getObjectMap().getChar());
            }
            counter++;
            if(counter % 55 == 0){
                System.out.println();
            }
        }
    }



    public FarmType getFarmType() {
        return farmType;
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

    public void setFarmType(FarmType farmType) {
        this.farmType = farmType;
    }

    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }



    //    public void setCells(ArrayList<Cell> cells) {
//        this.cells = cells;
//    }
}
