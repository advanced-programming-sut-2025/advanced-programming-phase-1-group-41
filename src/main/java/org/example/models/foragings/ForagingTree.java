package org.example.models.foragings;

import org.example.models.*;
import org.example.models.locations.Farm;

import java.util.Random;

public class ForagingTree implements Foraging, Obstacle {
    @Override
    public String getChar() {
        return Colors.colorize(3,0,(typeIndex/10) + "" + typeIndex % 10);
    }

    @Override
    public String getName() {
        return foragingTreeType.toString();
    }

    private int hitPoints;
    private final ForagingTreeType foragingTreeType;
    private final int typeIndex;

    public ForagingTree(int x, int y, Farm farm) {
        Random rand = new Random();
        int type = rand.nextInt(ForagingTreeType.values().length);
        hitPoints = 4;
        typeIndex = type;
        foragingTreeType = ForagingTreeType.values()[type];
        Cell cell= Finder.findCellByCoordinates(x, y, farm);
        assert cell != null;
        cell.setObjectMap(this);
    }


    public int getHitPoints() {
        return hitPoints;
    }

    public void decreaseHitPoints() {
        hitPoints--;
    }
}
