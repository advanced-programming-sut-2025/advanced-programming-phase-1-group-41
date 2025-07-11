package com.CEliconValley.controllers.subgames;

import com.CEliconValley.models.*;
import com.CEliconValley.models.animals.animalKinds.*;
import com.CEliconValley.models.buildings.marketplaces.*;
import com.CEliconValley.models.items.*;
import com.CEliconValley.models.tools.*;

import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.ShippingBin;
import com.CEliconValley.models.buildings.Well;
import com.CEliconValley.models.buildings.animalContainer.BarnType;
import com.CEliconValley.models.buildings.animalContainer.CoopType;
import com.CEliconValley.models.foragings.Nature.Rock;
import com.CEliconValley.models.foragings.Nature.Wood;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;

public class MarketplaceController {
    Player player;
    Inventory inventory;
    Cell currentCell;
    private Result inMarketPlace(){
        player = App.getGame().getCurrentPlayer();
        currentCell = App.getGame().getVillage().getCell(player.getX(), player.getY());
        inventory = player.getInventory();
        if(!player.isPlayerIsInVillage()){
            return new Result(false, "you're not even in the village");
        }
        if(!(currentCell.getObjectMap() instanceof Marketplace)){
            System.out.println(currentCell.getX()+" "+currentCell.getY());
            return new Result(false, "You are not in Marketplace");
        }
        return new Result(true,"");
    }
    public static void updateHourly(){
        for(Building market : App.getGame().getVillage().getBuildings()){
            if(market instanceof Marketplace){
                ((Marketplace) market).updateHourly();
            }
        }
    }
    public Result showAllProducts(Matcher matcher){
        Result preResult = inMarketPlace();
        if(!preResult.success()){
            return preResult;
        }
        Marketplace mp = (Marketplace) currentCell.getObjectMap();
        System.out.println("All Products: \n");
        for(Slot slot : mp.getItemsForSale()){
            if(slot.getQuantity() > 0){
                if(slot.getQuantity()<=5){
                    System.out.print(Colors.foreColor(202));
                }
                else{
                    System.out.print(Colors.foreColor(46));
                }
                System.out.printf(
                        "%-40s -> %7d left | price: %6.0f%n",
                        slot.getItem().getName(),
                        slot.getQuantity(),
                        slot.getItem().getPrice()
                );
            }else {
                System.out.print(Colors.foreColor(124));
                System.out.printf("%-40s ->       SOLD OUT\n",slot.getItem().getName());

            } System.out.print(Colors.RESET);
        }
        return new Result(true,"");
    }

    public Result showAllAvailableProducts(Matcher matcher){
        Result preResult = inMarketPlace();
        if(!preResult.success()){
            return preResult;
        }
        Marketplace mp = (Marketplace) currentCell.getObjectMap();
        StringBuilder message = new StringBuilder();
        System.out.println("Available products:\n");
        for(Slot slot : mp.getItemsForSale()){
            if(slot.getQuantity() > 0){
                if(slot.getQuantity()<10){
                    System.out.print(Colors.foreColor(202));
                }else if(slot.getQuantity()<4){
                    System.out.print(Colors.foreColor(124));
                }
                else{
                    System.out.print(Colors.foreColor(46));
                }
                System.out.printf(
                        "%-40s -> %7d left | price: %6.0f%n",
                        slot.getItem().getName(),
                        slot.getQuantity(),
                        slot.getItem().getPrice()
                );
                System.out.print(Colors.RESET);
            }
        }

        return new Result(true,"");
    }

    private boolean isAvailable(Marketplace marketplace, Item item){
        if(marketplace instanceof MarnieRanch marnieRanch){
            if(item instanceof Shear){
                if(marnieRanch.isShearLimit()){
                    marnieRanch.setShearLimit(false);
                    return true;
                }
                return false;
            }
            if(item instanceof MilkPale milkPale){
                if(marnieRanch.isMilkpaleLimit()){
                   marnieRanch.setMilkpaleLimit(false);
                   return true;
                }
                return false;
            }
        }
        return true;
    }

