package models;

import models.items.Slot;

public class Trade {
    Player from;
    Player to;
    Slot item;
    boolean paidInMoney;
    int amount;
    Slot targetItem;
}
