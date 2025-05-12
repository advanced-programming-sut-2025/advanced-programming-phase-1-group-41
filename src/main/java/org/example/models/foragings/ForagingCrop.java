package org.example.models.foragings;

import org.example.models.Cell;
import org.example.models.Colors;
import org.example.models.Finder;
import org.example.models.locations.Farm;

import java.util.Random;

public class ForagingCrop implements Foraging{

    private final ForagingCropType foragingCropType;
    int typeIndex;

    @Override
    public String getChar() {
        return Colors.colorize(109,0,typeIndex / 10 + "" + typeIndex % 10);
    }

    @Override
    public String getName() {
        return foragingCropType.getName();
    }

    public ForagingCrop(int x, int y, Farm farm) {
        Random rand = new Random();
        int type = rand.nextInt(ForagingCropType.values().length);
        typeIndex = type;
        foragingCropType = ForagingCropType.values()[type];
        Cell cell = Finder.findCellByCoordinates(x, y, farm);
        assert cell != null;
        cell.setObjectMap(this);
    }


}
