package org.example.models.buildings;

import org.example.models.Colors;
import org.example.models.Point;
import org.example.models.animals.Animal;

import java.util.ArrayList;

public class PlayerHome implements Building {
    @Override
    public String getChar() {
        return Colors.colorize(94,178,"##");
    }

    private Point loc;
    private Refrigerator refrigerator;

    public PlayerHome(Point loc) {
        this.loc = loc;
    }

    public Point getLoc() {
        return loc;
    }

    public Refrigerator getRefrigerator() {
        return refrigerator;
    }
}
