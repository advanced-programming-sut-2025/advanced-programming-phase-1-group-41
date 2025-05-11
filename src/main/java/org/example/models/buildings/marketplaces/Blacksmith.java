package org.example.models.buildings.marketplaces;

import org.example.models.Colors;
import org.example.models.buildings.Building;
import org.example.models.items.Slot;

import java.util.ArrayList;

public class Blacksmith extends Marketplace implements Building{
    @Override
    public String getChar() {
        return Colors.colorize(0,160,"BB");
    }

    @Override
    public String getName() {
        return "Black smith";
    }
}
