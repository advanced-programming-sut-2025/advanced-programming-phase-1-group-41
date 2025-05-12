package org.example.models.foragings.Nature;

import org.example.models.*;
import org.example.models.locations.Farm;

public class Tree implements Nature, Obstacle {
    @Override
    public String getChar() {
        return Colors.colorize(3,15 - currentStage,(typeIndex/10) + "" + typeIndex % 10);
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
    private Boolean isWateredToday = false;
    private int waterStreak = 0;
    private Boolean isFertilizedToday = false;
    private int x;
    private int y;

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
            }
            currentStageLevel = 0;
        }
    }

    public Boolean shouldBeRemoved() {
        return (waterStreak >= 5 && !isWateredToday) || waterStreak >= 6;
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
    public void decreaseWaterStreak(){
        waterStreak--;
    }
    public void waterFertilize() {
        isFertilizedToday = true;
    }
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
        result.append("Quality: ").append("\n");
        result.append("Fertilized Today: ").append(isFertilizedToday).append("\n");
        return result.toString();
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
