package com.CEliconValley.common;

import com.CEliconValley.models.Player;
import com.CEliconValley.models.Trade;
import dev.morphia.annotations.Embedded;

@Embedded
public class TradeData {
    String fromName;
    String toName;
    SlotData item;
    boolean paidInMoney;
    int price;
    SlotData targetItem;
    boolean isRequest;
    boolean isDone;
    boolean isRejected;

    public TradeData() {
    }

    public TradeData(Trade trade) {
        fromName = trade.getFrom().getUser().getUsername();
        toName = trade.getTo().getUser().getUsername();
        this.item = new SlotData(trade.getItem());
        this.paidInMoney = trade.isPaidInMoney();
        this.price = trade.getPrice();
        this.targetItem = new SlotData(trade.getTargetItem());
        this.isRequest = trade.isRequest();
        this.isDone = trade.isDone();
        this.isRejected = trade.isRejected();
    }


    public Trade getTrade(Player from, Player to) {
        return new Trade(
          from, to, isDone, isRejected, isRequest, item.getSlot(), paidInMoney, price, targetItem.getSlot()
        );
    }

    public String getFromName() {
        return fromName;
    }

    public boolean isDone() {
        return isDone;
    }

    public boolean isRejected() {
        return isRejected;
    }

    public boolean isRequest() {
        return isRequest;
    }

    public SlotData getItem() {
        return item;
    }

    public boolean isPaidInMoney() {
        return paidInMoney;
    }

    public int getPrice() {
        return price;
    }

    public SlotData getTargetItem() {
        return targetItem;
    }

    public String getToName() {
        return toName;
    }
}
