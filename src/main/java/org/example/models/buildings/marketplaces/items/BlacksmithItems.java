package org.example.models.buildings.marketplaces.items;

import org.example.models.foragings.Nature.Mineral;
import org.example.models.foragings.Nature.MineralType;
import org.example.models.items.Item;

public enum BlacksmithItems implements Item {
    CopperOre(new Mineral(MineralType.CopperOre), 75),
    IronOre(new Mineral(MineralType.IronOre), 150),
    Coal(new Mineral(MineralType.Coal), 150),
    GoldOre(new Mineral(MineralType.GoldOre), 400),
    ;

    private String name;
    private double price;
    private String ch;

    BlacksmithItems(String ch, String name, double price) {
        this.ch = ch;
        this.name = name;
        this.price = price;
    }


    BlacksmithItems(Item item, double price) {
        this.ch = item.getChar();
        this.name = item.getName();
        this.price = price;
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
