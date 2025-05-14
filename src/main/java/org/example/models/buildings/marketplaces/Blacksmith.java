package org.example.models.buildings.marketplaces;

import org.example.models.App;
import org.example.models.Cell;
import org.example.models.Colors;
import org.example.models.Finder;
import org.example.models.buildings.Building;
import org.example.models.buildings.Door;
import org.example.models.buildings.Refrigerator;
import org.example.models.buildings.Wall;
import org.example.models.foragings.Nature.Mine;
import org.example.models.foragings.Nature.Mineral;
import org.example.models.foragings.Nature.MineralType;
import org.example.models.items.Slot;
import org.example.models.locations.Village;
import org.example.models.npc.Clint;

import java.util.ArrayList;

public class Blacksmith extends Marketplace implements Building{




    @Override
    public String getChar() {
        return Colors.colorize(0,160,"BS");
    }

    @Override
    public String getName() {
        return "Black smith";
    }
    private int x;
    private int y;
    public Blacksmith(int x, int y, Village village) {
//        super(App.getGame().getVillage().getnpcByName("clint"));
        super(null);
        constructBlacksmith(x,y,village);
        itemsForSale.add(new Slot(new Mineral(MineralType.CopperOre), 10000));
        itemsForSale.add(new Slot(new Mineral(MineralType.IronOre), 10000));
        itemsForSale.add(new Slot(new Mineral(MineralType.GoldOre), 10000));
        itemsForSale.add(new Slot(new Mineral(MineralType.Coal), 10000));
    }

    private void constructBlacksmith(int x, int y , Village village){
        this.x = x;
        this.y = y;
        int xWall;
        int yWall;
        yWall = y;
        while(yWall<=y+5) {
            for (int i = x; i <= x + 5; i++) {
                Cell cell = Finder.findCellByCoordinatesVillage(i, yWall,village);
                assert cell != null;
                cell.setObjectMap(new Wall());
            }
            yWall+=5;
        }
        xWall = x;
        while(xWall<=x+5) {
            for (int j = y+1; j <= y+5; j++) {
                Cell cell = Finder.findCellByCoordinatesVillage(xWall, j, village);
                assert cell != null;
                cell.setObjectMap(new Wall());
                if(j == y + 2&&xWall==x+5){
                    cell.setObjectMap(new Door());
                }
            }
            xWall+=5;
        }
        x++;
        y++;
        int xLength=4;
        int yLength=4;
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
        System.out.println("blacksmith updated..");
    }
}
