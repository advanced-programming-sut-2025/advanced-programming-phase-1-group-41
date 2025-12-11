package com.CEliconValley.models.npc.npchomes;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.ui.TerminalColors;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.buildings.Wall;
import com.CEliconValley.models.locations.Village;

public class AbigailHome extends NPCHome implements Building {
    @Override
    public String getChar() {
        return TerminalColors.colorize(0,93,"..");
    }
    @Override
    public String getName() {
        return "Abigail Home";
    }
    private int x;
    private int y;
    private int anchorX;
    private int anchorY;

    public AbigailHome() {
        anchorX=38;
        anchorY=59;
    }

    public AbigailHome(int x, int y, Village village) {
        this.x = x;
        this.y = y;
        int xWall;
        int yWall;
        yWall = y;
        while(yWall<=y+4) {
            for (int i = x; i <= x + 4; i++) {
                Cell cell = Finder.findCellByCoordinatesVillage(i, yWall,village);
                assert cell != null;
                cell.setObjectMap(new Wall());
                if(i == x + 2&&yWall==y){
                    cell.setObjectMap(new Door());
                }
            }
            yWall+=4;
        }
        xWall = x;
        while(xWall<=x+4) {
            for (int j = y+1; j <= y+4; j++) {
                Cell cell = Finder.findCellByCoordinatesVillage(xWall, j, village);
                assert cell != null;
                cell.setObjectMap(new Wall());
            }
            xWall+=4;
        }
        x++;
        y++;
        int xLength=3;
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

    @Override
    public int getAnchorX() {
        return anchorX;
    }

    @Override
    public int getAnchorY() {
        return anchorY;
    }
}
