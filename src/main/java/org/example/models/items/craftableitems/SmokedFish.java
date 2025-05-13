package org.example.models.items.craftableitems;

import org.example.models.animals.Fish;
import org.example.models.items.Eatable;
import org.example.models.items.Item;

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
