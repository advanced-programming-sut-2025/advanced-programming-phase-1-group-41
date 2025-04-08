package models.animals.animalKinds;

import models.Player;
import models.animals.Animal;
import models.animals.Breed;

public class Pig extends Animal {
    public Pig(Player owner){
        super(owner);
        super.breed = Breed.Barner;
    }

    @Override
    public void doTheFuckingJob() {

    }
}
