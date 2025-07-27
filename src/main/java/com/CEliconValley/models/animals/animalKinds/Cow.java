package com.CEliconValley.models.animals.animalKinds;

import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.animals.BarnOrCageSize;
import com.CEliconValley.models.animals.Breed;
import com.CEliconValley.models.items.Products.Product;

public class Cow extends Animal {

    public Cow(Breed breed, int buyPrice, int daysUntilProduce,
                   int friendShip, boolean isFedToday, boolean isHome,
                   boolean isPetToday, String name, Player owner,
                   Product product, BarnOrCageSize sizeNeeded, int x, int y){
        super(breed, buyPrice, daysUntilProduce, friendShip, isFedToday, isHome
            , isPetToday, name, owner, product, sizeNeeded, x, y);
    }

    public Cow(Player owner, String name) {
        super(owner, name, 1500, BarnOrCageSize.Normal);
        super.breed = Breed.Barn;
        daysUntilProduce = 1;
    }

    @Override
    public String getAnimalType() {
        return "Cow";
    }
    @Override
    public boolean canGiveProduct(){
        return true;

    }
    public String getChar(){
        return Colors.colorize(1,0,"🐄");
    }

}
