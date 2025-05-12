package org.example.models.foragings;

import org.example.models.items.Item;

public class Fruit implements Item {
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
    public double getPrice() {
        // maybe needs a change?
        return fruitType.getBaseSellPrice();
    }
}
