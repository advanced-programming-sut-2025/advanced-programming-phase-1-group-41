package com.CEliconValley.controllers.subgames;

import com.CEliconValley.models.*;
import com.CEliconValley.models.foragings.*;
import com.CEliconValley.models.foragings.Nature.*;
import com.CEliconValley.models.items.*;
import com.CEliconValley.models.items.craftablemachines.*;

import com.CEliconValley.models.animals.Fish;
import com.CEliconValley.models.animals.FishType;
import com.CEliconValley.models.buildings.Cottage;
import com.CEliconValley.models.buildings.Well;
import com.CEliconValley.models.items.Products.Product;
import com.CEliconValley.models.items.Products.ProductType;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.models.tools.BasicTool;
import com.CEliconValley.models.tools.Tool;
import com.CEliconValley.models.tools.TrashCan;

import javax.crypto.spec.PSource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;

public class CraftingController {
    int x;
    int y;

    private boolean inHome(Player player, Farm farm) {
        int x = player.getX();
        int y = player.getY();
        Cell cell = farm.getCell(x, y);
        if (cell == null) return false;
        if (cell.getObjectMap() instanceof Cottage) {
            return true;
        }
        return false;
    }

    public Result preValidateUseTool(Matcher matcher, Player player) {
        String dirName = matcher.group("direction").trim();
        int dir = Integer.parseInt(dirName) - 1;
        if (dir < 0 || dir > 7) {
            return new Result(false, "invalid dir");
        }
        int[][] dirs = {{-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0},};
        x = player.getX() + dirs[dir][0];
        y = player.getY() + dirs[dir][1];
        return new Result(true, "x: " + x + " y: " + y);
    }


