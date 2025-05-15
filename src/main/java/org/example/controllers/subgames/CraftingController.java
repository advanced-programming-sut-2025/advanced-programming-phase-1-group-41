package org.example.controllers.subgames;

import org.example.models.*;
import org.example.models.animals.Fish;
import org.example.models.animals.FishType;
import org.example.models.buildings.Cottage;
import org.example.models.buildings.Well;
import org.example.models.foragings.*;
import org.example.models.foragings.Nature.MineralType;
import org.example.models.foragings.Nature.Mushroom;
import org.example.models.foragings.Nature.Vegetable;
import org.example.models.foragings.Nature.Wood;
import org.example.models.items.*;
import org.example.models.items.Products.Product;
import org.example.models.items.Products.ProductType;
import org.example.models.items.craftablemachines.*;

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
        if(item instanceof Well){
            if(cell.getY() <= App.getGame().getCurrentPlayer().getY()
            && cell.getX() <= App.getGame().getCurrentPlayer().getX()){
                return new Result(false,"choose another direction");
            }
            Well well =new Well(cell.getX(), cell.getY(), App.getGame().getCurrentPlayerFarm());
            App.getGame().getCurrentPlayerFarm().getBuildings().add(well);
            return new Result(true, "well was placed");
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
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
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
        String itemsInput = matcher.group("items");
        String[] puttingItems = new String[0];
        if(itemsInput != null) {
            puttingItems = itemsInput.trim().split(" ");
        }
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
            case Dehydrator -> this.dehydrator(items, cell);
            case PreservesJar -> this.jarPreserver(items, cell);
            case OilMaker -> this.oilMaker(items, cell);
            case Keg -> this.keg(items, cell);
            case CheesePress -> this.cheesePress(items, cell);
            case BeeHouse -> this.beeHouse(items, cell);
            case Loom -> this.loom(items, cell);
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

    private Result cheesePress(ArrayList<Item> items, Cell cell) {
        Item type= setCheese(items);
        Player player = App.getGame().getCurrentPlayer();
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

    private Result beeHouse(ArrayList<Item> items, Cell cell) {
        Player player = App.getGame().getCurrentPlayer();
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

    private Result keg(ArrayList<Item> items, Cell cell) {
        Item type= setKeg(items);
        Player player = App.getGame().getCurrentPlayer();
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

    private Result dehydrator(ArrayList<Item> items, Cell cell) {
        Item type = setDehydratorType(items);
        if(type == null){
            return new Result(false, "invalid item type ");
        }
        Player player = App.getGame().getCurrentPlayer();
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

    private Result jarPreserver(ArrayList<Item> items, Cell cell){
        Item type= setPreserver(items);
        Player player = App.getGame().getCurrentPlayer();
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

    private Result oilMaker(ArrayList<Item> items, Cell cell){
        Item type= setOilType(items);
        Player player = App.getGame().getCurrentPlayer();
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

    private Result MayonnaiseMachine(Matcher matcher){return null;}

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

    private Result loom(ArrayList<Item> items, Cell cell){
        Item item = setLoom(items);
        Player player = App.getGame().getCurrentPlayer();
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
