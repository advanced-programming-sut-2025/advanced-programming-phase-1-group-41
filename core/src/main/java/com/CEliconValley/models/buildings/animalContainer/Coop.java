package com.CEliconValley.models.buildings.animalContainer;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.ui.TerminalColors;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.buildings.Wall;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.models.locations.Village;

import java.util.ArrayList;

public class Coop implements Building {
    private ArrayList<Animal> animals = new ArrayList<>();
    private int x;
    private int y;
    private int anchorX;
    private int anchorY;
    private CoopType coopType;
    private int capacity;

    public int getX() {
        return x;
    }

    public CoopType getCoopType() {
        return coopType;
    }

    public int getCapacity() {
        return capacity;
    }

    public Coop() {
    }

    public Coop(ArrayList<Animal> animals, int anchorX, int anchorY,
                CoopType coopType, int capacity, int x, int y, Farm farm) {
        this.animals = new ArrayList<>(animals);
        this.anchorX = anchorX;
        this.anchorY = anchorY;
        this.capacity = capacity;
        this.coopType = coopType;
        this.x = x;
        this.y = y;
        int size = 5 + coopType.getCapacity() / 4;
        int xWall;
        int yWall;
        yWall = y;
        while(yWall<=y + size) {
            for (int i = x; i <= x + size; i++) {
                Cell cell = Finder.findCellByCoordinates(i, yWall, farm);
                assert cell != null;
                cell.setObjectMap(new Wall());
                if(i == x + 1 && yWall == y ){
                    cell.setObjectMap(new Door());
                }
            }
            yWall+=size;
        }
        xWall = x;
        while(xWall<=x+size) {
            for (int j = y+1; j <= y+size; j++) {
                Cell cell = Finder.findCellByCoordinates(xWall, j, farm);
                assert cell != null;
                cell.setObjectMap(new Wall());
            }
            xWall+=size;
        }
        x++;
        y++;
        for(int i = x; i< size +x - 1; i++) {
            for(int j = y; j< size +y - 1; j++) {
                Cell cell=Finder.findCellByCoordinates(i, j, farm);
                assert cell != null;
                cell.setObjectMap(this);
            }
        }
    }
    public Coop(int x, int y, Farm farm, CoopType coopType) {
        this.capacity = coopType.getCapacity();
        this.x = x;
        this.y = y;
        int size = 5 + coopType.getCapacity() / 4;
        anchorX=x+size-1;
        anchorY=y+1;
        int xWall;
        int yWall;
        this.coopType = coopType;
        yWall = y;
        while(yWall<=y + size) {
            for (int i = x; i <= x + size; i++) {
                Cell cell = Finder.findCellByCoordinates(i, yWall, farm);
                assert cell != null;
                cell.setObjectMap(new Wall());
                if(i == x + 1 && yWall == y ){
                    cell.setObjectMap(new Door());
                }
            }
            yWall+=size;
        }
        xWall = x;
        while(xWall<=x+size) {
            for (int j = y+1; j <= y+size; j++) {
                Cell cell = Finder.findCellByCoordinates(xWall, j, farm);
                assert cell != null;
                cell.setObjectMap(new Wall());
            }
            xWall+=size;
        }
        x++;
        y++;
        for(int i = x; i< size +x - 1; i++) {
            for(int j = y; j< size +y - 1; j++) {
                Cell cell=Finder.findCellByCoordinates(i, j, farm);
                assert cell != null;
                cell.setObjectMap(this);
            }
        }
    }
    public Coop(int x, int y, Village village) {
        this.x = x;
        this.y = y;
        int size = 3;
        int xWall;
        int yWall;
        yWall = y;
        while(yWall<=y + size) {
            for (int i = x; i <= x + size; i++) {
                Cell cell = Finder.findCellByCoordinatesVillage(i, yWall, village);
                assert cell != null;
                cell.setObjectMap(new Wall());
            }
            yWall+=size;
        }
        xWall = x;
        while(xWall<=x+size) {
            for (int j = y+1; j <= y+size; j++) {
                Cell cell = Finder.findCellByCoordinatesVillage(xWall, j, village);
                assert cell != null;
                cell.setObjectMap(new Wall());
            }
            xWall+=size;
        }
        x++;
        y++;
        for(int i = x; i< size +x - 1; i++) {
            for(int j = y; j< size +y - 1; j++) {
                Cell cell=Finder.findCellByCoordinatesVillage(i, j, village);
                assert cell != null;
                cell.setObjectMap(this);
            }
        }
    }
    public int updateCapacity(int capacityChange) {
        capacity+=capacityChange;
        return capacity;
    }

    @Override
    public String getChar() {
        return TerminalColors.colorize(243,234,"cc");
    }

    @Override
    public String getName() {
        return "Barn";
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    @Override
    public int getAnchorX() {
        return anchorX;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getAnchorY() {
        return anchorY;
    }
}
