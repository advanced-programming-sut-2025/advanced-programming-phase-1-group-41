package org.example.models.foragings;

import java.util.ArrayList;

public class Crop implements Foraging {
    private final CropType cropType;
    private final ArrayList<Integer> stages;
    private int currentStage;

    public Crop(CropType cropType) {
        this.cropType = cropType;
        stages = cropType.getStages();
        currentStage = 0;
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
        return "CC";
    }

    @Override
    public String getName() {
        return cropType.getName();
    }

    public void increaseStage() {
        currentStage++;
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
}
