package com.CEliconValley.models.items;

import com.CEliconValley.models.foragings.Nature.*;
import com.CEliconValley.models.foragings.SeedType;

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
    Sprinkler(new HashMap<>(){{
        put(CraftableItem.IronBar,1);
        put(CraftableItem.CopperBar,1);
    }} , "SprinklerRecipe"),
    QualitySprinkler(new HashMap<>(){{
        put(CraftableItem.IronBar,1);
        put(CraftableItem.GoldBar,1);
    }} , "QualitySprinklerRecipe"),
    IridiumSprinkler(new HashMap<>(){{
        put(CraftableItem.GoldBar,1);
        put(CraftableItem.IridiumBar,1);
    }} , "IridiumSprinklerRecipe"),
    CharcoalKiln(new HashMap<>(){{
        put(new Wood(), 20);
        put(CraftableItem.CopperBar, 2);
    }}, "CharcoalKilnRecipe"),
    Furnace(new HashMap<>(){{
        put(new Mineral(MineralType.CopperOre),20);
        put(new Rock(), 25);
    }}, "FurnaceRecipe"),
    ScareCrow(new HashMap<>(){{
        put(new Wood(), 50);
        put(new Mineral(MineralType.Coal),1);
        put(new Fiber(), 20);
    }}, "ScareCrowRecipe"),
    DeluxeScareCrow(new HashMap<>(){{
        put(new Wood(), 50);
        put(new Mineral(MineralType.Coal),1);
        put(new Mineral(MineralType.IridiumOre),1);
        put(new Fiber(), 20);
    }}, "DeluxeScareCrowRecipe"),
    BeeHouse(new HashMap<>(){{
        put(new Wood(),40);
        put(new Mineral(MineralType.Coal),8);
        put(CraftableItem.IronBar, 1);
    }}, "BeehouseRecipe"),
    CheesePress(new HashMap<>(){{
        put(new Wood(),45);
        put(new Rock(),45);
        put(CraftableItem.CopperBar, 1);
    }}, "CheesePressRecipe"),
    Keg(new HashMap<>(){{
        put(new Wood(),30);
        put(CraftableItem.CopperBar, 1);
        put(CraftableItem.IronBar, 1);
    }}, "KegRecipe"),
    Loom(new HashMap<>(){{
        put(new Wood(),60);
        put(new Fiber(), 30);
    }}, "LoomRecipe"),
    MayonnaiseMachine(new HashMap<>(){{
        put(new Wood(),15);
        put(new Rock(),15);
        put(CraftableItem.CopperBar, 1);
    }} , "MayonnaiseMachineRecipe"),
    OilMaker(new HashMap<>(){{
        put(new Wood(),100);
        put(CraftableItem.GoldBar, 1);
        put(CraftableItem.IronBar, 1);
    }}, "OilMakerRecipe"),
    PreservesJar(new HashMap<>(){{
        put(new Wood(), 50);
        put(new Rock(), 40);
        put(new Mineral(MineralType.Coal),8);
    }}, "PreservesJarRecipe"),
    Dehydrator(new HashMap<>(){{
        put(new Wood(),30);
        put(new Rock(),20);
        put(new Fiber(), 30);
    }}, "DehydratorRecipe"),
    GrassStarter(new HashMap<>(){{
        put(new Wood(), 1);
        put(new Fiber(), 30);
    }}, "GrassStarterRecipe"),
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
