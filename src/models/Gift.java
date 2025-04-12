package models;

import models.items.Slot;

public class Gift {
    private Player from;
    private Player to;
    private Slot slot;

    public Player getFrom() {
        return from;
    }

    public Player getTo() {
        return to;
    }

    public Slot getSlot() {
        return slot;
    }
}
