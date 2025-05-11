package org.example.models.foragings;

import org.example.models.*;
import org.example.models.locations.Farm;

import java.util.Random;

public class Mineral implements ObjectMap, Obstacle {
    @Override
    public String getChar() {
        return Colors.colorize(15,51,"mm");
    }
    private final MineralType mineralType;
    public Mineral(int x, int y, Farm farm) {
        Random rand = new Random();
        int type = rand.nextInt(MineralType.values().length);
        mineralType = MineralType.values()[type];
        Cell cell= Finder.findCellByCoordinates(x, y, farm);
        assert cell != null;
        cell.setObjectMap(this);
    }
    public MineralType getMineralType() {
        return mineralType;
    }
}
