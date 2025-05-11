package org.example.controllers;

import org.example.models.App;
import org.example.models.Result;
import org.example.models.items.Item;
import org.example.models.items.Slot;

import java.util.ArrayList;
import java.util.regex.Matcher;

import static org.example.models.Finder.parseItem;


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

    public Result showInventory(Matcher matcher){
        ArrayList<Slot> inventory=App.getGame().getCurrentPlayer().getInventory().getSlots();
        for(Slot slot : inventory){
            if(slot.getQuantity()>0) {
                System.out.printf(slot.getQuantity() + " " + slot.getItem().getName());
            }
            if(slot.getQuantity()>1){
                System.out.printf("s\n");
            }
            else if(slot.getQuantity()==1){
                System.out.printf("\n");
            }
        }
        return new Result(true,App.getGame().getCurrentPlayer().getInventory().getEmptySlots()+" empty slots in your "+App.getGame().getCurrentPlayer().getInventory().getBackpack().name()+" backPack");
    }

    public Result cheatAddItem(Matcher matcher){
        String itemName = matcher.group(1);
        String quantity = matcher.group(2);

        if(parseItem(itemName)==null){
            return new Result(false, "Invalid item name");
        }

        Item item= parseItem(itemName);
        int itemQuantity = Integer.parseInt(quantity);

        if(App.getGame().getCurrentPlayer().getInventory().addToInventory(item,itemQuantity)){
            return new Result(true, itemName+" added to the inventory");
        }
        return new Result(false, "inventory is full");
    }

    public Result inventoryTrash(Matcher matcher) {
        String itemName = matcher.group(1);
        String quantity = matcher.group("number");
        if(parseItem(itemName)==null){
            return new Result(false, "Invalid item name");
        }

        Item item= parseItem(itemName);
        int itemQuantity = 0;
        if(quantity != null){
            itemQuantity = Integer.parseInt(quantity);
        }

        if(quantity != null) {
            if(App.getGame().getCurrentPlayer().getInventory().removeFromInventory(item,itemQuantity)){
                return new Result(true, itemName+" removed from the inventory");
            }else{
                return new Result(false, itemName+" doesn't exist");
            }
        }else{
            if(App.getGame().getCurrentPlayer().getInventory().removeFromInventory(item)){
                return new Result(true,"remove the item successfully");
            }else{
                return new Result(false,itemName +
                        " doesn't exist");
            }
        }

    }

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
