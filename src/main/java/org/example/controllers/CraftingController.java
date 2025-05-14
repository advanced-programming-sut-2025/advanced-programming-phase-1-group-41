package org.example.controllers;

import org.example.models.*;
import org.example.models.animals.Fish;
import org.example.models.animals.FishType;
import org.example.models.buildings.Cottage;
import org.example.models.foragings.Nature.MineralType;
import org.example.models.foragings.Nature.Wood;
import org.example.models.items.*;
import org.example.models.items.craftablemachines.FishSmoker;
import org.example.models.items.craftablemachines.Furnace;
import org.example.models.items.craftablemachines.Kiln;
import org.example.models.items.craftablemachines.Machine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;

public class CraftingController {
    int x;
    int y;

    private boolean inHome() {
        int x = App.getGame().getCurrentPlayer().getX();
        int y = App.getGame().getCurrentPlayer().getY();
        Cell cell = App.getGame().getCurrentPlayerFarm().getCell(x, y);
        if (cell == null) return false;
        if (cell.getObjectMap() instanceof Cottage) {
            return true;
        }
        return false;
    }

    public Result preValidateUseTool(Matcher matcher) {
        String dirName = matcher.group("direction").trim();
        int dir = Integer.parseInt(dirName) - 1;
        if (dir < 0 || dir > 7) {
            return new Result(false, "invalid dir");
        }
        int[][] dirs = {{-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0},};
        x = App.getGame().getCurrentPlayer().getX() + dirs[dir][0];
        y = App.getGame().getCurrentPlayer().getY() + dirs[dir][1];
        return new Result(true, "x: " + x + " y: " + y);
    }

    public Result placeItem(Matcher matcher) {
        Result preResult = preValidateUseTool(matcher);
        if (!preResult.success()) {
            return preResult;
        }
        String itemName = matcher.group(1).trim();
        Cell cell = Finder.findCellByCoordinates(x, y, App.getGame().getCurrentPlayerFarm());
        if (cell == null) {
            return new Result(false, "Cell not found");
        }
        Item item = Finder.parseItem(itemName);
        if (item == null) {
            return new Result(false, "item not found");
        }
        Inventory inventory = App.getGame().getCurrentPlayer().getInventory();
        Slot slot = inventory.getSlotByItem(item);
        if (slot == null) {
            return new Result(false, "slot not found");
        }
        cell.setObjectMap(item);
        inventory.removeFromInventory(item, 1);
        return new Result(true, itemName + " placed on " + x + " " + y);
    }

    public Result showRecepies(Matcher matcher) {
        if (!inHome()) {
            return new Result(false, "You are not in a home");
        }
        StringBuilder message = new StringBuilder();
        message.append("Crafting recipes :\n");
        Player player = App.getGame().getCurrentPlayer();
        for (CraftingRecipe craftingRecipe : player.getCraftingRecipes()) {
            message.append(craftingRecipe.toString()).append("\n");
        }
        message.delete(message.length() - 1, message.length());
        return new Result(true, message.toString());
    }

    public Result learnRecipe(Matcher matcher) {
        return null;
    }

    public Result craftRecipe(Matcher matcher) {
        if (!inHome()) {
            return new Result(false, "You are not in a home");
        }
        String itemName = matcher.group(1).trim();
        Item item = Finder.parseItem(itemName);
        if (item == null) {
            return new Result(false, "Item not found");
        }

        if (item.getName().equals(CraftableMachine.CherryBomb.getName())) {
            App.getGame().getCurrentPlayer().getInventory().addToInventory(CraftableMachine.CherryBomb, 1);
            return new Result(true, "you have a cherry bomb now");
        }


        for (CraftableMachine machine : CraftableMachine.values()) {
            if (item.getName().equals(machine.getName())) {
                boolean hasItems = hasNeededItems(machine);
                if (!hasItems) {
                    return new Result(false, "you don't have the needed items");
                }

                if (App.getGame().getCurrentPlayer().getInventory().getEmptySlots() <= 0) {
                    return new Result(false, "you don't have enough empty slots");
                }

                removeItems(machine);

                App.getGame().getCurrentPlayer().getInventory().addToInventory(machine, 1);
                return new Result(true, "You received a " + machine.getName());
            }
        }
        return new Result(false, "can't craft that item");
    }

    private void removeItems(CraftableMachine machine) {
        Inventory inventory = App.getGame().getCurrentPlayer().getInventory();
        for (Item item : machine.getRecipe().neededItems.keySet()) {
            Slot invSlot = inventory.getSlotByItem(item);
            inventory.removeFromInventory(item, machine.getRecipe().neededItems.get(item));
        }
    }

