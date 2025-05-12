package org.example.models.foragings;

import org.example.models.Season;
import org.example.models.items.Item;

public enum SeedType implements Item {

    //Trees
    ApricotSapling("ApricotSapling", Season.Spring),
    CherrySapling("CherrySapling", Season.Spring),
    BananaSapling("BananaSapling", Season.Summer),
    MangoSapling("MangoSapling", Season.Summer),
    OrangeSapling("OrangeSapling", Season.Summer),
    PeachSapling("PeachSapling", Season.Summer),
    AppleSapling("AppleSapling", Season.Autumn),
    PomegranateSapling("PomegranateSapling", Season.Autumn),

    //ForagingTrees
    Acorns("Acorns", Season.Special),
    MapleSeeds("MapleSeeds", Season.Special),
    PineCones("PineCones", Season.Special),
    MahoganySeeds("MahoganySeeds", Season.Special),
    MushroomTreeSeeds("MushroomTreeSeeds", Season.Special),
    MysticTreeSeeds("MysticTreeSeeds", Season.Special),

    //Mixed Seeds
    Mixed("MixedSeed", Season.Special),
    Cauliflower("Cauliflower", Season.Spring),
    Parsnip("Parsnip", Season.Spring),
    Potato("Potato", Season.Spring),
    BlueJazz("BlueJazz", Season.Spring),
    Tulip("Tulip", Season.Spring),
    Corn("Corn", Season.Summer),
    HotPepper("HotPepper", Season.Summer),
    Radish("Radish", Season.Summer),
    Wheat("Wheat", Season.Summer),
    Poppy("Poppy", Season.Summer),
    Sunflower("Sunflower", Season.Summer),
    SummerSpangle("SummerSpangle", Season.Summer),
    Artichoke("Artichoke", Season.Autumn),
    Eggplant("Eggplant", Season.Autumn),
    Pumpkin("Pumpkin", Season.Autumn),
    FairyRose("FairyRose", Season.Autumn),
    Powdermelon("Powdermelon", Season.Winter),

    //Other Foraging Seeds
    Jazz("JazzSeed", Season.Spring),
    Carrot("CarrotSeed", Season.Spring),
    CoffeeBean("CoffeeBean", Season.Spring),
    Garlic("GarlicSeed", Season.Spring),
    Bean("BeanStarter", Season.Spring),
    Kale("KaleSeed", Season.Spring),
    Rhubarb("RhubarbSeed", Season.Spring),
    Strawberry("StrawberrySeed", Season.Spring),
    Rice("RiceShoot", Season.Spring),
    Blueberry("BlueberrySeed", Season.Summer),
    Hops("HopsStarter", Season.Summer),
    Pepper("PepperSeed", Season.Summer),
    Melon("MelonSeed", Season.Summer),
    RedCabbage("RedCabbageSeed", Season.Summer),
    Starfruit("StarfruitSeed", Season.Summer),
    Spangle("SpangleSeed", Season.Summer),
    SummerSquash("SummerSquashSeed", Season.Summer),
    Tomato("TomatoSeed", Season.Summer),
    Amaranth("AmaranthSeed", Season.Autumn),
    Beet("BeetSeed", Season.Autumn),
    BokChoy("BokChoySeed", Season.Autumn),
    Broccoli("BroccoliSeed", Season.Autumn),
    Cranberry("CranberrySeed", Season.Autumn),
    Fairy("FairySeed", Season.Autumn),
    Grape("GrapeStarter", Season.Autumn),
    Yam("YamSeed", Season.Autumn),
    Rare("RareSeed", Season.Autumn),
    Ancient("AncientSeed", Season.Special);

    ;

    private final String name;
    private final Season season;

    SeedType(String name, Season season) {
        this.name = name;
        this.season = season;
    }

    @Override
    public String getChar() {
        return "ST";
    }

    public String getName() {
        return name;
    }

    public Season getSeason() {
        return season;
    }

    public static SeedType parseSeedType(String seedType) {
        for (SeedType value : SeedType.values()) {
            if(value.getName().equalsIgnoreCase(seedType)) {
                return value;
            }
        }
        return null;
    }

}
