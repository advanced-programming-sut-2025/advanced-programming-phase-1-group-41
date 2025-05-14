package org.example.models.buildings.marketplaces.items;

import org.example.models.items.CookingRecipe;
import org.example.models.items.CraftableItem;
import org.example.models.items.Food;
import org.example.models.items.Item;

public enum SaloonItems implements Item {
    Beer(CraftableItem.Beer, 400),
    Salad(Food.Salad, 220),
    Bread(Food.Bread, 120),
    Spaghetti(Food.Spaghetti, 240),
    Pizza(Food.Pizza, 600),
    Coffee(CraftableItem.Coffee, 300),
    HashbrownsRecipe(CookingRecipe.HashBrown, 50),
    OmeletRecipe(CookingRecipe.Omelet, 100),
    PancakesRecipe(CookingRecipe.Pancake, 100),
    BreadRecipe(CookingRecipe.Bread, 100),
    TortillaRecipe(CookingRecipe.Tortilla, 100),
    PizzaRecipe(CookingRecipe.Pizza, 150),
    MakiRollRecipe(CookingRecipe.MakiRoll, 300),
    TripleShotEspressoRecipe(CookingRecipe.TripleShotExspresso, 5000),
    CookieRecipe(CookingRecipe.Cookie, 300),
    ;

    private String name;
    private double price;
    private String ch;

    SaloonItems(String ch, String name, double price) {
        this.ch = ch;
        this.name = name;
        this.price = price;
    }


    SaloonItems(CookingRecipe recipe, double price) {
        this.name = recipe.getName();
        this.price = price;
        this.ch = "";
    }

    SaloonItems(Item item, double price) {
        this.ch = item.getChar();
        this.name = item.getName();
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

    public static SaloonItems parseItem(String itemName) {
        for (SaloonItems value : SaloonItems.values()) {
            if(value.getName().equalsIgnoreCase(itemName)){
                return value;
            }
        }
        return null;
    }
}
