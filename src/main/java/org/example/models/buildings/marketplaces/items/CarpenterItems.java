package org.example.models.buildings.marketplaces.items;

import org.example.models.buildings.ShippingBin;
import org.example.models.buildings.Well;
import org.example.models.foragings.Nature.Rock;
import org.example.models.foragings.Nature.Wood;
import org.example.models.items.Item;
import org.example.models.tools.MilkPale;
import org.example.models.tools.Shear;

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
