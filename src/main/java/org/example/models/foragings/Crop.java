package org.example.models.foragings;

import org.example.models.App;
import org.example.models.Cell;
import org.example.models.Colors;
import org.example.models.Finder;
import org.example.models.items.Item;
import org.example.models.locations.Farm;

import java.util.ArrayList;

public class Crop implements Item {
    private final CropType cropType;
    private final ArrayList<Integer> stages;
    private int typeIndex = 0;
    private int currentStage;
    private int currentStageLevel;
    private Boolean isWateredToday = false;
    private int waterStreak = 0;
    private Boolean isFertilizedToday = false;
    private int x;
    private int y;

    public Crop(int x, int y, Farm farm, CropType cropType) {
        this.x = x;
        this.y = y;
        this.cropType = cropType;
        for(CropType type : CropType.values()){
            if(type.equals(cropType)){
                break;
            }
            typeIndex++;
        }
        stages = cropType.getStages();
        currentStage = 0;
        Cell cell = Finder.findCellByCoordinates(x, y, farm);
        assert cell != null;
        cell.setObjectMap(this);
    }

    public CropType getCropType() {
        return cropType;
    }

    public ArrayList<Integer> getStages() {
        return stages;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    @Override
    public String getChar() {
        return Colors.colorize(53,4 - currentStage,typeIndex/10 + "" + typeIndex%10);
    }

    @Override
    public String getName() {
        return cropType.getName();
    }

    public void increaseStage() {
        if(!isWateredToday){
            waterStreak++;
        } else {
            waterStreak = 0;
        }
        if(waterStreak >= 2){
            App.getCurrentUser().getCurrentGame().getCurrentPlayerFarm().getCrops().remove(this);
        }
        currentStageLevel++;
        if(currentStageLevel >= stages.get(currentStage)){
            currentStage++;
            if(currentStage >= stages.size()){
                currentStage--;
            }
        } else {
            currentStageLevel = 0;
        }
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public Boolean isWateredToday() {
        return isWateredToday;
    }
    public Boolean isFertilizedToday() {
        return isFertilizedToday;
    }

    @Override
    public String toString() {
        StringBuilder stages = new StringBuilder();
        ArrayList<Integer> stagesList = getStages();
        for (int i = 0; i < stagesList.size(); i++) {
            if(i == stagesList.size() - 1) {
                stages.append(stagesList.get(i));
            } else{
                stages.append(stagesList.get(i)).append("-");
            }
        }
        StringBuilder result = new StringBuilder();
        result.append("Name: ").append(getName()).append("\n");
        result.append("Source: ").append(getCropType().getSource().getName()).append("\n");
        result.append("Stages: ").append(stages).append("\n");
        result.append("Total Harvest Time: ").append(cropType.getHarvestTime()).append("\n");
        result.append("One Time: ").append(cropType.isOneTimeHarvest());
        result.append("Regrowth Time: ").append(cropType.getRegrowthTime()).append("\n");
        result.append("Base Sell Price: ").append(cropType.getBaseSellPrice()).append("\n");
        result.append("Is Edible: ").append(cropType.isEatable()).append("\n");
        result.append("Base Energy: ").append(cropType.getEnergy()).append("\n");
        result.append("Base Health: ").append(cropType.getEnergy()/2).append("\n");
        result.append("Season: ").append(cropType.getSource().getSeason()).append("\n");
        result.append("Can Become Giant: ").append(cropType.canBecomeGiant()).append("\n");
        return result.toString();
    }

    public String toString2() {
        StringBuilder result = new StringBuilder();
        int dayLeft = -currentStageLevel;
        for(int i = currentStage; i < stages.size(); i++){
            dayLeft += stages.get(i);
        }
        result.append("Name: ").append(getName()).append("\n");
        result.append("Day Left: ").append(dayLeft).append("\n");
        result.append("Current Stage: ").append(currentStage).append("\n");
        result.append("Watered Today: ").append(isWateredToday).append("\n");
        //TODO Add Quality
        result.append("Quality: ").append("\n");
        result.append("Fertilized Today: ").append(isFertilizedToday).append("\n");
        return result.toString();
    }
}
