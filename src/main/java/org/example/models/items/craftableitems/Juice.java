package org.example.models.items.craftableitems;

import org.example.models.foragings.CropType;
import org.example.models.items.Eatable;
import org.example.models.items.Item;

public class Juice implements Item, Eatable {

    private CropType cropType;

    public Juice(CropType cropType) {
        this.cropType = cropType;
    }

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }

    @Override
    public double getEnergy() {
        return 2*cropType.getEnergy();
    }

    @Override
    public String getName() {
        return "Juice";
    }

    @Override
    public double getPrice() {
        return 2.25*cropType.getBaseSellPrice();
    }

    @Override
    public String getChar() {
        return "Ju";
    }
}
