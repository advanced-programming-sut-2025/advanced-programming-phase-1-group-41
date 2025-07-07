package com.CEliconValley.models.buildings;

import com.CEliconValley.models.Colors;
import com.CEliconValley.models.items.Item;

public class ShippingBin implements Building, Item {

    @Override
    public String getChar() {
        return Colors.foreColor(52) + Colors.backColor(208) + "||" + Colors.RESET;
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
