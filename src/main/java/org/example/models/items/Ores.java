package org.example.models.items;

public enum Ores {
    Coal(150),
    Copper(75),
    Iron(150),
    Gold(400)
    ;

    private int sellPrice;

    Ores(int SellPrice) {
        this.sellPrice = SellPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }
}
