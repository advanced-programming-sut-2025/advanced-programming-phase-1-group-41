package org.example.models.items;

public enum Backpack {
    Default(12),
    Big(24),
    Deluxe(1000),;

    private int size;

    Backpack(int size) {
        this.size = size;
    }
    public int getSize() {
        return size;
    }

}
