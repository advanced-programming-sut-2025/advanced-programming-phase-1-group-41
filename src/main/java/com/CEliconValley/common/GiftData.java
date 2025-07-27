package com.CEliconValley.common;

import com.CEliconValley.models.Gift;
import com.CEliconValley.models.Player;

public class GiftData {
    String fromName;
    SlotData slotData;
    String toName;

    public GiftData(Gift gift) {
        this.fromName = gift.getFrom().getUser().getUsername();
        this.slotData = new SlotData(gift.getSlot());
        this.toName = gift.getTo().getUser().getUsername();
    }

    public Gift getGift(Player from, Player to){
        return new Gift(from, to, slotData.getSlot());
    }
}
