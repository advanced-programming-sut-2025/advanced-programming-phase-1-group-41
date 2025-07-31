package com.CEliconValley.models.animals;

import com.CEliconValley.models.items.Eatable;
import com.CEliconValley.models.items.Item;

public class Fish implements Item, Eatable {
    private final FishType fishType;
    private double quality;

    public Fish(FishType fishType) {
        this.fishType = fishType;
    }

    public FishType getFishType() {
        return fishType;
    }

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.quality = quality;
    }

    @Override
    public double getEnergy() {
        return fishType.getEnergy();
    }

    @Override
    public String getChar() {
        return "ff";
    }

    @Override
    public String getName() {
        return fishType.getName();
    }

    @Override
    public double getPrice() {
        return fishType.getPrice();
    }
    @Override
    public int getID() {
        switch (this.fishType) {
            case Salmon -> { return 50000; }
            case Sardine -> { return 50001; }
            case Shad -> { return 50002; }
            case BlueDiscus -> { return 50003; }
            case MidnightCarp -> { return 50004; }

            case Squid -> { return 50100; }
            case Tuna -> { return 50101; }
            case Perch -> { return 50102; }
            case Flounder -> { return 50103; }
            case Lionfish -> { return 50104; }

            case Herring -> { return 50200; }
            case Ghostfish -> { return 50201; }
            case Tilapia -> { return 50202; }
            case Dorado -> { return 50203; }
            case Sunfish -> { return 50204; }

            case RainbowTrout -> { return 50300; }
            case Legend -> { return 50301; }
            case Glacierfish -> { return 50302; }
            case Angler -> { return 50303; }
            case Crimsonfish -> { return 50304; }
        }
        throw new IllegalStateException("Unknown fish: " + this);
    }

}
