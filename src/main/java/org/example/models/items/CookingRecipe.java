package org.example.models.items;

import java.util.HashMap;

public enum CookingRecipe {
//    Egg(),
//    Fish(),
    // etc
    ;
    public final HashMap<Item, Integer> neededItems;

    CookingRecipe(HashMap<Item, Integer> neededItems) {
        this.neededItems = neededItems;
    }


}
