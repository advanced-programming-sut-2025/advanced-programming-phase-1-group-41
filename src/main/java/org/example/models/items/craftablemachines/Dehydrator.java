package org.example.models.items.craftablemachines;

import org.example.models.App;
import org.example.models.foragings.Crop;
import org.example.models.foragings.CropType;
import org.example.models.foragings.Fruit;
import org.example.models.foragings.Nature.Mushroom;
import org.example.models.items.CraftableItem;
import org.example.models.items.CraftableMachine;
import org.example.models.items.Slot;
import org.example.models.items.craftableitems.DriedFruit;
import org.example.models.items.craftableitems.DriedMushroom;

public class Dehydrator extends Machine {
    Fruit fruit;
    Mushroom mushroom;
    Crop grape;
    boolean isGrape;
    public Dehydrator(Fruit fruit) {
        super(24- App.getGame().getTime().getHour(), CraftableMachine.Dehydrator);
        this.fruit = fruit;
        this.mushroom = null;
        this.grape = null;
        isGrape = false;
        slots.add(new Slot(fruit, 5));
        receivedItems.add(new Slot(fruit, 0));
    }

    public Dehydrator(Crop crop, boolean isGrape) {
        super(24- App.getGame().getTime().getHour(), CraftableMachine.Dehydrator);
        this.grape = crop;
        this.fruit = null;
        this.mushroom = null;
        this.isGrape = true;
        slots.add(new Slot(grape, 5));
        receivedItems.add(new Slot(grape, 0));
    }

    public Dehydrator(Mushroom mushroom) {
        super(24-App.getGame().getTime().getHour(), CraftableMachine.Dehydrator);
        this.mushroom = mushroom;
        this.fruit = null;
        this.grape = null;
        isGrape = false;
        slots.add(new Slot(mushroom, 5));
        receivedItems.add(new Slot(mushroom, 0));
    }

    @Override
    public void setProduce() {
        if(isGrape){
            this.produce = new Slot(CraftableItem.Raisin, 1);
            return;
        }
        if(fruit != null){
            this.produce = new Slot(new DriedFruit(this.fruit), 1);
            return;
        }
        if(mushroom != null){
            this.produce = new Slot(new DriedMushroom(mushroom), 1);
            return;
        }
    }
}
