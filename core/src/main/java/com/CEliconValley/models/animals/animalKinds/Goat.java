package com.CEliconValley.models.animals.animalKinds;

import com.CEliconValley.models.ui.TerminalColors;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.animals.BarnOrCageSize;
import com.CEliconValley.models.animals.Breed;
import com.CEliconValley.models.items.Products.Product;

public class Goat extends Animal {

    public Goat(Breed breed, int buyPrice, int daysUntilProduce,
                   int friendShip, boolean isFedToday, boolean isHome,
                   boolean isPetToday, String name, Player owner,
                   Product product, BarnOrCageSize sizeNeeded, int x, int y){
        super(breed, buyPrice, daysUntilProduce, friendShip, isFedToday, isHome
            , isPetToday, name, owner, product, sizeNeeded, x, y);
    }

    public Goat(Player owner, String name) {
        super(owner, name, 4000, BarnOrCageSize.Big);
        super.breed = Breed.Barn;
        daysUntilProduce=2;
    }

    @Override
    public String getAnimalType() {
        return "Goat";
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
        return TerminalColors.colorize(1,0,"🐐");
    }
}
