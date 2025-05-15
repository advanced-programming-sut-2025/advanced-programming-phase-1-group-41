package org.example.models.items;

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
}
