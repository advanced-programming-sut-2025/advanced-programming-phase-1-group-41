package com.CEliconValley.models.items;

import com.CEliconValley.models.foragings.Nature.Obstacle;
import com.CEliconValley.models.ui.TerminalColors;

public enum CraftableMachine implements Item, Obstacle {
    CherryBomb("cb", "CherryBomb",50,CraftingRecipe.CherryBomb),
    Bomb("bb","Bomb",50, CraftingRecipe.Bomb),
    MegaBomb("mb", "Megabomb", 50 , CraftingRecipe.MegaBomb ),
    Sprinkler("sp", "Sprinkler", 0, CraftingRecipe.Sprinkler),
    QualitySprinkler("qs", "QualitySprinkler", 0, CraftingRecipe.QualitySprinkler),
    IridiumSprinkler("is", "IridiumSprinkler", 0, CraftingRecipe.IridiumSprinkler),
    CharcoalKiln("ck", "CharcoalKiln", 0, CraftingRecipe.CharcoalKiln),
    Furnace("Fu", "Furnace", 0,CraftingRecipe.Furnace),
    Scarecrow(TerminalColors.colorize(160, 0, "!!"), "Scarecrow", 0, CraftingRecipe.ScareCrow),
    DeluxeScarecrow(TerminalColors.colorize(160, 0, "??"), "DeluxeScarecrow", 0, CraftingRecipe.DeluxeScareCrow),
    BeeHouse("bh", "BeeHouse", 0, CraftingRecipe.BeeHouse),
    CheesePress("cp", "CheesePress", 0, CraftingRecipe.CheesePress),
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
    public int getID() {
        return switch (this) {
            case CherryBomb -> 30000;
            case Bomb -> 30001;
            case MegaBomb -> 30002;
            case Sprinkler -> 30003;
            case QualitySprinkler -> 30004;
            case IridiumSprinkler -> 30005;

            case CharcoalKiln -> 30100;
            case Furnace -> 30101;
            case Scarecrow -> 30102;
            case DeluxeScarecrow -> 30103;
            case BeeHouse -> 30104;
            case CheesePress -> 30105;

            case Keg -> 30200;
            case Loom -> 30201;
            case MayonnaiseMachine -> 30202;
            case OilMaker -> 30203;
            case PreservesJar -> 30204;
            case Dehydrator -> 30205;

            case GrassStarter -> 30300;
            case FishSmoker -> 30301;
            case MysticTreeSeed -> 30302;

            default -> throw new IllegalStateException("Unknown item: " + this);
        };
    }

}
