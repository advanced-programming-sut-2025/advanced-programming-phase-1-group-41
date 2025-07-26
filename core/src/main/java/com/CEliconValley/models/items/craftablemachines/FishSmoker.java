package com.CEliconValley.models.items.craftablemachines;

import com.CEliconValley.models.animals.Fish;
import com.CEliconValley.models.items.CraftableMachine;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.items.craftableitems.SmokedFish;

import java.util.ArrayList;

public class FishSmoker extends Machine {
    Fish fish;

    public FishSmoker(Fish fish) {
        super(1, CraftableMachine.FishSmoker);
        this.fish = fish;
        slots.add(new Slot(fish, 1));
        receivedItems.add(new Slot(fish, 1));
    }

    public FishSmoker(int processTime, Slot produce,
                      ArrayList<Slot> receivedItems, ArrayList<Slot> slots, Fish fish) {
        super(CraftableMachine.FishSmoker, processTime, produce, receivedItems, slots);
        this.fish = fish;
    }

    @Override
    public void setProduce() {
        SmokedFish sf = new SmokedFish(fish);
        System.out.println(sf.getName()+" "+sf.getEnergy());
        this.produce = new Slot(sf, 1);
    }

    public Fish getFish() {
        return fish;
    }
}
