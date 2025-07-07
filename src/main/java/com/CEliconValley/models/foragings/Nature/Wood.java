package com.CEliconValley.models.foragings.Nature;

import com.CEliconValley.models.items.Item;

public class Wood implements Item {
    @Override
    public String getName() {
        return "Wood";
    }
    // TODO add the wood in finder
    @Override
    public String getChar() {
        return "wd";
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
