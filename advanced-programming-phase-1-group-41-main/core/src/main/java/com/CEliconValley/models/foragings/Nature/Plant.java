package com.CEliconValley.models.foragings.Nature;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.locations.Farm;

import java.util.Random;

public class Plant implements Nature {
    @Override
    public String getChar() {
        return Colors.colorize(109,0,"++");
    }

    @Override
    public String getName() {
        return "Plant";
    }
    private final PlantType plantType;
    public Plant(int x, int y, Farm farm) {
        Random rand = new Random();
        int type = rand.nextInt(PlantType.values().length);
        plantType = PlantType.values()[type];
        Cell cell= Finder.findCellByCoordinates(x, y, farm);
        assert cell != null;
        cell.setObjectMap(this);
    }
    public PlantType getPlantType() {
        return plantType;
    }

    @Override
    public double getPrice() {
        return plantType.getPrice();
    }
}
