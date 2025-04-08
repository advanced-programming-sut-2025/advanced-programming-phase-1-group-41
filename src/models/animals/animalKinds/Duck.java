package models.animals.animalKinds;

import models.Player;
import models.animals.Animal;
import models.animals.Breed;

public class Duck extends Animal {
    public Duck(Player owner) {
        super(owner);
        super.breed = Breed.Cooper;
    }

    @Override
    public void doTheFuckingJob() {

    }
}
