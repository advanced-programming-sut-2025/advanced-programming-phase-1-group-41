package com.CEliconValley.common;

import com.CEliconValley.models.foragings.Nature.Tree;
import com.CEliconValley.models.foragings.Nature.TreeType;

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
}
