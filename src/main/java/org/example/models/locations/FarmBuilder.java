package org.example.models.locations;

import org.example.models.App;
import org.example.models.Cell;
import org.example.models.Finder;
import org.example.models.foragings.Nature.Grass;
import org.example.models.WeatherType;
import org.example.models.foragings.*;
import org.example.models.foragings.Nature.Plant;
import org.example.models.foragings.Nature.Rock;
import org.example.models.foragings.Nature.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.example.models.App.MaxHeight;
import static org.example.models.App.MaxLength;

public class FarmBuilder {
    private final Farm farm;
    int rockCount;
    int foragingCropCount;
    int foragingTreeCount;
    int plantCount;

    public FarmBuilder(Farm farm) {
        this.farm = farm;
    }

    public void setCounters(){
        rockCount = 0;
        foragingCropCount = 0;
        foragingTreeCount = 0;
        plantCount = 0;
        for(Cell cell : farm.getCells()){
            if(cell.getObjectMap() instanceof Rock){
                rockCount++;
            }
            else if(cell.getObjectMap() instanceof ForagingCrop){
                foragingCropCount++;
            }
            else if(cell.getObjectMap() instanceof ForagingTree){
                foragingTreeCount++;
            }
            else if(cell.getObjectMap() instanceof Plant){
                plantCount++;
            }
        }

    }

    public void updateForagings(){
        setCounters();
        Random rand = new Random();
        rockCount = farm.getRockCount() - rockCount - 5 + rand.nextInt(11);
        foragingTreeCount = farm.getForagingTreeCount() - foragingTreeCount - 5 + rand.nextInt(11);
        plantCount = farm.getPlantCount() - plantCount - 5 + rand.nextInt(11);
        foragingCropCount = farm.getForagingCropCount() - foragingCropCount - 5 + rand.nextInt(11);
        for(int i = 0; i < rockCount ;i++){
            int y = rand.nextInt(MaxLength - 4) + 2;
            int x = rand.nextInt(MaxHeight - 4) + 2;
            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, farm)).getObjectMap().getChar(), new Grass().getChar())){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, farm)).setObjectMap(new Rock(x, y, farm));
            } else{
                i--;
            }
        }
        for(int i = 0; i < foragingTreeCount ;i++){
            int y = rand.nextInt(MaxLength - 4) + 4;
            int x = rand.nextInt(MaxHeight - 4) + 4;
            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, farm)).getObjectMap().getChar(), new Grass().getChar())){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, farm)).setObjectMap(new ForagingTree(x, y, farm));
            } else{
                i--;
            }
        }
        for(int i = 0; i < plantCount ;i++){
            int y = rand.nextInt(MaxLength - 4) + 4;
            int x = rand.nextInt(MaxHeight - 4) + 4;
            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, farm)).getObjectMap().getChar(), new Grass().getChar())){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, farm)).setObjectMap(new Plant(x, y, farm));
            } else{
                i--;
            }
        }
        for(int i = 0; i < foragingCropCount ;i++){
            int y = rand.nextInt(MaxLength - 4) + 4;
            int x = rand.nextInt(MaxHeight - 4) + 4;
            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, farm)).getObjectMap().getChar(), new Grass().getChar())){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, farm)).setObjectMap(new ForagingCrop(x, y, farm));
            } else{
                i--;
            }
        }
    }
    public void growCrops(){
        List<Crop> toRemove = new ArrayList<>();
        for (Crop crop : farm.getCrops()) {
            crop.increaseStage();
            if (crop.shouldBeRemoved()) {
                toRemove.add(crop);
            }
        }
        for(Crop crop : toRemove){
            farm.removeCrop(crop);
        }

        Random rand = new Random();
        App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().addToInventory(new Seed(SeedType.values()[rand.nextInt(SeedType.values().length)]), 2);
        App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().addToInventory(new Seed(SeedType.values()[rand.nextInt(SeedType.values().length)]), 2);
        App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().addToInventory(new Seed(SeedType.values()[rand.nextInt(SeedType.values().length)]), 2);
    }
    public void growTrees(){
        List<Tree> toRemove = new ArrayList<>();
        for (Tree tree : farm.getTrees()) {
            tree.increaseStage();
            if (tree.shouldBeRemoved()) {
                toRemove.add(tree);
            }
        }
        for(Tree tree : toRemove){
            farm.removeTree(tree);
        }
    }
    public void weatherUpdates(){
        for(Crop crop : farm.getCrops()){
            crop.nextDay();
        }
        for(Tree tree : farm.getTrees()){
            tree.nextDay();
        }
        if(App.getGame().getWeatherType().equals(WeatherType.Rainy)){
            for(Crop crop : farm.getCrops()){
                crop.water();
            }
            for(Tree tree : farm.getTrees()) {
                tree.water();
            }
        }
    }
}
