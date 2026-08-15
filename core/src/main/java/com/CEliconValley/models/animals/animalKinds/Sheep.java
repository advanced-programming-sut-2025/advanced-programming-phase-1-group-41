package com.CEliconValley.models.animals.animalKinds;

import com.CEliconValley.models.ui.TerminalColors;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.animals.BarnOrCageSize;
import com.CEliconValley.models.animals.Breed;
import com.CEliconValley.models.items.Products.Product;

public class Sheep extends Animal {

    public Sheep(Breed breed, int buyPrice, int daysUntilProduce,
                   int friendShip, boolean isFedToday, boolean isHome,
                   boolean isPetToday, String name, Player owner,
                   Product product, BarnOrCageSize sizeNeeded, int x, int y){
        super(breed, buyPrice, daysUntilProduce, friendShip, isFedToday, isHome
            , isPetToday, name, owner, product, sizeNeeded, x, y);
    }

    public Sheep(Player owner, String name) {
        super(owner, name, 8000, BarnOrCageSize.Deluxe);
        super.breed = Breed.Barn;
        daysUntilProduce=3;
    }

    @Override
    public String getAnimalType() {
        return "Sheep";
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
        return TerminalColors.colorize(1,0,"🐑");
    }
}
