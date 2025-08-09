package com.CEliconValley.controllers.subgames;

import com.CEliconValley.models.*;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.Cottage;
import com.CEliconValley.models.buildings.Refrigerator;
import com.CEliconValley.models.items.*;
import com.CEliconValley.models.locations.Farm;

import java.util.regex.Matcher;

public class CookingController {

    Refrigerator refrigerator;

    public CookingController() {
        for (Building building : App.getGame().getCurrentPlayerFarm().getBuildings()) {
            if(building instanceof Cottage) {
                refrigerator = (Refrigerator) ((Cottage) building).getRefrigerator();
            }
        }
    }


    private boolean inHome(Player player, Farm farm){
        for (Building building : farm.getBuildings()) {
            if(building instanceof Cottage) {
                refrigerator = (Refrigerator) ((Cottage) building).getRefrigerator();
            }
        }
        int x = player.getX();
        int y = player.getY();
        Cell cell = farm.getCell(x, y);
        if(cell == null) return false;
        if(cell.getObjectMap() instanceof Cottage){
            return true;
        }
        return false;
    }

    private Result pickFromRef(Item item, Player player, Farm farm) {
        Slot slot = refrigerator.getSlotByItem(item);
        if(slot == null){
            return new Result(false, "Slot not found");
        }
        Inventory inventory = player.getInventory();
        if(inventory.getEmptySlots() <= 0){
            return new Result(false, "Inventory is full");
        }
        inventory.addToInventory(slot.getItem(),slot.getQuantity());
        refrigerator.removeFromRef(slot.getItem(),slot.getQuantity());
        return new Result(true, "removed from ref :D");
    }

    private Result putInRef(Item item, Player player, Farm farm) {
        Slot slot = player.getInventory().getSlotByItem(item);
        int q = slot.getQuantity();
        if(slot==null){
            return new Result(false,"Slot not found");
        }
        if(refrigerator.getEmptySlots() <= 0){
            return new Result(false,"Refrigerator is full");
        }
        refrigerator.addToRef(slot.getItem(), slot.getQuantity());
        player.getInventory().removeFromInventory(slot.getItem(), slot.getQuantity());

        return new Result(true,slot.getItem().getName()+" "+q);
    }

    public Result cookingRef(Matcher matcher, String playername) {
        Player player = Finder.getPlayerByUsername(playername);
        Farm farm = Finder.getFarmByPlayer(player);
//        if(!inHome()){
//            return new Result(false, "You're not in a home");
//        }
        String pickput = matcher.group("which");
        String itemName = matcher.group(2);
        Item item = Finder.parseItem(itemName);
        if(item == null){
            return new Result(false,"Item not found");
        }
        if(pickput.equals("put")){
            return this.putInRef(item, player, farm);
        }else{
            return this.pickFromRef(item, player, farm);
        }
    }
    public Result showRef(Matcher matcher){
//        if(!inHome()){
//            return new Result(false, "You're not in a home");
//        }
        for(Slot slot : refrigerator.slots){
            if(slot.getItem()==null) continue;
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
        return new Result(true,":)");

    }

    public Result prepareFood(Matcher matcher, String playername) {
        Player player = Finder.getPlayerByUsername(playername);
        Farm farm = Finder.getFarmByPlayer(player);
        if(!inHome(player, farm)){
            return new Result(false, "You're not in a home");
        }
        String foodName = matcher.group(1).trim();
        Food food = Food.parseFood(foodName);
        if(food == null){
            return new Result(false,"Food not found");
        }
        if(!player.hasRecipe(food)){
            return new Result(false,"Food not found, recipe");
        }
        if(food.getRecipe() == null){
            return new Result(false,"Not implemented yet");
        }
        boolean checker = true;
        Inventory inventory = player.getInventory();
        if(inventory.getEmptySlots() <= 0){
            return new Result(false,"inventory is full :(");
        }
        for (Item item : food.getRecipe().neededItems.keySet()) {
            Slot invSlot = inventory.getSlotByItem(item);
            Slot refSlot = refrigerator.getSlotByItem(item);
            if(invSlot != null && invSlot.getQuantity()>=food.getRecipe().neededItems.get(item)){
            }
            else if(refSlot != null && refSlot.getQuantity()>=food.getRecipe().neededItems.get(item)){

            }else{
                checker = false;
                System.out.println(item.getName()+" not found");
                break;
            }
        }
        if(!checker){
            return new Result(false,"you don't have the needed items :(");
        }

        if(inventory.getEmptySlots() <= 0){
            return new Result(false,"you don't have enough empty slots");
        }

        if(player.getEnergy() < 3){
            return new Result(false,"you don't have enough energy");
        }

        // remove items

        for (Item item : food.getRecipe().neededItems.keySet()) {
            Slot invSlot = inventory.getSlotByItem(item);
            Slot refSlot = refrigerator.getSlotByItem(item);
            if(invSlot != null && invSlot.getQuantity()>=food.getRecipe().neededItems.get(item)){
                inventory.removeFromInventory(invSlot.getItem(),
                        food.getRecipe().neededItems.get(item));
            }
            else if(refSlot != null && refSlot.getQuantity()>=food.getRecipe().neededItems.get(item)){
                refrigerator.removeFromRef(refSlot.getItem(),
                        food.getRecipe().neededItems.get(item));
            }
        }


        inventory.addToInventory(food, 1);
        player.decEnergy(3);

        return new Result(true,"food made.");
    }

    public Result showRecepies(Matcher matcher) {
//        if(!inHome()){
//            return new Result(false, "You're not in a home");
//        }
        for (CookingRecipe recipe : App.getGame().getCurrentPlayer().getCookingRecipes()) {
            System.out.println(recipe.getName());
        }
        return new Result(true,"that was all..");
    }

}
