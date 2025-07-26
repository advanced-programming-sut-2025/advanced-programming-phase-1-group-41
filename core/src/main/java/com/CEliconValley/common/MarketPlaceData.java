package com.CEliconValley.common;

import com.CEliconValley.models.buildings.marketplaces.Marketplace;
import com.CEliconValley.models.items.Slot;

import java.util.ArrayList;

public class MarketPlaceData {
    ArrayList<SlotData> itemsForSaleData;
    String name;
    public MarketPlaceData(Marketplace marketplace) {
        this.name = marketplace.getName();
        itemsForSaleData = new ArrayList<>();
        for (Slot slot : marketplace.getItemsForSale()) {
            itemsForSaleData.add(new SlotData(slot));
        }
    }
}
