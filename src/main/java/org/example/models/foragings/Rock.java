package org.example.models.foragings;

import org.example.models.Cell;
import org.example.models.Finder;
import org.example.models.ObjectMap;
import org.example.models.locations.Farm;

import java.util.Random;

public class Rock implements ObjectMap {
    private final RockType rockType;
    public Rock(int x, int y, Farm farm) {
        Random rand = new Random();
        int type = rand.nextInt(RockType.values().length);
        rockType = RockType.values()[type];
        Cell cell= Finder.findCellByCoordinates(x, y, farm);
        assert cell != null;
        cell.setObjectMap(this);
    }
    public RockType getRockType() {
        return rockType;
    }
}
