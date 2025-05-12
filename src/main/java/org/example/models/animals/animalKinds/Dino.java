package org.example.models.animals.animalKinds;

import org.example.models.Colors;
import org.example.models.Player;
import org.example.models.animals.Animal;
import org.example.models.animals.BarnOrCageSize;
import org.example.models.animals.Breed;

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
