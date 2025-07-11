package com.CEliconValley.controllers.subgames;

import com.CEliconValley.controllers.MapController;
import com.CEliconValley.models.*;
import com.CEliconValley.models.items.*;

import com.CEliconValley.models.animals.Fish;
import com.CEliconValley.models.animals.FishType;
import com.CEliconValley.models.foragings.Fruit;
import com.CEliconValley.models.foragings.FruitType;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.models.locations.Village;
import com.CEliconValley.models.skills.Skill;
import com.CEliconValley.models.tools.FishingRod;
import com.CEliconValley.models.tools.FishingRodLevel;

import java.util.ArrayList;
import java.util.regex.Matcher;

import static com.CEliconValley.models.Finder.parseItem;


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
        App.getGame().getCurrentPlayer().setEnergyUnlimited(true);
        return new Result(true, App.getGame().getCurrentPlayer().getUser().getUsername()+
                " energy is now unlimited");
    }

    public Result showInventory(Matcher matcher){
        ArrayList<Slot> inventory=App.getGame().getCurrentPlayer().getInventory().getSlots();
        System.out.print(Colors.backColor(243));
        System.out.printf("┏");
        for(int i=0;i<52;i++){
            System.out.printf("━");
        }
        System.out.printf("┓");
        System.out.print(Colors.RESET);
        System.out.printf("\n");

        for(Slot slot : inventory){
            if(slot.getQuantity()>0) {
                System.out.print(Colors.backColor(243));
                System.out.print(Colors.foreColor(15));
                System.out.printf(
                        "┃%-40s -> %7d ┃",
                        slot.getItem().getName(),
                        slot.getQuantity()
                );
//                System.out.printf(slot.getQuantity() + " " + slot.getItem().getName());
                System.out.print(Colors.RESET);
                System.out.printf("\n");
            }

//            if(slot.getQuantity()>1){
//                char lastChar = slot.getItem().getName().charAt(slot.getItem().getName().length() - 1);
//                if(lastChar == 'e' || lastChar == 'a' || lastChar == 'i' || lastChar == 'o' || lastChar == 'u' || lastChar == 'd') {
//                    System.out.print("s\n");
//                } else {
//                    System.out.print("es\n");
//                }
//            }
//            else if(slot.getQuantity()==1){
//                System.out.print("\n");
//            }
        }
        System.out.print(Colors.backColor(243));
        System.out.printf("┗");
        for(int i=0;i<52;i++){
            System.out.printf("━");
        }
        System.out.printf("┛");
        System.out.print(Colors.RESET);
        System.out.printf("\n");
        return new Result(true,App.getGame().getCurrentPlayer().getInventory().getEmptySlots()+" empty slots in your "+App.getGame().getCurrentPlayer().getInventory().getBackpack().name()+" backPack");
    }

    public Result cheatAddItem(Matcher matcher){
        String itemName = matcher.group(1);
        String quantity = matcher.group(2);

        if(parseItem(itemName)==null){
            return new Result(false, "Invalid item name");
        }

        Item item = parseItem(itemName);
        int itemQuantity = Integer.parseInt(quantity);
        if(itemQuantity < 1){
            return new Result(false, "invalid quantity");
        }

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

    public Result eat(Matcher matcher){
        String itemName = matcher.group(1).trim();
        Food food = Food.parseFood(itemName);
        FruitType fruitType = FruitType.parseFruitType(itemName);
        Item item = Finder.parseItem(itemName);
        if(item instanceof CraftableItem ci){
            if(!ci.isEatable()){
                return new Result(false, "Invalid item");
            }
        }else if(food == null && fruitType == null && !(item instanceof Eatable)){
            return new Result(false, "Invalid item");
        }
        if(food != null){
            return eatFood(itemName);
        }else if(fruitType != null){
            return eatFruit(itemName);
        }else if(item instanceof CraftableItem ci){
            return eatCraftable(ci);
        }
        Slot e = App.getGame().getCurrentPlayer().getInventory().getSlotByItem(item);
        if(e.getItem() instanceof Eatable eatable){
            return eatEatable(eatable);
        }
        return new Result(false, "Invalid item");
    }

    private Result eatCraftable(CraftableItem item){
        App.getGame().getCurrentPlayer().incEnergy(item.getEnergy());
        double value = item.getEnergy();
        Inventory inventory = App.getGame().getCurrentPlayer().getInventory();
        inventory.removeFromInventory(item, 1);
        return new Result (true, "you got "+value+" energy");
    }

    private Result eatFruit(String fruitName){
        FruitType fruitType = FruitType.parseFruitType(fruitName);
        if(fruitType==null){
            return new Result(false,"invalid fruit");
        }
        Inventory inventory = App.getGame().getCurrentPlayer().getInventory();
        Slot slot = inventory.getSlotByItem(new Fruit(fruitType));
        if(slot==null || slot.getQuantity()==0){
            return new Result(false,"you don't have the fruit "+fruitName);
        }
        if(!fruitType.isEatable()){
            return new Result(false,"fruit "+fruitName+" is not eatable");
        }
        App.getGame().getCurrentPlayer().incEnergy(fruitType.getEnergy());
        double value = fruitType.getEnergy();
        inventory.removeFromInventory(new Fruit(fruitType), 1);
        return new Result (true, "you got "+value+" energy");
    }

    private Result eatFood(String foodName){
        Food wantedFood = Food.parseFood(foodName);
        if(wantedFood == null){
            return new Result(false, "Invalid food");
        }

        Inventory inventory = App.getGame().getCurrentPlayer().getInventory();
        Slot slot = inventory.getSlotByItem(wantedFood);
        if(slot==null){
            return new Result(false,"you don't have this food");
        }
        if(slot.getItem() instanceof Food){
            if(((Food) slot.getItem()).getBuff() != null){
                Buff buff = ((Food) slot.getItem()).getBuff();
                Buff playerBuff = App.getGame().getCurrentPlayer().getBuff();
                if(playerBuff != null){
                    App.getGame().getCurrentPlayer().setMaxEnergy(200);
                }
                App.getGame().getCurrentPlayer().setBuff(new Buff(buff.getBuffTime(), buff.getBuffAmount(), buff.getBuffType()));
                if(buff.getBuffType().equals(BuffType.MaxEnergy)){
                    App.getGame().getCurrentPlayer().setMaxEnergy(200 + buff.getBuffAmount());
                }
            }
        }
        App.getGame().getCurrentPlayer().incEnergy(wantedFood.getEnergy());
        double value  = wantedFood.getEnergy();
        inventory.removeFromInventory(wantedFood, 1);
        return new Result(true, value+
                " energy added :)");
    }

    private Result eatEatable(Eatable eatable){
        Inventory inventory = App.getGame().getCurrentPlayer().getInventory();
        Slot slot = inventory.getSlotByItem((Item) eatable);
        if(slot==null){
            return new Result(false,"you don't have this food");
        }
        Eatable e = (Eatable)slot.getItem();
        App.getGame().getCurrentPlayer().incEnergy(e.getEnergy());
        double value = e.getEnergy();
        inventory.removeFromInventory((Item) e, 1);
        return new Result(true, value+" energy added :)");
    }

    public Result fishing(Matcher matcher){
        Game game=App.getGame();
        String fishingRodName = matcher.group(1).trim();
        Player player = App.getGame().getCurrentPlayer();
        if(!(player.getCurrentTool() instanceof FishingRod)){
            return new Result(false,"your current tool is not a rod!");
        }
        FishingRod fishingRod = (FishingRod) player.getCurrentTool();
        if(!fishingRod.getName().equalsIgnoreCase(fishingRodName)){
            return new Result(false,"your rod isn't "+fishingRodName+" it's a "+fishingRod.getName());
        }
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
        if(fishingRod.getLevel() == FishingRodLevel.Training){
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
        int value = App.getGame().getCurrentPlayer().getFishingSkill().isMaxLevel() ? -1 : 0;
        int energy = fishingRod.getLevel().getEnergyUsage() + value;
        if(App.getGame().getCurrentPlayer().getBuff() != null){
            if(App.getGame().getCurrentPlayer().getBuff().getBuffType().equals(BuffType.Fishing)){
                System.out.println("since you have a buff you're gonna lose 1 less energy");
                if(energy >= 1){
                    energy--;
                }
            }
        }
        if(energy > App.getGame().getCurrentPlayer().getEnergy()){
                new Result(false, "your energy is too low..");
        }
        fishQuality=Math.floor(Math.random()*( App.getGame().getCurrentPlayer().getFishingSkill().getLevel() + 2)*fishingRod.getLevel().getPole())/(7-weatherEffect);
        assert caughtFish != null;
        caughtFish.setQuality(fishQuality);
        App.getGame().getCurrentPlayer().getFishingSkill().increaseXp(quantityOfFish * 5);
        game.getCurrentPlayer().decEnergy(energy);
        game.getCurrentPlayer().getInventory().addToInventory(caughtFish,quantityOfFish,(int)fishQuality);
        return new Result(true,"You have "+quantityOfFish+" fresh fish of "+caughtFish.getFishType().getName());

    }
    public Result walkHome(Matcher matcher){
        Player player = App.getGame().getCurrentPlayer();
        Farm farm = null;
        for (Farm farm1 : App.getGame().getFarms()) {
            if(farm1.getId() == player.getFarmId()){
                farm = farm1;
            }
        }
        if(farm == null){
            return new Result(false, "farm not found!");
        }
        Village village = App.getGame().getVillage();
        MapController c = new MapController();
        if(player.isPlayerIsInVillage()){
            player.resetInFarmId();
            Result preResult = c.walk(null,village.getTransferCells().get(0).getX(),village.getTransferCells().get(0).getY());
            if(!preResult.success()){
                return new Result(false,"you're probably stuck..");
            }
        }
        return c.walk(null,farm.getTransferCells().get(0).getX(),farm.getTransferCells().get(0).getY());
    }
    public Result walkVillage(Matcher matcher){
        Farm farm = App.getGame().getCurrentPlayerFarm();
        MapController c = new MapController();
        return c.walk(null,farm.getTransferCells().get(0).getX(),farm.getTransferCells().get(0).getY());
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
