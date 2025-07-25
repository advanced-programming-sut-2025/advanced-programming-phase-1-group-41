package com.CEliconValley.models.foragings;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Finder;

import com.CEliconValley.models.foragings.Nature.Obstacle;
import com.CEliconValley.models.foragings.Nature.TreeType;
import com.CEliconValley.models.locations.Farm;

import java.util.Random;

public class ForagingTree implements Foraging, Obstacle {
    @Override
    public String getChar() {
        if(isThundered){
            return Colors.colorize(15,0,(typeIndex/10) + "" + typeIndex % 10);
        }
        return Colors.colorize(3,0,(typeIndex/10) + "" + typeIndex % 10);
    }

    @Override
    public String getName() {
        return foragingTreeType.getName();
    }

    private int hitPoints;
    private final TreeType foragingTreeType;
    private final int typeIndex;
    private boolean isThundered = false;

    public ForagingTree(int x, int y, Farm farm) {
        Random rand = new Random();
        int type = rand.nextInt(ForagingTreeType.values().length);
        hitPoints = 4;
        typeIndex = type;
        foragingTreeType = ForagingTreeType.values()[type].getTreeType();
        Cell cell= Finder.findCellByCoordinates(x, y, farm);
        assert cell != null;
        cell.setObjectMap(this);
    }

    public void thunder(){
        isThundered = true;
    }
    public boolean isThundered(){
        return isThundered;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void decreaseHitPoints() {
        hitPoints--;
    }

    public TreeType getTreeType() {
        return foragingTreeType;
    }
}
