package org.example.controllers;

import org.example.models.*;
import org.example.models.buildings.marketplaces.Blacksmith;
import org.example.models.buildings.marketplaces.Marketplace;
import org.example.models.items.CraftableItem;
import org.example.models.items.Inventory;
import org.example.models.items.Item;
import org.example.models.items.Products.Product;
import org.example.models.items.Slot;
import org.example.models.tools.LevelTool;
import org.example.models.tools.Tool;
import org.example.models.tools.ToolLevel;
import org.example.models.tools.TrashCan;

import java.util.regex.Matcher;

public class MarketplaceController {
    Player player;
    Inventory inventory;
    Cell currentCell;
    private Result inMarketPlace(){
        player = App.getGame().getCurrentPlayer();
        currentCell = App.getGame().getVillage().getCell(player.getX(), player.getY());
        inventory = player.getInventory();
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

    public Result purchaseProduct(Matcher matcher){
        Result preResult = inMarketPlace();
        if(!preResult.success()){
            return preResult;
        }
        Marketplace mp = (Marketplace) currentCell.getObjectMap();
        String itemName = matcher.group(1).trim();
        int wantedQuantity = Integer.parseInt(matcher.group(2).trim());
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
        slot.setQuantity(slot.getQuantity() - wantedQuantity);
        player.setMoney(delta);
        inventory.addToInventory(slot.getItem(), slot.getQuantity());
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


}
