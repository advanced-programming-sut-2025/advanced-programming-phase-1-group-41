package com.CEliconValley.models.items.craftablemachines;

import com.CEliconValley.models.foragings.Crop;
import com.CEliconValley.models.foragings.Fruit;
import com.CEliconValley.models.items.CraftableMachine;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.items.craftableitems.Jelly;
import com.CEliconValley.models.items.craftableitems.Pickles;

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
