package com.CEliconValley.controllers.subgames;

import com.CEliconValley.models.*;
import com.CEliconValley.models.foragings.*;
import com.CEliconValley.models.foragings.Nature.*;
import com.CEliconValley.models.tools.*;

import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.animals.animalKinds.Cow;
import com.CEliconValley.models.animals.animalKinds.Goat;
import com.CEliconValley.models.animals.animalKinds.Sheep;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.GreenHouse.Greenhouse;
import com.CEliconValley.models.buildings.GreenHouse.WaterTank;
import com.CEliconValley.models.buildings.Well;
import com.CEliconValley.models.buildings.animalContainer.Barn;
import com.CEliconValley.models.items.BuffType;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Products.Product;
import com.CEliconValley.models.items.Products.ProductType;
import com.CEliconValley.models.locations.Farm;
import dev.morphia.aggregation.stages.Match;

import java.util.Random;
import java.util.regex.Matcher;

public class ToolsController {
    int x;
    int y;
    public Result equipTool(Matcher matcher, String playername){
        Player player = Finder.getPlayerByUsername(playername);
        String toolName = matcher.group(1).trim();
        Tool tool = Finder.getToolByName(toolName);
        if(tool == null){
            return new Result(false, "Tool " +
                    toolName +
                    " not found");
        }
        player.setCurrentTool(tool);
        return new Result(true, "current tool is set to "+ tool.getName());
    }


    public Result upgradeTool(Matcher matcher){
        String itemName = matcher.group(1).trim();
        Tool tool = Finder.getToolByName(itemName);
        if(tool == null){
            return new Result(false, "You don't have"+itemName+" in your inventory");
        }
        return (new MarketplaceController()).upgradeTool(tool);
    }

    public Result preValidateUseTool(Matcher matcher, Player player){
        String dirName = matcher.group("direction").trim();
        int dir = Integer.parseInt(dirName)-1;
        if(dir < 0 || dir > 7){
            return new Result(false, "invalid dir");
        }
        int [][]dirs = {{-1,-1},{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1},{-1,0},};
        int [][]secondDirs = {{0,1},{1,0},{0,-1},{-1,0}};
        x = player.getX()+secondDirs[dir][0];
        y = player.getY()+secondDirs[dir][1];
        return new Result(true, "x: "+x+" y: "+y);
    }

    public Result useTool(Matcher matcher, String playername){
        Player player = Finder.getPlayerByUsername(playername);
        Result preResult = preValidateUseTool(matcher, player);
        if (!preResult.success()){
            return preResult;
        }
        System.out.println(preResult);

        Cell cell = Finder.findCellByCoordinates(x,y,Finder.getFarmByPlayer(player));
        System.out.println(x+" "+y);
        if(cell == null){
            return new Result(false, "Cell not found");
        }
        Tool tool = player.getCurrentTool();
        if(tool == null){
            return new Result(false, "you don't have any tool equipped");
        }

        if(tool instanceof Pickaxe){
            return usePickaxe(cell, tool, playername);
        }else if(tool instanceof WateringCan){
            return useWateringCan(cell, tool, playername);
        }else if(tool instanceof Hoe){
            return useHoe(cell, tool, playername);
        }else if(tool instanceof Axe){
            return useAxe(cell, tool, playername);
        }else if(tool instanceof Scythe){
            return useScythe(cell, tool, playername);
        }else if(tool instanceof MilkPale){
//            return useMilkPale(cell, tool, playername);
        }else if(tool instanceof Shear){
//            return useShear(cell, tool, playername);
        }



        return new Result(false,"unable to use this ig");


    }


