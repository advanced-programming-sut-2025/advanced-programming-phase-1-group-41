package org.example.models.buildings.marketplaces;

import org.example.models.items.Item;
import org.example.models.items.Slot;
import org.example.models.npc.npcCharacters.NPC;

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

    public Slot getSlotByItem(Item item){
        for (Slot slot : itemsForSale) {
            if(slot.getItem().getName().equalsIgnoreCase(item.getName())){
                return slot;
            }
        }
        return null;
    }
}
