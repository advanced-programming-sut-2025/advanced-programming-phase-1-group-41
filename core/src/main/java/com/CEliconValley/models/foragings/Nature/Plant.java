package com.CEliconValley.models.foragings.Nature;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.ui.TerminalColors;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.locations.Farm;

public class Plant implements Nature {
    @Override
    public String getChar() {
        return TerminalColors.colorize(109,0,"++");
    }

    @Override
    public String getName() {
        return "Plant";
    }

    public Plant() {
    }

    public Plant(int x, int y, Farm farm) {
        Cell cell= Finder.findCellByCoordinates(x, y, farm);
        assert cell != null;
        cell.setObjectMap(this);
    }

    @Override
    public double getPrice() {
        return 0;
    }
    public int getID() {

        return -1;
    }
}