    private void placeBomb(CraftableMachine craftableMachine, Cell cell, Player player, Farm farm) {
        int size = 3;
        if(craftableMachine.equals(CraftableMachine.Bomb)){
            size = 5;
        } else if(craftableMachine.equals(CraftableMachine.MegaBomb)){
            size = 7;
        }
        Inventory inventory = player.getInventory();
        for(int x = cell.getX() - size; x < cell.getX() + size; x++) {
            for(int y = cell.getY() - size; y < cell.getY() + size; y++) {
                int x1 = x - cell.getX();
                int y1 = y - cell.getY();
                if(x1 * x1 + y1 * y1 >= size * size){
                    continue;
                }
                Cell cell1 = Finder.findCellByCoordinates(x, y, farm);
                if(cell1 == null){
                    continue;
                }
                ObjectMap objectMap = cell1.getObjectMap();
                if(objectMap instanceof Rock || objectMap instanceof Bush || objectMap instanceof Plant
                    || objectMap instanceof Tree || objectMap instanceof Mineral || objectMap instanceof Crop
                    || objectMap instanceof ForagingTree || objectMap instanceof ForagingCrop || objectMap instanceof Grass){
                    if(objectMap instanceof Tree || objectMap instanceof ForagingTree){
                        inventory.addToInventory(new Wood(), 100);
                        System.out.println("You got 100 wood by burning a " + objectMap.getName());
                    } else if(objectMap instanceof Rock){
                        if(((Rock) objectMap).getRockType() == RockType.BigRock){
                            continue;
                        }
                        inventory.addToInventory(new Rock(), 1);
                        System.out.println("You got 1 rock by cracking a Small Rock");
                    } else if(objectMap instanceof Mineral){
                        inventory.addToInventory(new Mineral(((Mineral) objectMap).getMineralType()), 1);
                        System.out.println("You got a " + ((Mineral) objectMap).getMineralType().getName() + " by destroying a Mineral.");
                        cell1.setObjectMap(new Mine(x,y,farm,0));
                        continue;
                    }
                    Grass grass = new Grass();
                    grass.setBombed(true);
                    cell1.setObjectMap(grass);
                }
            }
        }
    }
    public void placeSprinkler(CraftableMachine craftableMachine, Cell cell, Player player, Farm farm) {
        int size = 4;
        if(craftableMachine.equals(CraftableMachine.QualitySprinkler)){
            size = 8;
        }
        if(craftableMachine.equals(CraftableMachine.IridiumSprinkler)) {
            size = 24;
        }
        for(int x = cell.getX() - size; x < cell.getX() + size; x++) {
            for (int y = cell.getY() - size; y < cell.getY() + size; y++) {
                Cell cell1 = Finder.findCellByCoordinates(x, y, farm);
                if (cell1 == null) {
                    continue;
                }
                ObjectMap objectMap = cell1.getObjectMap();
                if(objectMap instanceof Crop crop){
                    crop.water();
                    System.out.println("You watered " + crop.getCropType().getName() + " at " + x + ", " + y);
                } else if(objectMap instanceof Tree tree){
                    tree.water();
                    System.out.println("You watered " + tree.getTreeType().getName() + " at " + x + ", " + y);
                }
            }
        }
    }
    public Result placeItem(Matcher matcher, String playername) {
        Player player = Finder.getPlayerByUsername(playername);
        Farm farm = Finder.getFarmByPlayer(player);
        Result preResult = preValidateUseTool(matcher, player);
        if (!preResult.success()) {
            return preResult;
        }
        String itemName = matcher.group(1).trim();
        Cell cell = Finder.findCellByCoordinates(x, y, farm);
        if (cell == null) {
            return new Result(false, "Cell not found");
        }
        Item item = Finder.parseItem(itemName);
        if (item == null) {
            return new Result(false, "item not found");
        }
        if (item instanceof Tool){
            if(item instanceof TrashCan bt){
            }else{
                return new Result(false,"u can't place a tool");
            }
        }
        Inventory inventory = player.getInventory();
        Slot slot = inventory.getSlotByItem(item);
        if (slot == null) {
            return new Result(false, "slot not found");
        }
//        if(item instanceof Well){
//            if(cell.getY() <= player.getY()
//            && cell.getX() <= player.getX()){
//                return new Result(false,"choose another direction");
//            }
//            Well well = new Well(cell.getX(), cell.getY(), farm);
//            farm.getBuildings().add(well);
//            inventory.removeFromInventory(item, 1);
//            return new Result(true, "well was placed at "+well.getX()+", "+well.getY());
//        }
        if(item instanceof CraftableMachine machine){
            inventory.removeFromInventory(item, 1);
            if(machine.equals(CraftableMachine.Bomb) || machine.equals(CraftableMachine.CherryBomb) || machine.equals(CraftableMachine.MegaBomb)){
                placeBomb(machine, cell, player, farm);
                return new Result(true, "Ka-Booooom");
            }
            if(machine.equals(CraftableMachine.Sprinkler) || machine.equals(CraftableMachine.QualitySprinkler) || machine.equals(CraftableMachine.IridiumSprinkler)){
                placeSprinkler(machine ,cell, player, farm);
                return new Result(true, "Fshhhh.., watering all");
            }
            if(machine.equals(CraftableMachine.MysticTreeSeed)){
                Tree tree = new Tree(cell.getX(), cell.getY(), farm, TreeType.Mystic);
                farm.addTree(tree);
                cell.setObjectMap(tree);
                return new Result(true, "Mystical tree planted O_o");
            }
        }
        if(!(cell.getObjectMap() instanceof Grass ) &&
        !(cell.getObjectMap() instanceof Cottage)){
            return new Result(false, "there is already an item there named "+cell.getObjectMap().getName());
        }
        cell.setObjectMap(item);
        inventory.removeFromInventory(item, 1);
        return new Result(true, itemName + " placed on " + x + " " + y);
    }

    public Result showRecepies(Matcher matcher, String playername) {
//        if (!inHome()) {
//            return new Result(false, "You are not in a home");
//        }
        Player player = Finder.getPlayerByUsername(playername);
        StringBuilder message = new StringBuilder();
        message.append("Crafting recipes :\n");
        for (CraftingRecipe craftingRecipe : player.getCraftingRecipes()) {
            message.append(craftingRecipe.getName()).append("\n");
        }
        message.delete(message.length() - 1, message.length());
        return new Result(true, message.toString());
    }

