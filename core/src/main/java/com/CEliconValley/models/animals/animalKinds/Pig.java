package com.CEliconValley.models.animals.animalKinds;

import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.animals.BarnOrCageSize;
import com.CEliconValley.models.animals.Breed;
import com.CEliconValley.models.items.Products.Product;

public class Pig extends Animal {

    public Pig(Breed breed, int buyPrice, int daysUntilProduce,
                   int friendShip, boolean isFedToday, boolean isHome,
                   boolean isPetToday, String name, Player owner,
                   Product product, BarnOrCageSize sizeNeeded, int x, int y){
        super(breed, buyPrice, daysUntilProduce, friendShip, isFedToday, isHome
            , isPetToday, name, owner, product, sizeNeeded, x, y);
    }

    public Pig(Player owner, String name) {
        super(owner, name, 16000, BarnOrCageSize.Deluxe);
        super.breed = Breed.Barn;
        daysUntilProduce=(int)(Math.random()*4);
    }

    @Override
    public String getAnimalType() {
        return "Pig";
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
