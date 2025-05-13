package org.example.models.locations;

import org.example.models.*;
import org.example.models.buildings.Building;
import org.example.models.foragings.Nature.Grass;
import org.example.models.foragings.Nature.Lake;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static org.example.models.App.MaxHeight;
import static org.example.models.App.MaxLength;


public class Village { ;
    private ArrayList<Cell> cells = new ArrayList<>();

    private final ArrayList<Building> buildings = new ArrayList<>();



    public Village() {

        for (int i = 0; i < 65; i++) {
            for (int j = 0; j < 95; j++) {
                cells.add(new Cell(new Grass(), j, i));
            }
        }
        makeOvalInCenter(30,20,0.8,1.2);
        makeOvalInCenter(6,4,0.7,1.3);
        for(int i = 0; i < 95; i++){
            for(int j = 0; j < 65; j++){
                boolean isGrass = false;
                boolean isGround = false;
                if(Math.abs(i / 3 - j / 2) < 2 && i > 5 && j > 3){
                    if(Finder.findCellByCoordinatesVillage(i, j, this) != null){
                        if(Finder.findCellByCoordinatesVillage(i, j, this).getObjectMap() instanceof Grass){
                            isGrass = true;
                            isGround = ((Grass) Finder.findCellByCoordinatesVillage(i, j, this).getObjectMap()).isGround();
                        }
                    }
                }
                if(Finder.findCellByCoordinatesVillage(i, j, this) != null && i / 3 == j / 2){
                    Objects.requireNonNull(Finder.findCellByCoordinatesVillage(i, j, this)).setObjectMap(new Lake(i, j, this));
                }
                if(isGrass){
                    Grass grass = new Grass();
                    if(isGround){
                        grass.setGround(true);
                    }
                    Objects.requireNonNull(Finder.findCellByCoordinatesVillage(i, j, this)).setObjectMap(grass);
                }
                if(i * i / 9 + j * j / 4 <= 50 || (i >= 75 && j >= 63)){
                    Objects.requireNonNull(Finder.findCellByCoordinatesVillage(i, j, this)).setObjectMap(new Lake(i, j, this, 0));
                }
            }
        }
    }
        Random rand = new Random();
    public void makeOvalInCenter(int a, int b,double c,double d) {
        int centerX = 95 / 2;
        int centerY = 65 / 2;

        for (int i = 0; i < 95; i++) {
            for (int j = 0; j < 65; j++) {

                double distance = Math.pow(i - centerX, 2) / Math.pow(a, 2) + Math.pow(j - centerY, 2) / Math.pow(b, 2);

                if (distance <= d && distance >= c) {
                    Cell cell = getCell(i, j);
                    if (cell != null && cell.getObjectMap() instanceof Grass) {

                        ((Grass) cell.getObjectMap()).setGround(true);
                    }

                }
            }
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
            if(counter % 95 == 0){
                System.out.println();
            }
        }
    }



    public ArrayList<Building> getBuildings() {
        return buildings;
    }


    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }



    //    public void setCells(ArrayList<Cell> cells) {
//        this.cells = cells;
//    }
}
