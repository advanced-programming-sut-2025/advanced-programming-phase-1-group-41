package models.buildings;

import models.Point;
import models.animals.Animal;

import java.util.ArrayList;

public class Coop {
    private Point loc;
    public final ArrayList<Animal> animals;

    public Coop(Point loc) {
        this.loc = loc;
        this.animals = new ArrayList<>();
    }

    public Point getLoc() {
        return loc;
    }
}
