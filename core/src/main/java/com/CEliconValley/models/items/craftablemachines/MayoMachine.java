package com.CEliconValley.models.items.craftablemachines;

import com.CEliconValley.models.items.CraftableItem;
import com.CEliconValley.models.items.CraftableMachine;
import com.CEliconValley.models.items.Products.Product;
import com.CEliconValley.models.items.Products.ProductType;
import com.CEliconValley.models.items.Slot;

public class MayoMachine extends Machine{
    Product product;
    int which = 0;
    public MayoMachine(Product product) {
        super(3, CraftableMachine.MayonnaiseMachine);
        if(product.getProductType()== ProductType.ChickenEgg){
            which =1;
        }else if(product.getProductType()==ProductType.BigChickenEgg){
            which = 2;
        }else if(product.getProductType()==ProductType.DuckEgg){
            which = 3;
        }else if(product.getProductType()==ProductType.DinoEgg){
            which = 4;
        }
        this.product = product;
        this.slots.add(new Slot(product, 1));
        this.receivedItems.add(new Slot(product, 0));
    }

    @Override
    public void setProduce() {
        switch (which){
            case 1 -> {
                produce = new Slot(CraftableItem.Mayonnaise, 1);
            }
            case 2 -> {
                produce = new Slot(CraftableItem.LargeMayonnaise, 1);
            }
            case 3 -> {
                produce = new Slot(CraftableItem.DuckMayonnaise, 1);
            }
            case 4 -> {
                produce = new Slot(CraftableItem.DinosaurMayonnaise, 1);
            }
        }
    }
}