    private Result useWateringCan(Cell cell, Tool tool, String playername){
        Player player = Finder.getPlayerByUsername(playername);
        Farm farm = Finder.getFarmByPlayer(player);
        WateringCan wc =  (WateringCan) tool;
        int energy = 0;
        switch (wc.getLevel()){
            case Default -> energy = 5;
            case Copper -> energy = 4;
            case Iron -> energy = 3;
            case Gold -> energy = 2;
            case Iridium -> energy = 1;
        }
        if(player.getFarmingSkill().isMaxLevel()){
            energy--;
        }
        if(cell.getObjectMap() instanceof Lake ||
                cell.getObjectMap() instanceof WaterTank ||
                cell.getObjectMap() instanceof Well){
            if(energy > player.getEnergy()){
                return new Result(false, "you don't have enough energy for this tool");
            }
            player.decEnergyTool(energy);
            wc.setTiles(wc.getMaxTilesNumberByLevel());
            // todo count the skill of energy decrease
            Result result = new Result(true, "wc is filled now with " +
                wc.getTiles() +
                " tiles");
            App.sendResult(result, playername);
            return result;
        } else if(cell.getObjectMap() instanceof Crop || cell.getObjectMap() instanceof Tree){
            if(cell.getObjectMap() instanceof Crop){
                if(((Crop) cell.getObjectMap()).isWateredToday()){
                    return new Result(true, cell.getObjectMap().getName() + " has been already watered today!");
                }
            }
            if(cell.getObjectMap() instanceof Tree){
                if(((Tree) cell.getObjectMap()).isWateredToday()){
                    return new Result(true, cell.getObjectMap().getName() + " has been already watered today!");
                }
            }
            if(wc.decreaseTiles()){
                if(energy > player.getEnergy()){
                    return new Result(false, "you don't have enough energy for this tool");
                }
                player.decEnergyTool(energy);
                if(cell.getObjectMap() instanceof Crop){
                    ((Crop) cell.getObjectMap()).water();
                } else if(cell.getObjectMap() instanceof Tree){
                    ((Tree) cell.getObjectMap()).water();
                }
                Result result = new Result(true, cell.getObjectMap().getName() + " has been watered successfully.");
                App.sendResult(result, playername);
                return result;
            } else{
                return new Result(false, "Not enough water: " + wc.getTiles());
            }
        }
        return new Result(false, "unable to use this on "+cell.getObjectMap());
    }

