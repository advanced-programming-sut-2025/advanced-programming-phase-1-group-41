package com.CEliconValley.models.buildings.marketplaces;

import com.CEliconValley.common.CellData;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.npc.npcCharacters.NPC;

import java.util.ArrayList;

public abstract class Marketplace implements Building {
    protected NPC owner;
    protected ArrayList<Slot> itemsForSale;
    public boolean isOpen = false;
    public static final int outOfHome = 11;
    public static final int outOfWork = 18;
    public ArrayList<Cell> doors = new ArrayList<>();

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

    public void setItemsForSale(ArrayList<Slot> itemsForSale) {
        this.itemsForSale = itemsForSale;
    }
}
