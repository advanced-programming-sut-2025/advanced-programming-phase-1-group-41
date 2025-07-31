package com.CEliconValley.common;

import com.CEliconValley.models.buildings.marketplaces.Marketplace;
import com.CEliconValley.models.items.Slot;
import dev.morphia.annotations.Embedded;

import java.util.ArrayList;


@Embedded
public class MarketPlaceData {
    ArrayList<SlotData> itemsForSaleData;
    String name;

    public MarketPlaceData() {
    }

    public MarketPlaceData(Marketplace marketplace) {
        this.name = marketplace.getName();
        itemsForSaleData = new ArrayList<>();
        for (Slot slot : marketplace.getItemsForSale()) {
            itemsForSaleData.add(new SlotData(slot));
        }
    }

    public ArrayList<SlotData> getItemsForSaleData() {
        return itemsForSaleData;
    }

    public String getName() {
        return name;
    }
}
