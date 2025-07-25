package com.CEliconValley.models.animals.animalKinds;

import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.animals.BarnOrCageSize;
import com.CEliconValley.models.animals.Breed;

public class Dino extends Animal {
    public Dino(Player owner, String name) {
        super(owner, name, 14000, BarnOrCageSize.Big);
        super.breed = Breed.Coop;
        daysUntilProduce = 7;
    }

    @Override
    public void doTheFuckingJob() {

    }
    @Override
    public boolean canGiveProduct(){
        if(daysUntilProduce == 0){
            daysUntilProduce = 7;
            return true;
        }
        daysUntilProduce--;
        return false;

    }
    public String getChar(){
        return Colors.colorize(1,0,"🦕");
    }
}
