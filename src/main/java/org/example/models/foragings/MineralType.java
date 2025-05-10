package org.example.models.foragings;

public enum MineralType implements Foraging{
    // TODO
    ;

    private String name;
    private int price;

    MineralType(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
