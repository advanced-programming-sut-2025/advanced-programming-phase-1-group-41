package com.CEliconValley.models.buildings.marketplaces;

import com.CEliconValley.models.App;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.buildings.Wall;
import com.CEliconValley.models.buildings.marketplaces.items.JojamartItems;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.locations.Village;

import java.util.ArrayList;

public class Jojamart extends Marketplace implements Building {
    ArrayList<Slot> permanent = new ArrayList<>();
    ArrayList<Slot> spring = new ArrayList<>();
    ArrayList<Slot> summer = new ArrayList<>();
    ArrayList<Slot> fall = new ArrayList<>();
    ArrayList<Slot> winter = new ArrayList<>();
    private final Door door = new Door();
    private final Door door2 = new Door();

    @Override
    public String getChar() {
        return Colors.colorize(8,71,"JM");
    }

    @Override
    public String getName() {
        return "Jojamart";
    }
    private int x;
    private int y;
    public Jojamart(int x, int y, Village village) {
        super(null);
        constructJojamart(x,y,village);

        permanent.add(new Slot(JojamartItems.Jojacola, 10000));
        permanent.add(new Slot(JojamartItems.AncientSeed, 1));
        permanent.add(new Slot(JojamartItems.GrassStarter, 10000));
        permanent.add(new Slot(JojamartItems.Sugar, 10000));
        permanent.add(new Slot(JojamartItems.Wheatflour, 10000));
        permanent.add(new Slot(JojamartItems.Rice, 10000));



        spring.add(new Slot(JojamartItems.ParsnipSeed, 5));
        spring.add(new Slot(JojamartItems.BeanStarter, 5));
        spring.add(new Slot(JojamartItems.CauliflowerSeed, 5));
        spring.add(new Slot(JojamartItems.PotatoSeed, 5));
        spring.add(new Slot(JojamartItems.StrawberrySeed, 5));
        spring.add(new Slot(JojamartItems.TulipBulb, 5));
        spring.add(new Slot(JojamartItems.KaleSeed, 5));
        spring.add(new Slot(JojamartItems.CoffeeBean, 1));
        spring.add(new Slot(JojamartItems.CarrotSeed, 10));
        spring.add(new Slot(JojamartItems.RhubarbSeed, 5));
        spring.add(new Slot(JojamartItems.JazzSeed, 5));


        summer.add(new Slot(JojamartItems.TomatoSeed, 5));
        summer.add(new Slot(JojamartItems.PepperSeed, 5));
        summer.add(new Slot(JojamartItems.WheatSeed, 10));
        summer.add(new Slot(JojamartItems.SummerSquashSeed, 10));
        summer.add(new Slot(JojamartItems.RadishSeed, 5));
        summer.add(new Slot(JojamartItems.MelonSeed, 5));
        summer.add(new Slot(JojamartItems.HopsStarter, 5));
        summer.add(new Slot(JojamartItems.PoppySeed, 5));
        summer.add(new Slot(JojamartItems.SpangleSeed, 5));
        summer.add(new Slot(JojamartItems.StarfruitSeed, 5));
        summer.add(new Slot(JojamartItems.CoffeeBean, 1));
        summer.add(new Slot(JojamartItems.SunflowerSeed, 5));


        fall.add(new Slot(JojamartItems.Corn, 5));
        fall.add(new Slot(JojamartItems.Eggplant, 5));
        fall.add(new Slot(JojamartItems.Pumpkin, 5));
        fall.add(new Slot(JojamartItems.Broccoli, 5));
        fall.add(new Slot(JojamartItems.Amaranth, 5));
        fall.add(new Slot(JojamartItems.GrapeStarter, 5));
        fall.add(new Slot(JojamartItems.Beet, 5));
        fall.add(new Slot(JojamartItems.Yam, 5));
        fall.add(new Slot(JojamartItems.BokChoy, 5));
        fall.add(new Slot(JojamartItems.Cranberry, 5));
        fall.add(new Slot(JojamartItems.SunflowerSeed, 5));
        fall.add(new Slot(JojamartItems.Fairy, 5));
        fall.add(new Slot(JojamartItems.Rare, 1));
        fall.add(new Slot(JojamartItems.WheatSeed, 5));

        winter.add(new Slot(JojamartItems.PowdermelonSeeds, 10));

        this.updateStock();

    }

    public void constructJojamart(int x, int y, Village village) {
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
                if(i == x + 5&&yWall==y+7) {
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
                if(j == y + 1&&xWall==x) {
                    cell.setObjectMap(door2);
                }
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
        this.itemsForSale.clear();
        for (Slot slot : permanent) {
            this.itemsForSale.add(new Slot(slot.getItem(), slot.getQuantity()));
        }
        ArrayList<Slot> whichSeason = spring;
        switch (App.getGame().getTime().getSeason()){
            case Spring -> {
                whichSeason = spring;
            }
            case Summer -> {
                whichSeason = summer;
            }
            case Autumn -> {
                whichSeason = fall;
            }
            case Winter -> {
                whichSeason = winter;
            }
        }

        for (Slot slot : whichSeason) {
            this.itemsForSale.add(new Slot(slot.getItem(), slot.getQuantity()));
        }
    }
    @Override
    public void updateHourly() {
        if(App.getGame().getTime().getHour() >= 9 && App.getGame().getTime().getHour() < 23) {
            door.setClosed(false);
            door2.setClosed(false);
            door.setClosesSoon(false);
            door2.setClosesSoon(false);
            if(App.getGame().getTime().getHour() >= 21) {
                door.setClosesSoon(true);
                door2.setClosesSoon(true);
            }
        } else{
            door.setClosed(true);
            door2.setClosed(true);
            door.setClosesSoon(false);
            door2.setClosesSoon(false);
        }
    }
}
