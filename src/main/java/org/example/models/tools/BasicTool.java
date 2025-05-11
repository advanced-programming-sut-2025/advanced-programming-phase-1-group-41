package org.example.models.tools;

import org.example.models.items.Item;
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

    public static Item parseBasicTool(String name) {
        return switch (name){
            case "Hoe" -> BasicTool.Hoe;
            case "WateringCan" -> BasicTool.WateringCan;
            case "Pickaxe" -> BasicTool.Pickaxe;
            case "Axe" -> BasicTool.Axe;
            case "TrashCan" -> BasicTool.TrashCan;
            default -> null;
        };
    }

    @Override
    public String getName() {
        return this.name();
    }
}
