package org.example.models.tools;

import org.example.models.App;
import org.example.models.items.Item;
import org.example.models.items.Slot;
import org.example.models.skills.Skill;

public class FishingRod implements Tool {
    private FishingRodLevel level;
    public FishingRod() {
        this.level = FishingRodLevel.Training;
    }

    public FishingRod(FishingRodLevel level) {
        this.level = level;
    }

    public FishingRodLevel getLevel() {
        return level;
    }

    public void increaseLevel() {
        int nextLevel = Math.min(level.ordinal()+1,FishingRodLevel.values().length - 1);
        level = FishingRodLevel.values()[nextLevel];
    }
    public static FishingRod findFishingRod(){
        for (Slot slot : App.getGame().getCurrentPlayer().getInventory().getSlots()) {
            if(slot.getItem() instanceof FishingRod){
                return (FishingRod) slot.getItem();
            }
        }
        return null;
    }

    @Override
    public String getChar() {
        return "FR";
    }

    @Override
    public String getName() {
        return level.getName();
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
