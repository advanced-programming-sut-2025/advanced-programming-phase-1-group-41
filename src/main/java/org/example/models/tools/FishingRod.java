package org.example.models.tools;

import org.example.models.items.Item;
import org.example.models.skills.Skill;

public class FishingRod implements Tool {
    private Skill usage;
    private FishingRodLevel level;
    public FishingRod() {
        this.usage = Skill.Fishing;
        this.level = FishingRodLevel.Training;
    }

    public Skill getUsage() {
        return usage;
    }


    public FishingRodLevel getLevel() {
        return level;
    }

    public void increaseLevel() {
        int nextLevel = Math.max(level.ordinal()+1,FishingRodLevel.values().length - 1);
        level = FishingRodLevel.values()[nextLevel];
    }

    @Override
    public String getChar() {
        return "FR";
    }

    @Override
    public String getName() {
        return "Fishing Rod";
    }
}
