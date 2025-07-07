package com.CEliconValley.models.foragings.Nature;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Finder;

import com.CEliconValley.models.locations.Farm;

public class Tree implements Nature, Obstacle {
    @Override
    public String getChar() {
        if(isThundered){
            return Colors.colorize(53,0,(typeIndex/10) + "" + typeIndex % 10);
        }
        if(treeType.equals(TreeType.Mystic)){
            return Colors.colorize(53,214 - 6 * currentStage,"MY");
        }
        return Colors.colorize(53,214 - 6 * currentStage,(typeIndex/10) + "" + typeIndex % 10);
    }

    @Override
    public String getName() {
        return treeType.getName();
    }

    private int hitPoints;
    private final TreeType treeType;
    private int typeIndex = 0;
    private int currentStage = 0;
    private int currentStageLevel = 0;
    private boolean isWateredToday = false;
    private int waterStreak = 0;
    private boolean isFertilizedToday = false;
    private boolean isProtected = false;
    private boolean isThundered = false;
    private boolean isAttacked = false;
    private final int x;
    private final int y;

    public Tree(int x, int y, Farm farm, TreeType treeType) {
        this.x = x;
        this.y = y;
        this.treeType = treeType;
        for(TreeType type : TreeType.values()){
            if(type.equals(treeType)){
                break;
            }
            typeIndex++;
        }
        hitPoints = 4;
        Cell cell= Finder.findCellByCoordinates(x, y, farm);
        assert cell != null;
        cell.setObjectMap(this);
    }
    public TreeType getTreeType() {
        return treeType;
    }
    public int getTypeIndex() {
        return typeIndex;
    }
    public int getHitPoints() {
        return hitPoints;
    }
    public void decreaseHitPoints() {
        hitPoints--;
    }
    public int getCurrentStage() {
        return currentStage;
    }
    public int getCurrentStageLevel() {return currentStageLevel;}
    public void setCurrentStageLevel(int level){currentStageLevel = level;}
    public boolean isAttacked() {
        return isAttacked;
    }
    public void setAttacked(boolean isAttacked) {
        this.isAttacked = isAttacked;
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
        if(currentStageLevel >= 7){
            currentStage++;
            if(currentStage >= 4){
                currentStage--;
            } else {
//                currentStageLevel = 0;
            }
        }
    }

    public Boolean shouldBeRemoved() {
        return (waterStreak >= 6 && !isWateredToday) || waterStreak >= 7;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public boolean isWateredToday() {
        return isWateredToday;
    }
    public boolean isFertilizedToday() {
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
    public void thunder(){
        isThundered = true;
    }
    public boolean isThundered(){
        return isThundered;
    }
    public void decreaseWaterStreak(){
        waterStreak--;
    }
    public void waterFertilize() {
        isFertilizedToday = true;
    }
    public boolean isProtected() {
        return isProtected;
    }
    public void setIsProtected(boolean isProtected) {this.isProtected = isProtected;}

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int dayLeft = -currentStageLevel;
        for(int i = currentStage; i < 4; i++){
            dayLeft += 7;
        }
        result.append("Name: ").append(getName()).append("\n");
        result.append("Day Left: ").append(dayLeft).append("\n");
        result.append("Current Stage: ").append(currentStage).append("\n");
        result.append("Watered Today: ").append(isWateredToday).append("\n");
        //TODO Add Quality
        result.append("Quality: ").append(getPrice() / 2 + 10).append("\n");
        result.append("Fertilized Today: ").append(isFertilizedToday).append("\n");
        return result.toString();
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
