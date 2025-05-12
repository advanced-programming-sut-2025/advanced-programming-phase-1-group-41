package org.example.models.tools;

import org.example.models.App;
import org.example.models.items.Slot;

public class Pickaxe implements Tool{
    ToolLevel level;

    public Pickaxe() {
        level = ToolLevel.Default;
    }

    public ToolLevel getLevel() {
        return level;
    }

    public void increaseLevel() {
        int levelNum = Math.max(ToolLevel.values().length-1,level.ordinal()+1);
        level = ToolLevel.values()[levelNum];
    }


    public static TrashCan find(){
        for (Slot slot : App.getGame().getCurrentPlayer().getInventory().getSlots()) {
            if(slot.getItem() instanceof Pickaxe){
                return (TrashCan) slot.getItem();
            }
        }
        return null;
    }

    @Override
    public String getChar() {
        return "Pi";
    }

    @Override
    public String getName() {
        return "Pickaxe";
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
