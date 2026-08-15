package com.CEliconValley.models.items.craftablemachines;

import com.CEliconValley.models.foragings.Crop;
import com.CEliconValley.models.foragings.CropType;
import com.CEliconValley.models.foragings.Seed;
import com.CEliconValley.models.foragings.SeedType;
import com.CEliconValley.models.items.CraftableItem;
import com.CEliconValley.models.items.CraftableMachine;
import com.CEliconValley.models.items.Products.Product;
import com.CEliconValley.models.items.Products.ProductType;
import com.CEliconValley.models.items.Slot;

import java.util.ArrayList;

public class OilMaker extends Machine{
    boolean isCorn = false;
    boolean isPigTruffle = false;


    public OilMaker(int processTime, Slot produce,
                    ArrayList<Slot> receivedItems, ArrayList<Slot> slots, boolean isCorn, boolean isPigTruffle) {
        super(CraftableMachine.OilMaker, processTime, produce, receivedItems, slots);
        this.isCorn = isCorn;
        this.isPigTruffle = isPigTruffle;
    }

    public OilMaker( CropType corn, boolean isCorn) {
        super(6, CraftableMachine.OilMaker);
        this.isCorn = true;
        slots.add(new Slot(new Crop(corn), 1));
        receivedItems.add(new Slot(new Crop(corn), 0));
    }
    public OilMaker(CropType sunflower) {
        super(1, CraftableMachine.OilMaker);
        slots.add(new Slot(new Crop(sunflower), 1));
        receivedItems.add(new Slot(new Crop(sunflower), 0));
    }
    public OilMaker( SeedType flowerSeed) {
        super(48, CraftableMachine.OilMaker);
        slots.add(new Slot(new Seed(flowerSeed), 1));
        receivedItems.add(new Slot(new Seed(flowerSeed), 0));
    }
    public OilMaker( ProductType Truffle) {
        super(6, CraftableMachine.OilMaker);
        this.isPigTruffle = true;
        slots.add(new Slot(new Product(Truffle), 1));
        receivedItems.add(new Slot(new Product(Truffle), 0));
    }

    @Override
    public void setProduce() {
        if(isPigTruffle){
            this.produce = new Slot(CraftableItem.TruffleOil, 1);
            return;
        }
        this.produce = new Slot(CraftableItem.Oil, 1);
    }


    public boolean isCorn() {
        return isCorn;
    }

    public boolean isPigTruffle() {
        return isPigTruffle;
    }
}
