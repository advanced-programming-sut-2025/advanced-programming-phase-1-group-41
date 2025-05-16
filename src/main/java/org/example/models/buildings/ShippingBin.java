package org.example.models.buildings;

import org.example.models.Colors;
import org.example.models.ObjectMap;
import org.example.models.items.Item;

import java.awt.*;

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