    public Result learnRecipe(Matcher matcher) {
        return null;
    }

    public Result craftRecipe(Matcher matcher, String playername) {
        Player player = Finder.getPlayerByUsername(playername);
        Farm farm = Finder.getFarmByPlayer(player);
        if (!inHome(player, farm)) {
            return new Result(false, "You are not in a home");
        }
        String itemName = matcher.group(1).trim();
        Item item = Finder.parseItem(itemName);
        if (item == null) {
            return new Result(false, "Item not found");
        }



        for (CraftableMachine machine : CraftableMachine.values()) {
            if (item.getName().equals(machine.getName())) {
                boolean hasItems = hasNeededItems(machine, player);
                if (!hasItems) {
                    return new Result(false, "you don't have the needed items");
                }

                if (player.getInventory().getEmptySlots() <= 0) {
                    return new Result(false, "you don't have enough empty slots");
                }

                removeItems(machine, player);
                player.decEnergy(2);
                player.getInventory().addToInventory(machine, 1);
                return new Result(true, "You received a " + machine.getName());
            }
        }
        return new Result(false, "can't craft that item");
    }

    private void removeItems(CraftableMachine machine, Player player) {
        Inventory inventory = player.getInventory();
        for (Item item : machine.getRecipe().neededItems.keySet()) {
            Slot invSlot = inventory.getSlotByItem(item);
            inventory.removeFromInventory(item, machine.getRecipe().neededItems.get(item));
        }
    }

    private boolean hasNeededItems(CraftableMachine machine, Player player) {
        Inventory inventory = player.getInventory();
        boolean checker = true;
        for (Item item : machine.getRecipe().neededItems.keySet()) {
            Slot invSlot = inventory.getSlotByItem(item);
            if (invSlot != null && invSlot.getQuantity() >= machine.getRecipe().neededItems.get(item)) {
            } else {
                checker = false;
                System.out.println(item.getName() + " not found");
                break;
            }
        }
        return checker;
    }

    private Cell findArtisan(String machineName, Player player, Farm farm) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                x = player.getX() + i;
                y = player.getY() + j;
                Cell cell = farm.getCell(x, y);
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

    public Result artisanUse(Matcher matcher, String playername) {
        Player player = Finder.getPlayerByUsername(playername);
        Farm farm = Finder.getFarmByPlayer(player);
        String machineName = matcher.group(1).trim();
        String itemsInput = matcher.group("items");
        String[] puttingItems = new String[0];
        if(itemsInput != null) {
            puttingItems = itemsInput.trim().split(" ");
        }
        Cell cell = findArtisan(machineName, player, farm);
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
            Slot slot = player.getInventory().getSlotByItem(item);
            if (slot == null || slot.getQuantity() == 0) {
                return new Result(false,"you don't have " + item.getName());
            }
        }

