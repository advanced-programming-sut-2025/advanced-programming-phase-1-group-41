package org.example.models.foragings.Nature;

import org.example.models.*;
import org.example.models.locations.Farm;

import java.util.Random;

public class Tree implements Nature, Obstacle {
    @Override
    public String getChar() {
        return Colors.colorize(3,0,(typeIndex/10) + "" + typeIndex % 10);
    }

    @Override
    public String getName() {
        return "Tree";
    }

    private int hitPoints;
    private final TreeType treeType;
    private final int typeIndex;

    public Tree(int x, int y, Farm farm) {
        Random rand = new Random();
        int type = rand.nextInt(TreeType.values().length);
        hitPoints = 4;
        typeIndex = type;
        treeType = TreeType.values()[type];
        Cell cell= Finder.findCellByCoordinates(x, y, farm);
        assert cell != null;
        cell.setObjectMap(this);
    }
    public TreeType getTreeType() {
        return treeType;
    }
    public int getTypeIndex() {
        return typeIndex;
    }
    public int getHitPoints() {
        return hitPoints;
    }
    public void decreaseHitPoints() {
        hitPoints--;
    }
}
