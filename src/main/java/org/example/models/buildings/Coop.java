package org.example.models.buildings;

import org.example.models.Point;
import org.example.models.animals.Animal;

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