        return switch (machine) {
            case Furnace -> this.furnace(items, cell, player);
            case CharcoalKiln -> this.charcoalKiln(items, cell, player);
            case FishSmoker -> this.fishSmoker(items, cell, player);
            case Dehydrator -> this.dehydrator(items, cell, player);
            case PreservesJar -> this.jarPreserver(items, cell, player);
            case OilMaker -> this.oilMaker(items, cell, player);
            case Keg -> this.keg(items, cell, player);
            case CheesePress -> this.cheesePress(items, cell, player);
            case BeeHouse -> this.beeHouse(items, cell, player);
            case Loom -> this.loom(items, cell, player);
            case MayonnaiseMachine -> this.mayonnaiseMachine(items, cell, player);
            default -> new Result(false, machineName + " is not useable");
        };
    }


    public Result artisanCheat(Matcher matcher, String playername) {
        Player player = Finder.getPlayerByUsername(playername);
        Farm farm = Finder.getFarmByPlayer(player);
        String machineName = matcher.group(1).trim();
        Cell cell = findArtisan(machineName, player, farm);
        if (cell == null) {
            return new Result(false, machineName + " is not around you");
        }
        CraftableMachine machine = (CraftableMachine) cell.getObjectMap();
        for (Machine onGoingMachine : player.getOnGoingMachines()) {
            if(!onGoingMachine.getCraftableMachine().getName().equalsIgnoreCase(machine.getName())) continue;
            onGoingMachine.setProcessTime(0);
            onGoingMachine.setProduce();
        }
        return new Result(true,"produce is ready");
    }

    public Result artisanStop(Matcher matcher, String playername) {
        Player player = Finder.getPlayerByUsername(playername);
        Farm farm = Finder.getFarmByPlayer(player);
        String machineName = matcher.group(1).trim();
        Cell cell = findArtisan(machineName, player, farm);
        if (cell == null) {
            return new Result(false, machineName + " is not around you");
        }
        CraftableMachine machine = (CraftableMachine) cell.getObjectMap();
        Iterator iterator = player.getOnGoingMachines().iterator();
        while(iterator.hasNext()){
            Machine onGoingMachine = (Machine) iterator.next();
            if(!onGoingMachine.getCraftableMachine().getName().equalsIgnoreCase(machine.getName())) continue;
            iterator.remove();
        }
        return new Result(true,"furnace canceled");
    }

    public Result artisanStart(Matcher matcher, String playername){
        Player player = Finder.getPlayerByUsername(playername);
        Farm farm = Finder.getFarmByPlayer(player);
        String machineName = matcher.group(1).trim();
        Cell cell = findArtisan(machineName, player, farm);
        if (cell == null) {
            return new Result(false, machineName + " is not around you");
        }
        CraftableMachine machine = (CraftableMachine) cell.getObjectMap();
        for (Machine onGoingMachine : player.getOnGoingMachines()) {
            if(!onGoingMachine.getCraftableMachine().getName().equals(machine.getName())) continue;
            if(onGoingMachine.suffice()){
                onGoingMachine.setStart(true);
                return new Result(true,"machine started");
            }else{
                return new Result(false,"insufficient material");
            }
        }
        return new Result(false,"no machine found!");
    }
    public Result artisanGet(Matcher matcher, String playername) {
        Player player = Finder.getPlayerByUsername(playername);
        Farm farm = Finder.getFarmByPlayer(player);
        String machineName = matcher.group(1).trim();
        Cell cell = findArtisan(machineName, player, farm);
        if (cell == null) {
            return new Result(false, machineName + " is not around you");
        }
        CraftableMachine machine = (CraftableMachine) cell.getObjectMap();
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
                return new Result(true, slot.getItem().getName() + " added to inventory");
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
            }else if(slot.getItem() instanceof Mineral && item instanceof Mineral && (
                    !((Mineral) slot.getItem()).getMineralType().equals(MineralType.Coal) &&
                            !((Mineral)item).getMineralType().equals(MineralType.Coal))
                    ){
                System.out.println(slot.getItem().getName()+" doesn't match "+item.getName());
            }
        }

    }


    private Result fishSmoker(ArrayList<Item> items, Cell cell, Player player) {
        Fish fish = setFish(items);
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

    private Item setCheese(ArrayList<Item> items) {
        for (Item item : items) {
            if(item instanceof Product product){
                if(product.getProductType()==ProductType.CowMilk ||
                        product.getProductType()==ProductType.BigCowMilk ||
                        product.getProductType()==ProductType.GoatMilk ||
                        product.getProductType()==ProductType.BigGoatMilk ){
                    return item;
                }
            }
        }
        return null;
    }

    private Result cheesePress(ArrayList<Item> items, Cell cell, Player player) {
        Item type= setCheese(items);
        if(type == null){
            return new Result(false, "Wrong item!");
        }
        for (Machine x : player.getOnGoingMachines()) {
            if (x instanceof CheesePress cp) {
                cheeseHelper(cp, items, player, type);
                return new Result(true, "done");
            }
        }
        CheesePress cp = null;
        if(type instanceof Product product){
            cp = new CheesePress(product);
        }
        if(cp == null){
            return new Result(false, "invalid item type "+type.getName());
        }
        player.getOnGoingMachines().add(cp);
        cheeseHelper(cp, items, player, type);
        return new Result(true, "donee");
    }

    private void cheeseHelper(CheesePress cp , ArrayList<Item> items , Player player, Item item){
        for (Item item1 : items) {
            if(item1.getName().equalsIgnoreCase(item.getName())){
                updateItems(item1, cp, player, 1);
            }else{
                System.out.println(item1.getName()+" is not a valid item");
                return;
            }
        }
    }

    private Result beeHouse(ArrayList<Item> items, Cell cell, Player player) {
        for (Machine x : player.getOnGoingMachines()) {
            if (x instanceof BeeHouse bh) {
                return new Result(true, "it's already ongoing ..");
            }
        }
        BeeHouse bh = new BeeHouse();
        player.getOnGoingMachines().add(bh);
        return new Result(true, "done");
    }


    private Item setKeg(ArrayList<Item> items) {
        for (Item item : items) {
            Vegetable vegetable = Vegetable.parseVegetable(item.getName());
            if(vegetable != null){
                return vegetable;
            }
            CropType type = CropType.parseCropType(item.getName());
            if(type != null){
                if(type == CropType.Wheat || type == CropType.Rice ||
                type == CropType.CoffeeBean || type == CropType.Hops){
                    return new Crop(type);
                }
            }
            FruitType fruit = FruitType.parseFruitType(item.getName());
            if(fruit != null){
                return new Fruit(fruit);
            }
            CraftableItem craftableItem = CraftableItem.parseCraftable(item.getName());
            if(craftableItem != null && craftableItem == CraftableItem.Honey){
                return craftableItem;
            }
        }
        return null;
    }

    private Result keg(ArrayList<Item> items, Cell cell, Player player) {
        Item type= setKeg(items);
        if(type == null){
            return new Result(false, "Wrong item!");
        }
        for (Machine x : player.getOnGoingMachines()) {
            if (x instanceof Keg keg) {
                kegHelper(keg, items, player, type);
                return new Result(true, "done");
            }
        }
        Keg keg = null;
        if(type instanceof Fruit fruit){
            keg = new Keg(fruit);
        }else if(type instanceof CraftableItem craftableItem){
            keg = new Keg(craftableItem);
        }else if(type instanceof Vegetable vegetable){
            keg = new Keg(vegetable);
        }else if(type instanceof Crop crop){
            keg = new Keg(crop);
        }
        if(keg == null){
            return new Result(false, "invalid item type "+type.getName());
        }
        player.getOnGoingMachines().add(keg);
        kegHelper(keg, items, player, type);
        return new Result(true, "donee");
    }

    private void kegHelper(Keg keg, ArrayList<Item> items, Player player, Item item) {
        for (Item item1 : items) {
            if(item1.getName().equalsIgnoreCase(item.getName())){
                int max = 1;
                if(item instanceof Crop crop){
                    if(crop.getCropType() == CropType.CoffeeBean){
                        max = 5;
                    }
                }
                updateItems(item1, keg, player, max);
            }else{
                System.out.println(item1.getName()+" is not a valid item");
                return;
            }
        }
    }



    private Result charcoalKiln(ArrayList<Item> items, Cell cell, Player player) {
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



    public Item setDehydratorType(ArrayList<Item> items){
        for (Item item : items) {
            Mushroom mushroom = Mushroom.parseMushroom(item.getName());
            if(mushroom != null){
                return mushroom;
            }
            FruitType fruit = FruitType.parseFruitType(item.getName());
            if(fruit != null){
                return new Fruit(fruit);
            }
            CropType cropType = CropType.parseCropType(item.getName());
            if(cropType != null && cropType.equals(CropType.Grape)){
                return new Crop(cropType);
            }
        }
        return null;
    }

    private Result dehydrator(ArrayList<Item> items, Cell cell, Player player) {
        Item type = setDehydratorType(items);
        if(type == null){
            return new Result(false, "invalid item type ");
        }
        for (Machine x : player.getOnGoingMachines()) {
            if (x instanceof Dehydrator dh) {
                dehyderatorHelper(dh, items, player, type);
                return new Result(true, "done");
            }
        }
        Dehydrator dehydrator = null;
        if(type instanceof Fruit){
            dehydrator = new Dehydrator((Fruit) type);
        }else if(type instanceof Crop){
            dehydrator = new Dehydrator((Crop) type, true);
        }else if(type instanceof Mushroom){
            dehydrator = new Dehydrator((Mushroom) type);
        }
        if(dehydrator == null){
            return new Result(false, "invalid item type "+type.getName());
        }
        player.getOnGoingMachines().add(dehydrator);
        dehyderatorHelper(dehydrator, items, player, type);
        return new Result(true, "donee");
    }

    private void dehyderatorHelper(Dehydrator dehydrator, ArrayList<Item> items, Player player, Item type){
        for (Item item : items) {
            if(item.getName().equalsIgnoreCase(type.getName())){
                updateItems(item, dehydrator, player, 5);
            }else{
                System.out.println(item.getName() + " is not a valid input");
                return;
            }
        }
    }



    private Result furnace(ArrayList<Item> items, Cell cell, Player player) {
        MineralType mineralType = setMineralType(items);
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


    private Item setPreserver(ArrayList<Item> items) {
        for (Item item : items) {
            CropType croptype = CropType.parseCropType(item.getName());
            if(croptype != null){
                if(croptype.isEatable()){
                    return new Crop(croptype);
                }else{
                    System.out.println(item.getName()+" is not eatable!");
                    return null;
                }
            }
            FruitType fruittype = FruitType.parseFruitType(item.getName());
            if(fruittype != null){
                return new Fruit(fruittype);
            }
        }
        return null;
    }

    private Result jarPreserver(ArrayList<Item> items, Cell cell, Player player){
        Item type= setPreserver(items);
        if(type == null){
            return new Result(false, "Wrong item!");
        }
        for (Machine x : player.getOnGoingMachines()) {
            if (x instanceof PreserveJar pj) {
                preserveHelper(pj, items, player, type);
                return new Result(true, "done");
            }
        }
        PreserveJar preserveJar = null;
        if(type instanceof Fruit fruit){
            preserveJar = new PreserveJar(fruit);
        }else if(type instanceof Crop crop){
            preserveJar = new PreserveJar(crop);
        }
        if(preserveJar == null){
            return new Result(false, "invalid item type "+type.getName());
        }
        player.getOnGoingMachines().add(preserveJar);
        preserveHelper(preserveJar, items, player, type);
        return new Result(true, "donee");
    }

    private void preserveHelper(PreserveJar pj, ArrayList<Item> items, Player player, Item type){
        for (Item item : items) {
            if(item.getName().equalsIgnoreCase(type.getName())){
                updateItems(item, pj, player, 1);
            }else{
                System.out.println(item.getName()+" is not a valid input");
                return;
            }
        }
    }



    private Item setOilType(ArrayList<Item> items) {
        for (Item item : items) {
            SeedType seedType = SeedType.parseSeedType(item.getName());
            if(seedType != null && seedType == SeedType.SunflowerSeed){
                return new Seed(seedType);
            }
            CropType cropType = CropType.parseCropType(item.getName());
            if(cropType != null && (cropType == CropType.Corn || cropType == CropType.Sunflower)){
                return new Crop(cropType);
            }
            ProductType productType = ProductType.parseType(item.getName());
            if(productType != null && productType == ProductType.PigTruffle){
                return new Product(productType);
            }
        }
        return null;
    }

    private Result oilMaker(ArrayList<Item> items, Cell cell, Player player){
        Item type= setOilType(items);
        if(type == null){
            return new Result(false, "Wrong item!");
        }
        for (Machine x : player.getOnGoingMachines()) {
            if (x instanceof OilMaker o) {
                oilHelper(o, items, player, type);
                return new Result(true, "done");
            }
        }
        OilMaker oilMaker = null;
        if(type instanceof Seed seed){
            oilMaker = new OilMaker(seed.getSeedType());
        }else if(type instanceof Crop crop){
            if(crop.getCropType() == CropType.Corn){
                oilMaker = new OilMaker(crop.getCropType(), true);
            }else{
                oilMaker = new OilMaker(crop.getCropType());
            }
        }else if(type instanceof Product product){
            oilMaker = new OilMaker(product.getProductType());
        }
        if(oilMaker == null){
            return new Result(false, "invalid item type "+type.getName());
        }
        player.getOnGoingMachines().add(oilMaker);
        oilHelper(oilMaker, items, player, type);
        return new Result(true, "donee");
    }

    private void oilHelper(OilMaker oilMaker, ArrayList<Item> items, Player player, Item type){
        for (Item item : items) {
            if(item.getName().equalsIgnoreCase(type.getName())){
                updateItems(item, oilMaker, player, 1);
            }else{
                System.out.println(item.getName()+" is not a valid input");
                return;
            }
        }
    }


    private Item setMayo(ArrayList<Item> items){
        for (Item item : items) {
            ProductType type = ProductType.parseType(item.getName());
            if(type != null ){
                if(type == ProductType.ChickenEgg || type == ProductType.BigChickenEgg ||
                type == ProductType.DuckEgg || type == ProductType.DinoEgg){
                    return new Product(type);
                }
            }
        }
        return null;
    }

    private Result mayonnaiseMachine(ArrayList<Item> items, Cell cell, Player player){
        Item type= setMayo(items);
        if(type == null){
            return new Result(false, "Wrong item!");
        }
        for (Machine x : player.getOnGoingMachines()) {
            if (x instanceof MayoMachine mayoMachine) {
                mayoHelper(mayoMachine, items, player, type);
                return new Result(true, "done");
            }
        }
        MayoMachine mayoMachine = null;
        if(type instanceof Product product){
            mayoMachine = new MayoMachine(product);
        }
        if(mayoMachine == null){
            return new Result(false, "invalid item type "+type.getName());
        }
        player.getOnGoingMachines().add(mayoMachine);
        mayoHelper(mayoMachine, items, player, type);
        return new Result(true, "donee");
    }

    private void mayoHelper(MayoMachine mayoMachine, ArrayList<Item> items, Player player, Item type){
        for (Item item : items) {
            if(item.getName().equalsIgnoreCase(type.getName())){
                updateItems(item, mayoMachine, player, 1);
            }else{
                System.out.println(item.getName()+" is not a valid input");
                return;
            }
        }
    }



    private Item setLoom(ArrayList<Item> items){
        for (Item item : items) {
            Product product = ProductType.parseProductType(item.getName());
            if(product != null){
                if(product.getProductType() == ProductType.SheepWool ||
                product.getProductType() == ProductType.RabbitWool){
                    return product;
                }
            }
        }
        return null;
    }

    private Result loom(ArrayList<Item> items, Cell cell, Player player){
        Item item = setLoom(items);
        if(item == null){
            return new Result(false, "Wrong item!");
        }
        for (Machine x : player.getOnGoingMachines()) {
            if (x instanceof Loom loom) {
                loomHelper(loom, items, player, item);
                return new Result(true, "done");
            }
        }
        Loom loom = null;
        if(item instanceof Product product){
            loom = new Loom(product);
        }
        if(loom == null){
            return new Result(false, "invalid item type "+item.getName());
        }
        player.getOnGoingMachines().add(loom);
        loomHelper(loom, items, player, item);
        return new Result(true, "donee");
    }

    private void loomHelper(Loom loom, ArrayList<Item> items, Player player, Item type){
        for (Item item : items) {
            if(item.getName().equalsIgnoreCase(type.getName())){
                updateItems(item, loom, player, 1);
            }else{
                System.out.println(item.getName()+" is not a valid input");
                return;
            }
        }
    }


    public static void check(){
        for (Player player : App.getGame().getPlayers()) {
            for (Machine onGoingMachine : player.getOnGoingMachines()) {
                if(onGoingMachine.suffice() && onGoingMachine.isStart()){
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

}
