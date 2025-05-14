package org.example.models.buildings.marketplaces.items;

import org.example.models.foragings.Nature.Mineral;
import org.example.models.foragings.Nature.MineralType;
import org.example.models.items.Item;
import org.example.models.tools.MilkPale;
import org.example.models.tools.Shear;

public enum RanchItems implements Item {
    Hay("Ha","Hay", 75),
    MilkPale(new MilkPale(), 1000),
    Shear(new Shear(), 1000),
    ;

    private String name;
    private double price;
    private String ch;

    RanchItems(String ch, String name, double price) {
        this.ch = ch;
        this.name = name;
        this.price = price;
    }


    RanchItems(Item item, double price) {
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
