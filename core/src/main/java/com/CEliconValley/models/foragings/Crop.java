package com.CEliconValley.models.foragings;

import com.CEliconValley.models.*;

import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.locations.Farm;

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



    public Crop(boolean canRegrow, CropType cropType, int currentStage,
                int currentStageLevel, boolean isFertilizedToday,
                boolean isGiantCrop, boolean isProtected, boolean isWateredToday,
                int regrowthTime, ArrayList<Integer> stages, int typeIndex,
                int waterStreak, int x, int y) {
        this.canRegrow = canRegrow;
        this.cropType = cropType;
        this.currentStage = currentStage;
        this.currentStageLevel = currentStageLevel;
        this.isFertilizedToday = isFertilizedToday;
        this.isGiantCrop = isGiantCrop;
        this.isProtected = isProtected;
        this.isWateredToday = isWateredToday;
        this.regrowthTime = regrowthTime;
        this.stages = new ArrayList<>(stages);
        this.typeIndex = typeIndex;
        this.waterStreak = waterStreak;
        this.x = x;
        this.y = y;
    }

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
        currentStage = 3;
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

        for(CropType type : CropType.values()){
            if(type.equals(cropType)){
                break;
            }
            typeIndex++;
        }

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

    public boolean isCanRegrow() {
        return canRegrow;
    }

    public boolean isFertilizedToday() {
        return isFertilizedToday;
    }

    public int getTypeIndex() {
        return typeIndex;
    }

    public int getWaterStreak() {
        return waterStreak;
    }

    public int getID() {
        switch (this.cropType) {
            case BlueJazz -> { return 10000; }
            case Carrot -> { return 10001; }
            case Cauliflower -> { return 10002; }
            case CoffeeBean -> { return 10003; }
            case Garlic -> { return 10004; }
            case GreenBean -> { return 10005; }
            case Kale -> { return 10006; }
            case Parsnip -> { return 10007; }
            case Potato -> { return 10008; }
            case Rhubarb -> { return 10009; }
            case Strawberry -> { return 10010; }
            case Tulip -> { return 10011; }

            case Rice -> { return 10100; }
            case Blueberry -> { return 10101; }
            case Corn -> { return 10102; }
            case Hops -> { return 10103; }
            case HotPepper -> { return 10104; }
            case Melon -> { return 10105; }
            case Poppy -> { return 10106; }
            case Radish -> { return 10107; }
            case RedCabbage -> { return 10108; }
            case Starfruit -> { return 10109; }
            case SummerSpangle -> { return 10110; }
            case SummerSquash -> { return 10111; }

            case Sunflower -> { return 10200; }
            case Tomato -> { return 10201; }
            case Wheat -> { return 10202; }
            case Amaranth -> { return 10203; }
            case Artichoke -> { return 10204; }
            case Beet -> { return 10205; }
            case BokChoy -> { return 10206; }
            case Broccoli -> { return 10207; }
            case Cranberries -> { return 10208; }
            case Eggplant -> { return 10209; }
            case FairyRose -> { return 10210; }
            case Grape -> { return 10211; }

            case Pumpkin -> { return 10300; }
            case Yam -> { return 10301; }
            case SweetGemBerry -> { return 10302; }
            case Powdermelon -> { return 10303; }
            case AncientFruit -> { return 10304; }
        }

        throw new IllegalStateException("Unknown cropType: " + this.cropType);
    }



}
