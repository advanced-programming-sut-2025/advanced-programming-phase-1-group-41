package com.CEliconValley.models.tools;

import com.CEliconValley.models.App;
import com.CEliconValley.models.items.Slot;

public class Pickaxe implements Tool, LevelTool{
    ToolLevel level;

    public Pickaxe() {
        level = ToolLevel.Iridium;
    }

    public ToolLevel getLevel() {
        return level;
    }

    public void increaseLevel() {
        int levelNum = Math.min(ToolLevel.values().length-1,level.ordinal()+1);
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
