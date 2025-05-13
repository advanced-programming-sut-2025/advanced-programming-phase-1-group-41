package org.example.models.items.craftablemachines;

import org.example.models.animals.Fish;
import org.example.models.animals.FishType;
import org.example.models.items.CraftableMachine;
import org.example.models.items.Slot;
import org.example.models.items.craftableitems.SmokedFish;

public class FishSmoker extends Machine {
    Fish fish;

    public FishSmoker(Fish fish) {
        super(1, CraftableMachine.FishSmoker);
        this.fish = fish;
        slots.add(new Slot(fish, 1));
        receivedItems.add(new Slot(fish, 1));
    }

    @Override
    public void setProduce() {
        SmokedFish sf = new SmokedFish(fish);
        System.out.println(sf.getName()+" "+sf.getEnergy());
        this.produce = new Slot(sf, 1);
    }
}
