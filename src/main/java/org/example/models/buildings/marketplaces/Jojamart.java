package org.example.models.buildings.marketplaces;

import org.example.models.Colors;
import org.example.models.buildings.Building;

public class Jojamart extends Marketplace implements Building {
    @Override
    public String getChar() {
        return Colors.colorize(8,37,"JS");
    }

    @Override
    public String getName() {
        return "Jojamart";
    }

}
