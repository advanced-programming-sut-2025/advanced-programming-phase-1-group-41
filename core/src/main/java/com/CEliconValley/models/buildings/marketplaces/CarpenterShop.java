package com.CEliconValley.models.buildings.marketplaces;

import com.CEliconValley.models.App;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.buildings.Wall;
import com.CEliconValley.models.buildings.Well;
import com.CEliconValley.models.buildings.animalContainer.BarnType;
import com.CEliconValley.models.buildings.animalContainer.CoopType;
import com.CEliconValley.models.buildings.marketplaces.items.CarpenterItems;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.locations.Village;

import java.util.HashMap;

public class CarpenterShop extends Marketplace implements Building {

    HashMap<BarnType, Integer> barnLimits;
    HashMap<CoopType, Integer> coopLimits;
    private final Door door = new Door();

    @Override
    public String getChar() {
        return Colors.colorize(0,205,"CS");
    }

    @Override
    public String getName() {
        return "Carpenter Shop";
    }
    private int x;
    private int y;
    public CarpenterShop(int x, int y, Village village) {
        super(null);
        constructCarpenterShop(x, y, village);

        barnLimits = new HashMap<>();
        coopLimits = new HashMap<>();

        itemsForSale.add(new Slot(CarpenterItems.Wood, 100000));
        itemsForSale.add(new Slot(CarpenterItems.Wood, 100000));
        itemsForSale.add(new Slot(CarpenterItems.ShippingBin, 100000));
        itemsForSale.add(new Slot(CarpenterItems.Well, 1));

        barnLimits.put(BarnType.Normal, 1);
        barnLimits.put(BarnType.Big, 1);
        barnLimits.put(BarnType.Deluxe, 1);

        coopLimits.put(CoopType.Normal, 1);
        coopLimits.put(CoopType.Big, 1);
        coopLimits.put(CoopType.Deluxe, 1);

    }

    public void constructCarpenterShop(int x, int y, Village village) {
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
        for (BarnType barnType : barnLimits.keySet()) {
            barnLimits.put(barnType, 1);
        }
        for (CoopType coopType : coopLimits.keySet()) {
            coopLimits.put(coopType, 1);
        }
        for (Slot slot : itemsForSale) {
            slot.setQuantity(100000);
            if(slot.getItem() instanceof Well){
                slot.setQuantity(1);
            }
        }
    }

    @Override
    public void updateHourly() {
        if(App.getGame().getTime().getHour() >= 9 && App.getGame().getTime().getHour() < 20) {
            door.setClosed(false);
            door.setClosesSoon(false);
            if(App.getGame().getTime().getHour() >= 18) {
                door.setClosesSoon(true);
            }
        } else{
            door.setClosed(true);
            door.setClosesSoon(false);
        }
    }


    public HashMap<BarnType, Integer> getBarnLimits() {
        return barnLimits;
    }

    public HashMap<CoopType, Integer> getCoopLimits() {
        return coopLimits;
    }
}
