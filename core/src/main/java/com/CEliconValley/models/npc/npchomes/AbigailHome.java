package com.CEliconValley.models.npc.npchomes;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.buildings.Wall;
import com.CEliconValley.models.locations.Village;

public class AbigailHome extends NPCHome implements Building {
    @Override
    public String getChar() {
        return Colors.colorize(0,93,"..");
    }
    @Override
    public String getName() {
        return "Abigail Home";
    }
    private int x;
    private int y;
    public AbigailHome(int x, int y, Village village) {
        this.x = x;
        this.y = y;
        int xWall;
        int yWall;
        yWall = y;
        while(yWall<=y+6) {
            for (int i = x; i <= x + 8; i++) {
                Cell cell = Finder.findCellByCoordinatesVillage(i, yWall,village);
                assert cell != null;
                cell.setObjectMap(new Wall());
                if(i == x + 4&&yWall==y+6){
                    cell.setObjectMap(new Door());
                }
            }
            yWall+=6;
        }
        xWall = x;
        while(xWall<=x+8) {
            for (int j = y+1; j <= y+6; j++) {
                Cell cell = Finder.findCellByCoordinatesVillage(xWall, j, village);
                assert cell != null;
                cell.setObjectMap(new Wall());
            }
            xWall+=8;
        }
        x++;
        y++;
        int xLength=7;
        int yLength=5;
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
