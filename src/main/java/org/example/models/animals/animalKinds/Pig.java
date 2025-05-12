package org.example.models.animals.animalKinds;

import org.example.models.Colors;
import org.example.models.Player;
import org.example.models.animals.Animal;
import org.example.models.animals.BarnOrCageSize;
import org.example.models.animals.Breed;

public class Pig extends Animal {
    public Pig(Player owner, String name) {
        super(owner, name, 16000, BarnOrCageSize.Deluxe);
        super.breed = Breed.Barn;
        daysUntilProduce=(int)(Math.random()*4);
    }

    @Override
    public void doTheFuckingJob() {

    }
    @Override
    public boolean canGiveProduct(){
        if(daysUntilProduce==0){
            daysUntilProduce=(int)(Math.random()*4);
            return true;
        }
        daysUntilProduce--;
        return false;

    }
    public String getChar(){
        return Colors.colorize(1,0,"🐖");
    }
}
