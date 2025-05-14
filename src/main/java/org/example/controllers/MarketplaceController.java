package org.example.controllers;

import org.example.models.*;
import org.example.models.animals.Animal;
import org.example.models.animals.animalKinds.*;
import org.example.models.buildings.Building;
import org.example.models.buildings.ShippingBin;
import org.example.models.buildings.Well;
import org.example.models.buildings.marketplaces.*;
import org.example.models.foragings.Nature.Rock;
import org.example.models.foragings.Nature.Wood;
import org.example.models.items.*;
import org.example.models.items.Products.Product;
import org.example.models.tools.*;

import java.util.ArrayList;
import java.util.HashMap;
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


    public Result showAllProducts(Matcher matcher){
        Result preResult = inMarketPlace();
        if(!preResult.success()){
            return preResult;
        }
        Marketplace mp = (Marketplace) currentCell.getObjectMap();
        return new Result(true,mp.getItemsForSale().toString());
    }

    public Result showAllAvailableProducts(Matcher matcher){
        Result preResult = inMarketPlace();
        if(!preResult.success()){
            return preResult;
        }
        Marketplace mp = (Marketplace) currentCell.getObjectMap();
        StringBuilder message = new StringBuilder();
        message.append("Available products:\n");
        for(Slot slot : mp.getItemsForSale()){
            if(slot.getQuantity() > 0){
                message.append(slot.getItem().getName()+" : "+slot.getQuantity()+"\n");
            }
        }
        message.delete(message.length()-1,message.length());
        return new Result(true,message.toString());
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
        Item item = Finder.parseItem(itemName);
        if(item == null){
            return new Result(false, "Item doesn't exist");
        }
        Slot slot = mp.getSlotByItem(item);
        if(slot == null){
            return new Result(false, "Slot doesn't exist");
        }
        if(slot.getQuantity() == 0){
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
                case ToolLevel.Default -> {
                    cost = 2000;
                    neededItem = new Slot(CraftableItem.CopperBar, 5);
                }
                case ToolLevel.Copper -> {
                    cost = 5000;
                    neededItem = new Slot(CraftableItem.IronBar, 5);
                }
                case ToolLevel.Iron -> {
                    cost = 10000;
                    neededItem = new Slot(CraftableItem.GoldBar, 5);
                }
                case ToolLevel.Gold -> {
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

    public Result buildBaoop(double cost, int rockCount, int woodCount){
        return null;
    }

}
