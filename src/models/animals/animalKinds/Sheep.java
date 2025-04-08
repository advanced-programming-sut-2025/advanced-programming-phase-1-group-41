package models.animals.animalKinds;

import models.Player;
import models.animals.Animal;
import models.animals.Breed;

public class Sheep extends Animal {
    public Sheep(Player owner) {
        super(owner);
        super.breed = Breed.Barner;
    }

    @Override
    public void doTheFuckingJob() {

    }
}
