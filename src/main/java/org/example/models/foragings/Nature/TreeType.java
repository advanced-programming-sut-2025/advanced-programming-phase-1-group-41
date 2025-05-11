package org.example.models.foragings.Nature;

import org.example.models.Season;
import org.example.models.foragings.FruitType;
import org.example.models.foragings.SeedType;

import java.util.ArrayList;

public enum TreeType {
    Apricot("ApricotTree", SeedType.ApricotSapling, 28, FruitType.Apricot, 1, Season.Spring),
    Cherry("CherryTree", SeedType.CherrySapling, 28, FruitType.Cherry, 1, Season.Spring),
    Banana("BananaTree", SeedType.BananaSapling, 28, FruitType.Banana, 1, Season.Summer),
    Mango("MangoTree", SeedType.MangoSapling, 28, FruitType.Mango, 1, Season.Summer),
    Orange("OrangeTree", SeedType.OrangeSapling, 28, FruitType.Orange, 1, Season.Summer),
    Peach("PeachTree", SeedType.PeachSapling, 28, FruitType.Peach, 1, Season.Summer),
    Apple("AppleTree", SeedType.AppleSapling, 28, FruitType.Apple, 1, Season.Autumn),
    Pomegranate("PomegranateTree", SeedType.PomegranateSapling, 28, FruitType.Pomegranate, 1, Season.Autumn),
    Oak("OakTree", SeedType.Acorns, 28, FruitType.OakResin, 7, Season.Special),
    Maple("MapleTree", SeedType.MapleSeeds, 28, FruitType.MapleSyrup, 9, Season.Special),
    Pine("PineTree", SeedType.PineCones, 28, FruitType.PineTar, 5, Season.Special),
    Mahogany("MahoganyTree", SeedType.MahoganySeeds, 28, FruitType.Sap, 1, Season.Special),
    Mushroom("MushroomTree", SeedType.MushroomTreeSeeds, 28, FruitType.CommonMushroom, 1, Season.Special),
    Mystic("MysticTree", SeedType.MysticTreeSeeds, 28, FruitType.MysticSyrup, 7, Season.Special);

    private final String name;
    private final SeedType source;
    private final int totalHarvestTime;
    private final FruitType fruitType;
    private final int fruitHarvestCycle;
    private final Season season;

    TreeType(String name, SeedType source, int totalHarvestTime, FruitType fruitType, int fruitHarvestCycle, Season season) {
        this.name = name;
        this.source = source;
        this.totalHarvestTime = totalHarvestTime;
        this.fruitType = fruitType;
        this.fruitHarvestCycle = fruitHarvestCycle;
        this.season = season;
    }

    public String getName() {
        return name;
    }

    public Season getSeason() {
        return season;
    }

    public FruitType getFruitType() {
        return fruitType;
    }

    public int getFruitHarvestCycle() {
        return fruitHarvestCycle;
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public SeedType getSource() {
        return source;
    }
}
