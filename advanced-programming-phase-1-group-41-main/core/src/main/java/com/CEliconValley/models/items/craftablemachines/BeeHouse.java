package com.CEliconValley.models.items.craftablemachines;

import com.CEliconValley.models.items.CraftableItem;
import com.CEliconValley.models.items.CraftableMachine;
import com.CEliconValley.models.items.Slot;

public class BeeHouse extends Machine{
    public BeeHouse() {
        super(4*24, CraftableMachine.BeeHouse);
    }

    @Override
    public void setProduce() {
        produce = new Slot(CraftableItem.Honey, 1);
    }
}
