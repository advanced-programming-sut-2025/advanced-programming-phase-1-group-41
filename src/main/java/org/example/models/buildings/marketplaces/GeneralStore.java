package org.example.models.buildings.marketplaces;

import org.example.models.*;
import org.example.models.buildings.Building;
import org.example.models.buildings.Door;
import org.example.models.buildings.Wall;
import org.example.models.buildings.marketplaces.items.GeneralStoreItems;
import org.example.models.buildings.marketplaces.items.MarketplaceItems;
import org.example.models.items.Item;
import org.example.models.items.Slot;
import org.example.models.locations.Village;

import java.util.ArrayList;

public class GeneralStore extends Marketplace implements Building {

    ArrayList<Slot> permanent = new ArrayList<>();
    ArrayList<Slot> backpacks = new ArrayList<>();
    ArrayList<Slot> spring = new ArrayList<>();
    ArrayList<Slot> summer = new ArrayList<>();
    ArrayList<Slot> fall = new ArrayList<>();
    private final Door door = new Door();


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
        super(null);
        constructGeneralStore(x,y,village);

        constructPermanentStock();

        constructBackpackStock();

        constructSpringStock();

        constructSummerStock();

        constructFallStock();

        updateStock();

    }

    public void constructPermanentStock(){
        permanent.add(new Slot(GeneralStoreItems.Rice, 10000));
        permanent.add(new Slot(GeneralStoreItems.Wheatflour, 10000));
        permanent.add(new Slot(MarketplaceItems.Bouquet, 2));
        permanent.add(new Slot(MarketplaceItems.WeddingRing, 2));
        permanent.add(new Slot(GeneralStoreItems.DehydratorRecipe, 1));
        permanent.add(new Slot(GeneralStoreItems.GrassStarterRecipe, 1));
        permanent.add(new Slot(GeneralStoreItems.Sugar, 10000));
        permanent.add(new Slot(GeneralStoreItems.Oil, 10000));
        permanent.add(new Slot(GeneralStoreItems.DeluxeRetainingSoil, 10000));
        permanent.add(new Slot(GeneralStoreItems.GrassStarter, 10000));
        permanent.add(new Slot(GeneralStoreItems.PlantGrow, 10000));
        permanent.add(new Slot(GeneralStoreItems.AppleSapling, 10000));
        permanent.add(new Slot(GeneralStoreItems.ApricotSapling, 10000));
        permanent.add(new Slot(GeneralStoreItems.CherrySapling, 10000));
        permanent.add(new Slot(GeneralStoreItems.OrangeSapling, 10000));
        permanent.add(new Slot(GeneralStoreItems.PeachSapling, 10000));
        permanent.add(new Slot(GeneralStoreItems.PomegranateSapling, 10000));
        permanent.add(new Slot(GeneralStoreItems.BasicRetainingSoil, 10000));
        permanent.add(new Slot(GeneralStoreItems.QualityRetainingSoil, 10000));
    }

    public void constructGeneralStore(int x, int y, Village village) {
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
                    cell.setObjectMap(door);
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

    public void constructBackpackStock(){
        backpacks.add(new Slot(GeneralStoreItems.LargeBackpack, 1));
        backpacks.add(new Slot(GeneralStoreItems.DeluxeBackpack, 1));
    }

    public void constructSpringStock(){
        spring.add(new Slot(GeneralStoreItems.ParsnipSeed, 5));
        spring.add(new Slot(GeneralStoreItems.BeanStarter, 5));
        spring.add(new Slot(GeneralStoreItems.CauliflowerSeed, 5));
        spring.add(new Slot(GeneralStoreItems.PotatoSeed, 5));
        spring.add(new Slot(GeneralStoreItems.TulipBulb, 5));
        spring.add(new Slot(GeneralStoreItems.KaleSeed, 5));
        spring.add(new Slot(GeneralStoreItems.JazzSeed, 5));
        spring.add(new Slot(GeneralStoreItems.GarlicSeed, 5));
        spring.add(new Slot(GeneralStoreItems.RiceShoot, 5));
    }

    public void constructSummerStock(){
        summer.add(new Slot(GeneralStoreItems.MelonSeed, 5));
        summer.add(new Slot(GeneralStoreItems.TomatoSeed, 5));
        summer.add(new Slot(GeneralStoreItems.BlueberrySeed, 5));
        summer.add(new Slot(GeneralStoreItems.PepperSeed, 5));
        summer.add(new Slot(GeneralStoreItems.WheatSeed, 5));
        summer.add(new Slot(GeneralStoreItems.RadishSeed, 5));
        summer.add(new Slot(GeneralStoreItems.PoppySeed, 5));
        summer.add(new Slot(GeneralStoreItems.SpangleSeed, 5));
        summer.add(new Slot(GeneralStoreItems.HopsStarter, 5));
        summer.add(new Slot(GeneralStoreItems.CornSeed, 5));
        summer.add(new Slot(GeneralStoreItems.SunflowerSeed, 5));
        summer.add(new Slot(GeneralStoreItems.RedCabbageSeed, 5));
    }

    public void constructFallStock(){
        fall.add(new Slot(GeneralStoreItems.EggplantSeed, 5));
        fall.add(new Slot(GeneralStoreItems.CornSeed, 5));
        fall.add(new Slot(GeneralStoreItems.PumpkinSeed, 5));
        fall.add(new Slot(GeneralStoreItems.BokChoySeed, 5));
        fall.add(new Slot(GeneralStoreItems.YamSeed, 5));
        fall.add(new Slot(GeneralStoreItems.CranberrySeed, 5));
        fall.add(new Slot(GeneralStoreItems.SunflowerSeed, 5));
        fall.add(new Slot(GeneralStoreItems.FairySeed, 5));
        fall.add(new Slot(GeneralStoreItems.AmaranthSeed, 5));
        fall.add(new Slot(GeneralStoreItems.GrapeStarter, 5));
        fall.add(new Slot(GeneralStoreItems.WheatSeed, 5));
        fall.add(new Slot(GeneralStoreItems.ArtichokeSeed, 5));
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
        for (Slot slot : itemsForSale) {
            if(slot.getItem() instanceof GeneralStoreItems gs){
                if(gs.isOffSeason()){
                    gs.setPrice(gs.getPrice() * 2/3);
                    gs.setOffSeason(false);
                }
            }
        }
        itemsForSale.clear();
        for (Slot slot : permanent) {
            itemsForSale.add(new Slot(slot.getItem(), slot.getQuantity()));
        }
        for (Slot slot : backpacks) {
            itemsForSale.add(new Slot(slot.getItem(), slot.getQuantity()));
        }
        for (Slot slot : spring) {
            double price = slot.getItem().getPrice();
            if(App.getGame().getTime().getSeason() != Season.Spring){
                price *= 1.5;
            }
            updateHelper(slot, price);
            if(!isInItemsForSale(slot)){
                itemsForSale.add(new Slot(slot.getItem(), slot.getQuantity()));
            }
        }
        for (Slot slot : summer) {
            double price = slot.getItem().getPrice();
            if(App.getGame().getTime().getSeason() != Season.Summer){
                price *= 1.5;
            }
            updateHelper(slot, price);
            if(!isInItemsForSale(slot)){
                itemsForSale.add(new Slot(slot.getItem(), slot.getQuantity()));
            }
        }
        for (Slot slot : fall) {
            double price = slot.getItem().getPrice();
            if(App.getGame().getTime().getSeason() != Season.Autumn){
                price *= 1.5;
            }
            updateHelper(slot, price);
            if(!isInItemsForSale(slot)){
                itemsForSale.add(new Slot(slot.getItem(), slot.getQuantity()));
            }
        }
    }

    private void updateHelper(Slot slot, double price){
        if(slot.getItem() instanceof GeneralStoreItems gs){
            if(!gs.isOffSeason()){
                if(price - gs.getPrice() > 0.1){
                    gs.setOffSeason(true);
                    gs.setPrice(price);
                }
            }
        }
    }


    public boolean isInItemsForSale(Slot slot){
        for (Slot slot1 : itemsForSale) {
            if(slot1.getItem().getName().equalsIgnoreCase(slot.getItem().getName())){
                return true;
            }
        }
        return false;
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
