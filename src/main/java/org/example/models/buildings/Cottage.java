package org.example.models.buildings;

import org.example.models.Cell;
import org.example.models.Finder;
import org.example.models.Point;
import org.example.models.buildings.GreenHouse.WaterTank;
import org.example.models.locations.Farm;


public class Cottage implements Building {
    int x;
    int y;
    Farm farm;

    public Cottage(int x, int y, Farm farm) {
        this.x = x;
        this.y = y;
        this.farm = farm;
        int xWall;
        int yWall;
        yWall = y;
        while(yWall<=y+7) {
            for (int i = x; i <= x + 6; i++) {
                Cell cell = Finder.findCellByCoordinates(i, yWall, farm);
                assert cell != null;
                cell.setObjectMap(new Wall());
            }
            yWall+=7;
        }
        xWall = x;
        while(xWall<=y+6) {
            for (int j = y+1; j <= y+6; j++) {
                Cell cell = Finder.findCellByCoordinates(xWall, j, farm);
                assert cell != null;
                cell.setObjectMap(new Wall());
            }
            xWall+=6;
        }
        x++;
        y++;
        int xLength=4;
        int yLength=4;
        for(int i=x; i<xLength+x; i++) {
            for(int j=y; j<yLength+y; j++) {
                Cell cell=Finder.findCellByCoordinates(i, j, farm);
                assert cell != null;
                cell.setObjectMap(this);
            }
        }
    }

}
