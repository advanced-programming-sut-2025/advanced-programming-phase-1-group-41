package org.example.models.items;

import org.example.models.animals.Fish;
import org.example.models.animals.FishType;
import org.example.models.buildings.marketplaces.items.MarketplaceItems;
import org.example.models.foragings.*;
import org.example.models.foragings.Nature.Fiber;
import org.example.models.items.Products.Product;
import org.example.models.items.Products.ProductType;

import java.util.HashMap;

public enum CookingRecipe {
    FriedEgg(new HashMap<>(){{
        put(new Product(ProductType.ChickenEgg), 1);
    }}, "FriedEggRecipe"),

    BakedFish(new HashMap<>(){{
        put(new Fish(FishType.Salmon), 1);
        put(new Fish(FishType.Sardine), 1);
        put(new Crop(CropType.Wheat), 1);
    }}, "BakedFishRecipe"),

    Salad(new HashMap<>(){{
        put(new ForagingCrop(ForagingCropType.Leek), 1);
        put(new ForagingCrop(ForagingCropType.Dandelion), 1);
    }}, "SaladRecipe"),

    Omelet(new HashMap<>(){{
        put(new Product(ProductType.ChickenEgg), 1);
        put(new Product(ProductType.CowMilk), 1);
    }}, "OmeletRecipe"),

    PumpkinPie(new HashMap<>(){{
        put(new Crop(CropType.Pumpkin), 1);
        put(MarketplaceItems.Wheatflour, 1);
        put(new Product(ProductType.CowMilk), 1);
        put(MarketplaceItems.Sugar, 1);
    }}, "PumpkinPieRecipe"),

    Spaghetti(new HashMap<>(){{
        put(MarketplaceItems.Wheatflour, 1);
        put(new Crop(CropType.Tomato), 1);
    }}, "SpaghettiRecipe"),

    Pizza(new HashMap<>(){{
        put(MarketplaceItems.Wheatflour, 1);
        put(new Crop(CropType.Tomato), 1);
        put(CraftableItem.Cheese, 1);
    }}, "PizzaRecipe"),

    Tortilla(new HashMap<>(){{
        put(new Crop(CropType.Corn), 1);
    }}, "TortillaRecipe"),

    // needs completion
    MakiRoll(new HashMap<>(){{
        // fish
        put(MarketplaceItems.Rice, 1);
        put(new Fiber(), 1);
    }}, "MakiRollRecipe"),

    TripleShotExspresso(new HashMap<>(){{
        put(new Crop(CropType.CoffeeBean), 3);
    }}, "TripleShotExspressoRecipe"),

    Cookie(new HashMap<>(){{
        put(MarketplaceItems.Wheatflour, 1);
        put(MarketplaceItems.Sugar, 1);
        put(new Product(ProductType.ChickenEgg), 1);
    }}, "CookieRecipe"),

    HashBrown(new HashMap<>(){{
        put(new Crop(CropType.Potato), 1);
        put(CraftableItem.Oil, 1);
    }}, "HashBrownRecipe"),

    Pancake(new HashMap<>(){{
        put(MarketplaceItems.Wheatflour, 1);
        put(new Product(ProductType.ChickenEgg), 1);
    }}, "PancakeRecipe"),

    FruitSalad(new HashMap<>(){{
        put(new Crop(CropType.Blueberry), 1);
        put(new Crop(CropType.Melon), 1);
        put(new Fruit(FruitType.Apricot), 1);
    }}, "FruitSaladRecipe"),

    RedPlate(new HashMap<>(){{
        put(new Crop(CropType.RedCabbage), 1);
        put(new Crop(CropType.Radish), 1);
    }}, "RedPlateRecipe"),

    Bread(new HashMap<>(){{
        put(MarketplaceItems.Wheatflour, 1);
    }}, "BreadRecipe"),

    SalmonDinner(new HashMap<>(){{
        put(new Fish(FishType.Salmon), 1);
        put(new Seed(SeedType.AmaranthSeed), 1);
        put(new Seed(SeedType.KaleSeed), 1);
    }}, "SalmonDinnerRecipe"),

    VegetableMedly(new HashMap<>(){{
        put(new Crop(CropType.Tomato), 1);
        put(new Seed(SeedType.BeetSeed), 1);
    }}, "VegetableMedlyRecipe"),

    // needs completion
    FarmerLunch(new HashMap<>(){{
        // omelet
        put(new Crop(CropType.Parsnip), 1);
    }}, "FarmerLunchRecipe"),

    // needs completion
    SurvivalBurger(new HashMap<>(){{
        // bread
        put(new Crop(CropType.Carrot), 1);
        put(new Crop(CropType.Eggplant), 1);
    }}, "SurvivalBurgerRecipe"),

    // needs completion
    dishOSea(new HashMap<>(){{
        put(new Fish(FishType.Sardine), 2);
        // hash browns
    }}, "DishOSeaRecipe"),

    SeaformPuddin(new HashMap<>(){{
        put(new Fish(FishType.Flounder), 1);
        put(new Fish(FishType.MidnightCarp), 1);
    }}, "SeaformPuddinRecipe"),

    MinerTreat(new HashMap<>(){{
        put(new Crop(CropType.Carrot), 1);
        put(MarketplaceItems.Sugar, 1);
        put(new Product(ProductType.CowMilk), 1);
    }}, "MinerTreatRecipe"),

    ;

    String name;

    static {
        FishType.values();
    }

    public final HashMap<Item, Integer> neededItems;

    CookingRecipe(HashMap<Item, Integer> neededItems, String name) {
        this.neededItems = neededItems;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public static CookingRecipe parseRecipe(String name){
        for (CookingRecipe value : CookingRecipe.values()) {
            if(value.name.equalsIgnoreCase(name)){
                return value;
            }
        }
        return null;
    }
}
