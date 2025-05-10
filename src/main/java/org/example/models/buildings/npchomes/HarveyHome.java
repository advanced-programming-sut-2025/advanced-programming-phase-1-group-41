package org.example.models.buildings.npchomes;

import org.example.models.Colors;
import org.example.models.buildings.Building;

public class HarveyHome extends NPCHome implements Building {
    @Override
    public String getChar() {
        return Colors.colorize(8,249,"HH");
    }
}
