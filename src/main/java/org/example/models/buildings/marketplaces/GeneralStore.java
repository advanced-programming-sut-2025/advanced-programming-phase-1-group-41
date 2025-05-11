package org.example.models.buildings.marketplaces;

import org.example.models.Colors;
import org.example.models.buildings.Building;

public class GeneralStore extends Marketplace implements Building {
    @Override
    public String getChar() {
        return Colors.colorize(0,195,"GS");
    }

    @Override
    public String getName() {
        return "General Store";
    }
}
