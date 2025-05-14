package org.example.models.buildings.marketplaces;

import org.example.models.Cell;
import org.example.models.Colors;
import org.example.models.Finder;
import org.example.models.buildings.Building;
import org.example.models.buildings.Door;
import org.example.models.buildings.Wall;
import org.example.models.locations.Village;

public class GeneralStore extends Marketplace implements Building {
    @Override
    public String getChar() {
        return Colors.colorize(0,105,"GS");
    }

    @Override
    public String getName() {
        return "General Store";
    }
    private int x;
    private int y;
    public GeneralStore(int x, int y, Village village) {
        this.x = x;
        this.y = y;
        int xWall;
        int yWall;
        yWall = y;
        while(yWall<=y+8) {
            for (int i = x; i <= x + 18; i++) {
                Cell cell = Finder.findCellByCoordinatesVillage(i, yWall,village);
                assert cell != null;
                cell.setObjectMap(new Wall());
                if(i == x + 9&&yWall==y+8){
                    cell.setObjectMap(new Door());
                }
            }
            yWall+=8;
        }
        xWall = x;
        while(xWall<=x+18) {
            for (int j = y+1; j <= y+8; j++) {
                Cell cell = Finder.findCellByCoordinatesVillage(xWall, j, village);
                assert cell != null;
                cell.setObjectMap(new Wall());
            }
            xWall+=18;
        }
        x++;
        y++;
        int xLength=17;
        int yLength=7;
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
    public void updateStock() {

    }
}
