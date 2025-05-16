package org.example.models.foragings;

import org.example.models.App;
import org.example.models.Cell;
import org.example.models.Colors;
import org.example.models.Finder;
import org.example.models.items.Item;
import org.example.models.locations.Farm;

import java.util.Random;

public class ForagingCrop implements Foraging, Item {

    private ForagingCropType foragingCropType;
    int typeIndex;

    @Override
    public String getChar() {
        return Colors.colorize(109,0,typeIndex / 10 + "" + typeIndex % 10);
    }

    @Override
    public String getName() {
        return foragingCropType.getName();
    }

    @Override
    public double getPrice() {
        return foragingCropType.getBaseSellPrice();
    }

    public ForagingCrop(ForagingCropType foragingCropType) {
        this.foragingCropType = foragingCropType;
    }

    public ForagingCrop(int x, int y, Farm farm) {
        Random rand = new Random();
        do {
            int type = rand.nextInt(ForagingCropType.values().length);
            typeIndex = type;
            foragingCropType = ForagingCropType.values()[type];
        } while (!foragingCropType.getGrowingSeason().equals(App.getGame().getTime().getSeason()));
        Cell cell = Finder.findCellByCoordinates(x, y, farm);
        assert cell != null;
        cell.setObjectMap(this);
    }


}
