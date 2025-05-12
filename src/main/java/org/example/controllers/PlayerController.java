package org.example.controllers;

import org.example.models.App;
import org.example.models.Result;
import org.example.models.*;
import org.example.models.animals.Fish;
import org.example.models.animals.FishType;
import org.example.models.items.Food;
import org.example.models.items.Inventory;
import org.example.models.items.Item;
import org.example.models.items.Slot;
import org.example.models.skills.Skill;
import org.example.models.tools.FishingRod;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static org.example.models.Finder.parseItem;


public class PlayerController {

    public Result showMoney(Matcher matcher) {
        return new Result(true, "money: "+App.getGame().getCurrentPlayer().getMoney());
    }

    public Result showEnergy(Matcher matcher){
        return new Result(true, App.getGame().getCurrentPlayer().getEnergy()+"");
    }
    public Result showSkill(Matcher matcher){
        StringBuilder message = new StringBuilder();
        Player player = App.getGame().getCurrentPlayer();
        Skill skill = player.getFarmingSkill();
        message.append("farming:\n");
        message.append("  level: ").append(skill.getLevel()).append("\n");
        message.append("  xp: ").append(skill.getXp()).append("\n");
        skill = player.getMiningSkill();
        message.append("mining:\n");
        message.append("  level: ").append(skill.getLevel()).append("\n");
        message.append("  xp: ").append(skill.getXp()).append("\n");
        skill = player.getForagingSkill();
        message.append("foraging:\n");
        message.append("  level: ").append(skill.getLevel()).append("\n");
        message.append("  xp: ").append(skill.getXp()).append("\n");
        skill = player.getFishingSkill();
        message.append("fishing:\n");
        message.append("  level: ").append(skill.getLevel()).append("\n");
        message.append("  xp: ").append(skill.getXp());
        return new Result(true, message.toString());
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
                char lastChar = slot.getItem().getName().charAt(slot.getItem().getName().length() - 1);
                if(lastChar == 'e' || lastChar == 'a' || lastChar == 'i' || lastChar == 'o' || lastChar == 'u' || lastChar == 'd') {
                    System.out.print("s\n");
                } else {
                    System.out.print("es\n");
                }
            }
            else if(slot.getQuantity()==1){
                System.out.print("\n");
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
        // trim MUSTT be there, cuz of the regex ,don't touch it
        String itemName = matcher.group(1).trim();
        String quantity = matcher.group("number");
        Item item = parseItem(itemName);
        if(item==null){
            return new Result(false, "Invalid item name");
        }

        int itemQuantity = 0;
        if(quantity != null){
            itemQuantity = Integer.parseInt(quantity);
        }

        double cof = App.getGame().getCurrentPlayer().getInventory().getCofOfTrashCan();
        System.out.println("cof is : "+cof);
        if(quantity != null) {
            if(App.getGame().getCurrentPlayer().getInventory().removeFromInventory(item,itemQuantity)){
                double value = (item.getPrice())*itemQuantity*cof;
                App.getGame().getCurrentPlayer().incMoney(value);
                System.out.println("you received "+value+" money");
                return new Result(true, itemName+" removed from the inventory");
            }else{
                return new Result(false, itemName+" doesn't exist");
            }
        }else{
            Slot slot = App.getGame().getCurrentPlayer().getInventory().getSlotByItem(item);
            double value = 0;
            if(slot!=null){
                value = (item.getPrice())*slot.getQuantity()*cof;
            }
            if(App.getGame().getCurrentPlayer().getInventory().removeFromInventory(item)){
                App.getGame().getCurrentPlayer().incMoney(value);
                System.out.println("you received "+value+" money");
                return new Result(true,"removed the item successfully");
            }else{
                return new Result(false,itemName +
                        " doesn't exist");
            }
        }

    }

    public Result eatFood(Matcher matcher){
        String foodName = matcher.group(1);
        Food wantedFood = Food.parseFood(foodName);
        if(wantedFood == null){
            return new Result(false, "Invalid food");
        }
        Inventory inventory = App.getGame().getCurrentPlayer().getInventory();
        Slot slot = inventory.getSlotByItem(wantedFood);
        if(slot==null){
            return new Result(false,"you don't have this food");
        }
        for (int i = 0; i < slot.getQuantity(); i++) {
            App.getGame().getCurrentPlayer().incEnergy(wantedFood.getEnergyValue());
            if(App.getGame().getCurrentPlayer().getEnergy() > 100){
                App.getGame().getCurrentPlayer().setEnergy(100);
            }
        }
        double value  = wantedFood.getEnergyValue()*slot.getQuantity();
        inventory.removeFromInventory(wantedFood);
        return new Result(true, value+
                " energy added :)");
    }

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

        int quantityOfFish=(int)Math.floor(Math.random()*weatherEffect*(App.getGame().getCurrentPlayer().getFishingSkill().getLevel() + 2));
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
                    if(chance<7&&App.getGame().getCurrentPlayer().getFishingSkill().isMaxLevel()){
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
                    if(chance<7&&App.getGame().getCurrentPlayer().getFishingSkill().isMaxLevel()){
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
                    if(chance<7&&App.getGame().getCurrentPlayer().getFishingSkill().isMaxLevel()){
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
                    if(chance<20&&App.getGame().getCurrentPlayer().getFishingSkill().isMaxLevel()){
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
        fishQuality=(Math.random()*( App.getGame().getCurrentPlayer().getFishingSkill().getLevel() + 2)*fishingRod.getLevel().getPole())/(7-weatherEffect);
        caughtFish.setQuality(fishQuality);
        int value = App.getGame().getCurrentPlayer().getFishingSkill().isMaxLevel() ? -1 : 0;
        App.getGame().getCurrentPlayer().getFishingSkill().increaseXp(quantityOfFish * 5);
        game.getCurrentPlayer().decEnergy(fishingRod.getLevel().getEnergyUsage() + value);
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
