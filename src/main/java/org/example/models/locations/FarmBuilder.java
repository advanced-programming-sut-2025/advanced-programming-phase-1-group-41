package org.example.models.locations;

import org.example.models.Finder;
import org.example.models.Grass;
import org.example.models.foragings.Crop;
import org.example.models.foragings.ForagingCrop;
import org.example.models.foragings.Nature.Plant;
import org.example.models.foragings.Nature.Rock;
import org.example.models.foragings.Nature.Tree;

import java.util.Objects;
import java.util.Random;

import static org.example.models.App.MaxHeight;
import static org.example.models.App.MaxLength;

public class FarmBuilder {
    private final Farm farm;

    public FarmBuilder(Farm farm) {
        this.farm = farm;
    }

    public void updateForagings(){
        Random rand = new Random();
        int rockCount = (5 + rand.nextInt(10)) * farm.getFarmType().rockCoefficient;
        int treeCount = (5 + rand.nextInt(10)) * farm.getFarmType().treeCoefficient;
        int plantCount = (5 + rand.nextInt(10)) * farm.getFarmType().treeCoefficient;
        int cropCount = (5 + rand.nextInt(5));
        for(int i = 0; i < rockCount ;i++){
            int y = rand.nextInt(MaxLength - 4) + 2;
            int x = rand.nextInt(MaxHeight - 4) + 2;
            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, farm)).getObjectMap().getChar(), new Grass().getChar())){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, farm)).setObjectMap(new Rock(x, y, farm));
            } else{
                i--;
            }
        }
        for(int i = 0; i < treeCount ;i++){
            int y = rand.nextInt(MaxLength - 4) + 4;
            int x = rand.nextInt(MaxHeight - 4) + 4;
            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, farm)).getObjectMap().getChar(), new Grass().getChar())){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, farm)).setObjectMap(new Tree(x, y, farm));
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
        for(int i = 0; i < cropCount ;i++){
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
        for(Crop crop : farm.getCrops()){
            crop.increaseStage();
        }
    }
}
