package org.example.models.foragings;

import org.example.models.Cell;
import org.example.models.Finder;
import org.example.models.ObjectMap;
import org.example.models.locations.Farm;

import java.util.Random;

public class Seed implements ObjectMap {
    private final SeedType seedType;
    private final SeedStage seedStage;
    public Seed(int x, int y, Farm farm) {
        Random rand = new Random();
        int type = rand.nextInt(SeedType.values().length);
        seedType = SeedType.values()[type];
        seedStage = SeedStage.values()[0];
        Cell cell= Finder.findCellByCoordinates(x, y, farm);
        assert cell != null;
        cell.setObjectMap(this);
    }
    public SeedType getSeedType() {
        return seedType;
    }
    public SeedStage getSeedStage() {
        return seedStage;
    }
}
