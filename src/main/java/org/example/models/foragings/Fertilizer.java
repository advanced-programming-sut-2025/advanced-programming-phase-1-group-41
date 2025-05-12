package org.example.models.foragings;

import org.example.models.items.Item;

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

    public FertilizerType getFertilizerType() {
        return fertilizerType;
    }

    @Override
    public double getPrice() {
        return fertilizerType.getPrice();
    }
}
