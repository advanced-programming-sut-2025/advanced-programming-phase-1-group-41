package org.example.controllers;

import org.example.models.App;
import org.example.models.Result;
import org.example.models.*;
import org.example.models.animals.Fish;
import org.example.models.animals.FishType;
import org.example.models.items.Item;
import org.example.models.items.Slot;
import org.example.models.tools.FishingRod;

import java.util.ArrayList;
import java.util.List;
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

        Item item=parseItem(itemName);
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

    public Result fishing(Matcher matcher){
        Game game=App.getGame();
        String fishingRodName = matcher.group(1);
        List<String> list = List.of("Training", "Bamboo", "FiberGlass", "Iridium");
        if(!list.contains(fishingRodName)){
            return new Result(false, "Invalid rod name");
        }
        if(FishingRod.findFishingRod()==null){
            return new Result(false, "You don't have any fishing rod");
        }
        if(!FishingRod.findFishingRod().getLevel().name().equals(fishingRodName)){
            return new Result(false, "You don't "+fishingRodName+" Pol, instead, use "+FishingRod.findFishingRod().getLevel().name());
        }
        FishingRod fishingRod=FishingRod.findFishingRod();

        if(!game.getCurrentPlayerFarm().isLakeAround()){
            return new Result(false, "no lake around you!");
        }
        double weatherEffect;
        switch(game.getWeatherType()){
            case Sunny:
                weatherEffect=1.5;
                break;
            case Rainy:
                weatherEffect=1.2;
                break;
            case Stormy:
                weatherEffect=0.5;
                break;
            default:
                weatherEffect=1.0;
                break;
        }

        int quantityOfFish=(int)Math.floor(Math.random()*weatherEffect*(1 + 2));//TODO SKILL LEVEL IS UNDEFINED.
        Fish caughtFish = null;
        double fishQuality=1.0;
        if(fishingRodName.equals("Training")){
            switch(game.getTime().getSeason()){
                case Spring:
                    caughtFish=new Fish(FishType.Herring);
                    break;
                case Summer:
                    caughtFish=new Fish(FishType.Sunfish);
                    break;
                case Autumn:
                    caughtFish=new Fish(FishType.Sardine);
                    break;
                case Winter:
                    caughtFish=new Fish(FishType.Perch);
                    break;
            }
        }
        else{
            int chance = 1 + (int)(Math.random() * 100);
            switch(game.getTime().getSeason()){
                case Spring:
                    if(chance<7){
                        caughtFish=new Fish(FishType.Legend);
                    }
                    else if(chance<30){
                        caughtFish=new Fish(FishType.Flounder);
                    }
                    else if(chance<50){
                        caughtFish=new Fish(FishType.Lionfish);
                    }
                    else if(chance<75){
                        caughtFish=new Fish(FishType.Herring);
                    }
                    else{
                        caughtFish=new Fish(FishType.Ghostfish);
                    }
                    break;
                case Summer:
                    if(chance<7){
                        caughtFish=new Fish(FishType.Crimsonfish);
                    }
                    else if(chance<30){
                        caughtFish=new Fish(FishType.Tilapia);
                    }
                    else if(chance<50){
                        caughtFish=new Fish(FishType.Dorado);
                    }
                    else if(chance<75){
                        caughtFish=new Fish(FishType.Sunfish);
                    }
                    else{
                        caughtFish=new Fish(FishType.RainbowTrout);
                    }
                    break;
                case Autumn:
                    if(chance<7){
                        caughtFish=new Fish(FishType.Angler);
                    }
                    else if(chance<30){
                        caughtFish=new Fish(FishType.Sardine);
                    }
                    else if(chance<50){
                        caughtFish=new Fish(FishType.Shad);
                    }
                    else if(chance<75){
                        caughtFish=new Fish(FishType.BlueDiscus);
                    }
                    else{
                        caughtFish=new Fish(FishType.Salmon);
                    }
                    break;
                case Winter:
                    if(chance<20&&true){//TODO base on skill level , true must change , only the highest level is true;
                        caughtFish=new Fish(FishType.Glacierfish);
                    }
                    else if(chance<30){
                        caughtFish=new Fish(FishType.MidnightCarp);
                    }
                    else if(chance<50){
                        caughtFish=new Fish(FishType.Perch);
                    }
                    else if(chance<75){
                        caughtFish=new Fish(FishType.Tuna);
                    }
                    else{
                        caughtFish=new Fish(FishType.Squid);
                    }
                    break;

            }
        }
        fishQuality=(int)Math.floor((Math.random()*( 1 + 2)*fishingRod.getLevel().getPole())/(7-weatherEffect));
        game.getCurrentPlayer().decEnergy(fishingRod.getLevel().getEnergyUsage());//TODO skill has an effect on dec of energy
        game.getCurrentPlayer().getInventory().addToInventory(caughtFish,quantityOfFish);
        return new Result(true,"You have "+quantityOfFish+" fresh fish of "+caughtFish.getFishType().getName());

    }

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
