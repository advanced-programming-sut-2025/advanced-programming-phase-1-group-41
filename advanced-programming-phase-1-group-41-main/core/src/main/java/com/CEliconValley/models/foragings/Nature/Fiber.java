package com.CEliconValley.models.foragings.Nature;

import com.CEliconValley.models.items.Item;

public class Fiber implements Item {
    @Override
    public String getName() {
        return "Fiber";
    }

    @Override
    public double getPrice() {
        return 10;
    }

    @Override
    public String getChar() {
        return "Fi";
    }
}
