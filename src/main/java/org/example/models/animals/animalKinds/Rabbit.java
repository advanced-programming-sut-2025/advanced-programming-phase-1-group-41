package org.example.models.animals.animalKinds;

import org.example.models.Player;
import org.example.models.animals.Animal;
import org.example.models.animals.BarnOrCageSize;
import org.example.models.animals.Breed;

public class Rabbit extends Animal {
    public Rabbit(Player owner, String name) {
        super(owner, name, 8000, BarnOrCageSize.Deluxe);
        super.breed = Breed.Coop;
    }

    @Override
    public void doTheFuckingJob() {

    }
}
