package org.example.models.buildings.marketplaces.items;

import org.example.models.foragings.Nature.Mineral;
import org.example.models.foragings.Nature.MineralType;
import org.example.models.items.Item;
import org.example.models.tools.MilkPale;
import org.example.models.tools.Shear;

public enum RanchItems implements Item {
    Hay(MarketplaceItems.Hay),
    MilkPale(new MilkPale(), 1000),
    Shear(new Shear(), 1000),
    ;

    static {
        MarketplaceItems.values();
    }

    private String name;
    private double price;
    private String ch;

    RanchItems(Item item){
        this.name = item.getName();
        this.price = item.getPrice();
        this.ch = item.getChar();
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
