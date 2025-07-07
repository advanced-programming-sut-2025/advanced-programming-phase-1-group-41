package com.CEliconValley.models.buildings;

import com.CEliconValley.models.Colors;

public class Wall implements Building {
    @Override
    public String getChar() {
        return Colors.colorize(3,0,"##");
    }

    @Override
    public String getName() {
        return "Wall";
    }
}
