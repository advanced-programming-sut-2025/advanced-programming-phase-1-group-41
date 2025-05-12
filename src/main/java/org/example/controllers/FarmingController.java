package org.example.controllers;

import org.example.models.*;
import org.example.models.foragings.Crop;
import org.example.models.foragings.CropType;
import org.example.models.foragings.Nature.Tree;
import org.example.models.foragings.Nature.TreeType;
import org.example.models.foragings.Seed;
import org.example.models.foragings.SeedType;
import org.example.models.items.Inventory;
import org.example.models.items.Slot;
import org.example.models.locations.Farm;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;

public class FarmingController {
    public Result craftInfo(Matcher matcher) {
        String craftName = matcher.group("craftName");
        for(CropType cropType : CropType.values()){
            if(craftName.equalsIgnoreCase(cropType.getName())){
                return new Result(true, cropType.toString());
            }
        }
        for(TreeType treeType : TreeType.values()){
            if(craftName.equalsIgnoreCase(treeType.getName())){
                return new Result(true, treeType.toString());
            }
        }
        return new Result(false, "Craft not found!");
    }

    public Result plant(Matcher matcher){
        String seed = matcher.group("seed");
        SeedType seedType = null;
        for(SeedType type : SeedType.values()){
            if(seed.equals(type.name())){
                seedType = type;
            }
        }
        if(seedType == null){
            return new Result(false, "Seed type not found!");
        }

        if(!App.getGame().getTime().getSeason().equals(seedType.getSeason()) && !seedType.getSeason().equals(Season.Special)){
            return new Result(false, "Can't plant this seed on this season!");
        }

        String direction = matcher.group("direction");
        int dir = Integer.parseInt(direction)-1;
        if(dir < 0 || dir > 7){
            return new Result(false, "invalid direction");
        }
        int [][]dirs = {{-1,-1},{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1},{-1,0},};
        int x = App.getGame().getCurrentPlayer().getX()+dirs[dir][0];
        int y = App.getGame().getCurrentPlayer().getY()+dirs[dir][1];

        Farm farm = App.getCurrentUser().getCurrentGame().getCurrentPlayerFarm();
        Inventory inventory = App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory();
        Cell cell = Finder.findCellByCoordinates(x, y, farm);

        assert cell != null;
        if(cell.getObjectMap() instanceof Grass){
            if(((Grass) cell.getObjectMap()).isFarmland()){
                for(Slot slot : inventory.getSlots()){
                    if(slot.getItem() instanceof Seed){
                        if(slot.getItem().getName().equals(seed)
                            || ((Seed) slot.getItem()).getSeedType().equals(seedType)){
                            List<String> mixedCropNames = Arrays.asList(
                                    "Cauliflower", "Parsnip", "Potato", "BlueJazz", "Tulip",
                                    "Corn", "HotPepper", "Radish", "Wheat", "Poppy", "Sunflower", "SummerSpangle",
                                    "Artichoke", "Eggplant", "Pumpkin", "FairyRose", "Powdermelon"
                            );
                            if(seedType.equals(SeedType.Mixed)){
                                Random rand = new Random();
                                String randomType = mixedCropNames.get(rand.nextInt(mixedCropNames.size()));
                                for(CropType cropType : CropType.values()){
                                    if(cropType.getSource().getName().equalsIgnoreCase(randomType)){
                                        farm.addCrop(new Crop(x, y, farm, cropType));
                                        inventory.removeFromInventory(slot.getItem(), 1);
                                        return new Result(true, "A " + cropType.getName() + " planted at " + x + "," + y);
                                    }
                                }
                            }
                            for(CropType cropType : CropType.values()){
                                if(cropType.getSource().equals(seedType)){
                                    farm.addCrop(new Crop(x, y, farm, cropType));
                                    inventory.removeFromInventory(slot.getItem(), 1);
                                    return new Result(true, "A " + cropType.getName() + " planted at " + x + "," + y);
                                }
                            }
                            for(TreeType treeType : TreeType.values()){
                                if(treeType.getSource().equals(seedType)){
                                    farm.addTree(new Tree(x, y, farm, treeType));
                                    inventory.removeFromInventory(slot.getItem(), 1);
                                    return new Result(true, "A " + treeType.getName() + " planted at " + x + "," + y);
                                }
                            }
                        }
                    }
                }
            }
        }
        return new Result(false, "Cell x: " + x + " y: " + y + " is not farmland!");
    }
    public Result showPlant(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Farm farm = App.getCurrentUser().getCurrentGame().getCurrentPlayerFarm();
        for(Crop crop : farm.getCrops()){
            if(crop.getX() == x && crop.getY() == y){
                return new Result(true, crop.toString());
            }
        }
        for(Tree tree : farm.getTrees()){
            if(tree.getX() == x && tree.getY() == y){
                return new Result(true, tree.toString());
            }
        }
        return new Result(false, "No Plant found");
    }
}
