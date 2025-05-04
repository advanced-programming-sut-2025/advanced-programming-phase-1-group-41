package org.example.models.buildings;

import org.example.models.Point;
import org.example.models.animals.Animal;

import java.util.ArrayList;

public class PlayerHome implements Building {

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
