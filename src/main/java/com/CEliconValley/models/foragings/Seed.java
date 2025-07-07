package com.CEliconValley.models.foragings;

import com.CEliconValley.models.items.Item;

public class Seed implements Item {
    private final SeedType seedType;

    public Seed(SeedType seedType) {
        this.seedType = seedType;
    }

    @Override
    public String getChar() {
        return "SS";
    }

    @Override
    public String getName() {
        return seedType.getName();
    }

    public SeedType getSeedType() {
        return seedType;
    }

    @Override
    public double getPrice() {
        return seedType.getPrice();
    }
}
