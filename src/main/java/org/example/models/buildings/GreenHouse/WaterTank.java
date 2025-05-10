package org.example.models.buildings.GreenHouse;

import org.example.models.Colors;
import org.example.models.ObjectMap;

public class WaterTank implements ObjectMap {
    @Override
    public String getChar() {
        return Colors.colorize(20,27,"@@");
    }
}
