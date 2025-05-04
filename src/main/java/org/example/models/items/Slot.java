package org.example.models.items;

import java.util.ArrayList;

public class Slot {
    private ArrayList<Item> items;
    private int quantity;

    public Slot(ArrayList<Item> items, int quantity) {
        this.items = items;
        this.quantity = quantity;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
