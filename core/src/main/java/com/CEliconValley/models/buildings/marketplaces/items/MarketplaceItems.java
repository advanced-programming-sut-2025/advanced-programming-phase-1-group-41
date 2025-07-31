package com.CEliconValley.models.buildings.marketplaces.items;

import com.CEliconValley.models.items.Item;

public enum MarketplaceItems implements Item {
    Jojacola("Jc","Jojacola", 75),
    Hay("Ha","Hay",50),


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
    public int getID() {
        switch (this) {
            case Jojacola -> { return 11002; }
            case Hay -> { return 11003; }
            case Wheatflour -> { return 20304; }
            case Sugar -> { return 20305; }
            case Rice -> { return 10100; }
            case Bouquet -> { return 20404; }
            case WeddingRing -> { return 20403; }
            case TroutSoup -> { return 10410; }
        }
        throw new IllegalStateException("Unknown item: " + this);
    }


}
