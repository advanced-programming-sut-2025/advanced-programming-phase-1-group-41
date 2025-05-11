package org.example.models.animals;

import org.example.models.Player;
import org.example.models.items.Item;

public class Fish implements Item {
    private final FishType fishType;

    public Fish(FishType fishType) {
        this.fishType = fishType;
    }

    public FishType getFishType() {
        return fishType;
    }

    @Override
    public String getChar() {
        return "ff";
    }

    @Override
    public String getName() {
        return fishType.getName();
    }
}
