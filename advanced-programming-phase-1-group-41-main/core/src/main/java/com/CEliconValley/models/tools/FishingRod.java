package com.CEliconValley.models.tools;

public class FishingRod implements Tool {
    private FishingRodLevel level;
    public FishingRod() {
        this.level = FishingRodLevel.Training;
    }

    public FishingRod(FishingRodLevel level) {
        this.level = level;
    }

    public FishingRodLevel getLevel() {
        return level;
    }


    @Override
    public String getChar() {
        return "FR";
    }

    @Override
    public String getName() {
        return level.getName();
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
