package org.example.models.items.craftableitems;

import org.example.models.foragings.CropType;
import org.example.models.items.Eatable;
import org.example.models.items.Item;

public class Pickles implements Item , Eatable {
    private CropType cropType;

    public Pickles(CropType cropType) {
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
        return cropType.getEnergy()*1.75;
    }

    @Override
    public String getName() {
        return cropType+"Pickle";
    }

    @Override
    public double getPrice() {
        return 2* cropType.getBaseSellPrice()+50;
    }

    @Override
    public String getChar() {
        return "Pi";
    }
}
