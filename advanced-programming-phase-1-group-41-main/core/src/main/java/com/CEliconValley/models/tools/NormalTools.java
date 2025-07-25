package com.CEliconValley.models.tools;

import com.CEliconValley.models.items.Item;

public enum NormalTools implements Tool {
    Scythe(new Scythe()),
    MilkPail(new MilkPale()),
    Shear(new Shear()),
    ;

    private Item item;

    NormalTools(Item item) {
        this.item = item;
    }

    public static Item parseNormalTool(String name) {
        for (NormalTools value : NormalTools.values()) {
            if(value.getName().equalsIgnoreCase(name)){
                return value.item;
            }
        }
        return null;
    }

    @Override
    public String getChar() {
        return item.getChar();
    }

    @Override
    public String getName() {
        return item.getName();
    }

    @Override
    public double getPrice() {
        return item.getPrice();
    }


}
