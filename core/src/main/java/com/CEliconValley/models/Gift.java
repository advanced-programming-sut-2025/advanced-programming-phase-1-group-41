package com.CEliconValley.models;

import com.CEliconValley.models.items.Slot;

public class Gift {
    private final Player from;
    private final Player to;
    private final Slot slot;

    public Gift(Player from, Player to, Slot slot) {
        this.from = from;
        this.to = to;
        this.slot = slot;
    }

    public Player getFrom() {
        return from;
    }

    public Player getTo() {
        return to;
    }

    public Slot getSlot() {
        return slot;
    }

    @Override
    public String toString() {
        return slot.getQuantity() + " " + slot.getItem().getName() + " from " + from.getUser().getUsername();
    }
    public String toString2() {
        return slot.getQuantity() + " " + slot.getItem().getName() + " sent to " + to.getUser().getUsername();
    }
}
