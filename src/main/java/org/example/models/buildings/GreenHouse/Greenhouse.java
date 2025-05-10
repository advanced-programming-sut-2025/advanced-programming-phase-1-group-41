package org.example.models.buildings.GreenHouse;

import org.example.models.Cell;
import org.example.models.Finder;
import org.example.models.buildings.Building;
import org.example.models.locations.Farm;

public class Greenhouse implements Building {
    public Greenhouse(int x, int y, Farm farm) {
        int xWall;
        int yWall;
        yWall = y;
        while(yWall<=y+7) {
            for (int i = x; i <= x + 6; i++) {
                Cell cell = Finder.findCellByCoordinates(i, yWall, farm);
                if(yWall==y){
                    cell.setObjectMap(new WaterTank());
                }
                else {
                    cell.setObjectMap(new Wall());
                }
            }
            yWall+=7;
        }
        xWall = x;
        while(xWall<=y+6) {
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
                Cell cell=Finder.findCellByCoordinates(x, y, farm);
                assert cell != null;
                cell.setObjectMap(this);
            }
        }
    }
}
