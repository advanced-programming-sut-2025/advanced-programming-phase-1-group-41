package com.CEliconValley.models.foragings.Nature;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Finder;

import com.CEliconValley.models.locations.Farm;

import java.util.Random;

public class Mineral implements Nature, Obstacle {
    @Override
    public String getChar() {
        return Colors.colorize(15,51,typeIndex/10 + "" + typeIndex%10);
    }

    private final int typeIndex;

    @Override
    public String getName() {
        return mineralType.getName();
    }
    private final MineralType mineralType;
    public Mineral(int x, int y, Farm farm) {
        Random rand = new Random();
        int type = rand.nextInt(MineralType.values().length);
        typeIndex = type;
        mineralType = MineralType.values()[type];
        Cell cell= Finder.findCellByCoordinates(x, y, farm);
        assert cell != null;
        cell.setObjectMap(this);
    }
    public Mineral(MineralType mineralType) {
        this.mineralType = mineralType;
        this.typeIndex = mineralType.ordinal();
    }

    public MineralType getMineralType() {
        return mineralType;
    }


    @Override
    public double getPrice() {
        return mineralType.getPrice();
    }
}