    private boolean hasNeededItems(CraftableMachine machine) {
        Inventory inventory = App.getGame().getCurrentPlayer().getInventory();
        boolean checker = true;
        for (Item item : machine.getRecipe().neededItems.keySet()) {
            Slot invSlot = inventory.getSlotByItem(item);
            if (invSlot != null && invSlot.getQuantity() >= machine.getRecipe().neededItems.get(item)) {
            } else {
                checker = false;
                System.out.println(item + " not found");
                break;
            }
        }
        return checker;
    }

    private Cell findArtisan(String machineName) {
        for (int i = -1; i < 1; i++) {
            for (int j = -1; j < 1; j++) {
                x = App.getGame().getCurrentPlayer().getX() + i;
                y = App.getGame().getCurrentPlayer().getY() + j;
                Cell cell = App.getGame().getCurrentPlayerFarm().getCell(x, y);
                if (cell != null) {
                    if (cell.getObjectMap() instanceof CraftableMachine) {
                        System.out.println("found a " + cell.getObjectMap().getName() + " in " + x + " " + y);
                        if (cell.getObjectMap().getName().equalsIgnoreCase(machineName)) {
                            return cell;
                        }
                    }
                }
            }
        }
        return null;
    }

    public Result artisanUse(Matcher matcher) {
        String machineName = matcher.group(1).trim();
        String[] puttingItems = matcher.group(2).trim().split(" ");
        Cell cell = findArtisan(machineName);
        if (cell == null) {
            return new Result(false, machineName + " is not around you");
        }
        CraftableMachine machine = (CraftableMachine) cell.getObjectMap();
        ArrayList<Item> items = new ArrayList<>();
        for (String itemName : puttingItems) {
            Item item = Finder.parseItem(itemName);
            if (item == null) {
                return new Result(false, itemName + "doesn't exist");
            }
            items.add(item);
        }
        for (Item item : items) {
            Slot slot = App.getGame().getCurrentPlayer().getInventory().getSlotByItem(item);
            if (slot == null || slot.getQuantity() == 0) {
                return new Result(false,"you don't have " + item.getName());
            }
        }

        return switch (machine) {
            case Furnace -> this.furnace(items, cell);
            case CharcoalKiln -> this.charcoalKiln(items, cell);
            case FishSmoker -> this.fishSmoker(items, cell);
            default -> new Result(false, machineName + " is not useable");
        };
    }

    public Result artisanGet(Matcher matcher) {
        String machineName = matcher.group(1).trim();
        Cell cell = findArtisan(machineName);
        if (cell == null) {
            return new Result(false, machineName + " is not around you");
        }
        CraftableMachine machine = (CraftableMachine) cell.getObjectMap();
        Player player = App.getGame().getCurrentPlayer();
        Iterator<Machine> iterator = player.getOnGoingMachines().iterator();
        while (iterator.hasNext()) {
            Machine onGoingMachine = iterator.next();
            if (onGoingMachine.getCraftableMachine().getName().equals(machine.getName())) {
                if (!onGoingMachine.suffice()) {
                    return new Result(false, machine.getName() + "'s needed items are not ready yet");
                }
                Slot slot = onGoingMachine.getProduce();
                if (slot == null) {
                    return new Result(false, onGoingMachine.getProcessTime() + " hours left");
                }
                player.getInventory().addToInventory(slot.getItem(), slot.getQuantity());
                iterator.remove();
                return new Result(true, slot.getItem() + " added to inventory");
            }
        }
        return new Result(false, machineName + " is not ongoing");
    }


    // atrisans

    private void updateItems(Item item, Machine machine, Player player, int max) {
        for (Slot slot : machine.getReceivedItems()) {
            if(slot.getItem().getName().equals(item.getName())){
                int playerQua = player.getInventory().getSlotByItem(item).getQuantity();
                if(playerQua > max-slot.getQuantity()){
                    playerQua = max - slot.getQuantity();
                }
                slot.setQuantity(slot.getQuantity() + playerQua);
                player.getInventory().removeFromInventory(item , playerQua);
                System.out.println("added "+playerQua+" of "+slot.getItem().getName());
                break;
            }
        }

    }


    private Result fishSmoker(ArrayList<Item> items, Cell cell) {
        Fish fish = setFish(items);
        Player player = App.getGame().getCurrentPlayer();
        for (Machine x : player.getOnGoingMachines()) {
            if(x instanceof FishSmoker fs){
                fishSmokerHelper(fs, items, cell, player, fish);
                return new Result(true, "donee");
            }
        }
        FishSmoker fs = new FishSmoker(fish);
        player.getOnGoingMachines().add(fs);
        fishSmokerHelper(fs, items, cell, player, fish);
        return new Result(true, "done");
    }

