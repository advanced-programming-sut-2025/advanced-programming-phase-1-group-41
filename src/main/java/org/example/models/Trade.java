package org.example.models;

import org.example.models.items.Slot;

public class Trade {
    private Player from;
    private Player to;
    private Slot item;
    private boolean paidInMoney;
    private int amount;
    private Slot targetItem;

    public Trade(Player from, Player to, Slot item, boolean paidInMoney, int amount, Slot targetItem) {
        this.from = from;
        this.to = to;
        this.item = item;
        this.paidInMoney = paidInMoney;
        this.amount = amount;
        this.targetItem = targetItem;
    }

    public Player getFrom() {
        return from;
    }

    public Player getTo() {
        return to;
    }

    public Slot getItem() {
        return item;
    }

    public boolean isPaidInMoney() {
        return paidInMoney;
    }

    public int getAmount() {
        return amount;
    }

    public Slot getTargetItem() {
        return targetItem;
    }
}
