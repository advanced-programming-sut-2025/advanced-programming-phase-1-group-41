package org.example.models.buildings;

import org.example.models.Colors;

public class PlayerHome implements Building {
    @Override
    public String getChar() {
        return Colors.colorize(94,178,"##");
    }

    private Refrigerator refrigerator;

    public PlayerHome() {
    }


    public Refrigerator getRefrigerator() {
        return refrigerator;
    }
}
