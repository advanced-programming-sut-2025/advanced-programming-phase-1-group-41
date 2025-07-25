package com.CEliconValley.controllers.subgames;

import com.CEliconValley.models.*;
import com.CEliconValley.models.foragings.*;

import com.CEliconValley.models.buildings.GreenHouse.Greenhouse;
import com.CEliconValley.models.foragings.Nature.Grass;
import com.CEliconValley.models.foragings.Nature.Tree;
import com.CEliconValley.models.foragings.Nature.TreeType;
import com.CEliconValley.models.foragings.Nature.Wood;
import com.CEliconValley.models.items.Inventory;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.locations.Farm;

import java.util.*;
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
        for(ForagingCropType foragingCropType : ForagingCropType.values()){
            if(craftName.equalsIgnoreCase(foragingCropType.getName())){
                return new Result(true, foragingCropType.toString());
            }
        }
        return new Result(false, "Craft not found!");
    }
    public Result buildGreenhouse(Matcher matcher) {
        Player player = App.getGame().getCurrentPlayer();
        if(player.getInventory().getSlotByItem(new Wood()) == null){
            return new Result(false, "You don't have any wood! Try to chop some trees first!");
        }
        if(player.getMoney() < 1000 || player.getInventory().getSlotByItem(new Wood()).getQuantity() < 500){
            return new Result(false, "insufficient funds, you need at least 1000 money and 500 wood!\nMoney: "
                    + player.getMoney() + "\nWood: " + player.getInventory().getSlotByItem(new Wood()).getQuantity());
        }
        App.getGame().getCurrentPlayerFarm().getGreenhouse().unlock();
        player.getInventory().removeFromInventory(new Wood(), 500);
        player.decMoney(1000);
        return new Result(true, "Greenhouse has been built!");
    }
    public Result plant(Matcher matcher){
        String seed = matcher.group("seed");
        SeedType seedType = SeedType.parseSeedType(seed);
        String direction = matcher.group("direction");
        int dir = Integer.parseInt(direction)-1;
        if(dir < 0 || dir > 7){
            return new Result(false, "invalid direction");
        }
        int [][]dirs = {{-1,-1},{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1},{-1,0},};
        int x = App.getGame().getCurrentPlayer().getX()+dirs[dir][0];
        int y = App.getGame().getCurrentPlayer().getY()+dirs[dir][1];

        if(seedType == null){
            return new Result(false, "Seed type not found!");
        }

        if(!App.getGame().getTime().getSeason().equals(seedType.getSeason()) && !seedType.getSeason().equals(Season.Special)
                && !App.getGame().getCurrentPlayerFarm().getGreenhouse().isGreenHouse(x, y)){
            return new Result(false, "Can't plant this seed on this season!");
        }
        if(App.getGame().getCurrentPlayer().getInventory().getSlotByItem(new Seed(seedType)) == null){
            return new Result(false, "You don't have any seed in your inventory!");
        }

        Farm farm = App.getCurrentUser().getCurrentGame().getCurrentPlayerFarm();
        Inventory inventory = App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory();
        Cell cell = Finder.findCellByCoordinates(x, y, farm);

        assert cell != null;
        if(cell.getObjectMap() instanceof Grass || cell.getObjectMap() instanceof Greenhouse){
            boolean canPlant = true;
            if(cell.getObjectMap() instanceof Grass){
                canPlant = ((Grass) cell.getObjectMap()).isFarmland();
            }
            if(canPlant){
                for(Slot slot : inventory.getSlots()){
                    if(slot.getItem() instanceof Seed){
                        if(slot.getItem().getName().equals(seed)
                            || ((Seed) slot.getItem()).getSeedType().equals(seedType)){
                            List<String> mixedCropNames = Arrays.asList(
                                    "CauliflowerSeed", "ParsnipSeed", "PotatoSeed", "BlueJazzSeed", "TulipSeed",
                                    "CornSeed", "HotPepperSeed", "RadishSeed", "WheatSeed", "PoppySeed", "SunflowerSeed", "SummerSpangleSeed",
                                    "ArtichokeSeed", "EggplantSeed", "PumpkinSeed", "FairyRoseSeed", "PowdermelonSeed"
                            );
                            if(seedType.equals(SeedType.Mixed)){
                                Random rand = new Random();
                                while (true){
                                    String randomType = mixedCropNames.get(rand.nextInt(mixedCropNames.size()));
                                    for(CropType cropType : CropType.values()){
                                        if(cropType.getSource().getName().equalsIgnoreCase(randomType)){
                                            if(!cropType.getSource().getSeason().equals(App.getGame().getTime().getSeason())){
                                                break;
                                            }
                                            Result result = new Result(false, "");
                                            if(!farm.getGreenhouse().isGreenHouse(x, y)){
                                                result = giantCrop(x, y, cropType, farm);
                                            }
                                            if(result.success()){
                                                inventory.removeFromInventory(slot.getItem(), 1);
                                                return result;
                                            }
                                            farm.addCrop(new Crop(x, y, farm, cropType));
                                            inventory.removeFromInventory(slot.getItem(), 1);
                                            return new Result(true, "A " + cropType.getName() + " planted at " + x + "," + y);
                                        }
                                    }
                                }
                            }
                            for(CropType cropType : CropType.values()){
                                if(cropType.getSource().equals(seedType)){
                                    Result result = new Result(false, "");
                                    if(!farm.getGreenhouse().isGreenHouse(x, y)){
                                        result = giantCrop(x, y, cropType, farm);
                                    }
                                    if(result.success()){
                                        inventory.removeFromInventory(slot.getItem(), 1);
                                        return result;
                                    }
                                    farm.addCrop(new Crop(x, y, farm, cropType));
                                    inventory.removeFromInventory(slot.getItem(), 1);
                                    return new Result(true, "A " + cropType.getName() + " planted at " + x + "," + y);
                                }
                            }
                            for(TreeType treeType : TreeType.values()){
                                System.out.println("TreeType " + treeType.getName());
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

    private Result giantCrop(int x, int y, CropType cropType, Farm farm) {
        if(!cropType.canBecomeGiant()){
            return new Result(false, "Can't become giant!");
        }
        List<int[][]> directionSets = List.of(
                new int[][]{{0, 0}, {1, 0}, {0, 1}, {1, 1}},
                new int[][]{{0, 0}, {1, 0}, {0, -1}, {1, -1}},
                new int[][]{{0, 0}, {-1, 0}, {0, 1}, {-1, 1}},
                new int[][]{{0, 0}, {-1, 0}, {0, -1}, {-1, -1}}
        );

        for (int[][] directions : directionSets) {
            Result result = checkGiantCropFormation(x, y, farm, cropType, directions);
            if (result.success()) return result;
        }

        return new Result(false, "No Giant Crop found.");
    }

    private Result checkGiantCropFormation(int x, int y, Farm farm, CropType cropType, int[][] directions) {
        int count = 0;
        int maxCurrentStage = 0;
        int maxCurrentStageLevel = 0;
        ArrayList<Crop> crops = new ArrayList<>();

        for (int[] dir : directions) {
            Cell neighborCell = Finder.findCellByCoordinates(x + dir[0], y + dir[1], farm);
            if (neighborCell != null) {
                Object cellObject = neighborCell.getObjectMap();
                if (cellObject instanceof Crop neighborCrop) {
                    if (neighborCrop.getCropType().getName().equals(cropType.getName())) {
                        crops.add(neighborCrop);
                        count++;
                        if (neighborCrop.getCurrentStage() > maxCurrentStage) {
                            maxCurrentStage = neighborCrop.getCurrentStage();
                            maxCurrentStageLevel = neighborCrop.getCurrentStageLevel();
                        } else if (neighborCrop.getCurrentStage() == maxCurrentStage
                                && neighborCrop.getCurrentStageLevel() > maxCurrentStageLevel) {
                            maxCurrentStageLevel = neighborCrop.getCurrentStageLevel();
                        }
                    }
                }
            }
        }
        if(directions[3][0] == 1 && directions[3][1] == -1){
            y -= 1;
        }
        if(directions[3][0] == -1 && directions[3][1] == 1){
            x -= 1;
        }
        if(directions[3][0] == -1 && directions[3][1] == -1){
            x -= 1;
            y -= 1;
        }

        if (count >= 3) {
            farm.getCrops().removeAll(crops);
            Crop crop = new Crop(x, y, farm, cropType, maxCurrentStage, maxCurrentStageLevel);
            farm.addCrop(crop);
            Objects.requireNonNull(Finder.findCellByCoordinates(x, y, farm))
                    .setObjectMap(crop);
            return new Result(true, "A Giant " + cropType.getName() + " planted at " + x + "," + y);
        }
        return new Result(false, "No Giant Crop found.");
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
        return new Result(false, "No Plant found!");
    }
    public Result fertilize(Matcher matcher){
        String fertilizer = matcher.group("fertilizer");
        FertilizerType fertilizerType = FertilizerType.parseFertilizerType(fertilizer);
        if(fertilizerType == null){
            return new Result(false, "Fertilizer type not found!");
        }

        String direction = matcher.group("direction");
        int dir = Integer.parseInt(direction)-1;
        if(dir < 0 || dir > 7){
            return new Result(false, "invalid direction");
        }
        int [][]dirs = {{-1,-1},{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1},{-1,0},};
        int x = App.getGame().getCurrentPlayer().getX()+dirs[dir][0];
        int y = App.getGame().getCurrentPlayer().getY()+dirs[dir][1];
        Cell cell = Finder.findCellByCoordinates(x, y, App.getGame().getCurrentPlayerFarm());
        assert cell != null;
        if(fertilizerType.equals(FertilizerType.GrassStarter)){
            fertilizeGrass(cell, App.getGame().getCurrentPlayerFarm());
            return new Result(true, "Grass starter used at " + x + "," + y);
        }
        if(!(cell.getObjectMap() instanceof Crop) && !(cell.getObjectMap() instanceof Tree)){
            return new Result(false, "Cell is not a crop or a tree!");
        }
        Inventory inventory = App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory();
        for(Slot slot : inventory.getSlots()){
            if(slot.getItem() instanceof Fertilizer){
                if(slot.getItem().getName().equals(fertilizerType.getName())){
                    if(cell.getObjectMap() instanceof Crop){
                        fertilizeCrop((Crop) cell.getObjectMap(), fertilizerType);
                        inventory.removeFromInventory(slot.getItem(), 1);
                        return new Result(true, "Crop " + cell.getObjectMap().getName() + " fertilized with " + fertilizerType.getName());
                    }
                    if(cell.getObjectMap() instanceof Tree){
                        fertilizeTree((Tree) cell.getObjectMap(), fertilizerType);
                        inventory.removeFromInventory(slot.getItem(), 1);
                        return new Result(true, "Tree " + cell.getObjectMap().getName() + " fertilized with " + fertilizerType.getName());
                    }
                }
            }
        }
        return new Result(false, "No Fertilizer of this type found in your inventory!");
    }
    private void fertilizeCrop(Crop crop, FertilizerType fertilizerType){
        Random rand = new Random();
        int chance = 0;
        if(fertilizerType.equals(FertilizerType.BasicRetainingSoil)){
            chance = 1 + rand.nextInt(100);
        }
        else if(fertilizerType.equals(FertilizerType.QualityRetainingSoil)){
            chance = 1 + rand.nextInt(200);
        } else if(fertilizerType.equals(FertilizerType.DeluxeRetainingSoil)){
            chance = 100;
        }
        if(chance >= 50){
            crop.waterFertilize();
        }
        if(chance == 0){
            crop.decreaseWaterStreak();
            crop.increaseStage();
        }
    }
    private void fertilizeTree(Tree tree, FertilizerType fertilizerType){
        Random rand = new Random();
        int chance = 0;
        if(fertilizerType.equals(FertilizerType.BasicRetainingSoil)){
            chance = 1 + rand.nextInt(100);
        }
        else if(fertilizerType.equals(FertilizerType.QualityRetainingSoil)){
            chance = 1 + rand.nextInt(200);
        } else if(fertilizerType.equals(FertilizerType.DeluxeRetainingSoil)){
            chance = 100;
        }
        if(chance >= 50){
            tree.decreaseWaterStreak();
        }
        if(chance == 0){
            tree.decreaseWaterStreak();
            tree.increaseStage();
        }
    }
    private void fertilizeGrass(Cell cell, Farm farm){
        for(int i = -2 + cell.getX(); i <= 2 + cell.getX(); i++){
            for(int j = -2 + cell.getY(); j <= 2 + cell.getY(); j++){
                Cell cell1 = farm.getCell(i, j);
                if(cell1.getObjectMap() instanceof Grass){
                    ((Grass) cell1.getObjectMap()).setGround(false);
                }
            }
        }
    }
}
