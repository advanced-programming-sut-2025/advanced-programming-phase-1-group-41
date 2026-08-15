package com.CEliconValley.common;

import com.CEliconValley.models.Finder;
import com.CEliconValley.models.items.Slot;
import dev.morphia.annotations.Embedded;

@Embedded
public class SlotData {

    String itemName;
    int quantity;
    double price;
    public SlotData() {
    }

    public SlotData(Slot slot) {
        this.itemName = slot.getItem().getName();
        this.quantity = slot.getQuantity();
    }
    public SlotData(Slot slot, double price) {
        this.itemName = slot.getItem().getName();
        this.quantity = slot.getQuantity();
        this.price = price;
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


    public double getPrice() {
        return price;
    }
}
