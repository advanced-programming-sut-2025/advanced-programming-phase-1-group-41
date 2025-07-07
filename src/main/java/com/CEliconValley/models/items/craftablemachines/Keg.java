package com.CEliconValley.models.items.craftablemachines;

import com.CEliconValley.models.foragings.Crop;
import com.CEliconValley.models.foragings.CropType;
import com.CEliconValley.models.foragings.Fruit;
import com.CEliconValley.models.foragings.Nature.Vegetable;
import com.CEliconValley.models.items.CraftableItem;
import com.CEliconValley.models.items.CraftableMachine;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.items.craftableitems.Juice;
import com.CEliconValley.models.items.craftableitems.Wine;

public class Keg extends Machine{
    Crop crop = null;
    boolean isWheat = false;
    boolean isRice = false;
    boolean isCoffee = false;
    boolean isHops = false;
    Fruit fruit = null;
    CraftableItem honey;
    Vegetable vegetable = null;
    public Keg(Crop crop) {
        super(1, CraftableMachine.Keg);
        if(crop.getCropType()==CropType.Wheat){
            this.crop = crop;
            isWheat = true;
            this.processTime = 24;
            slots.add(new Slot(crop, 1));
            receivedItems.add(new Slot(crop, 0));
        }
        else if(crop.getCropType()==CropType.Rice){
            this.crop = crop;
            this.isRice = true;
            this.processTime = 10;
            slots.add(new Slot(crop, 1));
            receivedItems.add(new Slot(crop, 0));
        }
        else if(crop.getCropType()==CropType.CoffeeBean){
            this.crop = crop;
            isCoffee = true;
            this.processTime = 2;
            slots.add(new Slot(crop, 5));
            receivedItems.add(new Slot(crop, 0));
        }
        else if(crop.getCropType()==CropType.Hops){
            this.crop = crop;
            isHops = true;
            this.processTime = 72;
            slots.add(new Slot(crop, 1));
            receivedItems.add(new Slot(crop, 0));
        }
    }
    public Keg(Fruit fruit){
        super(7*24, CraftableMachine.Keg);
        this.fruit = fruit;
        slots.add(new Slot(fruit, 1));
        receivedItems.add(new Slot(fruit, 0));
    }
    public Keg(CraftableItem honey){
        super(10, CraftableMachine.Keg);
        this.honey = honey;
        slots.add(new Slot(honey, 1));
        receivedItems.add(new Slot(honey, 0));
    }
    public Keg(Vegetable vegetable){
        super(4*24, CraftableMachine.Keg);
        this.vegetable = vegetable;
        slots.add(new Slot(vegetable, 1));
        receivedItems.add(new Slot(vegetable, 0));
    }

    @Override
    public void setProduce() {
        if(isWheat){
            this.produce = new Slot(CraftableItem.Beer, 1);
            return;
        }
        if(isRice){
            this.produce = new Slot(CraftableItem.Vineger, 1);
            return;
        }
        if(isCoffee){
            this.produce = new Slot(CraftableItem.Coffee, 1);
            return;
        }
        if(isHops){
            this.produce = new Slot(CraftableItem.PaleAle, 1);
            return;
        }
        if(this.fruit!=null){
            this.produce= new Slot(new Wine(fruit), 1);
            return;
        }
        if(this.honey!=null){
            this.produce = new Slot(CraftableItem.Mead, 1);
            return;
        }
        if(this.vegetable!=null){
            this.produce = new Slot(new Juice(vegetable), 1);
            return;
        }
    }
}
