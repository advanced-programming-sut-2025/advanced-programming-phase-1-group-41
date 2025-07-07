package com.CEliconValley.models.buildings.marketplaces.items;

import com.CEliconValley.models.foragings.Fertilizer;
import com.CEliconValley.models.foragings.FertilizerType;
import com.CEliconValley.models.foragings.Seed;
import com.CEliconValley.models.foragings.SeedType;
import com.CEliconValley.models.items.Backpack;
import com.CEliconValley.models.items.CraftableItem;
import com.CEliconValley.models.items.CraftingRecipe;
import com.CEliconValley.models.items.Item;

public enum GeneralStoreItems implements Item {
    // permanent


    Rice(MarketplaceItems.Rice, 200),
    Wheatflour(MarketplaceItems.Wheatflour, 100),
    DehydratorRecipe(CraftingRecipe.Dehydrator, 10000),
    GrassStarterRecipe(CraftingRecipe.GrassStarter, 1000),
    Sugar(MarketplaceItems.Sugar, 100),
    Oil(CraftableItem.Oil, 200),
    Vineger(CraftableItem.Vineger, 200),
    DeluxeRetainingSoil(new Fertilizer(FertilizerType.DeluxeRetainingSoil), 150),
    GrassStarter(new Fertilizer(FertilizerType.GrassStarter), 100),
    PlantGrow(new Fertilizer(FertilizerType.PlantGrow), 100),
    AppleSapling(new Seed(SeedType.AppleSapling), 4000),
    ApricotSapling(new Seed(SeedType.ApricotSapling), 2000),
    CherrySapling(new Seed(SeedType.CherrySapling), 3400),
    OrangeSapling(new Seed(SeedType.OrangeSapling), 4000),
    PeachSapling(new Seed(SeedType.PeachSapling), 6000),
    PomegranateSapling(new Seed(SeedType.PomegranateSapling), 6000),
    BasicRetainingSoil(new Fertilizer(FertilizerType.BasicRetainingSoil), 100),
    QualityRetainingSoil(new Fertilizer(FertilizerType.QualityRetainingSoil), 150),

    // backpack

    LargeBackpack(Backpack.Large, 2000),
    DeluxeBackpack(Backpack.Deluxe, 10000),

    // spring

    ParsnipSeed(new Seed(SeedType.ParsnipSeed), 20),
    BeanStarter(new Seed(SeedType.Bean), 60),
    CauliflowerSeed(new Seed(SeedType.CauliflowerSeed), 80),
    PotatoSeed(new Seed(SeedType.PotatoSeed), 50),
    TulipBulb(new Seed(SeedType.TulipSeed), 20),
    KaleSeed(new Seed(SeedType.KaleSeed), 70),
    JazzSeed(new Seed(SeedType.Jazz), 30),
    GarlicSeed(new Seed(SeedType.GarlicSeed), 40),
    RiceShoot(new Seed(SeedType.Rice), 40),

    // summer

    MelonSeed(new Seed(SeedType.MelonSeed), 80),
    TomatoSeed(new Seed(SeedType.TomatoSeed), 50),
    BlueberrySeed(new Seed(SeedType.BlueberrySeed), 80),
    PepperSeed(new Seed(SeedType.Pepper), 40),
    RadishSeed(new Seed(SeedType.RadishSeed), 40),
    PoppySeed(new Seed(SeedType.PoppySeed), 100),
    SpangleSeed(new Seed(SeedType.Spangle), 50),
    HopsStarter(new Seed(SeedType.HopsStarter), 60),
    RedCabbageSeed(new Seed(SeedType.RedCabbageSeed), 100),

    // fall

    EggplantSeed(new Seed(SeedType.EggplantSeed), 20),
    PumpkinSeed(new Seed(SeedType.PumpkinSeed), 100),
    BokChoySeed(new Seed(SeedType.BokChoySeed), 50),
    YamSeed(new Seed(SeedType.Yam), 60),
    CranberrySeed(new Seed(SeedType.CranberrySeed), 240),
    FairySeed(new Seed(SeedType.Fairy), 200),
    AmaranthSeed(new Seed(SeedType.AmaranthSeed), 70),
    GrapeStarter(new Seed(SeedType.GrapeStarter), 60),
    ArtichokeSeed(new Seed(SeedType.ArtichokeSeed), 30),

    // multiple season
    CornSeed(new Seed(SeedType.CornSeed), 150),
    SunflowerSeed(new Seed(SeedType.SunflowerSeed), 200),
    WheatSeed(new Seed(SeedType.WheatSeed), 10),


    ;


    private String name;
    private double price;
    private String ch;
    boolean isOffSeason = false;
    GeneralStoreItems(Item item){
        this.name = item.getName();
        this.price = item.getPrice();
        this.ch = item.getChar();
    }



    GeneralStoreItems(Item item, double price) {
        this.ch = item.getChar();
        this.name = item.getName();
        this.price = price;
    }

    GeneralStoreItems(CraftingRecipe recipe, double price) {
        this.name = recipe.getName();
        this.ch = "";
        this.price = price;
    }

    GeneralStoreItems(Backpack backpack, double price){
        this.name = backpack.getName();
        this.ch = "";
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

    public void setPrice(double price){
        this.price = price;
        this.isOffSeason = true;
    }

    public void setOffSeason(boolean offSeason) {
        isOffSeason = offSeason;
    }

    public boolean isOffSeason() {
        return isOffSeason;
    }

    public static GeneralStoreItems parseItem(String itemName){
        for (GeneralStoreItems value : GeneralStoreItems.values()) {
            if(value.getName().equalsIgnoreCase(itemName)){
                return value;
            }
        }
        return null;
    }
}
