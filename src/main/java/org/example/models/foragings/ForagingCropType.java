package org.example.models.foragings;

import org.example.models.Season;

import java.util.ArrayList;

public enum ForagingCropType {
    WildHorseradish("WildHorseradish", 50, 13, Season.Spring),
    FiddleheadFern("FiddleheadFern", 90, 25, Season.Summer),
    Grape("Grape", 80, 38, Season.Summer),
    RedMushroom("RedMushroom", 75, -50, Season.Summer),
    SpiceBerry("SpiceBerry", 80, 25, Season.Summer),
    SweetPea("SweetPea", 50, 0, Season.Summer),
    Blackberry("Blackberry", 25, 25, Season.Autumn),
    Chanterelle("Chanterelle", 160, 75, Season.Autumn),
    Hazelnut("Hazelnut", 40, 38, Season.Autumn),
    PurpleMushroom("PurpleMushroom", 90, 30, Season.Autumn),
    WildPlum("Wild Plum", 80, 25, Season.Autumn),
    Crocus("Crocus", 60, 0, Season.Winter),
    CrystalFruit("CrystalFruit", 150, 63, Season.Winter),
    Holly("Holly", 80, -37, Season.Winter),
    SnowYam("SnowYam", 100, 30, Season.Winter),
    WinterRoot("WinterRoot", 70, 25, Season.Winter),
    CommonMushroom("CommonMushroom", 40, 38, Season.Special),
    Daffodil("Daffodil", 30, 0, Season.Spring),
    Dandelion("Dandelion", 40, 25, Season.Spring),
    Leek("Leek", 60, 40, Season.Spring),
    Morel("Morel", 150, 20, Season.Spring),
    SalmonBerry("SalmonBerry", 5, 25, Season.Spring),
    SpringOnion("Spring Onion", 8, 13, Season.Spring);

    private final String Name;
    private final int BaseSellPrice, Energy;
    private final Season GrowingSeason;

    ForagingCropType(String name, int baseSellPrice, int energy, Season growingSeason) {
        this.Name = name;
        this.BaseSellPrice = baseSellPrice;
        this.Energy = energy;
        this.GrowingSeason = growingSeason;
    }

    public String getName() { return Name; }
    public int getBaseSellPrice() { return BaseSellPrice; }
    public int getEnergy() { return Energy; }
    public Season getGrowingSeason() { return GrowingSeason; }
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Name: ").append(getName()).append("\n");
        result.append("Base Sell Price: ").append(getBaseSellPrice()).append("\n");
        String res;
        res = String.valueOf(getEnergy());
        if(getEnergy() == -1){
            res = "-";
        }
        result.append("Base Energy: ").append(res).append("\n");
        res = String.valueOf(getEnergy() / 2);
        if(getEnergy() == -1){
            res = "-";
        }
        result.append("Base Health: ").append(res);
        return result.toString();
    }

    public static ForagingCropType parseForagingCropType(String type) {
        for (ForagingCropType value : ForagingCropType.values()) {
            if(value.getName().equalsIgnoreCase(type)){
                return value;
            }
        }
        return null;
    }
}
