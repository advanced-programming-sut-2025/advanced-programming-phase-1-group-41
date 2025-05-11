package org.example.models.animals;

import org.example.models.Player;
import org.example.models.items.Item;

public class Fish extends Animal implements Item {
    private final FishType fishType;

    public Fish(Player owner, FishType fishType) {
        this.fishType = fishType;
        super(owner);
        super.breed = Breed.Barner;
    }
    @Override
    public void doTheFuckingJob() {

    }
    public FishType getFishType() {
        return fishType;
    }

    @Override
    public String getName() {
        return "";
    }
}
