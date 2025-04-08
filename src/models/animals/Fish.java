package models.animals;

import models.items.Item;

public enum Fish implements Item {
    Salmon(kir),
    Sardine(khar),
    // etc
    ;

    private int price;

    Fish(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
