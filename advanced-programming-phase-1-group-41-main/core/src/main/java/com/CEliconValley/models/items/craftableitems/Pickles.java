package com.CEliconValley.models.items.craftableitems;

import com.CEliconValley.models.foragings.Crop;
import com.CEliconValley.models.items.Eatable;
import com.CEliconValley.models.items.Item;

public class Pickles implements Item , Eatable {
    private Crop crop;

    public Pickles() {
    }

    public Pickles(Crop crop) {
        this.crop = crop;
    }


    public Crop getCrop() {
        return crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    @Override
    public double getEnergy() {
        return crop.getCropType().getEnergy()*1.75;
    }

    @Override
    public String getName() {
        return "Pickle";
    }

    @Override
    public double getPrice() {
        return 2* crop.getPrice()+50;
    }

    @Override
    public String getChar() {
        return "Pi";
    }
}
