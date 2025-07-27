package com.CEliconValley.models.items.craftablemachines;

import com.CEliconValley.models.items.CraftableItem;
import com.CEliconValley.models.items.CraftableMachine;
import com.CEliconValley.models.items.Products.Product;
import com.CEliconValley.models.items.Slot;

import java.util.ArrayList;

public class Loom extends Machine {
    public Loom(Product wool){
        super(4, CraftableMachine.Loom);
        slots.add(new Slot(wool , 1));
        receivedItems.add(new Slot(wool, 0));
    }

    public Loom(int processTime, Slot produce,
                ArrayList<Slot> receivedItems, ArrayList<Slot> slots){
        super(CraftableMachine.Loom, processTime, produce, receivedItems, slots);
    }

    @Override
    public void setProduce() {
        produce = new Slot(CraftableItem.Cloth , 1);
    }
}
