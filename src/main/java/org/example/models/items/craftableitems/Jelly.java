package org.example.models.items.craftableitems;

import org.example.models.foragings.Fruit;
import org.example.models.items.CraftableItem;
import org.example.models.items.Eatable;
import org.example.models.items.Food;
import org.example.models.items.Item;

public class Jelly implements Item, Eatable {
    private Fruit fruit;

    public Jelly() {
    }

    public Jelly(Fruit fruit) {
        this.fruit = fruit;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    @Override
    public String getName() {
        return "Jelly";
    }

    @Override
    public double getPrice() {
        return this.fruit.getPrice()*2+50;
    }

    @Override
    public String getChar() {
        return "Je";
    }

    @Override
    public double getEnergy() {
        return this.fruit.getEnergy()*2;
    }
}
