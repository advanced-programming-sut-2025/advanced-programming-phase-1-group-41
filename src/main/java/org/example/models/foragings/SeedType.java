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
    CauliflowerSeed("CauliflowerSeed", Season.Spring),
    ParsnipSeed("ParsnipSeed", Season.Spring),
    PotatoSeed("PotatoSeed", Season.Spring),
    BlueJazzSeed("BlueJazzSeed", Season.Spring),
    TulipSeed("TulipSeed", Season.Spring),
    CornSeed("CornSeed", Season.Summer),
    HotPepperSeed("HotPepperSeed", Season.Summer),
    RadishSeed("RadishSeed", Season.Summer),
    WheatSeed("WheatSeed", Season.Summer),
    PoppySeed("PoppySeed", Season.Summer),
    SunflowerSeed("SunflowerSeed", Season.Summer),
    SummerSpangleSeed("SummerSpangleSeed", Season.Summer),
    ArtichokeSeed("ArtichokeSeed", Season.Autumn),
    EggplantSeed("EggplantSeed", Season.Autumn),
    PumpkinSeed("PumpkinSeed", Season.Autumn),
    FairyRoseSeed("FairyRoseSeed", Season.Autumn),
    PowdermelonSeed("PowdermelonSeed", Season.Winter),

    //Other Foraging Seeds
    Jazz("JazzSeed", Season.Spring),
    CarrotSeed("CarrotSeed", Season.Spring),
    CoffeeBeanSeed("CoffeeBeanSeed", Season.Spring),
    GarlicSeed("GarlicSeed", Season.Spring),
    Bean("BeanStarter", Season.Spring),
    KaleSeed("KaleSeed", Season.Spring),
    RhubarbSeed("RhubarbSeed", Season.Spring),
    StrawberrySeed("StrawberrySeed", Season.Spring),
    Rice("RiceShoot", Season.Spring),
    BlueberrySeed("BlueberrySeed", Season.Summer),
    HopsStarter("HopsStarter", Season.Summer),
    Pepper("PepperSeed", Season.Summer),
    MelonSeed("MelonSeed", Season.Summer),
    RedCabbageSeed("RedCabbageSeed", Season.Summer),
    StarfruitSeed("StarfruitSeed", Season.Summer),
    Spangle("SpangleSeed", Season.Summer),
    SummerSquashSeed("SummerSquashSeed", Season.Summer),
    TomatoSeed("TomatoSeed", Season.Summer),
    AmaranthSeed("AmaranthSeed", Season.Autumn),
    BeetSeed("BeetSeed", Season.Autumn),
    BokChoySeed("BokChoySeed", Season.Autumn),
    BroccoliSeed("BroccoliSeed", Season.Autumn),
    CranberrySeed("CranberrySeed", Season.Autumn),
    Fairy("FairySeed", Season.Autumn),
    GrapeStarter("GrapeStarter", Season.Autumn),
    Yam("YamSeed", Season.Autumn),
    RareSeed("RareSeed", Season.Autumn),
    AncientSeed("AncientSeed", Season.Special);

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

    @Override
    public double getPrice() {
        return 0;
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
