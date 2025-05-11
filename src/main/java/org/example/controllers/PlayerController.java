package org.example.controllers;

import org.example.models.App;
import org.example.models.Finder;
import org.example.models.Result;
import org.example.models.items.Item;

import java.util.regex.Matcher;

import static org.example.models.Finder.findItem;


public class PlayerController {

    public Result showEnergy(Matcher matcher){
        return new Result(true, App.getGame().getCurrentPlayer().getEnergy()+"");
    }

    public Result cheatEnergySet(Matcher matcher) {
        String valueRaw = matcher.group(1);
        int value = Integer.parseInt(valueRaw);
        App.getGame().getCurrentPlayer().setEnergy(value);
        return new Result(true, "energy has been set to "+
                App.getGame().getCurrentPlayer().getEnergy());
    }

    public Result cheatEnergyUnlimited(Matcher matcher) {
        App.getGame().getCurrentPlayer().setEnergyUnilimited(true);
        return new Result(true, App.getGame().getCurrentPlayer().getUser().getUsername()+
                " energy is now unlimited");
    }

    public Result showInventory(Matcher matcher){return null;}

    public Result cheatAddItem(Matcher matcher){
        String itemName = matcher.group(1);
        String quantity = matcher.group(2);

        if(findItem(itemName)==null){
            return new Result(false, "Invalid item name");
        }

        Item item=findItem(itemName);
        int itemQuantity = Integer.parseInt(quantity);

        if(App.getGame().getCurrentPlayer().getInventory().addToInventory(item,itemQuantity)){
            return new Result(true, itemName+" added to the inventory");
        }
        return new Result(false, "inventory is full");
    }

    public Result inventoryTrash(Matcher matcher) {return null;}

    public Result eatFood(Matcher matcher){return null;}

    public Result fishing(Matcher matcher){return null;}

    public Result sellProduct(Matcher matcher){return null;}


    public Result talk(Matcher matcher){return null;}

    public Result talkHistory(Matcher matcher){return null;}

    public Result gift(Matcher matcher){return null;}

    public Result giftList(Matcher matcher){return null;}

    public Result giftRate(Matcher matcher){return null;}

    public Result giftHistory(Matcher matcher){return null;}

    public Result hug(Matcher matcher){return null;}

    public Result flower(Matcher matcher){return null;}

    public Result askForMarriage(Matcher matcher){return null;}

    public Result respondToMarriage(Matcher matcher){return null;}

}
