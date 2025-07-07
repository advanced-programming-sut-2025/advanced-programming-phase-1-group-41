package com.CEliconValley.models.foragings.Nature;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Finder;

import com.CEliconValley.models.locations.Farm;

import java.util.Random;

public class Rock implements Nature, Obstacle {
    @Override
    public String getChar() {
        return Colors.colorize(250,0,"OO");
    }

    @Override
    public String getName() {
        return "Rock";
    }

    private int hitPoints;
    private final RockType rockType;

    public Rock(){
        rockType = RockType.SmallRock;
    }

    public Rock(int x, int y, Farm farm) {
        Random rand = new Random();
        if(y >= 40){
            rockType = RockType.BigRock;
            hitPoints = 3;
        } else{
            rockType = RockType.SmallRock;
            hitPoints = 1;
        }
        Cell cell = Finder.findCellByCoordinates(x, y, farm);
        assert cell != null;
        cell.setObjectMap(this);
        if(rockType.equals(RockType.BigRock)){
            Cell cell2 = Finder.findCellByCoordinates(x + 1, y, farm);
            if(cell2 != null&& cell2.getObjectMap() instanceof Grass){
                cell2.setObjectMap(this);
            }
            Cell cell3 = Finder.findCellByCoordinates(x + 1, y + 1, farm);
            if(cell3 != null && cell3.getObjectMap() instanceof Grass){
                cell3.setObjectMap(this);
            }
            Cell cell4 = Finder.findCellByCoordinates(x, y + 1, farm);
            if(cell4 != null && cell4.getObjectMap() instanceof Grass){
                cell4.setObjectMap(this);
            }
        }
    }
    public RockType getRockType() {
        return rockType;
    }

    public int getHitPoints() {
        return hitPoints;
    }
    public void decreaseHitPoints() {
        hitPoints--;
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
