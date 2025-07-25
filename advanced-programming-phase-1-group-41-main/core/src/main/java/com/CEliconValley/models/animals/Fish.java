package com.CEliconValley.models.animals;

import com.CEliconValley.models.items.Eatable;
import com.CEliconValley.models.items.Item;

public class Fish implements Item, Eatable {
    private final FishType fishType;
    private double quality;

    public Fish(FishType fishType) {
        this.fishType = fishType;
    }

    public FishType getFishType() {
        return fishType;
    }

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.quality = quality;
    }

    @Override
    public double getEnergy() {
        return fishType.getEnergy();
    }

    @Override
    public String getChar() {
        return "ff";
    }

    @Override
    public String getName() {
        return fishType.getName();
    }

    @Override
    public double getPrice() {
        return fishType.getPrice();
    }
}
