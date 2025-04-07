package models.items;

import java.util.ArrayList;

public class Inventory {
    ArrayList<Slot> slots;
    int numberOfSlots;

    public Inventory(int numberOfSlots) {
        this.slots = new ArrayList<>();
        this.numberOfSlots = numberOfSlots;
    }
}
