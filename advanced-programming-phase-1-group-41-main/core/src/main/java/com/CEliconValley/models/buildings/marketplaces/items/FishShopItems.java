package com.CEliconValley.models.buildings.marketplaces.items;

import com.CEliconValley.models.items.CraftingRecipe;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.tools.FishingRod;
import com.CEliconValley.models.tools.FishingRodLevel;

public enum FishShopItems implements Item {
    FishSmokerRecipe(CraftingRecipe.FishSmoker, 10000),
    TroutSoup(MarketplaceItems.TroutSoup),
    TrainingRod(new FishingRod(FishingRodLevel.Training), 25),
    BambooRod(new FishingRod(FishingRodLevel.Bamboo), 500),
    FiberglassRod(new FishingRod(FishingRodLevel.FiberGlass), 1800),
    IridiumRod(new FishingRod(FishingRodLevel.Iridium), 7500),
    ;


    static {
        MarketplaceItems.values();
    }

    private String name;
    private double price;
    private String ch;

    FishShopItems(Item item){
        this.name = item.getName();
        this.price = item.getPrice();
        this.ch = item.getChar();
    }

    FishShopItems(Item item, double price) {
        this.ch = item.getChar();
        this.name = item.getName();
        this.price = price;
    }

    FishShopItems(CraftingRecipe recipe, double price) {
        this.name = recipe.getName();
        this.price = price;
        this.ch = "";
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

    public static Item parseItem(String itemName){
        for (FishShopItems value : FishShopItems.values()) {
            if(value.getName().equalsIgnoreCase(itemName)){
                return value;
            }
        }
        return null;
    }
}
