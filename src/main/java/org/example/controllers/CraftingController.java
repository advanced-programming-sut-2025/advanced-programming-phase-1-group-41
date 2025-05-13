package org.example.controllers;

import org.example.models.*;
import org.example.models.buildings.Cottage;
import org.example.models.items.*;

import java.util.regex.Matcher;

public class CraftingController {

    private boolean inHome(){
        int x = App.getGame().getCurrentPlayer().getX();
        int y = App.getGame().getCurrentPlayer().getY();
        Cell cell = App.getGame().getCurrentPlayerFarm().getCell(x, y);
        if(cell == null) return false;
        if(cell.getObjectMap() instanceof Cottage){
            return true;
        }
        return false;
    }

    public Result showRecepies(Matcher matcher){
        if(!inHome()){
            return new Result(false, "You are not in a home");
        }
        StringBuilder message = new StringBuilder();
        message.append("Crafting recipes :\n");
        Player player = App.getGame().getCurrentPlayer();
        for (CraftingRecipe craftingRecipe : player.getCraftingRecipes()) {
            message.append(craftingRecipe.toString()).append("\n");
        }
        message.delete(message.length()-1, message.length());
        return new Result(true, message.toString());
    }

    public Result learnRecipe(Matcher matcher){return null;}

    public Result craftRecipe(Matcher matcher) {
        if(!inHome()){
            return new Result(false, "You are not in a home");
        }
        String itemName = matcher.group(1).trim();
        Item item = Finder.parseItem(itemName);
        if(item == null){
            return new Result(false, "Item not found");
        }

        if(item.getName().equals(CraftableMachine.CherryBomb.getName())){
            App.getGame().getCurrentPlayer().getInventory().addToInventory(CraftableMachine.CherryBomb, 1);
            return new Result(true,"you have a cherry bomb now");
        }


        for (CraftableMachine machine : CraftableMachine.values()) {
            if(item.getName().equals(machine.getName())){
                boolean hasItems = hasNeededItems(machine);
                if(!hasItems){
                    return new Result(false, "you don't have the needed items");
                }

                if(App.getGame().getCurrentPlayer().getInventory().getEmptySlots() <= 0){
                    return new Result(false,"you don't have enough empty slots");
                }

                removeItems(machine);

                App.getGame().getCurrentPlayer().getInventory().addToInventory(machine, 1);
                return new Result(true, "You received a "+ machine.getName());
            }
        }
        return new Result(false, "can't craft that item");
    }

    private void removeItems(CraftableMachine machine){
        Inventory inventory = App.getGame().getCurrentPlayer().getInventory();
        for (Item item : machine.getRecipe().neededItems.keySet()) {
            Slot invSlot = inventory.getSlotByItem(item);
            inventory.removeFromInventory(item, machine.getRecipe().neededItems.get(item));
        }
    }

    private boolean hasNeededItems(CraftableMachine machine){
        Inventory inventory = App.getGame().getCurrentPlayer().getInventory();
        boolean checker = true;
        for (Item item : machine.getRecipe().neededItems.keySet()) {
            Slot invSlot = inventory.getSlotByItem(item);
            if(invSlot != null && invSlot.getQuantity()>=machine.getRecipe().neededItems.get(item)){
            }else{
                checker = false;
                System.out.println(item+" not found");
                break;
            }
        }
        return checker;
    }

    public Result artisanUse(Matcher matcher){return null;}

    public Result artisanGet(Matcher matcher){return null;}


    // atrisans

    private Result FishSmoker(Matcher matcher){return null;}

    private Result cheesePress(Matcher matcher){return null;}

    private Result beeHouse(Matcher matcher){return null;}

    private Result keg(Matcher matcher){return null;}

    private Result dehydrator(Matcher matcher){return null;}

    private Result charcoalKiln(Matcher matcher){return null;}

    private Result furnace(Matcher matcher){return null;}

    private Result jarPreserver(Matcher matcher){return null;}

    private Result oilMaker(Matcher matcher){return null;}

    private Result MayonnaiseMachine(Matcher matcher){return null;}

    private Result loom(Matcher matcher){return null;}

}
