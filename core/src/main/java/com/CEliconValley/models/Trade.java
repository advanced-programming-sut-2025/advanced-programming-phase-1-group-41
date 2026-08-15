package com.CEliconValley.models;

import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.ui.TerminalColors;

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

    public Trade(Player from, Player to, boolean isDone, boolean isRejected,
                 boolean isRequest, Slot item, boolean paidInMoney,
                 int price, Slot targetItem) {
        this.from = from;
        this.to = to;
        this.isDone = isDone;
        this.isRejected = isRejected;
        this.isRequest = isRequest;
        this.item = item;
        this.paidInMoney = paidInMoney;
        this.price = price;
        this.targetItem = targetItem;
    }

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

    public void Done() {isDone = true;}

    public void reject() {isRejected = true;}

    @Override
    public String toString() {
        if(paidInMoney){
            if(isRequest){
                return TerminalColors.foreColor(87) + from.getUser().getUsername() + ": requests " + item.getQuantity() + " " + item.getItem().getName()
                        + " purchased with " + price + " money." + TerminalColors.RESET;
            }else{
                return TerminalColors.foreColor(87) + from.getUser().getUsername() + ": offers " + item.getQuantity() + " " + item.getItem().getName()
                        + " purchased with " + price + " money." + TerminalColors.RESET;
            }
        }else{
            if(isRequest){
                assert targetItem != null;
                return TerminalColors.foreColor(87) + from.getUser().getUsername() + ": requests " + item.getQuantity() + " " + item.getItem().getName()
                        + " changed with " + targetItem.getQuantity() + " " + targetItem.getItem().getName() + "." + TerminalColors.RESET;
            }else{
                assert targetItem != null;
                return TerminalColors.foreColor(87) + from.getUser().getUsername() + ": offers " + item.getQuantity() + " " + item.getItem().getName()
                        + " changed with " + targetItem.getQuantity() + " " + targetItem.getItem().getName() + "." + TerminalColors.RESET;
            }
        }
    }
    public String toString2(){
        if(isDone){
            System.out.print(TerminalColors.foreColor(46));
            if(paidInMoney){
                if(isRequest){

                    return TerminalColors.foreColor(40) + "Trade Done: " + from.getUser().getUsername() + " got " + item.getQuantity() + " " + item.getItem().getName()
                            + " purchased with " + price + " money" + " from " + to.getUser().getUsername()+ TerminalColors.RESET;
                }else{
                    return TerminalColors.foreColor(40) + "Trade Done: " + from.getUser().getUsername() + " gave " + item.getQuantity() + " " + item.getItem().getName()
                            + " purchased with " + price + " money" + " to " + to.getUser().getUsername()+ TerminalColors.RESET;
                }
            }else{
                if(isRequest){
                    assert targetItem != null;
                    return TerminalColors.foreColor(40) + "Trade Done: " + from.getUser().getUsername() + " got " + item.getQuantity() + " " + item.getItem().getName()
                            + " and gave " + targetItem.getQuantity() + " " + targetItem.getItem().getName() + " to " + to.getUser().getUsername()+ TerminalColors.RESET;
                }else{
                    assert targetItem != null;
                    return TerminalColors.foreColor(40) + "Trade Done: " + from.getUser().getUsername() + " gave " + item.getQuantity() + " " + item.getItem().getName()
                            + " and got " + targetItem.getQuantity() + " " + targetItem.getItem().getName() + " with " + to.getUser().getUsername()+ TerminalColors.RESET;
                }
            }
        } else if(isRejected){
            System.out.print(TerminalColors.foreColor(124));
            if(paidInMoney){
                if(isRequest){
                    return TerminalColors.foreColor(196) + "Trade Rejected: You requested " + item.getQuantity() + " " + item.getItem().getName()
                            + " purchased with " + price + " money from " + to.getUser().getUsername()+ TerminalColors.RESET;
                }else{
                    return TerminalColors.foreColor(196) + "Trade Rejected: You offered " + item.getQuantity() + " " + item.getItem().getName()
                            + " purchased with " + price + " money to " + to.getUser().getUsername()+ TerminalColors.RESET;
                }
            }else{
                if(isRequest){
                    assert targetItem != null;
                    return TerminalColors.foreColor(196) + "Trade Rejected: You requested " + item.getQuantity() + " " + item.getItem().getName()
                            + " changed with " + targetItem.getQuantity() + " " + targetItem.getItem().getName() + " from " + to.getUser().getUsername()+ TerminalColors.RESET;
                }else{
                    assert targetItem != null;
                    return TerminalColors.foreColor(196) + "Trade Rejected: You offered " + item.getQuantity() + " " + item.getItem().getName()
                            + " changed with " + targetItem.getQuantity() + " " + targetItem.getItem().getName() + " to " + to.getUser().getUsername()+ TerminalColors.RESET;
                }
            }
        } else{
            System.out.print(TerminalColors.foreColor(123));
            if(paidInMoney){
                if(isRequest){
                    return TerminalColors.foreColor(190) + "Trade Pending: " + from.getUser().getUsername() + " requests " + item.getQuantity() + " " + item.getItem().getName()
                            + " purchased with " + price + " money from " + to.getUser().getUsername()+ TerminalColors.RESET;
                }else{
                    return TerminalColors.foreColor(190) + "Trade Pending: " + from.getUser().getUsername() + " offers " + item.getQuantity() + " " + item.getItem().getName()
                            + " purchased with " + price + " money to " + to.getUser().getUsername()+ TerminalColors.RESET;
                }
            }else{
                if(isRequest){
                    assert targetItem != null;
                    return TerminalColors.foreColor(190) + "Trade Pending: " + from.getUser().getUsername() + " requests " + item.getQuantity() + " " + item.getItem().getName()
                            + " changed with " + targetItem.getQuantity() + " " + targetItem.getItem().getName() + " from " + to.getUser().getUsername()+ TerminalColors.RESET;
                }else{
                    assert targetItem != null;
                    return TerminalColors.foreColor(190) + "Trade Pending: " + from.getUser().getUsername() + " offers " + item.getQuantity() + " " + item.getItem().getName()
                            + " changed with " + targetItem.getQuantity() + " " + targetItem.getItem().getName() + " to " + to.getUser().getUsername()+ TerminalColors.RESET;
                }
            }
        }
    }


    public boolean isDone() {
        return isDone;
    }

    public boolean isRejected() {
        return isRejected;
    }
}
