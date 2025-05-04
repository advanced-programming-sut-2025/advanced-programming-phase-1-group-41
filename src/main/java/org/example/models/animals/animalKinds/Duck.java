package org.example.models.animals.animalKinds;

import org.example.models.Player;
import org.example.models.animals.Animal;
import org.example.models.animals.Breed;

public class Duck extends Animal {
    public Duck(Player owner) {
        super(owner);
        super.breed = Breed.Cooper;
    }

    @Override
    public void doTheFuckingJob() {

    }
}
