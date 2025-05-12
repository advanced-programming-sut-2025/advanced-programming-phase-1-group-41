package org.example.models.animals.animalKinds;

import org.example.models.Player;
import org.example.models.animals.Animal;
import org.example.models.animals.BarnOrCageSize;
import org.example.models.animals.Breed;

public class Duck extends Animal {
    public Duck(Player owner, String name) {
        super(owner, name, 1200, BarnOrCageSize.Big);
        super.breed = Breed.Coop;
    }

    @Override
    public void doTheFuckingJob() {

    }
    public String getChar(){
        return "🦆";
    }
}
