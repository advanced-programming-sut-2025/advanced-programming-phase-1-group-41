package com.CEliconValley.models.items;

import com.CEliconValley.models.foragings.Nature.MineralType;

public enum CraftableItem implements Item{
    CopperBar("cb", "CopperBar", 10 * MineralType.CopperOre.getPrice(), false),
    IronBar("ib", "IronBar", 10 * MineralType.IronOre.getPrice(), false),
    GoldBar("gb", "GoldBar", 10 * MineralType.GoldOre.getPrice(), false),
    IridiumBar("ib", "IridiumBar", 10 * MineralType.IridiumOre.getPrice(), false),
    Oil("oi","Oil",100,true,13),
    TruffleOil("to","TruffleOil",1065,true,38),
    Mayonnaise("ma","Mayonnaise",190,true,50),
    LargeMayonnaise("ma","Mayonnaise",237,true,50),
    DuckMayonnaise("dm","DuckMayonnaise",37, true, 75),
    DinosaurMayonnaise("Dm","DinosaurMayonnaise", 800, true, 125),
    Cloth("Cl","Cloth",470,false),
    Honey("Ho","Honey",350,true, 75),
    Cheese("Ch","Cheese",230,true,100),
    LargeCheese("Ch","Cheese",345,true,100),
    GoatCheese("GC","GoatCheese",400,true,100),
    LargeGoatCheese("GC","GoatCheese",600,true,100),
    Beer("Be","Beer",200,true,50),
    Vineger("vi","Vineger",100,true,13),
    Coffee("co","Coffee",150,true,75),
    Mead("me","Mead",300,true,100),
    PaleAle("pa","PaleAle",300,true,50),
    Raisin("Ra","Raisin",600,true,125),
    Coal("Co","Coal",50,false),
    ;

    String name;
    String ch;
    double price;
    boolean isEatable;
    double energy;

    CraftableItem(String ch, String name, double price, boolean isEatable) {
        this.ch = ch;
        this.name = name;
        this.price = price;
        this.isEatable = isEatable;
        this.energy = 0;
    }
    CraftableItem(String ch, String name, double price, boolean isEatable, double energy) {
        this.ch = ch;
        this.name = name;
        this.price = price;
        this.isEatable = isEatable;
        this.energy = energy;
    }


    CraftableItem(Item item) {
        this.name = item.getName();
        this.price = item.getPrice();
        this.ch = item.getChar();
    }

    public boolean isEatable() {
        return isEatable;
    }

    public void setEatable(boolean eatable) {
        isEatable = eatable;
    }

    public String getCh() {
        return ch;
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

    public double getEnergy() {
        return energy;
    }

    public static CraftableItem parseCraftable(String craftable){
        for (CraftableItem value : CraftableItem.values()) {
            if(value.getName().equalsIgnoreCase(craftable)){
                return value;
            }
        }
        return null;
    }
    public int getID() {
        return switch (this) {
            case CopperBar -> 20000;
            case IronBar -> 20001;
            case GoldBar -> 20002;
            case IridiumBar -> 20003;
            case Oil -> 20004;
            case TruffleOil -> 20005;

            case Mayonnaise -> 20100;
            case LargeMayonnaise -> 20101;
            case DuckMayonnaise -> 20102;
            case DinosaurMayonnaise -> 20103;
            case Cloth -> 20104;
            case Honey -> 20105;

            case Cheese -> 20200;
            case LargeCheese -> 20201;
            case GoatCheese -> 20202;
            case LargeGoatCheese -> 20203;
            case Beer -> 20204;
            case Vineger -> 20205;

            case Coffee -> 20300;
            case Mead -> 20301;
            case PaleAle -> 20302;
            case Raisin -> 20303;
            case Coal -> 70301;

            default -> throw new IllegalStateException("Unknown item: " + this);
        };
    }

}
