package org.example.models.buildings;

import org.example.models.Point;
import org.example.models.animals.Animal;

import java.util.ArrayList;

public class Cottage implements Building {
    private Point loc;

    public Cottage(Point loc) {
        this.loc = loc;
    }

    public Point getLoc() {
        return loc;
    }
}
