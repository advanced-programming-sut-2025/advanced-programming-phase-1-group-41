package org.example.models.buildings;

import org.example.models.ObjectMap;
import org.example.models.items.Item;

public class ShippingBin implements Building, Item {

    @Override
    public String getChar() {
        return "SB";
    }

    @Override
    public String getName() {
        return "ShippingBin";
    }

    @Override
    public double getPrice() {
        return 1000;
    }
}
