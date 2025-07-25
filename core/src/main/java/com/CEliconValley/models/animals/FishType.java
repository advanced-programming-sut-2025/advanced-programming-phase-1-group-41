package com.CEliconValley.models.animals;

import com.CEliconValley.models.Season;
import com.CEliconValley.models.items.Eatable;
import com.CEliconValley.models.items.Item;

public enum FishType implements Item, Eatable {
    Salmon(75, Season.Autumn, 75),
    Sardine(40, Season.Autumn, 40),
    Shad(60, Season.Autumn, 60),
    BlueDiscus(120, Season.Autumn, 120),
    MidnightCarp(150, Season.Winter, 150),
    Squid(80, Season.Winter, 80),
    Tuna(100, Season.Winter, 100),
    Perch(55, Season.Winter, 55),
    Flounder(100, Season.Spring, 100),
    Lionfish(100, Season.Spring, 100),
    Herring(30, Season.Spring, 30),
    Ghostfish(45, Season.Spring, 45),
    Tilapia(75, Season.Summer, 75),
    Dorado(100, Season.Summer, 100),
    Sunfish(30, Season.Summer, 30),
    RainbowTrout(65, Season.Summer, 65),

    // Legendary Fishes:
    Legend(5000, Season.Spring, 5000),
    Glacierfish(1000, Season.Winter, 1000),
    Angler(900, Season.Autumn, 900),
    Crimsonfish(1500, Season.Summer, 1500),
    ;

    private final int price;
    private final Season season;
    private final double energy;
    FishType(int price, Season season, double energy) {
        this.price = price;
        this.season = season;
        this.energy = energy;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public Season getSeason() {return season;}


    public static Item parseFish(String name) {
        return switch(name){
            case "Salmon" -> Salmon;
            case "Sardine" -> Sardine;
            case "Shad" -> Shad;
            case "BlueDiscus " -> BlueDiscus;
            case "MidnightCarp" -> MidnightCarp;
            case "Squid" -> Squid;
            case "Tuna" -> Tuna;
            case "Perch" -> Perch;
            case "Flounder" -> Flounder;
            case "Lionfish" -> Lionfish;
            case "Herring" -> Herring;
            case "Ghostfish" -> Ghostfish;
            case "Tilapia" -> Tilapia;
            case "Dorado" -> Dorado;
            case "Sunfish" -> Sunfish;
            case "RainbowTrout" -> RainbowTrout;
            case "Legend" -> Legend;
            case "Glacierfish" -> Glacierfish;
            case "Angler" -> Angler;
            case "Crimsonfish" -> Crimsonfish;
            default -> null;
        };
    }

    public double getEnergy() {
        return energy;
    }

    @Override
    public String getChar() {
        return "Fi";
    }

    @Override
    public String getName() {
        return this.name();
    }


}
