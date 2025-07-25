package com.CEliconValley.models.animals.animalKinds;

import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.animals.BarnOrCageSize;
import com.CEliconValley.models.animals.Breed;

public class Sheep extends Animal {
    public Sheep(Player owner, String name) {
        super(owner, name, 8000, BarnOrCageSize.Deluxe);
        super.breed = Breed.Barn;
        daysUntilProduce=3;
    }

    @Override
    public void doTheFuckingJob() {

    }
    @Override
    public boolean canGiveProduct(){
        if(daysUntilProduce==0&&this.getFriendShip()>=70){
            return true;
        }
        else if(this.getFriendShip()<70){
            daysUntilProduce=3;
            return false;
        }
        daysUntilProduce--;
        return false;


    }
    public String getChar(){
        return Colors.colorize(1,0,"🐑");
    }
}
