package com.CEliconValley.common;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.buildings.marketplaces.Marketplace;
import com.CEliconValley.models.items.Slot;
import dev.morphia.annotations.Embedded;

import java.util.ArrayList;


@Embedded
public class MarketPlaceData {
    ArrayList<SlotData> itemsForSaleData;
    String name;
    boolean isOpen;
    ArrayList<CellData> doors;
    public MarketPlaceData() {
    }

    public MarketPlaceData(Marketplace marketplace) {
        this.name = marketplace.getName();
        itemsForSaleData = new ArrayList<>();
        for (Slot slot : marketplace.getItemsForSale()) {
            itemsForSaleData.add(new SlotData(slot));
        }
        this.isOpen = marketplace.isOpen;
        this.doors = new ArrayList<>();
        for (Cell door : marketplace.doors) {
            this.doors.add(new CellData(door));
        }
    }

    public ArrayList<SlotData> getItemsForSaleData() {
        return itemsForSaleData;
    }

    public String getName() {
        return name;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public ArrayList<CellData> getDoors() {
        return doors;
    }
}
