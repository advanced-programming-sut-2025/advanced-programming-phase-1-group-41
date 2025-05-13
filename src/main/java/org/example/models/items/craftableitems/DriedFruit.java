package org.example.models.items.craftableitems;

import org.example.models.foragings.Fruit;
import org.example.models.items.Eatable;
import org.example.models.items.Item;

public class DriedFruit implements Item, Eatable {

    Fruit fruit;

    public DriedFruit() {
    }

    public DriedFruit(Fruit fruit) {
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
        return 75;
    }

    @Override
    public String getName() {
        return "DriedFruit";
    }

    @Override
    public double getPrice() {
        return 25+7.5*fruit.getPrice();
    }

    @Override
    public String getChar() {
        return "DF";
    }
}
