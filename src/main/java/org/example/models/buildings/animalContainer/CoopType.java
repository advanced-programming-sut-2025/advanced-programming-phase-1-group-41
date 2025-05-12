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


    public static CoopType parseCoop(String name) {
        return switch (name) {
            case "normal" -> Normal;
            case "big" -> Big;
            case "deluxe" -> Deluxe;
            default -> null;
        };
    }
}
