package org.example.models.animals.animalKinds;

import org.example.models.Player;
import org.example.models.animals.Animal;
import org.example.models.animals.BarnOrCageSize;
import org.example.models.animals.Breed;

public class Goat extends Animal {
    public Goat(Player owner, String name) {
        super(owner, name, 4000, BarnOrCageSize.Big);
        super.breed = Breed.Barn;
    }

    @Override
    public void doTheFuckingJob() {

    }
}
