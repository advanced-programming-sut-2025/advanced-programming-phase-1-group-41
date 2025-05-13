package org.example.models.items.craftablemachines;

import org.example.models.foragings.Nature.Wood;
import org.example.models.items.CraftableItem;
import org.example.models.items.CraftableMachine;
import org.example.models.items.Slot;

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
