package com.CEliconValley.models.buildings.marketplaces.items;

import com.CEliconValley.models.buildings.ShippingBin;
import com.CEliconValley.models.buildings.Well;
import com.CEliconValley.models.foragings.Nature.Rock;
import com.CEliconValley.models.foragings.Nature.Wood;
import com.CEliconValley.models.items.Item;

public enum CarpenterItems implements Item{
    Wood(new Wood(), 10),
    Rock(new Rock(), 20),
    Well(new Well(), 1000),
    ShippingBin(new ShippingBin(), 250),
    ;

    static {
        MarketplaceItems.values();
    }

    private String name;
    private double price;
    private String ch;

    CarpenterItems(Item item){
        this.name = item.getName();
        this.price = item.getPrice();
        this.ch = item.getChar();
    }

    CarpenterItems(Item item, double price) {
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
