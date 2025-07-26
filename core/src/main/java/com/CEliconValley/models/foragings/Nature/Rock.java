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
    private final int variant;
    private int anchorX;
    private  int anchorY;

    public int getType() {
        return variant;
    }
    public Rock(){

        rockType = RockType.SmallRock;
        this.variant = (int)(Math.random() * 15);
    }


    public Rock(int hitPoints, RockType rockType, int variant) {
        this.hitPoints = hitPoints;
        this.rockType = rockType;
        this.variant = variant;
    }


    public Rock(int x, int y, Farm farm) {
        anchorX = x;
        anchorY = y;
        Random rand = new Random();
        if(y >= 40){
            rockType = RockType.BigRock;
            hitPoints = 3;
            this.variant = (int)(Math.random() * 4);
        } else{
            rockType = RockType.SmallRock;
            hitPoints = 1;
            this.variant = (int)(Math.random() * 15);
        }
        Cell cell = Finder.findCellByCoordinates(x, y, farm);
        assert cell != null;
        cell.setObjectMap(this);
        if(rockType.equals(RockType.BigRock)){
            anchorX=x+1;
            anchorY=y;
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

    public int getVariant(){
        return variant;
    }
    public int getAnchorX() {
        return anchorX;
    }

    public int getAnchorY() {
        return anchorY;
    }
}
