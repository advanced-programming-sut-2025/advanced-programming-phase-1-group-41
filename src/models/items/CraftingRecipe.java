package models.items;

import java.util.HashMap;

public enum CraftingRecipe {
    Bomb(),
    Sprinkler(),
    ;
    HashMap<Item, Integer> neededItems;

    CraftingRecipe(HashMap<Item, Integer> neededItems) {
        this.neededItems = neededItems;
    }
}
