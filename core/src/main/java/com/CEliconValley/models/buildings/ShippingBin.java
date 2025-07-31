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
    public int getID() {

        return 30400;
    }

    @Override
    public int getAnchorX() {
        return 0;
    }

    @Override
    public int getAnchorY() {
        return 0;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }
}
