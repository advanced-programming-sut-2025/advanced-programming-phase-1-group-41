package org.example.models.items.craftableitems;

import org.example.models.items.Item;

public enum CraftableNames {
    DriedFruit(new DriedFruit()),
    DriedMushroom(new DriedMushroom()),
    Jelly(new Jelly()),
    Juice(new Juice()),
    Pickles(new Pickles()),
    Wine(new Wine()),
    SmokedFish(new SmokedFish()),
    ;
    public final Item item;

    CraftableNames(Item item) {
        this.item = item;
    }

    public static Item parseItem(String itemName){
        for (CraftableNames value : CraftableNames.values()) {
            if(value.item.getName().equalsIgnoreCase(itemName)){
                return value.item;
            }
        }
        return null;
    }
}
