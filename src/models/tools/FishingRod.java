package models.tools;

import models.skills.Skill;

public class FishingRod implements Tool {
    private Skill usage;

    public FishingRod() {
        this.usage = Skill.Fishing;
    }

    public Skill getUsage() {
        return usage;
    }
}
