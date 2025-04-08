package models.animals.animalKinds;

import models.Player;
import models.animals.Animal;
import models.animals.Breed;

public class Dino extends Animal {
    public Dino(Player owner) {
        super(owner);
        super.breed = Breed.Cooper;
    }

    @Override
    public void doTheFuckingJob() {

    }
}
