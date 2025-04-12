package models.items;

import java.util.ArrayList;

public class Inventory {
    public final ArrayList<Slot> slots;
    private int numberOfSlots;

    public Inventory(int numberOfSlots) {
        this.slots = new ArrayList<>();
        this.numberOfSlots = numberOfSlots;
    }

    public ArrayList<Slot> getSlots() {
        return slots;
    }

    public int getNumberOfSlots() {
        return numberOfSlots;
    }
}
