package com.CEliconValley.models.animals;

public enum BarnOrCageSize {
    Normal(4),
    Big(8),
    Deluxe(12);

    private final int capacity;

    BarnOrCageSize(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
