package org.example.models.locations;

import org.example.models.*;
import org.example.models.buildings.Building;
import org.example.models.foragings.Nature.Bush;

import java.util.ArrayList;
import java.util.Random;

import static org.example.models.App.MaxHeight;
import static org.example.models.App.MaxLength;


public class Village { ;
    private ArrayList<Cell> cells = new ArrayList<>();

    private final ArrayList<Building> buildings = new ArrayList<>();



    public Village() {

        for (int i = 0; i < MaxLength; i++) {
            for (int j = 0; j < MaxHeight; j++) {
                cells.add(new Cell(new Grass(), j, i));
            }
        }
    }
        Random rand = new Random();



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
