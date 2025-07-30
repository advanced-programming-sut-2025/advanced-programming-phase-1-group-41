package com.CEliconValley.common;

import com.CEliconValley.models.foragings.Crop;
import com.CEliconValley.models.foragings.CropType;
import dev.morphia.annotations.Embedded;

import java.util.ArrayList;


@Embedded
public class CropData {
    int cropTypeInt;
    ArrayList<Integer> stages;
    int typeIndex;
    int currentStage;
    int currentStageLevel;
    boolean isWateredToday ;
    boolean isFertilizedToday;
    boolean isGiantCrop;
    int regrowthTime;
    boolean canRegrow ;
    boolean isProtected ;
    int waterStreak ;
    int x;
    int y;

    public CropData (Crop crop) {
        this.cropTypeInt = crop.getCropType().ordinal();
        this.stages = new ArrayList<>(crop.getStages());
        this.typeIndex = crop.getTypeIndex();
        this.currentStage = crop.getCurrentStage();
        this.currentStageLevel = crop.getCurrentStageLevel();
        this.isWateredToday = crop.isWateredToday();
        this.isFertilizedToday = crop.isFertilizedToday();
        this.isGiantCrop = crop.isGiantCrop();
        this.regrowthTime = crop.getRegrowthTime();
        this.canRegrow = crop.isCanRegrow();
        this.isProtected = crop.isProtected();
        this.waterStreak = crop.getWaterStreak();
        this.x = crop.getX();
        this.y = crop.getY();
    }

    public Crop getCrop() {
        return new Crop(canRegrow, CropType.values()[cropTypeInt], currentStage,
            currentStageLevel, isFertilizedToday, isGiantCrop, isProtected, isWateredToday,
            regrowthTime, stages, typeIndex, waterStreak, x, y);
    }
}
