package com.CEliconValley.models.items.craftablemachines;

import com.CEliconValley.models.foragings.Nature.Wood;
import com.CEliconValley.models.items.CraftableItem;
import com.CEliconValley.models.items.CraftableMachine;
import com.CEliconValley.models.items.Slot;

public class Kiln extends Machine{
    public Kiln() {
        super(1, CraftableMachine.CharcoalKiln);
        slots.add(new Slot(new Wood(),10));
        receivedItems.add(new Slot(new Wood(),0));
    }


    @Override
    public void setProduce() {
        this.produce = new Slot(CraftableItem.Coal,1);
    }
}
