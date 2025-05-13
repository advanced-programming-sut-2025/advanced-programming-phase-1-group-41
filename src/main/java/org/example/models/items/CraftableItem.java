package org.example.models.items;

import org.example.models.foragings.Nature.MineralType;

public enum CraftableItem implements Item{
    CopperBar("cb","CopperBar",10* MineralType.CopperOre.getPrice())

    ;

    String name;
    String ch;
    double price;

    CraftableItem(String ch, String name, double price) {
        this.ch = ch;
        this.name = name;
        this.price = price;
    }

    CraftableItem(Item item) {
        this.name = item.getName();
        this.price = item.getPrice();
        this.ch = item.getChar();
    }

    @Override
    public String getChar() {
        return this.ch;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getPrice() {
        return this.price;
    }
}
