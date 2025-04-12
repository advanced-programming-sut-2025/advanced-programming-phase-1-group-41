package models.buildings;

import models.Point;
import models.animals.Animal;

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
