package org.example.models.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Slot {
    Item item;
    private int quantity;
    private final PriorityQueue<Integer> itemsPrice;
    public Slot(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
        itemsPrice = new PriorityQueue<>();
    }


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PriorityQueue getItemsPrice() {
        return itemsPrice;
    }

    @Override
    public String toString() {
        return "Slot{" +
                "item=" + item.getName() +
                ", quantity=" + quantity +
                '}';
    }
}
