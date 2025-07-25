package com.CEliconValley.models.items.craftablemachines;

import com.CEliconValley.models.items.CraftableItem;
import com.CEliconValley.models.items.CraftableMachine;
import com.CEliconValley.models.items.Products.Product;
import com.CEliconValley.models.items.Slot;

public class Loom extends Machine {
    public Loom(Product wool){
        super(4, CraftableMachine.Loom);
        slots.add(new Slot(wool , 1));
        receivedItems.add(new Slot(wool, 0));
    }

    @Override
    public void setProduce() {
        produce = new Slot(CraftableItem.Cloth , 1);
    }
}
