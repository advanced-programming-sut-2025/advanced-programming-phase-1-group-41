package com.CEliconValley.models.foragings;

import com.CEliconValley.models.items.Eatable;
import com.CEliconValley.models.items.Item;

public class Fruit implements Item, Eatable {
    private final FruitType fruitType;

    public Fruit(FruitType fruitType) {
        this.fruitType = fruitType;
    }

    @Override
    public String getChar() {
        return "FF";
    }

    @Override
    public String getName() {
        return fruitType.getName();
    }

    public FruitType getType() {
        return fruitType;
    }

    @Override
    public double getEnergy() {
        return this.fruitType.getEnergy();
    }

    @Override
    public double getPrice() {
        // maybe needs a change?
        return fruitType.getBaseSellPrice();
    }
}
