package com.CEliconValley.common;

import com.CEliconValley.models.buildings.Refrigerator;
import com.CEliconValley.models.items.Slot;
import dev.morphia.annotations.Embedded;

import java.util.ArrayList;

@Embedded
public class RefrigeratorData {
    ArrayList<SlotData> slotsData;

    public RefrigeratorData() {
    }

    public RefrigeratorData(Refrigerator refrigerator) {
        slotsData = new ArrayList<>();
        for (Slot slot : refrigerator.getSlots()) {
            if(slot == null || slot.getItem() == null || slot.getQuantity() <= 0) continue;
            slotsData.add(new SlotData(slot));
        }
    }

    public Refrigerator getRefrigerator() {
        ArrayList<Slot> slots = new ArrayList<>();
        if(slotsData == null) slotsData = new ArrayList<>();
        for (SlotData slotData : slotsData) {
            slots.add(slotData.getSlot());
        }
        Refrigerator refrigerator = new Refrigerator(slots);
        return refrigerator;
    }
}
