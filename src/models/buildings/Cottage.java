package models.buildings;

import models.Point;
import models.animals.Animal;

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
