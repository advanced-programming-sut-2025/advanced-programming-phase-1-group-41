package org.example.models.buildings.marketplaces;

import org.example.models.Cell;
import org.example.models.Colors;
import org.example.models.Finder;
import org.example.models.buildings.Building;
import org.example.models.buildings.Door;
import org.example.models.buildings.Wall;
import org.example.models.buildings.marketplaces.items.FishShopItems;
import org.example.models.items.Slot;
import org.example.models.locations.Village;

import java.util.ArrayList;

public class FishShop extends Marketplace implements Building {

    ArrayList<Slot> stock= new ArrayList<>();

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
        stock.add(new Slot(FishShopItems.TroutSoup, 1));
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
        this.itemsForSale.clear();
        for (Slot slot : this.stock) {
            this.itemsForSale.add(new Slot(slot.getItem(), slot.getQuantity()));
        }
    }
}
