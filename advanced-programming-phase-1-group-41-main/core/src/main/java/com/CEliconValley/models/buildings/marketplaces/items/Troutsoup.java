package com.CEliconValley.models.buildings.marketplaces.items;

import com.CEliconValley.models.items.Eatable;
import com.CEliconValley.models.items.Item;

public class Troutsoup implements Item,  Eatable {
    @Override
    public double getEnergy() {
        return 250;
    }

    @Override
    public String getName() {
        return "Troutsoup";
    }

    @Override
    public double getPrice() {
        return 250;
    }

    @Override
    public String getChar() {
        return "TS";
    }
}
