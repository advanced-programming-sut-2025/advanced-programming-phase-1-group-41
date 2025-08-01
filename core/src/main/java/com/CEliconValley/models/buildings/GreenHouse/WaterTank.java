package com.CEliconValley.models.buildings.GreenHouse;

import com.CEliconValley.models.ui.TerminalColors;
import com.CEliconValley.models.ObjectMap;

public class WaterTank implements ObjectMap {
    @Override
    public String getChar() {
        return TerminalColors.colorize(27,0,"@@");
    }

    @Override
    public String getName() {
        return "Water Tank";
    }
}
