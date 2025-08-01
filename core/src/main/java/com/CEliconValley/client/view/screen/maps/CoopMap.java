package com.CEliconValley.client.view.screen.maps;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.buildings.animalContainer.CoopType;
import com.CEliconValley.models.foragings.Nature.Grass;
import com.CEliconValley.models.locations.Location;
import dev.morphia.annotations.Transient;

import java.util.ArrayList;

public class CoopMap implements Location {

    @Transient
    private final ArrayList<Cell> cells = new ArrayList<>();
    private final int offsetX;
    private final int offsetY;
    CoopType coopType;

    public CoopMap(int offsetX, int offsetY, CoopType coopType) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.coopType = coopType;
        switch(coopType){
            case Deluxe: {
                for (int i = 0; i < 19; i++) {
                    for (int j = 0; j < 10; j++) {
                        Grass grass = new Grass();
                        Cell cell = new Cell(grass, offsetY + i, offsetX + j);
                        cells.add(cell);
                    }
                }
            }
            case Big: {
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 10; j++) {
                        Grass grass = new Grass();
                        Cell cell = new Cell(grass, offsetY + i, offsetX + j);
                        cells.add(cell);
                    }
                }
            }
            case Normal: {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 6; j++) {
                        if(i>0&&j==0)continue;
                        if(i==0&&j==0)continue;
                        Grass grass = new Grass();
                        Cell cell = new Cell(grass, offsetY + i, offsetX + j);
                        cells.add(cell);
                    }
                }

            }
        }

        Door door = new Door();
        cells.add(new Cell(door, offsetX+1 , offsetY));
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }
    public CoopType getCoopType(){
        return coopType;
    }

    public Cell getCell(int x, int y) {
        for (Cell cell : cells) {
            if (cell.getX() == x && cell.getY() == y) {
                return cell;
            }
        }
        return null;
    }

    public boolean isInsideCoop(int x, int y) {
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
