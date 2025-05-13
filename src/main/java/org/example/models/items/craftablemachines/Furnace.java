package org.example.models.items.craftablemachines;

import org.bouncycastle.crypto.Mac;
import org.example.models.foragings.Nature.Mineral;
import org.example.models.foragings.Nature.MineralType;
import org.example.models.items.CraftableItem;
import org.example.models.items.CraftableMachine;
import org.example.models.items.Item;
import org.example.models.items.Slot;

import java.util.ArrayList;

import static org.example.models.foragings.Nature.MineralType.*;

public class Furnace extends Machine {
    public Furnace(MineralType mineralType) {
        super(4, CraftableMachine.Furnace);
        slots.add(new Slot(new Mineral(mineralType),5));
        slots.add(new Slot(new Mineral(MineralType.Coal),1));
        receivedItems.add(new Slot(new Mineral(mineralType),0));
        receivedItems.add(new Slot(new Mineral(MineralType.Coal),0));
    }


    public void setProduce() {
        Item item = slots.get(0).getItem();
        if(item.getName().equals(CopperOre.getName())) {
            this.produce = new Slot(CraftableItem.CopperBar,5);
        }else if(item.getName().equals(IronOre.getName())) {
            this.produce = new Slot(CraftableItem.IronBar,5);
        }else if(item.getName().equals(GoldOre.getName())) {
            this.produce = new Slot(CraftableItem.GoldBar,5);
        }else if(item.getName().equals(IridiumOre.getName())) {
            this.produce = new Slot(CraftableItem.IridiumBar,5);
        }
    }

}
