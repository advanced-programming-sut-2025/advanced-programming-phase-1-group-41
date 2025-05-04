package org.example.models;

import org.example.models.items.Slot;

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
