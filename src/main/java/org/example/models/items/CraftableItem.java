package org.example.models.items;

public enum CraftableItem implements Item{
//    Bomb(),
//    Sprinkler(),
    // etc

    ;

    private final CraftingRecipe recipe;

    CraftableItem(CraftingRecipe recipe) {
        this.recipe = recipe;
    }

    public CraftingRecipe getRecipe() {
        return recipe;
    }
}
