package org.example.models.buildings.npchomes;

import org.example.models.Colors;
import org.example.models.buildings.Building;

public class RobinHome extends NPCHome implements Building {
    @Override
    public String getChar() {
        return Colors.colorize(15,233,"RH");
    }

    @Override
    public String getName() {
        return "Rabin Home";
    }
}
