package org.example.models.animals.animalKinds;

import org.example.models.Player;
import org.example.models.animals.Animal;
import org.example.models.animals.Breed;

public class Sheep extends Animal {
    public Sheep(Player owner) {
        super(owner);
        super.breed = Breed.Barner;
    }

    @Override
    public void doTheFuckingJob() {

    }
}
