package org.example.models.buildings.marketplaces.items;

import org.example.models.items.Item;

public enum MarketplaceItems implements Item {
    Hay("Ha","Hay",50),


    Jojacola("Jc","Jojacola", 75),
    Wheatflour("Wf","Wheatflour", 125),
    Sugar("Su","Sugar", 125),
    Rice("Ri","Rice", 250),


    Bouquet("BQ","Bouquet", 1000),
    WeddingRing("WR","WeddingRing", 10000),



    TroutSoup(new Troutsoup()),

    ;


    private String name;
    private double price;
    private String ch;

    MarketplaceItems(String ch, String name, double price) {
        this.name = name;
        this.ch = ch;
        this.price = price;
    }

    MarketplaceItems(Item item) {
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

    public static Item parseItem(String itemName) {
        for (MarketplaceItems value : MarketplaceItems.values()) {
            if(value.name.equalsIgnoreCase(itemName)){
                return value;
            }
        }
        return null;
    }
}