    private boolean nearShippingBin(){
        player = App.getGame().getCurrentPlayer();
        inventory = player.getInventory();
        if(!player.isPlayerIsInVillage()){
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    Cell nextCell = Finder.findCellByCoordinates(player.getX()+i, player.getY()+j, App.getGame().getCurrentPlayerFarm());
                    if(nextCell == null) continue;
                    if(nextCell.getObjectMap() instanceof ShippingBin){
                        return true;
                    }
                }
            }
            return false;
        }
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Cell nextCell = Finder.findCellByCoordinatesVillage(player.getX()+i, player.getY()+j, App.getGame().getVillage());
                if(nextCell == null ) continue;
                if(nextCell.getObjectMap() instanceof ShippingBin){
                    return true;
                }
            }
        }
        return false;

    }

    public Result sellProduct(Matcher matcher){
        boolean nearShippingBin = nearShippingBin();
        if(!nearShippingBin){
            return new Result(false, "you're not near a shipping bin");
        }
        String itemName = matcher.group(1);
        String iqs = matcher.group("count");

        Item item = Finder.parseItem(itemName);
        if(item == null){
            return new Result(false, "item doesn't exist");
        }
        Player player = App.getGame().getCurrentPlayer();
        Slot slot = player.getInventory().getSlotByItem(item);
        if(slot == null){
            return new Result(false, "you don't have this item");
        }
        int wantedQuantity = slot.getQuantity();
        if(iqs != null){
            wantedQuantity = Integer.parseInt(iqs);
        }
        if(slot.getQuantity() < wantedQuantity){
            return new Result(false, "you don't have enough of this item");
        }
        double value = wantedQuantity*slot.getItem().getPrice();
        double cof = player.getFarmingSkill().getLevel() + player.getForagingSkill().getLevel() +
                player.getMiningSkill().getLevel() + player.getFishingSkill().getLevel();
        cof /= 8;
        System.out.println(value+" "+wantedQuantity+" "+slot.getItem().getPrice());
        value *= cof;
        // shipping bin checkk heree
        value += 1;
        player.incSavings(value);
        player.getInventory().removeFromInventory(slot.getItem(), wantedQuantity);
        return new Result(true,"you're gonna gain "+value+" later.. current saving: "+player.getSavings());
    }

    public Result purchaseProduct(Matcher matcher){
        Result preResult = inMarketPlace();
        if(!preResult.success()){
            return preResult;
        }
        Marketplace mp = (Marketplace) currentCell.getObjectMap();
        String itemName = matcher.group(1).trim();
        String wqn = matcher.group("count");
        int wantedQuantity;
        if(wqn != null){
            wantedQuantity = Integer.parseInt(wqn.trim());
        }else{
            wantedQuantity = 1;
        }
        if(mp instanceof FishShop fishShop){
            FishingRodLevel level = FishingRodLevel.parseFishingRodLevel(itemName);
            if(level != null){
                switch (level){
                    case FiberGlass -> {
                        int fishingLevel = player.getFishingSkill().getLevel();
                        if(fishingLevel < 2){
                            return new Result(false, "your fishing needs to be at least 2");
                        }
                    }
                    case Iridium -> {
                        int fishingLevel = player.getFishingSkill().getLevel();
                        if(fishingLevel < 4){
                            return new Result(false, "your fishing needs to be at least 4");
                        }
                    }
                }
            }
        }
        if(mp instanceof  GeneralStore gs){
            Backpack bp = Backpack.parseBackpack(itemName);
            if(bp != null){
                switch (bp){
                    case Deluxe -> {
                        if(player.getInventory().getBackpack() == Backpack.Default){
                            return new Result(false, "you need to buy a large backpack first");
                        }
                    }
                }
                if(bp.equals(player.getInventory().getBackpack())){
                    return new Result(false,"you already have " +
                            player.getInventory().getBackpack()+" backpack");
                }
                if(bp.ordinal() < player.getInventory().getBackpack().ordinal()){
                    return new Result(false,"you can't downgrade your backpack..");
                }
            }
        }

        Item item = Finder.parseItem(itemName);
        if(item == null){
            System.out.println("["+itemName+"]");
            return new Result(false, "Item doesn't exist");
        }
        Slot slot = mp.getSlotByItem(item);
        if(slot == null){
            return new Result(false, "this marketplace doesn't have this item");
        }
        if(slot.getQuantity() <= 0){
            return new Result(false, "Out of stock for "+itemName);
        }
        if(slot.getQuantity() < wantedQuantity){
            return new Result(false, "Low stock for "+itemName);
        }
        double delta = player.getMoney() - (wantedQuantity * slot.getItem().getPrice());
        if(delta < 0){
            return new Result(false, "Not enough money for "+itemName+" you need "+(-delta) + " more money");
        }
        if(mp instanceof CarpenterShop carpenterShop){
            Slot neededItem = null;
            if(item instanceof Well){
                neededItem = new Slot(new Rock(), 75);
            }else if(item instanceof ShippingBin){
                neededItem = new Slot(new Wood(), 150);
            }
            if(neededItem != null){
                Slot s = inventory.getSlotByItem(neededItem.getItem());
                if(s != null){
                    if(s.getQuantity() < neededItem.getQuantity()){
                        return new Result(false,"insufficient material..");
                    }
                    inventory.removeFromInventory(neededItem.getItem(), neededItem.getQuantity());
                }else{
                    return new Result(false, "you don't have the needed resources");
                }
            }
        }

        if(!isAvailable(mp, item)){
            return new Result(false, "come back later..");
        }
        System.out.println("wanted "+wantedQuantity+" "+itemName);
        slot.setQuantity(slot.getQuantity() - wantedQuantity);
        player.setMoney(delta);

        if(mp instanceof Saloon saloon){
            CookingRecipe recipe = CookingRecipe.parseRecipe(item.getName());
            if(recipe != null){
                if(!player.getCookingRecipes().contains(recipe)){
                    player.getCookingRecipes().add(recipe);
                    return new Result(true, wantedQuantity+" "+itemName+" recipe purchased");
                }else{
                    return new Result(false,"you just wasted your money..");
                }
            }
        }
        if(mp instanceof FishShop fishShop){
            CraftingRecipe recipe = CraftingRecipe.parseRecipe(item.getName());
            System.out.println("here "+item.getName()+" "+CraftingRecipe.FishSmoker.getName());
            if(recipe != null){
                if(!player.getCraftingRecipes().contains(recipe)){
                    player.getCraftingRecipes().add(recipe);
                    return new Result(true, wantedQuantity+" "+itemName+" recipe purchased");
                }else{
                    return new Result(false,"you just wasted your money..");
                }
            }
            if(Objects.equals(slot.getItem().getName(), FishingRodLevel.Training.getName())){
                inventory.addToInventory(new FishingRod(FishingRodLevel.Training), 1);
                return new Result(true,"got "+slot.getItem());
            }
            if(Objects.equals(slot.getItem().getName(), FishingRodLevel.Bamboo.getName())){
                inventory.addToInventory(new FishingRod(FishingRodLevel.Bamboo), 1);
                return new Result(true,"got "+slot.getItem());
            }
            if(Objects.equals(slot.getItem().getName(), FishingRodLevel.FiberGlass.getName())){
                inventory.addToInventory(new FishingRod(FishingRodLevel.FiberGlass), 1);
                return new Result(true,"got "+slot.getItem());
            }
            if(Objects.equals(slot.getItem().getName(), FishingRodLevel.Iridium.getName())){
                inventory.addToInventory(new FishingRod(FishingRodLevel.Iridium), 1);
                return new Result(true,"got "+slot.getItem());
            }

        }
        if(mp instanceof GeneralStore gs){
            CraftingRecipe recipe = CraftingRecipe.parseRecipe(item.getName());
            if(recipe != null){
                if(!player.getCraftingRecipes().contains(recipe)){
                    player.getCraftingRecipes().add(recipe);
                    return new Result(true, wantedQuantity+" "+itemName+" recipe purchased");
                }else{
                    return new Result(false,"you just wasted your money..");
                }
            }

            Backpack bp = Backpack.parseBackpack(item.getName());
            if(bp != null){
                player.getInventory().upgradeBackpack(bp);
                return new Result(true,"your backpack has been upgraded," +
                        " new size: "+player.getInventory().getBackpack().getSize());
            }
        }
        inventory.addToInventory(slot.getItem(), wantedQuantity);
        return new Result(true, wantedQuantity+"x "+itemName+" purchased");
    }

    public Result cheatAddMoney(Matcher matcher){
        int delta = Integer.parseInt(matcher.group(1).trim());
        Player player = App.getGame().getCurrentPlayer();
        player.incMoney(delta);
        return new Result(true,"new money is : "+player.getMoney());
    }


    public Result upgradeTool(Tool tool){
        Result preResult = inMarketPlace();
        if(!preResult.success()){
            return preResult;
        }
        Blacksmith bs;
        try{
            bs = (Blacksmith) currentCell.getObjectMap();
        }catch (ClassCastException e){
            return new Result(false, "You are not in Blacksmith");
        }
        double cost = 0;
        Slot neededItem = null;
        if(tool instanceof LevelTool lt){
            switch (lt.getLevel()){
                case Default -> {
                    cost = 2000;
                    neededItem = new Slot(CraftableItem.CopperBar, 5);
                }
                case Copper -> {
                    cost = 5000;
                    neededItem = new Slot(CraftableItem.IronBar, 5);
                }
                case Iron -> {
                    cost = 10000;
                    neededItem = new Slot(CraftableItem.GoldBar, 5);
                }
                case Gold -> {
                    cost = 25000;
                    neededItem = new Slot(CraftableItem.IridiumBar, 5);
                }
                default -> {
                    return new Result(false, "Tool can't be upgraded anymore");
                }
            }
        }
        if(tool instanceof TrashCan trashCan){
            cost /= 2;
        }
        double delta = player.getMoney() - cost;
        if(delta < 0){
            return new Result(false, "Not enough money for "+tool.getName());
        }
        Slot slot = player.getInventory().getSlotByItem(neededItem.getItem());
        if(slot == null){
            return new Result(false, "You don't have the needed item");
        }
        if(slot.getQuantity() < neededItem.getQuantity()){
            return new Result(false, "you need "+(neededItem.getQuantity()-slot.getQuantity())+
                    "more of "+neededItem.getItem());
        }

        if(tool instanceof LevelTool levelTool){
            if(levelTool instanceof TrashCan trashCan){
                if(!bs.getUpdates().get(trashCan.getLevel().ordinal()+4)){
                    return new Result(false,"come back tmrw");
                }
            }else{
                if(!bs.getUpdates().get(levelTool.getLevel().ordinal())){
                    return new Result(false,"come back tmrw");
                }
            }
        }

        inventory.removeFromInventory(neededItem.getItem(), neededItem.getQuantity());
        player.decMoney(cost);
        if(tool instanceof LevelTool levelTool){
            System.out.println("old level: "+levelTool.getLevel());
            levelTool.increaseLevel();
            System.out.println("new level: "+levelTool.getLevel());
            if(levelTool instanceof TrashCan trashCan){
                bs.getUpdates().set(trashCan.getLevel().ordinal()-1+4,false);
            }else{
                bs.getUpdates().set(levelTool.getLevel().ordinal()-1,false);
            }
            for (Boolean update : bs.getUpdates()) {
                System.out.println("update: "+update);
            }
        }
        return new Result(true,"tool upgraded.."
                +"new money is : "+player.getMoney());
    }


    public Result buyAnimal(Animal animal, Building building){
        Result preResult = inMarketPlace();
        if(!preResult.success()){
            return preResult;
        }
        MarnieRanch ranch;
        if((currentCell.getObjectMap() instanceof MarnieRanch marnieRanch)){
            ranch = marnieRanch;
        }else{
            return new Result(false, "you're not in marnie's ranch");
        }
        if(player.getMoney() < animal.getBuyPrice() ){
            return new Result(false,"you need "+(animal.getBuyPrice()-player.getMoney())+" more money!");
        }
        HashMap<String, Integer> limits = ranch.getDailyLimit();
        if(animal instanceof Chicken){
            if(limits.get("Chicken") > 0){
                limits.put("Chicken", limits.get("Chicken") - 1);
            } else {
                return new Result(false, "We're outta chicken, come back tomorrow.");
            }
        } else if(animal instanceof Cow){
            if(limits.get("Cow") > 0){
                limits.put("Cow", limits.get("Cow") - 1);
            } else {
                return new Result(false, "We're outta cow, come back tomorrow.");
            }
        } else if(animal instanceof Goat){
            if(limits.get("Goat") > 0){
                limits.put("Goat", limits.get("Goat") - 1);
            } else {
                return new Result(false, "We're outta goat, come back tomorrow.");
            }
        } else if(animal instanceof Duck){
            if(limits.get("Duck") > 0){
                limits.put("Duck", limits.get("Duck") - 1);
            } else {
                return new Result(false, "We're outta duck, come back tomorrow.");
            }
        } else if(animal instanceof Sheep){
            if(limits.get("Sheep") > 0){
                limits.put("Sheep", limits.get("Sheep") - 1);
            } else {
                return new Result(false, "We're outta sheep, come back tomorrow.");
            }
        } else if(animal instanceof Rabbit){
            if(limits.get("Rabbit") > 0){
                limits.put("Rabbit", limits.get("Rabbit") - 1);
            } else {
                return new Result(false, "We're outta rabbit, come back tomorrow.");
            }
        } else if(animal instanceof Dino){
            if(limits.get("Dinosaur") > 0){
                limits.put("Dinosaur", limits.get("Dinosaur") - 1);
            } else {
                return new Result(false, "We're outta dinosaur, come back tomorrow.");
            }
        } else if(animal instanceof Pig){
            if(limits.get("Pig") > 0){
                limits.put("Pig", limits.get("Pig") - 1);
            } else {
                return new Result(false, "We're outta pig, come back tomorrow.");
            }
        }

        player.decMoney(animal.getBuyPrice());
        return new Result(true,"");
    }

    public Result buildBaoop(double cost, int rockCount, int woodCount, int index){
        Result preResult = inMarketPlace();
        if(!preResult.success()){
            return preResult;
        }
        Slot rockSlot = new Slot(new Rock(), rockCount);
        Slot woodSlot = new Slot(new Wood(), woodCount);
        Slot playerRock = inventory.getSlotByItem(rockSlot.getItem());
        Slot playerWood = inventory.getSlotByItem(woodSlot.getItem());
        if(playerRock == null || playerWood ==null){
            return new Result(false,"you're missing a material..");
        }
        if(playerRock.getQuantity() < rockSlot.getQuantity()){
            return new Result(false,"need "+(rockSlot.getQuantity()-playerRock.getQuantity())+" more rock!");
        }
        if(playerWood.getQuantity() < woodSlot.getQuantity()){
            return new Result(false, "need"+(woodSlot.getQuantity()-playerWood.getQuantity())+" more rock!");
        }
        if(player.getMoney() < cost){
            return new Result(false, "need "+(cost-player.getMoney())+" more money!");
        }
        CarpenterShop cr = null;
        if((currentCell.getObjectMap() instanceof CarpenterShop carpenterShop)){
            cr = carpenterShop;
        }else{
            return new Result(false, "you're not in CarpenterShop!");
        }
        if(index > 2){
            index -= 3;
            switch (index){
                case 0 -> {
                    if(cr.getCoopLimits().get(CoopType.Normal) >0){
                        cr.getCoopLimits().put(CoopType.Normal, 0);
                    }else{
                        return new Result(false,"comeback tmrw..");
                    }
                }
                case 1-> {
                    if(cr.getCoopLimits().get(CoopType.Big) >0){
                        cr.getCoopLimits().put(CoopType.Big, 0);
                    }else{
                        return new Result(false,"comeback tmrw..");
                    }
                }
                case 2-> {
                    if(cr.getCoopLimits().get(CoopType.Deluxe) >0){
                        cr.getCoopLimits().put(CoopType.Deluxe, 0);
                    }else{
                        return new Result(false,"comeback tmrw..");
                    }
                }
            }
        }else{
            switch (index){
                case 0 -> {
                    if(cr.getBarnLimits().get(BarnType.Normal) >0){
                        cr.getBarnLimits().put(BarnType.Normal, 0);
                    }else{
                        return new Result(false,"comeback tmrw..");
                    }
                }
                case 1-> {
                    if(cr.getBarnLimits().get(BarnType.Big) >0){
                        cr.getBarnLimits().put(BarnType.Big, 0);
                    }else{
                        return new Result(false,"comeback tmrw..");
                    }
                }
                case 2-> {
                    if(cr.getBarnLimits().get(BarnType.Deluxe) >0){
                        cr.getBarnLimits().put(BarnType.Deluxe, 0);
                    }else{
                        return new Result(false,"comeback tmrw..");
                    }
                }
            }
        }

        inventory.removeFromInventory(rockSlot.getItem(), rockSlot.getQuantity());
        inventory.removeFromInventory(woodSlot.getItem(), woodSlot.getQuantity());
        player.decMoney(cost);
        return new Result(true,"");
    }

}
