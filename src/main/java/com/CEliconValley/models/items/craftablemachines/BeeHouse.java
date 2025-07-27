package com.CEliconValley.models.items.craftablemachines;

import com.CEliconValley.models.items.CraftableItem;
import com.CEliconValley.models.items.CraftableMachine;
import com.CEliconValley.models.items.Slot;

import java.util.ArrayList;

public class BeeHouse extends Machine{
    public BeeHouse() {
        super(4*24, CraftableMachine.BeeHouse);
    }

    public BeeHouse(int processTime, Slot produce, ArrayList<Slot> receivedItems, ArrayList<Slot> slots) {
        super(CraftableMachine.BeeHouse, processTime, produce, receivedItems, slots);
    }



    @Override
    public void setProduce() {
        produce = new Slot(CraftableItem.Honey, 1);
    }
}
