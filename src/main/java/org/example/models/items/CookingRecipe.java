package org.example.models.items;

import org.example.models.animals.Fish;
import org.example.models.animals.FishType;
import org.example.models.foragings.Crop;
import org.example.models.foragings.CropType;
import org.example.models.foragings.SeedType;

import java.util.HashMap;

public enum CookingRecipe {
    BakedFish(new HashMap<>(){{
        put(new Fish(FishType.Salmon),1);
        put(new Fish(FishType.Sardine),1);
        put(new Crop(CropType.Wheat),1);
    }}),
    Spaghetti(new HashMap<>(){{
        put(new Crop(CropType.Wheat),1);
        put(new Crop(CropType.Tomato), 1);
    }}),
    Bread(new HashMap<>(){{
        put(new Crop(CropType.Wheat),1);
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
