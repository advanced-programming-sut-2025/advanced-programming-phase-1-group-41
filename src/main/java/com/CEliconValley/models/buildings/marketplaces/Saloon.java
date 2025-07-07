package com.CEliconValley.models.buildings.marketplaces;

import com.CEliconValley.models.App;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.buildings.Wall;
import com.CEliconValley.models.buildings.marketplaces.items.SaloonItems;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.locations.Village;

import java.util.ArrayList;

public class Saloon extends Marketplace implements Building {

    ArrayList<Slot> dailyLimit;
    private Door door = new Door();

    @Override
    public String getChar() {
        return Colors.colorize(136,214,"SS");
    }

    @Override
    public String getName() {
        return "Saloon";
    }
    private int x;
    private int y;
    public Saloon(int x, int y, Village village) {
        super(null);
        constructSaloon(x, y , village);



        itemsForSale.add(new Slot(SaloonItems.Beer, 10000));
        itemsForSale.add(new Slot(SaloonItems.Salad, 10000));
        itemsForSale.add(new Slot(SaloonItems.Bread, 10000));
        itemsForSale.add(new Slot(SaloonItems.Spaghetti, 10000));
        itemsForSale.add(new Slot(SaloonItems.Pizza, 10000));
        itemsForSale.add(new Slot(SaloonItems.Coffee, 10000));

        itemsForSale.add(new Slot(SaloonItems.HashbrownsRecipe, 1));
        itemsForSale.add(new Slot(SaloonItems.OmeletRecipe, 1));
        itemsForSale.add(new Slot(SaloonItems.PancakesRecipe, 1));
        itemsForSale.add(new Slot(SaloonItems.BreadRecipe, 1));
        itemsForSale.add(new Slot(SaloonItems.TortillaRecipe, 1));
        itemsForSale.add(new Slot(SaloonItems.PizzaRecipe, 1));
        itemsForSale.add(new Slot(SaloonItems.MakiRollRecipe, 1));
        itemsForSale.add(new Slot(SaloonItems.TripleShotEspressoRecipe, 1));
        itemsForSale.add(new Slot(SaloonItems.CookieRecipe, 1));

        dailyLimit = new ArrayList<>();

        dailyLimit.add(new Slot(SaloonItems.Beer, 10000));
        dailyLimit.add(new Slot(SaloonItems.Salad, 10000));
        dailyLimit.add(new Slot(SaloonItems.Bread, 10000));
        dailyLimit.add(new Slot(SaloonItems.Spaghetti, 10000));
        dailyLimit.add(new Slot(SaloonItems.Pizza, 10000));
        dailyLimit.add(new Slot(SaloonItems.Coffee, 10000));
        dailyLimit.add(new Slot(SaloonItems.HashbrownsRecipe, 1));
        dailyLimit.add(new Slot(SaloonItems.OmeletRecipe, 1));
        dailyLimit.add(new Slot(SaloonItems.PancakesRecipe, 1));
        dailyLimit.add(new Slot(SaloonItems.BreadRecipe, 1));
        dailyLimit.add(new Slot(SaloonItems.TortillaRecipe, 1));
        dailyLimit.add(new Slot(SaloonItems.PizzaRecipe, 1));
        dailyLimit.add(new Slot(SaloonItems.MakiRollRecipe, 1));
        dailyLimit.add(new Slot(SaloonItems.TripleShotEspressoRecipe, 1));
        dailyLimit.add(new Slot(SaloonItems.CookieRecipe, 1));
    }


    public void constructSaloon(int x, int y, Village village) {
        this.x = x;
        this.y = y;
        int xWall;
        int yWall;
        yWall = y;
        while(yWall<=y+7) {
            for (int i = x; i <= x + 5; i++) {
                Cell cell = Finder.findCellByCoordinatesVillage(i, yWall,village);
                assert cell != null;
                cell.setObjectMap(new Wall());
                if(i == x + 3&&yWall==y){
                    cell.setObjectMap(door);
                }
            }
            yWall+=7;
        }
        xWall = x;
        while(xWall<=x+5) {
            for (int j = y+1; j <= y+7; j++) {
                Cell cell = Finder.findCellByCoordinatesVillage(xWall, j, village);
                assert cell != null;
                cell.setObjectMap(new Wall());
            }
            xWall+=5;
        }
        x++;
        y++;
        int xLength=4;
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
        for (int i = 0; i < dailyLimit.size(); i++) {
            this.itemsForSale.get(i).setQuantity(this.dailyLimit.get(i).getQuantity());
        }
    }

    @Override
    public void updateHourly() {
        if(App.getGame().getTime().getHour() >= 12 && App.getGame().getTime().getHour() < 24) {
            door.setClosed(false);
            door.setClosesSoon(false);
            if(App.getGame().getTime().getHour() >= 22) {
                door.setClosesSoon(true);
            }
        } else{
            door.setClosed(true);
            door.setClosesSoon(false);
        }
    }
}
