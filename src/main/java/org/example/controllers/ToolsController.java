package org.example.controllers;

import org.example.models.*;
import org.example.models.animals.Animal;
import org.example.models.animals.animalKinds.Cow;
import org.example.models.animals.animalKinds.Goat;
import org.example.models.animals.animalKinds.Sheep;
import org.example.models.buildings.Building;
import org.example.models.buildings.GreenHouse.WaterTank;
import org.example.models.buildings.animalContainer.Barn;
import org.example.models.foragings.ForagingTree;
import org.example.models.foragings.Fruit;
import org.example.models.foragings.Nature.*;
import org.example.models.foragings.Crop;
import org.example.models.foragings.Nature.Mineral;
import org.example.models.foragings.Nature.Rock;
import org.example.models.foragings.Nature.RockType;
import org.example.models.foragings.Nature.Tree;
import org.example.models.items.Item;
import org.example.models.items.Products.Product;
import org.example.models.items.Products.ProductType;
import org.example.models.tools.*;

import java.util.regex.Matcher;

public class ToolsController {
    int x;
    int y;
    public Result equipTool(Matcher matcher){
        String toolName = matcher.group(1).trim();
        Tool tool = Finder.getToolByName(toolName);
        if(tool == null){
            return new Result(false, "Tool " +
                    toolName +
                    " not found");
        }
        App.getGame().getCurrentPlayer().setCurrentTool(tool);
        return new Result(true, "current tool is set to "+ tool.getName());
    }

    public Result showCurrentTool(Matcher matcher){
        Tool tool = App.getGame().getCurrentPlayer().getCurrentTool();
        if(tool == null){
            return new Result(false, "you don't have any tool equipped");
        }
        return new Result(true, "current tool is set to "+ tool.getName());
    }

    public Result showAvailableTools(Matcher matcher){
        StringBuilder message = new StringBuilder();
        message.append("Available tools:\n");
        App.getGame().getCurrentPlayer().getInventory().getSlots().forEach(slot -> {
            if(slot.getItem() instanceof Tool){
                message.append(slot.getItem().getName()).append(", ");
            }
        });
        message.delete(message.length()-2, message.length());
        return new Result(true, message.toString());
    }

    public Result upgradeTool(Matcher matcher){return null;}

    public Result preValidateUseTool(Matcher matcher){
        String dirName = matcher.group("direction").trim();
        int dir = Integer.parseInt(dirName)-1;
        if(dir < 0 || dir > 7){
            return new Result(false, "invalid dir");
        }
        int [][]dirs = {{-1,-1},{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1},{-1,0},};
        x = App.getGame().getCurrentPlayer().getX()+dirs[dir][0];
        y = App.getGame().getCurrentPlayer().getY()+dirs[dir][1];
        return new Result(true, "x: "+x+" y: "+y);
    }

    public Result useTool(Matcher matcher){
        Result preResult = preValidateUseTool(matcher);
        if (!preResult.success()){
            return preResult;
        }
        System.out.println(preResult);

        Cell cell = Finder.findCellByCoordinates(x,y,App.getGame().getCurrentPlayerFarm());
        System.out.println(x+" "+y);
        if(cell == null){
            return new Result(false, "Cell not found");
        }
        Tool tool = App.getGame().getCurrentPlayer().getCurrentTool();
        if(tool == null){
            return new Result(false, "you don't have any tool equipped");
        }

        if(tool instanceof Pickaxe){
            return usePickaxe(cell, tool);
        }else if(tool instanceof WateringCan){
            return useWateringCan(cell, tool);
        }else if(tool instanceof Hoe){
            return useHoe(cell, tool);
        }else if(tool instanceof Axe){
            return useAxe(cell, tool);
        }else if(tool instanceof Scythe){
            return useScythe(cell, tool);
        }else if(tool instanceof MilkPale){
            return useMilkPale(cell, tool);
        }else if(tool instanceof Shear){
            return useShear(cell, tool);
        }



        return new Result(false,"unable to use this ig");


    }


