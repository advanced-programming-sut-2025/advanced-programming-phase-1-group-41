package org.example.models.foragings;

import org.example.models.items.Item;

public class Fruit implements Item {
    private FruitType type;

    public Fruit(FruitType type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return "Fruit";
    }

    @Override
    public String getChar() {
        return "Fr";
    }
}
