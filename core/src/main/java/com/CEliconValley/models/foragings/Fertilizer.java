package com.CEliconValley.models.foragings;

import com.CEliconValley.models.items.Item;

public class Fertilizer implements Item {
    private final FertilizerType fertilizerType;

    public Fertilizer(FertilizerType fertilizerType) {
        this.fertilizerType = fertilizerType;
    }

    @Override
    public String getChar() {
        return "SS";
    }

    @Override
    public String getName() {
        return fertilizerType.getName();
    }

    @Override
    public int getID() {
        switch (this.fertilizerType) {
            case BasicRetainingSoil -> { return 40000; }
            case QualityRetainingSoil -> { return 40001; }
            case DeluxeRetainingSoil -> { return 40002; }
            case GrassStarter -> { return 40100; }
            case PlantGrow -> { return 40101; }
        }
        throw new IllegalStateException("Unknown item: " + this);
    }


    public FertilizerType getFertilizerType() {
        return fertilizerType;
    }

    @Override
    public double getPrice() {
        return fertilizerType.getPrice();
    }

}
