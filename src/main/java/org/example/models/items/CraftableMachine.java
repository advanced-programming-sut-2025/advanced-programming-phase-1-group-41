package org.example.models.items;

public enum CraftableMachine implements Item{
    CherryBomb("cb", "CherryBomb",50,CraftingRecipe.CherryBomb),
    Bomb("bb","Bomb",50, CraftingRecipe.Bomb),
    MegaBomb("mb", "Megabomb", 50 , CraftingRecipe.MegaBomb ),
    Sprinkler("sp", "Sprinkler", 0, null),
    QualitySprinkler("qs", "QualitySprinkler", 0, null),
    IridiumSprinkler("is", "IridiumSprinkler", 0, null),
    CharcoalKiln("ck", "CharcoalKiln", 0, null),
    Furnace("ck", "Furnace", 0,CraftingRecipe.Furnace),
    Scarecrow("sc", "Scarecrow", 0, null),
    DeluxeScarecrow("ds", "DeluxeScarecrow", 0, null),
    BeeHouse("bh", "BeeHouse", 0, null),
    CheesePress("cp", "Cheese Press", 0, null),
    Keg("k", "Keg", 0, null),
    Loom("l", "Loom", 0, null),
    MayonnaiseMachine("mm", "MayonnaiseMachine", 0, null),
    OilMaker("om", "OilMaker", 0, null),
    PreservesJar("pj", "PreservesJar", 0, null),
    Dehydrator("d", "Dehydrator", 0, null),
    GrassStarter("gs", "GrassStarter", 0, null),
    FishSmoker("fs", "FishSmoker", 0, null),
    MysticTreeSeed("mts", "MysticTreeSeed", 100, CraftingRecipe.MysticTreeSeed),
    ;

    private CraftingRecipe recipe;
    private String name;
    private String ch;
    private double price;


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
