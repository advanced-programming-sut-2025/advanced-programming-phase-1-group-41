package com.CEliconValley.common;

import com.CEliconValley.models.Player;
import com.CEliconValley.models.Trade;
import com.CEliconValley.models.items.Slot;

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
}
