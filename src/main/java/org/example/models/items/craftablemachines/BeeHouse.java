package org.example.models.items.craftablemachines;

import org.example.models.items.CraftableItem;
import org.example.models.items.CraftableMachine;
import org.example.models.items.Slot;

public class BeeHouse extends Machine{
    public BeeHouse() {
        super(4*24, CraftableMachine.BeeHouse);
    }

    @Override
    public void setProduce() {
        produce = new Slot(CraftableItem.Honey, 1);
    }
}
