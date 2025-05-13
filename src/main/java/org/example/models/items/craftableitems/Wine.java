package org.example.models.items.craftableitems;

import org.example.models.foragings.Fruit;
import org.example.models.items.Eatable;
import org.example.models.items.Item;

public class Wine implements Item, Eatable {
    private Fruit fruit;

    public Wine() {
    }

    public Wine(Fruit fruit) {
        this.fruit = fruit;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    @Override
    public double getEnergy() {
        return 1.75*fruit.getEnergy();
    }

    @Override
    public String getName() {
        return "Wine";
    }

    @Override
    public double getPrice() {
        return 3*fruit.getPrice();
    }

    @Override
    public String getChar() {
        return "Wi";
    }
}