    private Result usePickaxe(Cell cell, Tool tool, String playername){
        Pickaxe pickaxe = (Pickaxe) tool;
        int energy = 0;
        switch (pickaxe.getLevel()){
            case Default -> energy = 5;
            case Copper -> energy = 4;
            case Iron -> energy = 3;
            case Gold -> energy = 2;
            case Iridium -> energy = 1;
        }
        Player player = Finder.getPlayerByUsername(playername);
        Farm farm = Finder.getFarmByPlayer(player);
        if(player.getMiningSkill().isMaxLevel()){
            energy--;
        }

        System.out.println(cell.getObjectMap().getName());
        if(cell.getObjectMap() instanceof Mineral){
            if(energy > player.getEnergy()){
                return new Result(false ,"you don't have enough energy for this tool");
            }
            if(player.getBuff() != null){
                if(player.getBuff().getBuffType().equals(BuffType.Mining)){
                    energy--;
                }
            }
            player.decEnergyTool(Math.max(energy, 0));
            int value =  player.getMiningSkill().getLevel() >= 2 ? 1 : 0;
            player.getMiningSkill().increaseXp(100);
            player.getInventory().addToInventory(
                    (Item) cell.getObjectMap(), 1 + value
            );
            player.getForagingSkill().increaseXp(10);
            String name = ((Item)cell.getObjectMap()).getName();
            cell.setObjectMap(new Mine(x,y,farm,12121212));
            return new Result(true, "got a "+name);
        }else if(cell.getObjectMap() instanceof Rock){
            ((Rock) cell.getObjectMap()).decreaseHitPoints();
            if(((Rock) cell.getObjectMap()).getHitPoints() == 0){
                if(energy > player.getEnergy()){
                    return new Result(false, "you don't have enough energy for this tool");
                }
                if(player.getBuff() != null){
                    if(player.getBuff().getBuffType().equals(BuffType.Mining)
                            || player.getBuff().getBuffType().equals(BuffType.Foraging)){
                        if(energy >= 1){
                            energy--;
                        }
                    }
                }
                player.decEnergyTool(energy);
                int value =  player.getMiningSkill().getLevel() >= 2 ? 1 : 0;
                if(((Rock) cell.getObjectMap()).getRockType().equals(RockType.BigRock)){
                    player.getInventory().addToInventory(
                            (Item) cell.getObjectMap(), 4 + value
                    );
                    player.getForagingSkill().increaseXp(10);
                    player.getMiningSkill().increaseXp(40);
                }else{
                    player.getInventory().addToInventory(
                            (Item) cell.getObjectMap(), 1 + value
                    );
                    player.getForagingSkill().increaseXp(10);
                    player.getMiningSkill().increaseXp(10);
                }
                String name = ((Item)cell.getObjectMap()).getName();
                if(((Rock) cell.getObjectMap()).getRockType().equals(RockType.BigRock)){
                    Cell cell2 = Finder.findCellByCoordinates(x + 1, y,farm);
                    assert cell2 != null;
                    cell2.setObjectMap(new Grass());
                    Cell cell3 = Finder.findCellByCoordinates(x + 1, y + 1,farm);
                    assert cell3 != null;
                    cell3.setObjectMap(new Grass());
                    Cell cell4 = Finder.findCellByCoordinates(x, y + 1,farm);
                    assert cell4 != null;
                    cell4.setObjectMap(new Grass());
                }
                cell.setObjectMap(new Grass());
                return new Result(true, "got a "+name);
            } else{
                if(cell.getObjectMap() instanceof Rock && !cell.getObjectMap().getName().equals(new Grass().getName())){
                    return new Result(true, "Hits Left: "+((Rock) cell.getObjectMap()).getHitPoints());
                }
                return new Result(true,"broke");
            }
        }else{
            if(energy - 1 > player.getEnergy()){
                return new Result(false, "you don't have enough energy to use this tool");
            }
            if(player.getBuff() != null){
                if(player.getBuff().getBuffType().equals(BuffType.Mining)
                        || player.getBuff().getBuffType().equals(BuffType.Foraging)){
                    if(energy >= 1){
                        energy--;
                    }
                }
            }
            player.decEnergyTool(Math.max(0,energy-1));
            try{
                player.getInventory().addToInventory((Item)cell.getObjectMap(),1);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if(!(cell.getObjectMap() instanceof Building)){
                cell.setObjectMap(new Grass());
            }
            return new Result(false,"you used your pickaxe..");
        }
    }


    private Result useHoe(Cell cell, Tool tool, String playerName){
        Player player = Finder.getPlayerByUsername(playerName);
        Farm farm = Finder.getFarmByPlayer(player);
        Hoe hoe = (Hoe) tool;
        int energy = 0;
        switch (hoe.getLevel()){
            case Default -> energy = 5;
            case Copper -> energy = 4;
            case Iron -> energy = 3;
            case Gold -> energy = 2;
            case Iridium -> energy = 1;
        }
        if(player.getFarmingSkill().isMaxLevel()){
            energy--;
        }
        if(player.getBuff() != null){
            if(player.getBuff().getBuffType().equals(BuffType.Farming)){
                if(energy >= 1){
                    energy--;
                }
            }
        }
        if(cell.getObjectMap() instanceof Grass){
            if(energy > player.getEnergy()){
                return new Result(false,"you don't have enough energy to use this tool");
            }
            player.decEnergyTool(energy);
            Grass grass = (Grass) cell.getObjectMap();
            grass.setFarmland(true);
            return new Result(true, "grass is ready for shokhm");
        }
        return new Result(false,"it's not a grass!");
    }

    private Result useAxe(Cell cell, Tool tool, String playerName){
        Player player = Finder.getPlayerByUsername(playerName);
        Farm farm = Finder.getFarmByPlayer(player);
        Axe axe = (Axe) tool;
        int energy = 0;
        switch (axe.getLevel()){
            case Default -> energy = 5;
            case Copper -> energy = 4;
            case Iron -> energy = 3;
            case Gold -> energy = 2;
            case Iridium -> energy = 1;
        }
        if(player.getForagingSkill().isMaxLevel()){
            energy--;
        }
        if(player.getBuff() != null){
            if(player.getBuff().getBuffType().equals(BuffType.Foraging)){
                if(energy >= 1){
                    energy--;
                }
            }
        }
        if(cell.getObjectMap() instanceof Tree tree){
            tree.decreaseHitPoints();
            if(energy > player.getEnergy()){
                return new Result(false, "you don't have enough energy to use this tool");
            }
            player.decEnergyTool(energy);

            if(tree.isThundered()){
                player.getInventory().addToInventory(new Mineral(MineralType.Coal), 5);
                player.getForagingSkill().increaseXp(10);
                if(farm.getGreenhouse().isGreenHouse(tree.getX(), tree.getY())){
                    cell.setObjectMap(new Greenhouse());
                } else{
                    cell.setObjectMap(new Grass());
                }
                return new Result(true, "You got 5 coal.");
            }
            if(tree.getHitPoints() == 0){
                Random rand = new Random();
                int saplingCount = 1 + rand.nextInt(2);
                player.getInventory().addToInventory(new Wood(), 100);
                player.getInventory().addToInventory(new Seed(tree.getTreeType().getSource()), saplingCount);
                player.getForagingSkill().increaseXp(10);
                if(farm.getGreenhouse().isGreenHouse(tree.getX(), tree.getY())){
                    cell.setObjectMap(new Greenhouse());
                } else{
                    cell.setObjectMap(new Grass());
                }
                return new Result(true, "got some wood and " + saplingCount + " " + tree.getTreeType().getSource().getName());
            }else{
                return new Result(true, "hit points left: "+tree.getHitPoints());
            }
        }else if(cell.getObjectMap() instanceof ForagingTree foragingTree){
            if(energy > player.getEnergy()){
                return new Result(false, "you don't have enough energy to use this tool");
            }
            player.decEnergyTool(energy);
            foragingTree.decreaseHitPoints();

            if(foragingTree.isThundered()){
                player.getInventory().addToInventory(new Mineral(MineralType.Coal), 5);
                player.getForagingSkill().increaseXp(10);
                cell.setObjectMap(new Grass());
                return new Result(true, "You got 5 coal.");
            }
            if(foragingTree.getHitPoints() == 0){
                Random rand = new Random();
                int saplingCount = 1 + rand.nextInt(2);
                player.getInventory().addToInventory(new Wood(), 100);
                player.getInventory().addToInventory(new Seed(foragingTree.getTreeType().getSource()), saplingCount);
                player.getForagingSkill().increaseXp(10);
                cell.setObjectMap(new Grass());
                return new Result(true, "got some wood and " + saplingCount + " " + foragingTree.getTreeType().getSource().getName());
            }else{
                return new Result(true, "hit points left: "+foragingTree.getHitPoints());
            }
        }
        return new Result(false,"it's not a tree!");

    }

    private Result useScythe(Cell cell, Tool tool, String playername){
        Player player = Finder.getPlayerByUsername(playername);
        Farm farm = Finder.getFarmByPlayer(player);
        Scythe scythe = (Scythe) tool;
        int energy = 2;
        if(player.getBuff() != null){
            if(player.getBuff().getBuffType().equals(BuffType.Farming)){
                energy--;
            }
        }
        if(energy > player.getEnergy()){
            return new Result(false,"you don't have enough energy to use this tool");
        }
        player.decEnergyTool(energy);

        if(cell.getObjectMap() instanceof Bush){
            player.getInventory().addToInventory(new Fiber(), 5);
            cell.setObjectMap(new Grass());
            return new Result(true, "caught the bush");
        }else if(cell.getObjectMap() instanceof Grass){
            ((Grass) cell.getObjectMap()).setGround(true);
            return new Result(true, "It's now a ground");
        } else if(cell.getObjectMap() instanceof Crop crop){
            if(!(crop.getStages().size() - 1 == crop.getCurrentStage()) || (!crop.getCropType().isOneTimeHarvest() && crop.getCurrentStageLevel() < crop.getRegrowthTime())){
                return new Result(true, "Crop is not ripe yet!");
            } else{
                if(!crop.getCanRegrow()){
                    if(crop.isGiantCrop()){
                        player.getInventory().addToInventory(crop, 10);
                        player.getFarmingSkill().increaseXp(5);
                        int x = crop.getX();
                        int y = crop.getY();
                        farm.getCrops().remove(crop);
                        Cell cell1 = Finder.findCellByCoordinates(x, y, farm);
                        assert cell1 != null;
                        cell1.setObjectMap(new Grass());
                        Cell cell2 = Finder.findCellByCoordinates(x + 1, y, farm);
                        assert cell2 != null;
                        cell2.setObjectMap(new Grass());
                        Cell cell3 = Finder.findCellByCoordinates(x, y + 1, farm);
                        assert cell3 != null;
                        cell3.setObjectMap(new Grass());
                        Cell cell4 = Finder.findCellByCoordinates(x + 1, y + 1, farm);
                        assert cell4 != null;
                        cell4.setObjectMap(new Grass());
                        return new Result(true, "You got 10 " + crop.getName());
                    } else{
                        player.getInventory().addToInventory(crop, 1);
                        player.getFarmingSkill().increaseXp(5);
                        if(farm.getGreenhouse().isGreenHouse(crop.getX(), crop.getY())){
                            cell.setObjectMap(new Greenhouse());
                        } else{
                            cell.setObjectMap(new Grass());
                        }
                        return new Result(true, "You got a " + crop.getName());
                    }
                }
                else{
                    crop.setCurrentStageLevel(0);
                    crop.setCanRegrow(false);
                    if(crop.isGiantCrop()){
                        player.getInventory().addToInventory(crop, 10);
                        player.getFarmingSkill().increaseXp(20);
                        return new Result(true, "You got 10 " + crop.getName());
                    } else{
                        player.getInventory().addToInventory(crop, 1);
                        player.getFarmingSkill().increaseXp(5);
                        return new Result(true, "You got a " + crop.getName());
                    }
                }
            }
        } else if(cell.getObjectMap() instanceof Tree tree){
            if(tree.isThundered()){
                player.getInventory().addToInventory(new Mineral(MineralType.Coal), 5);
                cell.setObjectMap(new Grass());
                return new Result(true, "You got 5 coal.");
            }
            if(tree.isAttacked()){
                return new Result(false, "Tree was attacked last night!.");
            }
            if(tree.getCurrentStage() < 3 || tree.getCurrentStageLevel() < 7){
                return new Result(false, "Tree is not ripe yet!");
            } else if(tree.getCurrentStageLevel() < tree.getTreeType().getFruitHarvestCycle()){
                return new Result(false, "Tree is not at its fruit harvest cycle!");
            }
            tree.setCurrentStageLevel(0);
            player.getInventory().addToInventory(new Fruit(tree.getTreeType().getFruitType()), 1);
            player.getFarmingSkill().increaseXp(5);
            return new Result(true, "You got a " + tree.getTreeType().getFruitType().getName() + " fruit.");
        } else if(cell.getObjectMap() instanceof ForagingCrop crop){
            player.getInventory().addToInventory(crop, 1);
            player.getFarmingSkill().increaseXp(5);
            player.getForagingSkill().increaseXp(10);
            cell.setObjectMap(new Grass());
            return new Result(true, "You got a " + crop.getName());
        } else if(cell.getObjectMap() instanceof ForagingTree tree){
            if(tree.isThundered()){
                player.getInventory().addToInventory(new Mineral(MineralType.Coal), 5);
                cell.setObjectMap(new Grass());
                return new Result(true, "You got 5 coal.");
            }
            player.getInventory().addToInventory(new Fruit(tree.getTreeType().getFruitType()), 1);
            player.getFarmingSkill().increaseXp(5);
            player.getForagingSkill().increaseXp(10);
            return new Result(true, "You got a " + tree.getTreeType().getFruitType().getName() + " fruit.");
        }
        return new Result(false, "it wasn't a bush or grass or crop or tree!");
    }

    public Result useMilkPale(Matcher matcher ,String playername){
        Player player = Finder.getPlayerByUsername(playername);
        Farm farm = Finder.getFarmByPlayer(player);
        MilkPale milkPale = (MilkPale) player.getCurrentTool();
        String animalname = matcher.group(1).trim();
        int energy = 4;
        if(energy > player.getEnergy()){
            return new Result(false, "you don't have enough energy to use this tool");
        }
        player.decEnergyTool(energy);
        for (Barn barn : farm.getBarns()) {
            for (Animal animal : barn.getAnimals()) {
                if(!animal.getName().equals(animalname)) continue;
                double specialProduceChance=(animal.getFriendShip()+(150*(0.5 + Math.random()))/1500);
                if(animal instanceof Goat){
                    if(Math.random()<specialProduceChance){
                        player.getInventory().addToInventory
                            (new Product(ProductType.BigGoatMilk), 1);
                        animal.setProduct(null);
                        return new Result(true, "got a big goat milk");
                    }
                    else {
                        player.getInventory().addToInventory
                            (new Product(ProductType.GoatMilk), 1);
                        animal.setProduct(null);
                        return new Result(true, "got a goat milk");
                    }

                }else if(animal instanceof Cow){
                    if(Math.random()<specialProduceChance) {
                        player.getInventory().addToInventory
                            (new Product(ProductType.BigCowMilk), 1);
                        animal.setProduct(null);
                        return new Result(true, "got a big cow milk");
                    }else{
                        player.getInventory().addToInventory
                            (new Product(ProductType.CowMilk), 1);
                        animal.setProduct(null);
                        return new Result(true, "got a cow milk");
                    }
                }
            }
        }
        return new Result(false, "no animal around you");
    }

    public Result useShear(Matcher matcher, String playername){
        Player player = Finder.getPlayerByUsername(playername);
        Farm farm = Finder.getFarmByPlayer(player);
        String animalName = matcher.group(1).trim();
        Shear shear = (Shear) player.getCurrentTool();
        int energy = 4;
        if(energy > player.getEnergy()){
            return new Result(false, "you don't have enough energy to use this tool");
        }
        player.decEnergyTool(energy);
        for (Barn barn : farm.getBarns()) {
            for (Animal animal : barn.getAnimals()) {
                if(animal.getName().equals(animalName)){
                    if(animal instanceof Sheep){
                            player.getInventory().addToInventory
                                    (new Product(ProductType.SheepWool),1);
                            animal.setProduct(null);
                            return new Result(true, "got a sheep wool");
                    }else{
                        return new Result(false,"the animal is not a sheep");
                    }
                }
            }
        }
        return new Result(false,"no animal around u");
    }


}
