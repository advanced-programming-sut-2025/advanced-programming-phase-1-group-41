package com.CEliconValley.models.foragings;

public enum FruitType {
    Apricot("Apricot" , 59, true, 38),
    Cherry("Cherry" , 80, true, 38),
    Banana("Banana" , 150, true, 75),
    Mango("Mango" , 130, true, 100),
    Orange("Orange" , 100, true, 38),
    Peach("Peach" , 140, true, 38),
    Apple("Apple" , 100, true, 38),
    Pomegranate("Pomegranate", 140, true,	38),
    OakResin("Oak Resin" ,	150, false,	0),
    MapleSyrup("Maple Syrup", 200,	false, 0),
    PineTar("Pine Tar",	100,	false,	0),
    Sap("Sap", 2, true,	-2),
    CommonMushroom("Common Mushroom", 40, true, 38),
    MysticSyrup("Mystic Syrup",1000, true, 500),
    ;
    private final String name;
    private final int baseSellPrice;
    private final boolean eatable;
    private final int energy;

    FruitType(String name, int baseSellPrice, boolean eatable, int energy) {
        this.name = name;
        this.baseSellPrice = baseSellPrice;
        this.eatable = eatable;
        this.energy = energy;
    }

    public boolean isEatable() {
        return eatable;
    }

    public int getEnergy() {
        return energy;
    }

    public String getName() {
        return name;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public static FruitType parseFruitType(String fruitType) {
        for (FruitType value : FruitType.values()) {
            if(value.name.equalsIgnoreCase(fruitType)){
                return value;
            }
        }
        return null;
    }
}
