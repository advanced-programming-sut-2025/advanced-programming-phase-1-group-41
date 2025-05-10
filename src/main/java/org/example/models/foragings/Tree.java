package org.example.models.foragings;

import org.example.models.Cell;
import org.example.models.Colors;
import org.example.models.Finder;
import org.example.models.ObjectMap;
import org.example.models.locations.Farm;

import java.util.Random;

public class Tree implements ObjectMap {
    @Override
    public String getChar() {
        return Colors.colorize(3,0,"I^");
    }
    private TreeType treeType;
    public Tree(int x, int y, Farm farm) {
        Random rand = new Random();
        int type = rand.nextInt(TreeType.values().length);
        treeType = TreeType.values()[type];
        Cell cell= Finder.findCellByCoordinates(x, y, farm);
        assert cell != null;
        cell.setObjectMap(this);
    }
    public TreeType getTreeType() {
        return treeType;
    }
}
