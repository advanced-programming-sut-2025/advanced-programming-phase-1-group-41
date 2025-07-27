package com.CEliconValley.models.buildings;

public class Bridge implements Building {
    @Override
    public String getChar() {
        return "\u001B[48;2;101;67;33m\u001B[38;5;39m||\u001B[0m";
    }

    @Override
    public String getName() {
        return "Bridge";
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

