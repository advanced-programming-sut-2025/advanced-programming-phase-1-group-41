package com.CEliconValley.models.buildings;

import com.CEliconValley.models.foragings.Nature.Obstacle;
import com.CEliconValley.models.foragings.Nature.WaterTile;
import com.CEliconValley.models.items.Item;

public class Bridge implements Building, WaterTile {
    private int initialize=-1;
    private int type=-1;

    public int getInitialize() {
        return initialize;
    }

    public void setInitialize(int initialize) {
        this.initialize = initialize;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

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

