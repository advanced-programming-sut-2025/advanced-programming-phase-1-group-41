package org.example.models.buildings.Nature;

import org.example.models.Cell;
import org.example.models.Colors;
import org.example.models.Finder;
import org.example.models.ObjectMap;
import org.example.models.locations.Farm;

import java.util.Random;

public class Plant implements Nature {
    @Override
    public String getChar() {
        return Colors.colorize(109,0,"**");
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
}
