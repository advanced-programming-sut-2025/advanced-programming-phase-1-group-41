package org.example.models.animals.animalKinds;

import org.example.models.Colors;
import org.example.models.Player;
import org.example.models.animals.Animal;
import org.example.models.animals.BarnOrCageSize;
import org.example.models.animals.Breed;

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
