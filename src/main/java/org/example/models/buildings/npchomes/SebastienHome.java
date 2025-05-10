package org.example.models.buildings.npchomes;

import org.example.models.Colors;
import org.example.models.buildings.Building;

public class SebastienHome extends NPCHome implements Building {
    @Override
    public String getChar() {
        return Colors.colorize(0,235,"SH");
    }
}
