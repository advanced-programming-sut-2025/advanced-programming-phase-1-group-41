package models.buildings;

import models.Point;
import models.animals.Animal;

import java.util.ArrayList;

public class Barn implements Building {
    private Point loc;
    public final ArrayList<Animal> animals;

    public Barn(Point loc) {
        this.loc = loc;
        this.animals = new ArrayList<>();
    }
}
