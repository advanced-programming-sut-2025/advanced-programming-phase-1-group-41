package org.example.models.buildings.npchomes;

import org.example.models.Colors;
import org.example.models.buildings.Building;

public class LiaHome extends NPCHome implements Building {
    @Override
    public String getChar() {
        return Colors.colorize(243,255,"LH");
    }

    @Override
    public String getName() {
        return "Lia Home";
    }
}
