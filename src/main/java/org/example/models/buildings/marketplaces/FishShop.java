package org.example.models.buildings.marketplaces;

import org.example.models.Colors;
import org.example.models.buildings.Building;

public class FishShop extends Marketplace implements Building {
    @Override
    public String getChar() {
        return Colors.colorize(51,21,"FS");
    }

    @Override
    public String getName() {
        return "Fish Shop";
    }
}
