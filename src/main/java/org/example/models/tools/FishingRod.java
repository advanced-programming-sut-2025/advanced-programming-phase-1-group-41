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
