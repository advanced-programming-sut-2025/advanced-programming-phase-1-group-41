package org.example.models.foragings;

import org.example.models.*;
import org.example.models.items.Item;
import org.example.models.locations.Farm;

import java.util.ArrayList;
import java.util.Arrays;

public class Crop implements Item {
    @Override
    public String getChar() {
        return Colors.colorize(53,4 - currentStage,typeIndex/10 + "" + typeIndex%10);
    }

    @Override
    public String getName() {
        return cropType.getName();
    }

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


    public void increaseStage() {
        if(!isWateredToday){
            waterStreak++;
        } else {
            waterStreak = 0;
        }
        currentStageLevel++;
        if(currentStageLevel >= stages.get(currentStage)){
            currentStage++;
            if(currentStage >= stages.size()){
                currentStage--;
            }
            currentStageLevel = 0;
        }
    }

    public Boolean shouldBeRemoved() {
        return (waterStreak >= 1 && !isWateredToday) || waterStreak >= 2
                || (!Arrays.asList(cropType.getGrowingSeasons()).contains(App.getGame().getTime().getSeason())
                && !cropType.getGrowingSeasons()[0].equals(Season.Special));
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
    public void water(){
        isWateredToday = true;
    }
    public void fertilize(){
        isFertilizedToday = true;
    }
    public void nextDay(){
        isWateredToday = false;
        isFertilizedToday = false;
    }

    @Override
    public String toString() {
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

    @Override
    public double getPrice() {
        // TODO needs to change ?
        return this.cropType.getBaseSellPrice();
    }
}
