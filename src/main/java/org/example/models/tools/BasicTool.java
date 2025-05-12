package org.example.models.tools;

import org.example.models.items.Item;
import org.example.models.skills.Skill;

public enum BasicTool implements Tool {
    Hoe(),
    WateringCan(),
    Pickaxe(),
    Axe(),
    TrashCan()
    ;


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
    public String getChar() {
        return "BT";
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public double getPrice() {
        return 0;
    }


}
