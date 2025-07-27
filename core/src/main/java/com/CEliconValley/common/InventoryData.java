package com.CEliconValley.common;

import com.CEliconValley.models.Finder;
import com.CEliconValley.models.items.Backpack;
import com.CEliconValley.models.items.Inventory;
import com.CEliconValley.models.items.Slot;
import dev.morphia.annotations.Embedded;

import java.util.ArrayList;


@Embedded
public class InventoryData {
    // testing..
    ArrayList<SlotData> slots;
    int whichBackpack;
    public InventoryData(Inventory inventory) {
        slots=new ArrayList<>();
        for (Slot slot : inventory.getSlots()) {
            if(slot == null || slot.getItem() == null || slot.getQuantity() <= 0){
                continue;
            }
            slots.add(new SlotData(slot));
        }
        whichBackpack = inventory.getBackpack().ordinal();
    }

    public Inventory getInventory() {
        Inventory inventory = new Inventory(Backpack.values()[whichBackpack]);
        for (SlotData slot : slots) {
            inventory.addToInventory(Finder.parseItem(slot.getItemName()), slot.getQuantity());
        }
        return inventory;
    }
}
