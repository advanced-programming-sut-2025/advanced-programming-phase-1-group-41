package org.example.models.items.craftablemachines;

import org.example.models.items.CraftableItem;
import org.example.models.items.CraftableMachine;
import org.example.models.items.Products.Product;
import org.example.models.items.Slot;

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
