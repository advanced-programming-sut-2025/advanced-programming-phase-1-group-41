package org.example.models.tools;

import org.example.models.App;
import org.example.models.items.Item;
import org.example.models.items.Slot;

public class TrashCan implements Tool {
    private ToolLevel level;

    public TrashCan() {
        this.level = ToolLevel.Iridium;
    }

    public ToolLevel getLevel() {
        return level;
    }

    public void increaseLevel() {
        int levelNum = Math.max(ToolLevel.values().length-1,level.ordinal()+1);
        level = ToolLevel.values()[levelNum];
    }

    @Override
    public String getChar() {
        return "TC";
    }

    // TODO check if the name should be this
    @Override
    public String getName() {
        return "TrashCan";
    }

    public static TrashCan findTrashCan(){
        for (Slot slot : App.getGame().getCurrentPlayer().getInventory().getSlots()) {
            if(slot.getItem() instanceof TrashCan){
                return (TrashCan) slot.getItem();
            }
        }
        return null;
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
