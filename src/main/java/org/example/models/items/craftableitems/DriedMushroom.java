package org.example.models.items.craftableitems;

import org.example.models.foragings.ForagingCropType;
import org.example.models.items.Eatable;
import org.example.models.items.Item;

public class DriedMushroom implements Eatable, Item {
    ForagingCropType cropType;

    public DriedMushroom() {
    }

    public DriedMushroom(ForagingCropType cropType) {
        this.cropType = cropType;
    }

    public ForagingCropType getCropType() {
        return cropType;
    }

    public void setCropType(ForagingCropType cropType) {
        this.cropType = cropType;
    }

    @Override
    public double getEnergy() {
        return 50;
    }

    @Override
    public String getName() {
        return "DriedMushroom";
    }

    @Override
    public double getPrice() {
        return 25+7.5*cropType.getBaseSellPrice();
    }

    @Override
    public String getChar() {
        return "DM";
    }
}
