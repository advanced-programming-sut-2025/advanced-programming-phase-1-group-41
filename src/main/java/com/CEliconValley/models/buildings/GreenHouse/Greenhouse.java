package com.CEliconValley.models.buildings.GreenHouse;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.buildings.Wall;
import com.CEliconValley.models.locations.Farm;

import java.util.ArrayList;

public class Greenhouse implements Building {
    @Override
    public String getChar() {
        if(isUnlocked){
            return Colors.colorize(0,3,"XX");
        }
        return Colors.colorize(0,237,"XX");
    }
    private int x;
    private int y;
    private Farm farm;
    private boolean isUnlocked = false;
    private ArrayList<Door> doors = new ArrayList<>();

    @Override
    public String getName() {
        return "Green House";
    }

    public Greenhouse() {

    }
    public Greenhouse(int x, int y, Farm farm) {
        this.x = x;
        this.y = y;
        this.farm = farm;
        int xWall;
        int yWall;
        yWall = y;
        while(yWall<=y+7) {
            for (int i = x; i <= x + 6; i++) {
                Cell cell = Finder.findCellByCoordinates(i, yWall, farm);
                if(yWall == y && (i != x && i != x + 6)){
                    assert cell != null;
                    cell.setObjectMap(new WaterTank());
                }
                else {
                    assert cell != null;
                    cell.setObjectMap(new Wall());
                }
                if(i == x + 3 && yWall == y + 7){
                    Door door = new Door();
                    door.setClosed(true);
                    doors.add(door);
                    cell.setObjectMap(door);
                }

            }
            yWall += 7;
        }
        xWall = x;
        while(xWall<=x+6) {
            for (int j = y+1; j <= y+6; j++) {
                Cell cell = Finder.findCellByCoordinates(xWall, j, farm);
                assert cell != null;
                cell.setObjectMap(new Wall());
                if((j == y + 3 && xWall == x + 6) || (j == y + 4 && xWall == x + 6)){
                    Door door = new Door();
                    door.setClosed(true);
                    doors.add(door);
                    cell.setObjectMap(door);
                }
            }
            xWall+=6;
        }
        x++;
        y++;
        int xLength=5;
        int yLength=6;
        for(int i = x; i < xLength + x; i++) {
            for(int j = y; j < yLength + y; j++) {
                Cell cell=Finder.findCellByCoordinates(i, j, farm);
                assert cell != null;
                cell.setObjectMap(this);
            }
        }
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public Farm getFarm() {
        return farm;
    }
    public void unlock() {
        isUnlocked = true;
        for (Door door : doors) {
            door.setClosed(false);
        }
    }
    public boolean isGreenHouse(int x1, int y1) {
        for(int i = x; i< x + 5; i++) {
            for(int j = y; j< y + 6; j++) {
                if(i == x1 && j == y1){
                    return true;
                }
            }
        }
        return false;
    }
}
