package com.CEliconValley.models.buildings.marketplaces;

import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.npc.npcCharacters.NPC;

import java.util.ArrayList;

public abstract class Marketplace {
    protected NPC owner;
    protected final ArrayList<Slot> itemsForSale;



    public Marketplace(NPC owner) {
        this.owner = owner;
        this.itemsForSale = new ArrayList<>();
    }


    // TODO add the actual items in the constructor of
    // each marketplace

    public Marketplace() {
        this.itemsForSale = new ArrayList<>();
    }

    public NPC getOwner() {
        return owner;
    }

    public ArrayList<Slot> getItemsForSale() {
        return itemsForSale;
    }

    public abstract void updateStock();

    public abstract void updateHourly();

    public Slot getSlotByItem(Item item){
        for (Slot slot : itemsForSale) {
            if(slot.getItem().getName().equalsIgnoreCase(item.getName())){
                return slot;
            }
        }
        return null;
    }
}
