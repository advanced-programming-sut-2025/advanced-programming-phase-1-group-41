package org.example.models.foragings.Nature;

import org.example.models.items.Item;

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
