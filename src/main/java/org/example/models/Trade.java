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
    private boolean isDone;
    private boolean isRejected;

    public Trade(Player from, Player to, Slot item, int price, boolean isRequest) {
        this.from = from;
        this.to = to;
        this.item = item;
        this.paidInMoney = true;
        this.price = price;
        this.targetItem = null;
        this.isRequest = isRequest;
        isDone = false;
        isRejected = false;
    }
    public Trade(Player from, Player to, Slot item, Slot targetItem, boolean isRequest) {
        this.from = from;
        this.to = to;
        this.item = item;
        this.paidInMoney = false;
        this.price = 0;
        this.targetItem = targetItem;
        this.isRequest = isRequest;
        isDone = false;
        isRejected = false;
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

    public void setDone() {isDone = true;}

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
    public String toString2(){
        if(isDone){
            if(paidInMoney){
                if(isRequest){
                    return "Trade done: " + from.getUser().getUsername() + " got " + item.getQuantity() + " " + item.getItem().getName()
                            + " purchased with " + price + " money." + " from " + to.getUser().getUsername();
                }else{
                    return "Trade done: " + from.getUser().getUsername() + " gave " + item.getQuantity() + " " + item.getItem().getName()
                            + " purchased with " + price + " money." + " to " + to.getUser().getUsername();
                }
            }else{
                if(isRequest){
                    assert targetItem != null;
                    return "Trade done: " + from.getUser().getUsername() + " got " + item.getQuantity() + " " + item.getItem().getName()
                            + " changed with " + targetItem.getQuantity() + " " + item.getItem().getName() + " from " + to.getUser().getUsername();
                }else{
                    assert targetItem != null;
                    return "Trade done: " + from.getUser().getUsername() + " gave " + item.getQuantity() + " " + item.getItem().getName()
                            + " changed with " + targetItem.getQuantity() + " " + item.getItem().getName() + " to " + to.getUser().getUsername();
                }
            }
        } else if(isRejected){
            if(paidInMoney){
                if(isRequest){
                    return "Trade Rejected: You requested " + item.getQuantity() + " " + item.getItem().getName()
                            + " purchased with " + price + " money from " + to.getUser().getUsername();
                }else{
                    return "Trade Rejected: You offered " + item.getQuantity() + " " + item.getItem().getName()
                            + " purchased with " + price + " money to " + to.getUser().getUsername();
                }
            }else{
                if(isRequest){
                    assert targetItem != null;
                    return "Trade Rejected: You requested " + item.getQuantity() + " " + item.getItem().getName()
                            + " changed with " + targetItem.getQuantity() + " " + item.getItem().getName() + " from " + to.getUser().getUsername();
                }else{
                    assert targetItem != null;
                    return "Trade Rejected: You offered " + item.getQuantity() + " " + item.getItem().getName()
                            + " changed with " + targetItem.getQuantity() + " " + item.getItem().getName() + " to " + to.getUser().getUsername();
                }
            }
        } else{
            if(paidInMoney){
                if(isRequest){
                    return "Trade Pending: " + from.getUser().getUsername() + ": requests " + item.getQuantity() + " " + item.getItem().getName()
                            + " purchased with " + price + " money from " + to.getUser().getUsername();
                }else{
                    return "Trade Pending: " + from.getUser().getUsername() + ": offers " + item.getQuantity() + " " + item.getItem().getName()
                            + " purchased with " + price + " money to " + to.getUser().getUsername();
                }
            }else{
                if(isRequest){
                    assert targetItem != null;
                    return "Trade Pending: " + from.getUser().getUsername() + ": requests " + item.getQuantity() + " " + item.getItem().getName()
                            + " changed with " + targetItem.getQuantity() + " " + item.getItem().getName() + " from " + to.getUser().getUsername();
                }else{
                    assert targetItem != null;
                    return "Trade Pending: " + from.getUser().getUsername() + ": offers " + item.getQuantity() + " " + item.getItem().getName()
                            + " changed with " + targetItem.getQuantity() + " " + item.getItem().getName() + " to " + to.getUser().getUsername();
                }
            }
        }
    }
}
