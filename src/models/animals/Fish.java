package models.animals;

import models.Season;
import models.items.Item;

public enum Fish implements Item {
    Salmon(75, Season.Autumn),
    Sardine(40, Season.Autumn),
    Shad(60, Season.Autumn),
    BlueDiscus(120, Season.Autumn),
    MidnightCarp(150, Season.Winter),
    Squid(80, Season.Winter),
    Tuna(100, Season.Winter),
    Perch(55, Season.Winter),
    Flounder(100, Season.Spring),
    Lionfish(100, Season.Spring),
    Herring(30, Season.Spring),
    Ghostfish(45, Season.Spring),
    Tilapia(75, Season.Summer),
    Dorado(100, Season.Summer),
    Sunfish(30, Season.Summer),
    RainbowTrout(65, Season.Summer),

    //Legendary Fishes:
    Legend(5000, Season.Spring),
    Glacierfish(1000, Season.Winter),
    Angler(900, Season.Autumn),
    Crimsonfish(1500, Season.Summer),
    ;

    private final int price;
    private final Season season;

    Fish(int price, Season season) {
        this.price = price;
        this.season = season;
    }

    public int getPrice() {
        return price;
    }

    public Season getSeason() {return season;}
}
