package org.example.models.items.craftablemachines;

import org.example.models.foragings.Crop;
import org.example.models.foragings.Fruit;
import org.example.models.items.CraftableItem;
import org.example.models.items.CraftableMachine;
import org.example.models.items.Slot;
import org.example.models.items.craftableitems.Jelly;
import org.example.models.items.craftableitems.Pickles;

public class PreserveJar extends Machine{
    Fruit fruit;
    Crop crop;

    public PreserveJar(Crop crop) {
        super(6, CraftableMachine.PreservesJar);
        this.crop = crop;
        this.fruit = null;
        slots.add(new Slot(crop, 1));
        receivedItems.add(new Slot(crop, 0));
    }

    public PreserveJar(Fruit fruit) {
        super(3, CraftableMachine.PreservesJar);
        this.crop = null;
        this.fruit = fruit;
        slots.add(new Slot(fruit, 1));
        receivedItems.add(new Slot(fruit, 0));
    }

    @Override
    public void setProduce() {
        if(crop != null){
            this.produce = new Slot(new Pickles(crop), 1);
            return;
        }
        if(fruit != null){
            this.produce = new Slot(new Jelly(fruit), 1);
        }
    }
}