    private Result useWateringCan(Cell cell, Tool tool){
        WateringCan wc =  (WateringCan) tool;
        int energy = 0;
        switch (wc.getLevel()){
            case Default -> energy = 5;
            case Copper -> energy = 4;
            case Iron -> energy = 3;
            case Gold -> energy = 2;
            case Iridium -> energy = 1;
        }
        if(App.getGame().getCurrentPlayer().getFarmingSkill().isMaxLevel()){
            energy--;
        }
        if(cell.getObjectMap() instanceof Lake ||
                cell.getObjectMap() instanceof WaterTank){
            wc.setTiles(wc.getMaxTilesNumberByLevel());
            // todo count the skill of energy decrease
            App.getGame().getCurrentPlayer().decEnergy(energy);
            return new Result(true, "wc is filled now with " +
                    wc.getTiles() +
                    " tiles");
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
                App.getGame().getCurrentPlayer().decEnergy(energy);
                if(cell.getObjectMap() instanceof Crop){
                    ((Crop) cell.getObjectMap()).water();
                } else if(cell.getObjectMap() instanceof Tree){
                    ((Tree) cell.getObjectMap()).water();
                }
                return new Result(true, cell.getObjectMap().getName() + " has been watered successfully.");
            } else{
                return new Result(false, "Not enough water: " + wc.getTiles());
            }
        }
        return new Result(false, "unable to use this on "+cell.getObjectMap());
    }

    private Result usePickaxe(Cell cell, Tool tool){
        Pickaxe pickaxe = (Pickaxe) tool;
        int energy = 0;
        switch (pickaxe.getLevel()){
            case Default -> energy = 5;
            case Copper -> energy = 4;
            case Iron -> energy = 3;
            case Gold -> energy = 2;
            case Iridium -> energy = 1;
        }
        if(App.getGame().getCurrentPlayer().getMiningSkill().isMaxLevel()){
            energy--;
        }

        System.out.println(cell.getObjectMap().getClass());
        if(cell.getObjectMap() instanceof Mineral){
            int value =  App.getGame().getCurrentPlayer().getMiningSkill().getLevel() >= 2 ? 1 : 0;
            App.getGame().getCurrentPlayer().getMiningSkill().increaseXp(10);
            App.getGame().getCurrentPlayer().getInventory().addToInventory(
                    (Item) cell.getObjectMap(), 1 + value
            );
            String name = ((Item)cell.getObjectMap()).getName();
            App.getGame().getCurrentPlayer().decEnergy(energy);
            cell.setObjectMap(new Mine(x,y,App.getGame().getCurrentPlayerFarm(),12121212));
            return new Result(true, "got a "+name);
        }else if(cell.getObjectMap() instanceof Rock){
            ((Rock) cell.getObjectMap()).decreaseHitPoints();
            if(((Rock) cell.getObjectMap()).getHitPoints() == 0){
                int value =  App.getGame().getCurrentPlayer().getMiningSkill().getLevel() >= 2 ? 1 : 0;
                if(((Rock) cell.getObjectMap()).getRockType().equals(RockType.BigRock)){
                    App.getGame().getCurrentPlayer().getInventory().addToInventory(
                            (Item) cell.getObjectMap(), 4 + value
                    );
                    App.getGame().getCurrentPlayer().getMiningSkill().increaseXp(40);
                }else{
                    App.getGame().getCurrentPlayer().getInventory().addToInventory(
                            (Item) cell.getObjectMap(), 1 + value
                    );
                    App.getGame().getCurrentPlayer().getMiningSkill().increaseXp(10);
                }
                App.getGame().getCurrentPlayer().decEnergy(energy);
                String namemeeme = ((Item)cell.getObjectMap()).getName();
                if(((Rock) cell.getObjectMap()).getRockType().equals(RockType.BigRock)){
                    Cell cell2 = Finder.findCellByCoordinates(x + 1, y,App.getGame().getCurrentPlayerFarm());
                    assert cell2 != null;
                    cell2.setObjectMap(new Grass());
                    Cell cell3 = Finder.findCellByCoordinates(x + 1, y + 1,App.getGame().getCurrentPlayerFarm());
                    assert cell3 != null;
                    cell3.setObjectMap(new Grass());
                    Cell cell4 = Finder.findCellByCoordinates(x, y + 1,App.getGame().getCurrentPlayerFarm());
                    assert cell4 != null;
                    cell4.setObjectMap(new Grass());
                }
                cell.setObjectMap(new Grass());
                return new Result(true, "got a "+namemeeme);
            } else{
                if(cell.getObjectMap() instanceof Rock && !cell.getObjectMap().getName().equals(new Grass().getName())){
                    return new Result(true, "Hits Left: "+((Rock) cell.getObjectMap()).getHitPoints());
                }
                return new Result(true,"broke");
            }
        }else{
            App.getGame().getCurrentPlayer().decEnergy(Math.max(0,energy-1));
            if(!(cell.getObjectMap() instanceof Building)){
                cell.setObjectMap(new Grass());
            }
            return new Result(false,"fck this sht");
        }
    }


    private Result useHoe(Cell cell, Tool tool){
        Hoe hoe = (Hoe) tool;
        int energy = 0;
        switch (hoe.getLevel()){
            case Default -> energy = 5;
            case Copper -> energy = 4;
            case Iron -> energy = 3;
            case Gold -> energy = 2;
            case Iridium -> energy = 1;
        }
        if(App.getGame().getCurrentPlayer().getFarmingSkill().isMaxLevel()){
            energy--;
        }
        if(cell.getObjectMap() instanceof Grass){
            Grass grass = (Grass) cell.getObjectMap();
            grass.setFarmland(true);
            App.getGame().getCurrentPlayer().decEnergy(energy);
            return new Result(true, "grass is ready for shokhm");
        }
        return new Result(false,"it's not a grass!");
    }

    private Result useAxe(Cell cell, Tool tool){
        Axe axe = (Axe) tool;
        int energy = 0;
        switch (axe.getLevel()){
            case Default -> energy = 5;
            case Copper -> energy = 4;
            case Iron -> energy = 3;
            case Gold -> energy = 2;
            case Iridium -> energy = 1;
        }
        if(App.getGame().getCurrentPlayer().getForagingSkill().isMaxLevel()){
            energy--;
        }

        if(cell.getObjectMap() instanceof Tree){
            Tree tree = (Tree) cell.getObjectMap();
            tree.decreaseHitPoints();
            App.getGame().getCurrentPlayer().decEnergy(energy);

            if(tree.getHitPoints() == 0){
                App.getGame().getCurrentPlayer().getInventory().addToInventory(new Wood(), 100);
                cell.setObjectMap(new Grass());
                return new Result(true, "got some wood");
            }else{
                return new Result(true, "hit points left: "+tree.getHitPoints());
            }
        }else if(cell.getObjectMap() instanceof ForagingTree){
            ForagingTree foragingTree = (ForagingTree) cell.getObjectMap();
            foragingTree.decreaseHitPoints();
            App.getGame().getCurrentPlayer().decEnergy(energy);

            if(foragingTree.getHitPoints() == 0){
                App.getGame().getCurrentPlayer().getInventory().addToInventory(new Wood(), 100);
                cell.setObjectMap(new Grass());
                return new Result(true, "got some wood from foraging");
            }else{
                return new Result(true, "hit points left: "+foragingTree.getHitPoints());
            }
        }
        return new Result(false,"it's not a tree!");

    }

    private Result useScythe(Cell cell, Tool tool){
        Scythe scythe = (Scythe) tool;
        int energy = 2;
        App.getGame().getCurrentPlayer().decEnergy(energy);

        if(cell.getObjectMap() instanceof Bush){
            cell.setObjectMap(new Grass());
            return new Result(true, "caught the bush");
        }else if(cell.getObjectMap() instanceof Grass){
            ((Grass) cell.getObjectMap()).setGround(true);
            return new Result(true, "its now a ground");
        } else if(cell.getObjectMap() instanceof Crop crop){
            if(!(crop.getStages().size() - 1 == crop.getCurrentStage())){
                return new Result(true, "Crop is not ripe yet!");
            } else{
                App.getGame().getCurrentPlayer().getInventory().addToInventory(crop, 1);
                cell.setObjectMap(new Grass());
            }
        } else if(cell.getObjectMap() instanceof Tree tree){
            if(tree.getCurrentStage() < 3 || tree.getCurrentStageLevel() < 7){
                return new Result(true, "Tree is not ripe yet!");
            } else if(tree.getCurrentStageLevel() < tree.getTreeType().getFruitHarvestCycle()){
                return new Result(true, "Tree is not at its fruit harvest cycle!");
            }
            tree.setCurrentStageLevel(0);
            App.getGame().getCurrentPlayer().getInventory().addToInventory(new Fruit(tree.getTreeType().getFruitType()), 1);
            return new Result(true, "You got a " + tree.getTreeType().getFruitType().getName() + " fruit");
        }
        return new Result(false, "it wasn't a bush or grass or crop or tree!");
    }

    private Result useMilkPale(Cell cell, Tool tool){
        MilkPale milkPale = (MilkPale) tool;
        int energy = 4;
        App.getGame().getCurrentPlayer().decEnergy(energy);

        for (Barn barn : App.getGame().getCurrentPlayerFarm().getBarns()) {
            for (Animal animal : barn.getAnimals()) {
                System.out.println(animal.getName()+" "+animal.getX()+" "+animal.getY());
                if(animal.getX() == cell.getX() && animal.getY() == cell.getY()){
                    double specialProduceChance=(animal.getFriendShip()+(150*(0.5 + Math.random()))/1500);
                    if(animal instanceof Goat){
                        if(Math.random()<specialProduceChance){
                            App.getGame().getCurrentPlayer().getInventory().addToInventory
                                    (new Product(ProductType.BigGoatMilk), 1);
                            animal.setProduct(null);
                            return new Result(true, "got a big goat milk");
                        }
                        else {
                            App.getGame().getCurrentPlayer().getInventory().addToInventory
                                    (new Product(ProductType.GoatMilk), 1);
                            animal.setProduct(null);
                            return new Result(true, "got a goat milk");
                        }

                    }else if(animal instanceof Cow){
                        if(Math.random()<specialProduceChance) {
                            App.getGame().getCurrentPlayer().getInventory().addToInventory
                                    (new Product(ProductType.BigCowMilk), 1);
                            animal.setProduct(null);
                            return new Result(true, "got a big cow milk");
                        }else{
                            App.getGame().getCurrentPlayer().getInventory().addToInventory
                                    (new Product(ProductType.CowMilk), 1);
                            animal.setProduct(null);
                            return new Result(true, "got a cow milk");
                        }
                    }
                }
            }
        }
        return new Result(false, "no animal around you");
    }

    private Result useShear(Cell cell, Tool tool){
        Shear shear = (Shear) tool;
        int energy = 4;

        App.getGame().getCurrentPlayer().decEnergy(energy);

        for (Barn barn : App.getGame().getCurrentPlayerFarm().getBarns()) {
            for (Animal animal : barn.getAnimals()) {
                if(animal.getX() == cell.getX() && animal.getY() == cell.getY()){
                    if(animal instanceof Sheep){
                            App.getGame().getCurrentPlayer().getInventory().addToInventory
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
