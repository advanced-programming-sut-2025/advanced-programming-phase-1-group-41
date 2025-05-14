package org.example.models;

import org.example.models.items.Slot;

public class Trade {
    private final Player from;
    private final Player to;
    private final Slot item;
    private final boolean paidInMoney;
    private final int price;
    private final Slot targetItem;
    private final boolean isRequest;

    public Trade(Player from, Player to, Slot item, int price, boolean isRequest) {
        this.from = from;
        this.to = to;
        this.item = item;
        this.paidInMoney = true;
        this.price = price;
        this.targetItem = null;
        this.isRequest = isRequest;
    }
    public Trade(Player from, Player to, Slot item, Slot targetItem, boolean isRequest) {
        this.from = from;
        this.to = to;
        this.item = item;
        this.paidInMoney = false;
        this.price = 0;
        this.targetItem = targetItem;
        this.isRequest = isRequest;
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

    public int getPrice() {
        return price;
    }

    public Slot getTargetItem() {
        return targetItem;
    }

    public boolean isRequest() {return isRequest;}

    @Override
    public String toString() {
        if(paidInMoney){
            if(isRequest){
                return from.getUser().getUsername() + ": requests " + item.getQuantity() + " " + item.getItem().getName()
                        + " purchased with " + price + " money.";
            }else{
                return from.getUser().getUsername() + ": offers " + item.getQuantity() + " " + item.getItem().getName()
                        + " purchased with " + price + " money.";
            }
        }else{
            if(isRequest){
                assert targetItem != null;
                return from.getUser().getUsername() + ": requests " + item.getQuantity() + " " + item.getItem().getName()
                        + " changed with " + targetItem.getQuantity() + " " + item.getItem().getName() + ".";
            }else{
                assert targetItem != null;
                return from.getUser().getUsername() + ": offers " + item.getQuantity() + " " + item.getItem().getName()
                        + " changed with " + targetItem.getQuantity() + " " + item.getItem().getName() + ".";
            }
        }
    }
}
