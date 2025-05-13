package org.example.models.items;

import org.example.models.animals.Fish;
import org.example.models.foragings.Nature.Mine;
import org.example.models.foragings.Nature.Mineral;
import org.example.models.foragings.Nature.MineralType;

public enum CraftableItem implements Item{
    CopperBar("cb", "CopperBar", 10 * MineralType.CopperOre.getPrice(), false),
    IronBar("ib", "IronBar", 10 * MineralType.IronOre.getPrice(), false),
    GoldBar("gb", "GoldBar", 10 * MineralType.GoldOre.getPrice(), false),
    IridiumBar("ib", "IridiumBar", 10 * MineralType.IridiumOre.getPrice(), false),
    SmokedFish("sf","SmokedFish",Food.BakedFish.getPrice()*2, true, Food.BakedFish.getEnergy()*1.5),
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
}
