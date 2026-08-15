package com.CEliconValley.common;

import com.CEliconValley.models.foragings.Nature.Tree;
import com.CEliconValley.models.foragings.Nature.TreeType;
import dev.morphia.annotations.Embedded;

@Embedded
public class TreeData {
    int hitPoints;
    int treeTypeInt;
    int typeIndex;
    int currentStage;
    int currentStageLevel ;
    boolean isWateredToday ;
    int waterStreak ;
    boolean isFertilizedToday ;
    boolean isProtected;
    boolean isThundered;
    boolean isAttacked;
    int x;
    int y;

    public TreeData() {
    }

    public TreeData(Tree tree) {
        this.hitPoints = tree.getHitPoints();
        this.treeTypeInt = tree.getTreeType().ordinal();
        this.typeIndex = tree.getTypeIndex();
        this.currentStage = tree.getCurrentStage();
        this.currentStageLevel = tree.getCurrentStageLevel();
        this.isWateredToday = tree.isWateredToday();
        this.waterStreak = tree.getWaterStreak();
        this.isFertilizedToday = tree.isFertilizedToday();
        this.isProtected = tree.isProtected();
        this.isThundered = tree.isThundered();
        this.isAttacked = tree.isAttacked();
        this.x = tree.getX();
        this.y = tree.getY();
    }

    public Tree getTree() {
        return new Tree(y, x , waterStreak, typeIndex, TreeType.values()[treeTypeInt],
            isWateredToday, isThundered, isProtected, isFertilizedToday, isAttacked, hitPoints,
            currentStageLevel, currentStage);
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public int getCurrentStageLevel() {
        return currentStageLevel;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public boolean isAttacked() {
        return isAttacked;
    }

    public boolean isFertilizedToday() {
        return isFertilizedToday;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public boolean isThundered() {
        return isThundered;
    }

    public boolean isWateredToday() {
        return isWateredToday;
    }

    public int getTreeTypeInt() {
        return treeTypeInt;
    }

    public int getTypeIndex() {
        return typeIndex;
    }

    public int getWaterStreak() {
        return waterStreak;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