    private void fishSmokerHelper(FishSmoker fs,ArrayList<Item> items, Cell cell, Player player, Fish fish) {
        for (Item item : items) {
            if(item.getName().equalsIgnoreCase(fish.getFishType().getName())){
                if(item instanceof Eatable eatable){
                    updateItems(eatable, fs, player, 1);
                }
            }else if(item.getName().equalsIgnoreCase(MineralType.Coal.getName())){
                updateItems(item, fs, player, 1);
            }else{
                System.out.println(item.getName()+" is not a valid item");
            }
        }
    }

    private Fish setFish(ArrayList<Item> items) {
        for (Item item : items) {
            for (FishType ft : FishType.values()) {
                if(ft.getName().equals(item.getName())){
                    return new Fish(ft);
                }
            }
        }
        return null;
    }


    private Result cheesePress(Matcher matcher) {
        return null;
    }

    private Result beeHouse(Matcher matcher) {
        return null;
    }

    private Result keg(Matcher matcher) {
        return null;
    }

    private Result dehydrator(Matcher matcher) {
        return null;
    }

    private Result charcoalKiln(ArrayList<Item> items, Cell cell) {
        Player player = App.getGame().getCurrentPlayer();
        for (Machine x : player.getOnGoingMachines()) {
            if(x instanceof Kiln kiln){
                kilnHelper(kiln, items, cell, player);
                return new Result(true, "donee");
            }
        }
        Kiln kiln = new Kiln();
        player.getOnGoingMachines().add(kiln);
        kilnHelper(kiln, items, cell, player);
        return new Result(true, "done");
    }

    private void kilnHelper(Kiln kiln,ArrayList<Item> items, Cell cell, Player player) {
        for (Item item : items) {
            if(item.getName().equalsIgnoreCase(new Wood().getName())){
                updateItems(item, kiln, player, 10);
            }else{
                System.out.println(item.getName()+" is not a valid item");
            }
        }
    }


    private Result furnace(ArrayList<Item> items, Cell cell) {
        MineralType mineralType = setMineralType(items);
        Player player = App.getGame().getCurrentPlayer();
        for (Machine x : player.getOnGoingMachines()) {
            if (x instanceof Furnace furnace) {
                furnaceHelper(furnace, items, player, mineralType);
                return new Result(true, "done");
            }
        }
        if(mineralType == null){
            return new Result(false, "first fix the ore!");
        }
        Furnace furnace = new Furnace(mineralType);
        player.getOnGoingMachines().add(furnace);
        furnaceHelper(furnace, items, player, mineralType);
        return new Result(true, "donee");
    }

    private MineralType setMineralType(ArrayList<Item> items) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(MineralType.CopperOre.getName())) {
                return MineralType.CopperOre;
            }
            if (item.getName().equalsIgnoreCase(MineralType.GoldOre.getName())) {
                return MineralType.GoldOre;
            }
            if (item.getName().equalsIgnoreCase(MineralType.IronOre.getName())) {
                return MineralType.IronOre;
            }
            if (item.getName().equalsIgnoreCase(MineralType.IridiumOre.getName())) {
                return MineralType.IridiumOre;
            }
        }
        return null;
    }



    private void furnaceHelper(Furnace furnace, ArrayList<Item> items, Player player, MineralType mineralType){
        for (Item item : items) {
            if(item.getName().equalsIgnoreCase(MineralType.Coal.getName())){
                updateItems(item, furnace, player, 1);
            }else if(mineralType != null && item.getName().equalsIgnoreCase(mineralType.getName())){
                updateItems(item, furnace, player, 5);
            }else{
                System.out.println(item.getName() + " is not a valid input");
                return;
            }
        }
    }

    private Result jarPreserver(Matcher matcher){return null;}

    private Result oilMaker(Matcher matcher){return null;}

    private Result MayonnaiseMachine(Matcher matcher){return null;}

    private Result loom(Matcher matcher){return null;}


    public static void check(){
        Player player = App.getGame().getCurrentPlayer();
        for (Machine onGoingMachine : player.getOnGoingMachines()) {
            if(onGoingMachine.suffice()){
                System.out.println("it suffices reducing time..");
                onGoingMachine.decreaseProcessTime();
            if(onGoingMachine.getProcessTime()<=0 && onGoingMachine.getProduce() == null){
                onGoingMachine.setProduce();
                System.out.println("produce is ready");
            }
            }else{
                System.out.println(onGoingMachine+" is not suffice");
            }
        }
    }

}
