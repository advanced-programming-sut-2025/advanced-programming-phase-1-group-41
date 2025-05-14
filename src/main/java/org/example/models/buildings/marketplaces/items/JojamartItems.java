package org.example.models.buildings.marketplaces.items;

import org.example.models.foragings.Fertilizer;
import org.example.models.foragings.FertilizerType;
import org.example.models.foragings.Nature.Grass;
import org.example.models.foragings.Seed;
import org.example.models.foragings.SeedType;
import org.example.models.items.Item;

public enum JojamartItems implements Item {
    Jojacola(MarketplaceItems.Jojacola , 75),
    AncientSeed(new Seed(SeedType.AncientSeed), 500),
    GrassStarter(new Fertilizer(FertilizerType.GrassStarter), 125),
    Sugar(MarketplaceItems.Sugar , 125),
    Wheatflour(MarketplaceItems.Wheatflour , 125),
    Rice(MarketplaceItems.Rice , 250),

    // spring

    ParsnipSeed(new Seed(SeedType.ParsnipSeed), 25),
    BeanStarter(new Seed(SeedType.Bean), 75),
    CauliflowerSeed(new Seed(SeedType.CauliflowerSeed), 100),
    PotatoSeed(new Seed(SeedType.PotatoSeed), 62),
    StrawberrySeed(new Seed(SeedType.StrawberrySeed), 100),
    TulipBulb(new Seed(SeedType.TulipSeed), 25),
    KaleSeed(new Seed(SeedType.KaleSeed), 87),
    CarrotSeed(new Seed(SeedType.CarrotSeed), 5),
    RhubarbSeed(new Seed(SeedType.RhubarbSeed), 100),
    JazzSeed(new Seed(SeedType.Jazz), 37),

    // summer

    TomatoSeed(new Seed(SeedType.TomatoSeed), 62),
    PepperSeed(new Seed(SeedType.Pepper), 50),
    WheatSeed(new Seed(SeedType.WheatSeed), 12),
    SummerSquashSeed(new Seed(SeedType.SummerSquashSeed), 10),
    RadishSeed(new Seed(SeedType.RadishSeed), 50),
    MelonSeed(new Seed(SeedType.MelonSeed), 100),
    HopsStarter(new Seed(SeedType.HopsStarter), 75),
    PoppySeed(new Seed(SeedType.PoppySeed), 125),
    SpangleSeed(new Seed(SeedType.Spangle), 62),
    StarfruitSeed(new Seed(SeedType.StarfruitSeed), 400),


    // spring and summer

    CoffeeBean(new Seed(SeedType.CoffeeBeanSeed), 200),

    // Fall

    Corn(new Seed(SeedType.CornSeed), 187),
    Eggplant(new Seed(SeedType.EggplantSeed), 25),
    Pumpkin(new Seed(SeedType.PumpkinSeed), 125),
    Broccoli(new Seed(SeedType.BroccoliSeed), 15),
    Amaranth(new Seed(SeedType.AmaranthSeed), 87),
    GrapeStarter(new Seed(SeedType.GrapeStarter), 75),
    Beet(new Seed(SeedType.BeetSeed), 20),
    Yam(new Seed(SeedType.Yam), 75),
    BokChoy(new Seed(SeedType.BokChoySeed), 62),
    Cranberry(new Seed(SeedType.CranberrySeed), 300),
    Fairy(new Seed(SeedType.Fairy), 250),
    Rare(new Seed(SeedType.RareSeed), 1000),
    Wheat(new Seed(SeedType.WheatSeed), 12),

    // spring and fall

    SunflowerSeed(new Seed(SeedType.SunflowerSeed), 125),

    // winter
    PowdermelonSeeds(new Seed(SeedType.PowdermelonSeed), 20)

    ;

    private String name;
    private double price;
    private String ch;

    JojamartItems(Item item){
        this.name = item.getName();
        this.price = item.getPrice();
        this.ch = item.getChar();
    }



    JojamartItems(Item item, double price) {
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
