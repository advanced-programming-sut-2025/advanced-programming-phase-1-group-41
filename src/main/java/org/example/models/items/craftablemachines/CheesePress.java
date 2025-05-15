package org.example.models.items.craftablemachines;

import org.example.models.items.CraftableItem;
import org.example.models.items.CraftableMachine;
import org.example.models.items.Products.Product;
import org.example.models.items.Products.ProductType;
import org.example.models.items.Slot;

public class CheesePress extends Machine{
    Product item = null;
    int which = 0;
    public CheesePress(Product item) {
        super(3, CraftableMachine.CheesePress);
        if(item.getName().equalsIgnoreCase((new Product(ProductType.CowMilk)).getName())){
            this.item = item;
            which = 1;
        }else if(item.getName().equalsIgnoreCase((new Product(ProductType.BigCowMilk)).getName())){
            this.item = item;
            which = 2;
        }if(item.getName().equalsIgnoreCase((new Product(ProductType.GoatMilk)).getName())){
            this.item = item;
            which = 3;
        }else if(item.getName().equalsIgnoreCase((new Product(ProductType.BigGoatMilk)).getName())){
            this.item = item;
            which = 4;
        }
        slots.add(new Slot(item, 1));
        receivedItems.add(new Slot(item, 0));
    }

    @Override
    public void setProduce() {
        switch (which){
            case 1 -> {
                produce = new Slot(CraftableItem.Cheese, 1);
            }
            case 2 -> {
                produce = new Slot(CraftableItem.LargeCheese, 1);
            }
            case 3 -> {
                produce = new Slot(CraftableItem.GoatCheese, 1);
            }
            case 4 -> {
                produce = new Slot(CraftableItem.LargeGoatCheese, 1);
            }
        }
    }
}
