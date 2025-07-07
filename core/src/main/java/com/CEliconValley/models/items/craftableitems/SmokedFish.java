package com.CEliconValley.models.items.craftableitems;

import com.CEliconValley.models.animals.Fish;
import com.CEliconValley.models.items.Eatable;
import com.CEliconValley.models.items.Item;

public class SmokedFish implements Item, Eatable {
    private Fish fish;

    public SmokedFish() {
    }

    public SmokedFish(Fish fish) {
        this.fish = fish;
    }

    @Override
    public double getEnergy() {
        return fish.getEnergy()*1.5;
    }

    @Override
    public String getName() {
        return "SmokedFish";
    }

    @Override
    public double getPrice() {
        return 2*fish.getPrice();
    }

    @Override
    public String getChar() {
        return "SF";
    }
}
