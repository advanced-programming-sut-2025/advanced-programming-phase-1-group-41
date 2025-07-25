package com.CEliconValley.models.buildings.GreenHouse;

import com.CEliconValley.models.Colors;
import com.CEliconValley.models.ObjectMap;

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
