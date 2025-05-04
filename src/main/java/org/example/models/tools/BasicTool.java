package org.example.models.tools;

import org.example.models.skills.Skill;

public enum BasicTool implements Tool {
    Hoe(Skill.Farming),
    WateringCan(Skill.Farming),
    Pickaxe(Skill.Mining),
    Axe(Skill.Foraging),
    TrashCan(null)
    ;

    private Skill usage;
    private ToolLevel toolLevel;


    BasicTool(Skill usage) {
        this.usage = usage;
        this.toolLevel = ToolLevel.Default;
    }

    public ToolLevel getToolLevel() {
        return toolLevel;
    }

    public Skill getUsage() {
        return usage;
    }
}
