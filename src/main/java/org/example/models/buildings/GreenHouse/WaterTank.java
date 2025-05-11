package org.example.models.buildings.GreenHouse;

import org.example.models.Colors;
import org.example.models.ObjectMap;

public class WaterTank implements ObjectMap {
    @Override
    public String getChar() {
        return Colors.colorize(27,0,"@@");
    }

    @Override
    public String getName() {
        return "Water Tank";
    }
}
