package com.CEliconValley.models.animals.animalKinds;

import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.animals.BarnOrCageSize;
import com.CEliconValley.models.animals.Breed;

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
