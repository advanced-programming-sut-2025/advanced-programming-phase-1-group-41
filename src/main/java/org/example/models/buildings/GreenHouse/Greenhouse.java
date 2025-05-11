package org.example.models.buildings.GreenHouse;

import org.example.models.Cell;
import org.example.models.Colors;
import org.example.models.Finder;
import org.example.models.buildings.Building;
import org.example.models.buildings.Wall;
import org.example.models.locations.Farm;

public class Greenhouse implements Building {
    @Override
    public String getChar() {
        return Colors.colorize(0,3,"XX");
    }
    private final int x;
    private final int y;
    private final Farm farm;

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
                if(yWall==y&&(i!=x&&i!=x+6)){
                    cell.setObjectMap(new WaterTank());
                }
                else {
                    cell.setObjectMap(new Wall());
                }

            }
            yWall+=7;
        }
        xWall = x;
        while(xWall<=x+6) {
            for (int j = y+1; j <= y+6; j++) {
                Cell cell = Finder.findCellByCoordinates(xWall, j, farm);
                cell.setObjectMap(new Wall());
            }
            xWall+=6;
        }
        x++;
        y++;
        int xLength=5;
        int yLength=6;
        for(int i=x; i<xLength+x; i++) {
            for(int j=y; j<yLength+y; j++) {
                Cell cell=Finder.findCellByCoordinates(i, j, farm);
//                assert cell != null;
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
}
