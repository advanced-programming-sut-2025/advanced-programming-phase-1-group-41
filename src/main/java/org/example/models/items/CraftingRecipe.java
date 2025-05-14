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
    }}, "CherryBombRecipe"),
    Bomb(new HashMap<>(){{
        put(new Mineral(MineralType.IronOre),4);
        put(new Mineral(MineralType.Coal), 1);
    }}, "BombRecipe"),
    MegaBomb(new HashMap<>(){{
        put(new Mineral(MineralType.GoldOre),4);
        put(new Mineral(MineralType.Coal), 1);
    }}, "MegaBombRecipe"),
    // sprinkler
    // quality sprinkler
    // iridium sprinkler
    // charcoal klin
    Furnace(new HashMap<>(){{
        put(new Mineral(MineralType.CopperOre),20);
        put(new Rock(), 20);
    }}, "FurnaceRecipe"),
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
    }}, "PreservesJarRecipe"),
    // dehydrator
    // grass starter
    // fish smoker
    FishSmoker(new HashMap<>(){{
        put(new Wood(), 50);
        put(CraftableItem.IronBar, 3);
        put(new Mineral(MineralType.Coal), 10);
    }}, "FishSmokerRecipe"),
    MysticTreeSeed(new HashMap<>(){{
        put(SeedType.Acorns, 5);
        put(SeedType.MapleSeeds, 5);
        put(SeedType.PineCones, 5);
        put(SeedType.MahoganySeeds,5);
    }}, "MysticTreeSeedRecipe"),


    ;
    public final HashMap<Item, Integer> neededItems;
    private String name;

    CraftingRecipe(HashMap<Item, Integer> neededItems, String name) {
        this.neededItems = neededItems;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public HashMap<Item, Integer> getNeededItems() {
        return neededItems;
    }

    @Override
    public String toString() {
        return name+" CraftingRecipe{" +
                "neededItems=" + neededItems +
                '}';
    }

    public static CraftingRecipe parseRecipe(String input){
        for (CraftingRecipe value : CraftingRecipe.values()) {
            if(value.getName().equalsIgnoreCase(input)){
                return value;
            }
        }
        return null;
    }
}
