package com.CEliconValley.models.items.craftableitems;

import com.CEliconValley.models.foragings.Nature.Vegetable;
import com.CEliconValley.models.items.Eatable;
import com.CEliconValley.models.items.Item;

public class Juice implements Item, Eatable {

    private Vegetable vegetable;

    public Juice() {
    }


    public Juice(Vegetable vegetable) {
        this.vegetable = vegetable;
    }

    @Override
    public double getEnergy() {
        return 2* vegetable.getEnergy();
    }

    @Override
    public String getName() {
        return "Juice";
    }

    @Override
    public double getPrice() {
        return 2.25* vegetable.getPrice();
    }

    @Override
    public String getChar() {
        return "Ju";
    }
}
