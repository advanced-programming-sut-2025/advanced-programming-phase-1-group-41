package com.CEliconValley.views.maps;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.foragings.Nature.Grass;
import com.CEliconValley.models.locations.Location;
import dev.morphia.annotations.Transient;

import java.util.ArrayList;

public class CottageMap implements Location {

    @Transient
    private final ArrayList<Cell> cells = new ArrayList<>();
    private final int offsetX;
    private final int offsetY;

    public CottageMap(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;



        for (int i = 1; i < 24; i++) {
            for (int j = 2; j < 15; j++) {
                if(j==2&&i>=8&&i<=16)continue;
                if(j==3&&i>=10&&i<=16)continue;
                if(j==3&&i==8)continue;
                if(i>=15&&i<=16&&j>=5&&j<=8)continue;
                if(i!=9&&j>=8&&j<=10)continue;
                if((i!=9&&(i<11||i>20))&&j==11);
                if(j>10&&i>=20)continue;
                if(i==1&&j>=3&&j<=10)continue;
                if(i==10&&j>=13)continue;
                Grass grass = new Grass();
                Cell cell = new Cell(grass, offsetY + i, offsetX + j);
                cells.add(cell);
            }
        }
        Door door = new Door();
        cells.add(new Cell(door, offsetX + 9, offsetY + 3));
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public Cell getCell(int x, int y) {
        for (Cell cell : cells) {
            if (cell.getX() == x && cell.getY() == y) {
                return cell;
            }
        }
        return null;
    }



    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
}
