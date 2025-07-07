package com.CEliconValley.models.animals.animalKinds;

import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.animals.BarnOrCageSize;
import com.CEliconValley.models.animals.Breed;

public class Goat extends Animal {
    public Goat(Player owner, String name) {
        super(owner, name, 4000, BarnOrCageSize.Big);
        super.breed = Breed.Barn;
        daysUntilProduce=2;
    }

    @Override
    public void doTheFuckingJob() {

    }
    @Override
    public boolean canGiveProduct(){
        if(daysUntilProduce==0){
            daysUntilProduce=2;
            return true;
        }
        daysUntilProduce--;
        return false;
    }
    public String getChar(){
        return Colors.colorize(1,0,"🐐");
    }
}
