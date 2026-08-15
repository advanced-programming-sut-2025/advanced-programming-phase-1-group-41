package com.CEliconValley.models.buildings;

import com.CEliconValley.models.ui.TerminalColors;

public class Wall implements Building {
    @Override
    public String getChar() {
        return TerminalColors.colorize(3,0,"##");
    }

    @Override
    public String getName() {
        return "Wall";
    }

    @Override
    public int getAnchorX() {
        return 0;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int getAnchorY() {
        return 0;
    }
}
