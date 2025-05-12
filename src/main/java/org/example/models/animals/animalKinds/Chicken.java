package org.example.models.animals.animalKinds;

import org.example.models.Colors;
import org.example.models.Player;
import org.example.models.animals.Animal;
import org.example.models.animals.BarnOrCageSize;
import org.example.models.animals.Breed;

public class Chicken extends Animal {
    public Chicken(Player owner, String name) {
        super(owner, name, 800, BarnOrCageSize.Normal);
        super.breed = Breed.Coop;
        daysUntilProduce = 1;
    }

    @Override
    public void doTheFuckingJob() {

    }
    @Override
    public boolean canGiveProduct(){
        return true;

    }
    public String getChar(){
        return Colors.colorize(1,0,"🐓");
    }
}
