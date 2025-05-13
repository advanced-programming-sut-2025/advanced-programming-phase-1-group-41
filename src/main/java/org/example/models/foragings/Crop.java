package org.example.models.foragings;

import org.example.models.*;
import org.example.models.items.Item;
import org.example.models.locations.Farm;

import java.util.ArrayList;
import java.util.Arrays;

public class Crop implements Item {
    @Override
    public String getChar() {
        if(isGiantCrop){
            return Colors.colorize(53,21 + 6 * currentStage,typeIndex/10 + "" + typeIndex%10);
        }
        return Colors.colorize(53,58 + 6 * currentStage,typeIndex/10 + "" + typeIndex%10);
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
    private boolean isWateredToday = false;
    private boolean isFertilizedToday = false;
    private boolean isGiantCrop = false;
    private int regrowthTime = 0;
    private boolean canRegrow = false;
    private boolean isProtected = false;
    private int waterStreak = 0;
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
        if(!cropType.isOneTimeHarvest()){
            regrowthTime = cropType.getRegrowthTime();
            canRegrow = true;
        }
        Cell cell = Finder.findCellByCoordinates(x, y, farm);
        assert cell != null;
        cell.setObjectMap(this);
    }
    public Crop(CropType cropType) {
        this.cropType = cropType;
        stages = cropType.getStages();
        currentStage = 0;
    }
    public Crop(int x, int y, Farm farm, CropType cropType, int currentStage, int currentStageLevel) {
        this.x = x;
        this.y = y;
        this.cropType = cropType;
        this.currentStage = currentStage;
        this.currentStageLevel = currentStageLevel;

        stages = cropType.getStages();
        isWateredToday = true;
        isGiantCrop = true;
        if(!cropType.isOneTimeHarvest()){
            regrowthTime = cropType.getRegrowthTime();
            canRegrow = true;
        }
        Cell cell1 = Finder.findCellByCoordinates(x, y, farm);
        assert cell1 != null;
        cell1.setObjectMap(this);
        Cell cell2 = Finder.findCellByCoordinates(x + 1, y, farm);
        assert cell2 != null;
        cell2.setObjectMap(this);
        Cell cell3 = Finder.findCellByCoordinates(x + 1, y + 1, farm);
        assert cell3 != null;
        cell3.setObjectMap(this);
        Cell cell4 = Finder.findCellByCoordinates(x, y + 1, farm);
        assert cell4 != null;
        cell4.setObjectMap(this);
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
    public int getCurrentStageLevel() {
        return currentStageLevel;
    }


    public void increaseStage() {
        if(!isFertilizedToday){
            if(!isWateredToday){
                waterStreak++;
            } else {
                waterStreak = 0;
            }
        } else {
            isFertilizedToday = false;
        }
        currentStageLevel++;
        if(currentStageLevel >= stages.get(currentStage)){
            currentStage++;
            if(currentStage >= stages.size()){
                currentStage--;
            } else{
                currentStageLevel = 0;
            }
        }
    }

    public void setCurrentStageLevel(int currentStageLevel){
        this.currentStageLevel = currentStageLevel;
    }
    public boolean shouldBeRemoved(Farm farm) {
        return (waterStreak >= 2 && !isWateredToday) || waterStreak >= 3
                || ((!Arrays.asList(cropType.getGrowingSeasons()).contains(App.getGame().getTime().getSeason())
                && !cropType.getGrowingSeasons()[0].equals(Season.Special)) && !farm.getGreenhouse().isGreenHouse(x, y));
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
    public void decreaseWaterStreak(){
        waterStreak--;
    }
    public void waterFertilize() {
        isFertilizedToday = true;
    }
    public Boolean isGiantCrop() {return isGiantCrop;}
    public int getRegrowthTime(){return regrowthTime;}
    public void setCanRegrow(boolean canRegrow){
        this.canRegrow = canRegrow;
    }
    public boolean getCanRegrow(){
        return canRegrow;
    }
    public boolean isProtected() {return isProtected;}
    public void setIsProtected(boolean isProtected) {this.isProtected = isProtected;}

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
