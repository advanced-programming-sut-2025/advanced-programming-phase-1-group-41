package org.example.models.items;

import org.example.models.animals.Fish;
import org.example.models.animals.FishType;
import org.example.models.buildings.marketplaces.items.MarketplaceItems;
import org.example.models.foragings.*;
import org.example.models.items.Products.Product;
import org.example.models.items.Products.ProductType;

import java.util.HashMap;

public enum CookingRecipe {
    FriedEgg(new HashMap<>(){{
        put(new Product(ProductType.ChickenEgg), 1);
    }}),
    BakedFish(new HashMap<>(){{
        put(new Fish(FishType.Salmon),1);
        put(new Fish(FishType.Sardine),1);
        put(new Crop(CropType.Wheat),1);
    }}),
    Salad(new HashMap<>(){{
        put(new ForagingCrop(ForagingCropType.Leek), 1);
        put(new ForagingCrop(ForagingCropType.Dandelion), 1);
    }}),
    Omelet(new HashMap<>(){{
        put(new Product(ProductType.ChickenEgg), 1);
        put(new Product(ProductType.CowMilk), 1);
    }}),
    PumpkinPie(new HashMap<>(){{
        put(new Crop(CropType.Pumpkin), 1);
        put(MarketplaceItems.Wheatflour, 1);
        put(new Product(ProductType.CowMilk), 1);
        put(MarketplaceItems.Sugar, 1);
    }}),
    Spaghetti(new HashMap<>(){{
        put(MarketplaceItems.Wheatflour,1);
        put(new Crop(CropType.Tomato), 1);
    }}),
    Pizza(new HashMap<>(){{
        put(MarketplaceItems.Wheatflour,1);
        put(new Crop(CropType.Tomato), 1);
        put(CraftableItem.Cheese, 1);
    }}),
    Tortilla(new HashMap<>(){{
        put(new Crop(CropType.Corn), 1);
    }}),
    // needs completion
    MakiRoll(new HashMap<>(){{
        // fish
        put(MarketplaceItems.Rice, 1);
        // fiber
    }}),
    TripleShotExspresso(new HashMap<>(){{
        put(new Crop(CropType.CoffeeBean), 3);
    }}),
    Cookie(new HashMap<>(){{
        put(MarketplaceItems.Wheatflour, 1);
        put(MarketplaceItems.Sugar, 1);
        put(new Product(ProductType.ChickenEgg), 1);
    }}),
    HashBrown(new HashMap<>(){{
        put(new Crop(CropType.Potato), 1);
        put(CraftableItem.Oil, 1);
    }}),
    Pancake(new HashMap<>(){{
        put(MarketplaceItems.Wheatflour, 1);
        put(new Product(ProductType.ChickenEgg), 1);
    }}),
    FruitSalad(new HashMap<>(){{
        put(new Crop(CropType.Blueberry), 1);
        put(new Crop(CropType.Melon), 1);
        put(new Fruit(FruitType.Apricot), 1);
    }}),
    RedPlate(new HashMap<>(){{
        put(new Crop(CropType.RedCabbage), 1);
        put(new Crop(CropType.Radish), 1);
    }}),
    Bread(new HashMap<>(){{
        put(MarketplaceItems.Wheatflour,1);
    }}),
    SalmonDinner(new HashMap<>(){{
        put(new Fish(FishType.Salmon), 1);
        put(new Seed(SeedType.AmaranthSeed), 1);
        put(new Seed(SeedType.KaleSeed), 1);
    }}),
    VegetableMedly(new HashMap<>(){{
        put(new Crop(CropType.Tomato), 1);
        put(new Seed(SeedType.BeetSeed), 1);
    }}),
    // needs completation
    FarmerLunch(new HashMap<>(){{
        // omlet
        put(new Crop(CropType.Parsnip), 1);
    }}),
    // needs completation
    SurvivalBurger(new HashMap<>(){{
        // bread
        put(new Crop(CropType.Carrot), 1);
        put(new Crop(CropType.Eggplant), 1);
    }}),
    // needs completation
    dishOSea(new HashMap<>(){{
        put(new Fish(FishType.Sardine), 2);
        // hash browns
    }}),
    SeaformPuddin(new HashMap<>(){{
        put(new Fish(FishType.Flounder), 1);
        put(new Fish(FishType.MidnightCarp), 1);
    }}),
    MinerTreat(new HashMap<>(){{
        put(new Crop(CropType.Carrot), 1);
        put(MarketplaceItems.Sugar, 1);
        put(new Product(ProductType.CowMilk), 1);
    }}),

    ;

    static {
        FishType.values();
    }

    public final HashMap<Item, Integer> neededItems;

    CookingRecipe(HashMap<Item, Integer> neededItems) {
        this.neededItems = neededItems;
    }

    @Override
    public String toString() {
        return "CookingRecipe{" +
                "neededItems=" + neededItems +
                '}';
    }

    public static void updateRecipe(){
        FarmerLunch.neededItems.put(Food.Omelet, 1);
        SurvivalBurger.neededItems.put(Food.Bread, 1);
        dishOSea.neededItems.put(Food.HashBrowns, 1);
    }
}
