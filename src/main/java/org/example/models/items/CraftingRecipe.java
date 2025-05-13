package org.example.models.items;

import org.example.models.foragings.Nature.Mineral;
import org.example.models.foragings.Nature.MineralType;
import org.example.models.foragings.Nature.Rock;
import org.example.models.foragings.Nature.Wood;
import org.example.models.foragings.SeedType;

import java.util.HashMap;

public enum CraftingRecipe {
    CherryBomb(new HashMap<>(){{
        put(new Mineral(MineralType.CopperOre),4);
        put(new Mineral(MineralType.Coal), 1);
    }}),
    Bomb(new HashMap<>(){{
        put(new Mineral(MineralType.IronOre),4);
        put(new Mineral(MineralType.Coal), 1);
    }}),
    MegaBomb(new HashMap<>(){{
        put(new Mineral(MineralType.GoldOre),4);
        put(new Mineral(MineralType.Coal), 1);
    }}),
    // sprinkler
    // quality sprinkler
    // iridium sprinkler
    // charcoal klin
    Furnace(new HashMap<>(){{
        put(new Mineral(MineralType.CopperOre),20);
        put(new Rock(), 20);
    }}),
    // scarecrow
    // deluxe scarecrow
    // bee house
    // cheese press
    // keg
    // Loom
    // mayonnaise machine
    // oil maker
    PreservesJar(new HashMap<>(){{
        put(new Wood(), 50);
        put(new Rock(), 40);
        put(new Mineral(MineralType.Coal),8);
    }}),
    // dehydrator
    // grass starter
    // fish smoker
    MysticTreeSeed(new HashMap<>(){{
        put(SeedType.Acorns, 5);
        put(SeedType.MapleSeeds, 5);
        put(SeedType.PineCones, 5);
        put(SeedType.MahoganySeeds,5);
    }})
    ;
    public final HashMap<Item, Integer> neededItems;

    CraftingRecipe(HashMap<Item, Integer> neededItems) {
        this.neededItems = neededItems;
    }

    @Override
    public String toString() {
        return "CraftingRecipe{" +
                "neededItems=" + neededItems +
                '}';
    }
}
