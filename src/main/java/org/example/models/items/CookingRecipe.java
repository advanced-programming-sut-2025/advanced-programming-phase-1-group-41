package org.example.models.items;

import org.example.models.animals.FishType;
import org.example.models.foragings.SeedType;

import java.util.HashMap;

public enum CookingRecipe {
    BakedFish(new HashMap<>(){{
        put(FishType.Salmon,1);
        put(FishType.Sardine,1);
        put(SeedType.Wheat,1);
    }}),
    Pizza(new HashMap<>(){{
        put(SeedType.Wheat,1);
        put(SeedType.Tomato, 1);
        put(Food.Cheese, 1);
    }}),
    Spaghetti(new HashMap<>(){{
        put(SeedType.Wheat,1);
        put(SeedType.Tomato, 1);
    }}),
    Bread(new HashMap<>(){{
        put(SeedType.Wheat,1);
    }})
    ;
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
}
