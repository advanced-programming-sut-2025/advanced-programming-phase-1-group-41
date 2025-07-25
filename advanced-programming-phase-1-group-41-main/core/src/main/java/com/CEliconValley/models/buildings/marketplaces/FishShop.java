package com.CEliconValley.models.buildings.marketplaces;

import com.CEliconValley.models.App;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.buildings.Wall;
import com.CEliconValley.models.buildings.marketplaces.items.FishShopItems;
import com.CEliconValley.models.buildings.marketplaces.items.Troutsoup;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.locations.Village;

import java.util.ArrayList;

public class FishShop extends Marketplace implements Building {

    ArrayList<Slot> stock= new ArrayList<>();
    private final Door door = new Door();

    @Override
    public String getChar() {
        return Colors.colorize(51,21,"FS");
    }

    @Override
    public String getName() {
        return "Fish Shop";
    }
    private int x;
    private int y;
    public FishShop(int x, int y, Village village) {
        super(null);
        constructFishShop(x, y, village);

        stock.add(new Slot(FishShopItems.FishSmokerRecipe, 1));
        stock.add(new Slot(new Troutsoup(), 1));
        stock.add(new Slot(FishShopItems.TrainingRod, 1));
        stock.add(new Slot(FishShopItems.BambooRod, 1));
        stock.add(new Slot(FishShopItems.FiberglassRod, 1));
        stock.add(new Slot(FishShopItems.IridiumRod, 1));

        this.updateStock();
    }

    public void constructFishShop(int x , int y , Village village){
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
                if(j == y + 2&&xWall==x){
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
        this.itemsForSale.clear();
        for (Slot slot : this.stock) {
            this.itemsForSale.add(new Slot(slot.getItem(), slot.getQuantity()));
        }
    }
    @Override
    public void updateHourly() {
        if(App.getGame().getTime().getHour() >= 9 && App.getGame().getTime().getHour() < 17) {
            door.setClosed(false);
            door.setClosesSoon(false);
            if(App.getGame().getTime().getHour() >= 15) {
                door.setClosesSoon(true);
            }
        } else{
            door.setClosed(true);
            door.setClosesSoon(false);
        }
    }
}
