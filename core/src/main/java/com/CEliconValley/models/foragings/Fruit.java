package com.CEliconValley.models.foragings;

import com.CEliconValley.models.items.Eatable;
import com.CEliconValley.models.items.Item;

public class Fruit implements Item, Eatable {
    private final FruitType fruitType;

    public Fruit(FruitType fruitType) {
        this.fruitType = fruitType;
    }

    @Override
    public String getChar() {
        return "FF";
    }

    @Override
    public String getName() {
        return fruitType.getName();
    }

    public FruitType getType() {
        return fruitType;
    }

    @Override
    public double getEnergy() {
        return this.fruitType.getEnergy();
    }

    @Override
    public double getPrice() {
        // maybe needs a change?
        return fruitType.getBaseSellPrice();
    }
    @Override
    public int getID() {
        switch (this.fruitType) {
            case Apricot -> { return 10305; }
            case Cherry -> { return 10306; }
            case Banana -> { return 10307; }
            case Mango -> { return 10308; }
            case Orange -> { return 10309; }
            case Peach -> { return 10310; }
            case Apple -> { return 10311; }
            case Pomegranate -> { return 10400; }
            case OakResin -> { return 10401; }
            case MapleSyrup -> { return 10402; }
            case PineTar -> { return 10403; }
            case Sap -> { return 10404; }
            case CommonMushroom -> { return 10405; }
            case MysticSyrup -> { return 10406; }
        }
        throw new IllegalStateException("Unknown fruitType: " + this.fruitType);
    }

}
