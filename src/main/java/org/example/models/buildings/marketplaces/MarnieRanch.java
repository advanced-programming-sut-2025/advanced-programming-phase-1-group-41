package org.example.models.buildings.marketplaces;

import org.example.models.Cell;
import org.example.models.Colors;
import org.example.models.Finder;
import org.example.models.buildings.Building;
import org.example.models.buildings.Door;
import org.example.models.buildings.Wall;
import org.example.models.buildings.marketplaces.items.RanchItems;
import org.example.models.items.Slot;
import org.example.models.locations.Village;

public class MarnieRanch extends Marketplace implements Building {

    private boolean shearLimit;
    private boolean milkpaleLimit;


    @Override
    public String getChar() {
        return Colors.colorize(0,6,"MR");
    }

    @Override
    public String getName() {
        return "Marnie Ranch";
    }
    private int x;
    private int y;
    public MarnieRanch(int x, int y, Village village) {
        super(null);
        constructRanch(x, y, village);
        itemsForSale.add(new Slot(RanchItems.Hay, 10000));
        itemsForSale.add(new Slot(RanchItems.MilkPale, 10000));
        itemsForSale.add(new Slot(RanchItems.Shear, 10000));

        this.shearLimit = true;
        this.milkpaleLimit = true;
    }

    public void constructRanch(int x, int y, Village village) {
        this.x = x;
        this.y = y;
        int xWall;
        int yWall;
        yWall = y;
        while(yWall<=y+7) {
            for (int i = x; i <= x + 10; i++) {
                Cell cell = Finder.findCellByCoordinatesVillage(i, yWall,village);
                assert cell != null;
                cell.setObjectMap(new Wall());
                if(i == x + 5&&yWall==y+7){
                    cell.setObjectMap(new Door());
                }
            }
            yWall+=7;
        }
        xWall = x;
        while(xWall<=x+10) {
            for (int j = y+1; j <= y+7; j++) {
                Cell cell = Finder.findCellByCoordinatesVillage(xWall, j, village);
                assert cell != null;
                cell.setObjectMap(new Wall());
            }
            xWall+=10;
        }
        x++;
        y++;
        int xLength=9;
        int yLength=6;
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
        this.shearLimit = true;
        this.milkpaleLimit = true;
    }

    public boolean isMilkpaleLimit() {
        return milkpaleLimit;
    }

    public boolean isShearLimit() {
        return shearLimit;
    }

    public void setMilkpaleLimit(boolean milkpaleLimit) {
        this.milkpaleLimit = milkpaleLimit;
    }

    public void setShearLimit(boolean shearLimit) {
        this.shearLimit = shearLimit;
    }
}
