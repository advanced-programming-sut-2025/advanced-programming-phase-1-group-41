package org.example.models.buildings.animalContainer;

public enum CoopType {
    Normal(4),
    Big(8),
    Deluxe(12);

    private final int capacity;

    CoopType(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
