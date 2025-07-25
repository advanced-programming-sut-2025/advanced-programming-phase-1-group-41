package com.CEliconValley.models.buildings.animalContainer;

public enum BarnType {
    Normal(4),
    Big(8),
    Deluxe(12);

    private final int capacity;

    BarnType(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }



    public static BarnType parseBarn(String name){
        return switch(name){
            case "normal" -> Normal;
            case "big" -> Big;
            case "deluxe" -> Deluxe;
            default -> null;
        };
    }
}
