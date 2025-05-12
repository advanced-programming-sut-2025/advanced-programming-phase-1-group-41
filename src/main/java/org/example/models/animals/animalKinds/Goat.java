package org.example.models.animals.animalKinds;

import org.example.models.Colors;
import org.example.models.Player;
import org.example.models.animals.Animal;
import org.example.models.animals.BarnOrCageSize;
import org.example.models.animals.Breed;

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
