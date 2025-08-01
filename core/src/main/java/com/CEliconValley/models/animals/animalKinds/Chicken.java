package com.CEliconValley.models.animals.animalKinds;

import com.CEliconValley.models.ui.TerminalColors;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.animals.BarnOrCageSize;
import com.CEliconValley.models.animals.Breed;
import com.CEliconValley.models.items.Products.Product;

public class Chicken extends Animal {

    public Chicken(Breed breed, int buyPrice, int daysUntilProduce,
                  int friendShip, boolean isFedToday, boolean isHome,
                  boolean isPetToday, String name, Player owner,
                  Product product, BarnOrCageSize sizeNeeded, int x, int y){
        super(breed, buyPrice, daysUntilProduce, friendShip, isFedToday, isHome
        , isPetToday, name, owner, product, sizeNeeded, x, y);
    }

    public Chicken(Player owner, String name) {
        super(owner, name, 800, BarnOrCageSize.Normal);
        super.breed = Breed.Coop;
        daysUntilProduce = 1;
    }

    @Override
    public String getAnimalType() {
        return "Chicken";
    }
    @Override
    public boolean canGiveProduct(){
        return true;

    }
    public String getChar(){
        return TerminalColors.colorize(1,0,"🐓");
    }
}
