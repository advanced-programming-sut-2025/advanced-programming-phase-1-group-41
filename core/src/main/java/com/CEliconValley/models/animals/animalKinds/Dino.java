package com.CEliconValley.models.animals.animalKinds;

import com.CEliconValley.models.ui.TerminalColors;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.animals.BarnOrCageSize;
import com.CEliconValley.models.animals.Breed;
import com.CEliconValley.models.items.Products.Product;

public class Dino extends Animal {

    public Dino(Breed breed, int buyPrice, int daysUntilProduce,
                   int friendShip, boolean isFedToday, boolean isHome,
                   boolean isPetToday, String name, Player owner,
                   Product product, BarnOrCageSize sizeNeeded, int x, int y){
        super(breed, buyPrice, daysUntilProduce, friendShip, isFedToday, isHome
            , isPetToday, name, owner, product, sizeNeeded, x, y);
    }

    public Dino(Player owner, String name) {
        super(owner, name, 14000, BarnOrCageSize.Big);
        super.breed = Breed.Coop;
        daysUntilProduce = 7;
    }

    @Override
    public String getAnimalType() {
        return "Dino";
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
        return TerminalColors.colorize(1,0,"🦕");
    }
}
