package org.example.models.buildings.npchomes;

import org.example.models.Cell;
import org.example.models.Colors;
import org.example.models.Finder;
import org.example.models.buildings.Building;
import org.example.models.buildings.Door;
import org.example.models.buildings.Wall;
import org.example.models.locations.Village;

public class HarveyHome extends NPCHome implements Building {
    @Override
    public String getChar() {
        return Colors.colorize(15,0,"..");
    }

    @Override
    public String getName() {
        return "HarveyHome";
    }
    private int x;
    private int y;
    public HarveyHome(int x, int y, Village village) {
        this.x = x;
        this.y = y;
        int xWall;
        int yWall;
        yWall = y;
        while(yWall<=y+4) {
            for (int i = x; i <= x + 6; i++) {
                Cell cell = Finder.findCellByCoordinatesVillage(i, yWall,village);
                assert cell != null;
                cell.setObjectMap(new Wall());
                if(i == x + 3&&yWall==y+4){
                    cell.setObjectMap(new Door());
                }
            }
            yWall+=4;
        }
        xWall = x;
        while(xWall<=x+6) {
            for (int j = y+1; j <= y+4; j++) {
                Cell cell = Finder.findCellByCoordinatesVillage(xWall, j, village);
                assert cell != null;
                cell.setObjectMap(new Wall());
            }
            xWall+=6;
        }
        x++;
        y++;
        int xLength=5;
        int yLength=3;
        for(int i=x; i<xLength+x; i++) {
            for(int j=y; j<yLength+y; j++) {
                Cell cell=Finder.findCellByCoordinatesVillage(i, j, village);
                assert cell != null;
                cell.setObjectMap(this);
            }
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
