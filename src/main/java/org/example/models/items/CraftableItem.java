package org.example.models.items;

public enum CraftableItem implements Item{
//    Bomb(),
//    Sprinkler(),
    // etc

    ;

    @Override
    public String getChar() {
        return "CI";
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public double getPrice() {
        return 0;
    }

    private final CraftingRecipe recipe;

    CraftableItem(CraftingRecipe recipe) {
        this.recipe = recipe;
    }

    public CraftingRecipe getRecipe() {
        return recipe;
    }


    public static Item parseCraftable(String name) {
        return null;
    }


}
