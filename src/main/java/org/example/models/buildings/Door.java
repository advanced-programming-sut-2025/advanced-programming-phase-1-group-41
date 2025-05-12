package org.example.models.buildings;

import org.example.models.Colors;
import org.example.models.ObjectMap;

public class Door implements Building {
    @Override
    public String getChar() {
        return Colors.colorize(250,0,"[]");
    }

    @Override
    public String getName() {
        return "Door";
    }
}
