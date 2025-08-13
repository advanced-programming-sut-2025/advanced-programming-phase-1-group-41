package com.CEliconValley.client.view.screen.maps;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.buildings.animalContainer.BarnType;
import com.CEliconValley.models.foragings.Nature.Grass;
import com.CEliconValley.models.locations.Location;
import dev.morphia.annotations.Transient;

import java.util.ArrayList;

public class BarnMap implements Location {

    @Transient
    private final ArrayList<Cell> cells = new ArrayList<>();
    private final int offsetX;
    private final int offsetY;
    BarnType barnType;

    public BarnMap(int offsetX, int offsetY, BarnType  barnType) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.barnType = barnType;
        switch(barnType){
            case Deluxe: {
                for (int i = 3; i < 22; i++) {
                    for (int j = 4; j < 14; j++) {
                        if(i<=6&&j>=7&&j<=9)continue;
                        if(j==4&&i!=11)continue;
                        Grass grass = new Grass();
                        Cell cell = new Cell(grass, offsetY + i, offsetX + j);
                        cells.add(cell);
                    }
                }
                Door door = new Door();
                getCell(offsetX + 11, offsetY + 4).setObjectMap(door);
                break;
            }
            case Big: {
                for (int i = 1; i < 23; i++) {
                    for (int j = 2; j < 15; j++) {
                        if(i<=5&&j>=6&&j<=9)continue;
                        if(j==2&&i!=12)continue;
                        Grass grass = new Grass();
                        Cell cell = new Cell(grass, offsetY + i, offsetX + j);
                        cells.add(cell);
                    }
                }
                Door door = new Door();
                getCell(offsetX + 12, offsetY + 2).setObjectMap(door);
                break;
            }
            case Normal: {
                for (int i = 6; i < 19; i++) {
                    for (int j = 4; j < 13; j++) {
                        if(i<=9&&j>=7&&j<=8)continue;
                        if(j==4&&i!=14)continue;
                        Grass grass = new Grass();
                        Cell cell = new Cell(grass, offsetY + i, offsetX + j);
                        cells.add(cell);
                    }
                }
                Door door = new Door();
                getCell(offsetX + 14, offsetY + 4).setObjectMap(door);
                break;

            }
        }

//        getCell(offsetX + 11, offsetY + 4).setObjectMap(door);
//        getCell(offsetX + 10, offsetY + 4).setObjectMap(door);

    }

    public ArrayList<Cell> getCells() {
        return cells;
    }
    public BarnType getBarnType(){
        return barnType;
    }

    public Cell getCell(int x, int y) {
        for (Cell cell : cells) {
            if (cell.getX() == x && cell.getY() == y) {
                return cell;
            }
        }
        return null;
    }

    public boolean isInsideBarn(int x, int y) {
        return x >= offsetX && x < offsetX + 18 &&
            y >= offsetY && y < offsetY + 16;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
}
