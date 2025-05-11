package org.example.models.buildings;

import org.example.models.Colors;
import org.example.models.animals.Animal;

import java.util.ArrayList;

public class Barn implements Building {
    public final ArrayList<Animal> animals;

    public Barn() {
        this.animals = new ArrayList<>();
    }

    @Override
    public String getChar() {
        return Colors.colorize(243,234,"bb");
    }

    @Override
    public String getName() {
        return "Barn";
    }
}
