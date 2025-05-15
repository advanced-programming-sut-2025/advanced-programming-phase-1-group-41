package org.example.models.items;

import org.example.models.Colors;

public enum CraftableMachine implements Item{
    CherryBomb("cb", "CherryBomb",50,CraftingRecipe.CherryBomb),
    Bomb("bb","Bomb",50, CraftingRecipe.Bomb),
    MegaBomb("mb", "Megabomb", 50 , CraftingRecipe.MegaBomb ),
    Sprinkler("sp", "Sprinkler", 0, CraftingRecipe.Sprinkler),
    QualitySprinkler("qs", "QualitySprinkler", 0, CraftingRecipe.QualitySprinkler),
    IridiumSprinkler("is", "IridiumSprinkler", 0, CraftingRecipe.IridiumSprinkler),
    CharcoalKiln("ck", "CharcoalKiln", 0, CraftingRecipe.CharcoalKiln),
    Furnace("Fu", "Furnace", 0,CraftingRecipe.Furnace),
    Scarecrow(Colors.colorize(160, 0, "!!"), "Scarecrow", 0, CraftingRecipe.ScareCrow),
    DeluxeScarecrow(Colors.colorize(160, 0, "??"), "DeluxeScarecrow", 0, CraftingRecipe.DeluxeScareCrow),
    BeeHouse("bh", "BeeHouse", 0, CraftingRecipe.BeeHouse),
    CheesePress("cp", "Cheese Press", 0, CraftingRecipe.CheesePress),
    Keg("k", "Keg", 0, CraftingRecipe.Keg),
    Loom("l", "Loom", 0, CraftingRecipe.Loom),
    MayonnaiseMachine("mm", "MayonnaiseMachine", 0, CraftingRecipe.MayonnaiseMachine),
    OilMaker("om", "OilMaker", 0, CraftingRecipe.OilMaker),
    PreservesJar("pj", "PreservesJar", 0, CraftingRecipe.PreservesJar),
    Dehydrator("dh", "Dehydrator", 0, CraftingRecipe.Dehydrator),
    GrassStarter("gs", "GrassStarter", 0, CraftingRecipe.GrassStarter),
    FishSmoker("fs", "FishSmoker", 0, CraftingRecipe.FishSmoker),
    MysticTreeSeed("mts", "MysticTreeSeed", 100, CraftingRecipe.MysticTreeSeed),
    ;

    private final CraftingRecipe recipe;
    private final String name;
    private final String ch;
    private final double price;

    CraftableMachine(String ch, String name, double price, CraftingRecipe recipe) {
        this.ch = ch;
        this.name = name;
        this.price = price;
        this.recipe = recipe;
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

    public String getCh() {
        return ch;
    }

    public CraftingRecipe getRecipe() {
        return recipe;
    }

    public static CraftableMachine parseCraftable(String name){
        for (CraftableMachine value : CraftableMachine.values()) {
            if(value.getName().equalsIgnoreCase(name)){
                return value;
            }
        }
        return null;
    }
}
