package org.example.models.buildings;

import org.example.models.Colors;
import org.example.models.ObjectMap;

public class Bridge implements Building {
    @Override
    public String getChar() {
        return "\u001B[48;2;101;67;33m\u001B[38;5;39m||\u001B[0m";
    }

    @Override
    public String getName() {
        return "Bridge";
    }
}

