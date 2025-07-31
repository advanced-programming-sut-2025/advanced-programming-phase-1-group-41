package com.CEliconValley.models.items;

public enum Backpack {
    Default(12, "DefaultBackpack"),
    Large(24, "LargeBackpack"),
    Deluxe(1000, "DeluxeBackpack"),
    ;

    private int size;
    private String name;

    Backpack(int size, String name) {
        this.name = name;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public static Backpack parseBackpack(String backpack) {
        for (Backpack value : Backpack.values()) {
            if(value.getName().equalsIgnoreCase(backpack)){
                return value;
            }
        }
        return null;
    }
    public int getID() {
        switch (this) {
            case Default:
                return 30303;
            case  Large:
                return 30304;
            case Deluxe:
                return 30305;

        }
        return -1;
    }
}
