package org.example.models.foragings;

import org.example.models.Season;

public enum ForagingCropType {
    WildHorseradish("Wild Horseradish", 50, 13, Season.Spring),
    FiddleheadFern("Fiddlehead Fern", 90, 25, Season.Summer),
    Grape("Grape", 80, 38, Season.Summer),
    RedMushroom("RedMushroom", 75, -50, Season.Summer),
    SpiceBerry("Spice Berry", 80, 25, Season.Summer),
    SweetPea("Sweet Pea", 50, 0, Season.Summer),
    Blackberry("Blackberry", 25, 25, Season.Autumn),
    Chanterelle("Chanterelle", 160, 75, Season.Autumn),
    Hazelnut("Hazelnut", 40, 38, Season.Autumn),
    PurpleMushroom("PurpleMushroom", 90, 30, Season.Autumn),
    WildPlum("Wild Plum", 80, 25, Season.Autumn),
    Crocus("Crocus", 60, 0, Season.Winter),
    CrystalFruit("Crystal Fruit", 150, 63, Season.Winter),
    Holly("Holly", 80, -37, Season.Winter),
    SnowYam("Snow Yam", 100, 30, Season.Winter),
    WinterRoot("Winter Root", 70, 25, Season.Winter),
    CommonMushroom("CommonMushroom", 40, 38, Season.Special),
    Daffodil("Daffodil", 30, 0, Season.Spring),
    Dandelion("Dandelion", 40, 25, Season.Spring),
    Leek("Leek", 60, 40, Season.Spring),
    Morel("Morel", 150, 20, Season.Spring),
    Salmonberry("Salmonberry", 5, 25, Season.Spring),
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

    public static ForagingCropType foragingCropType(String type) {
        for (ForagingCropType value : ForagingCropType.values()) {
            if(value.getName().equalsIgnoreCase(type)){
                return value;
            }
        }
        return null;
    }
}
