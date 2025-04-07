package models.tools;

import models.skills.Skill;

public class FishingRod implements Tool {
    Skill usage;

    public FishingRod() {
        this.usage = Skill.Fishing;
    }

    public Skill getUsage() {
        return usage;
    }
}
