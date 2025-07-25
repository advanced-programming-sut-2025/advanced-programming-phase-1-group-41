package com.CEliconValley.models.buildings.marketplaces;

import com.CEliconValley.models.App;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.buildings.Wall;
import com.CEliconValley.models.buildings.marketplaces.items.RanchItems;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.locations.Village;

import java.util.HashMap;

public class MarnieRanch extends Marketplace implements Building {

    private boolean shearLimit;
    private boolean milkpaleLimit;
    HashMap<String, Integer> dailyLimit;
    private final Door door = new Door();

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
        dailyLimit = new HashMap<>(){{
            put("Chicken", 2);
            put("Cow", 2);
            put("Goat", 2);
            put("Duck", 2);
            put("Sheep", 2);
            put("Rabbit", 2);
            put("Dinosaur", 2);
            put("Pig", 2);
        }};
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
                    cell.setObjectMap(door);
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
        dailyLimit.replaceAll((s, v) -> 2);
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

    public HashMap<String, Integer> getDailyLimit() {
        return dailyLimit;
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
