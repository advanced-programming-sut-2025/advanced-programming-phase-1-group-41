package org.example.models.tools;

import org.example.models.skills.Skill;

public class FishingRod implements Tool {
    private Skill usage;

    public FishingRod() {
        this.usage = Skill.Fishing;
    }

    public Skill getUsage() {
        return usage;
    }
}
