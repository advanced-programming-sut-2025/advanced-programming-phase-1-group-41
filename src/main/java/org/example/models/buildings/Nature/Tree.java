package org.example.models.buildings.Nature;

import org.example.models.*;
import org.example.models.locations.Farm;

import java.util.Random;

public class Tree implements Nature, Obstacle {
    @Override
    public String getChar() {
        return Colors.colorize(3,0,(typeIndex/10) + "" + typeIndex % 10);
    }

    private final TreeType treeType;
    private final int typeIndex;

    public Tree(int x, int y, Farm farm) {
        Random rand = new Random();
        int type = rand.nextInt(TreeType.values().length);
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
}
