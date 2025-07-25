package com.CEliconValley.models.buildings.marketplaces;

import com.CEliconValley.models.App;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.buildings.Wall;
import com.CEliconValley.models.buildings.marketplaces.items.BlacksmithItems;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.locations.Village;

import java.util.ArrayList;

public class Blacksmith extends Marketplace implements Building{

    private ArrayList<Boolean> updates;

    private final Door door = new Door();

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
        itemsForSale.add(new Slot(BlacksmithItems.Coal, 10000));
        itemsForSale.add(new Slot(BlacksmithItems.IronOre, 10000));
        itemsForSale.add(new Slot(BlacksmithItems.GoldOre, 10000));
        itemsForSale.add(new Slot(BlacksmithItems.Coal, 10000));

        updates = new ArrayList<>();
        updates.add(true);
        updates.add(true);
        updates.add(true);
        updates.add(true);

        updates.add(true);
        updates.add(true);
        updates.add(true);
        updates.add(true);
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
                    cell.setObjectMap(door);
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
        updateHourly();
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

    public ArrayList<Boolean> getUpdates() {
        return updates;
    }

    @Override
    public void updateStock() {
        for (int i = 0; i < 8; i++) {
            updates.set(i,true);
        }
//        System.out.println("blacksmith updated..");
    }

    @Override
    public void updateHourly() {
        if(App.getGame().getTime().getHour() >= 9 && App.getGame().getTime().getHour() < 16) {
            door.setClosed(false);
            door.setClosesSoon(false);
            if(App.getGame().getTime().getHour() >= 14) {
                door.setClosesSoon(true);
            }
        } else{
            door.setClosed(true);
            door.setClosesSoon(false);
        }
    }
}
