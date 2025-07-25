package com.CEliconValley.common;

import com.CEliconValley.models.Finder;
import com.CEliconValley.models.items.Slot;
import dev.morphia.annotations.Transient;

public class SlotData {

    String itemName;
    int quantity;

    public SlotData(Slot slot) {
        this.itemName = slot.getItem().getName();
        this.quantity = slot.getQuantity();
    }


    public Slot getSlot(){
        return new Slot(Finder.parseItem(this.itemName), this.quantity);
    }


    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

}
