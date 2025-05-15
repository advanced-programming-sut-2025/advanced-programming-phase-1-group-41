package org.example.models.items.craftablemachines;

import org.example.models.foragings.Crop;
import org.example.models.foragings.CropType;
import org.example.models.foragings.Seed;
import org.example.models.foragings.SeedType;
import org.example.models.items.CraftableItem;
import org.example.models.items.CraftableMachine;
import org.example.models.items.Products.Product;
import org.example.models.items.Products.ProductType;
import org.example.models.items.Slot;

public class OilMaker extends Machine{
    ProductType pigTruffle = null;
    CropType cropType = null;
    SeedType seedType = null;
    boolean isCorn = false;

    public OilMaker( CropType corn, boolean isCorn) {
        super(6, CraftableMachine.OilMaker);
        this.cropType = corn;
        this.isCorn = true;
        slots.add(new Slot(new Crop(corn), 1));
        receivedItems.add(new Slot(new Crop(corn), 0));
    }
    public OilMaker(CropType sunflower) {
        super(1, CraftableMachine.OilMaker);
        this.cropType = sunflower;
        slots.add(new Slot(new Crop(sunflower), 1));
        receivedItems.add(new Slot(new Crop(sunflower), 0));
    }
    public OilMaker( SeedType flowerSeed) {
        super(48, CraftableMachine.OilMaker);
        this.seedType = flowerSeed;
        slots.add(new Slot(new Seed(flowerSeed), 1));
        receivedItems.add(new Slot(new Seed(flowerSeed), 0));
    }
    public OilMaker( ProductType Truffle) {
        super(6, CraftableMachine.OilMaker);
        this.pigTruffle = Truffle;
        slots.add(new Slot(new Product(Truffle), 1));
        receivedItems.add(new Slot(new Product(Truffle), 0));
    }

    @Override
    public void setProduce() {
        if(pigTruffle!=null){
            this.produce = new Slot(CraftableItem.TruffleOil, 1);
            return;
        }
        this.produce = new Slot(CraftableItem.Oil, 1);
    }
}
