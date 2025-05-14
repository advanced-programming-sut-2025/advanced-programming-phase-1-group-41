package org.example.models.tools;

import org.example.models.items.Item;
import org.example.models.skills.Skill;

public enum BasicTool implements Tool {
    Hoe(new Hoe()),
    WateringCan(new WateringCan()),
    Pickaxe(new Pickaxe()),
    Axe(new Axe()),
    TrashCan(new TrashCan()),
    ;

    private Item item;

    BasicTool(Item item) {
        this.item = item;
    }

    public static Item parseBasicTool(String name) {
        for (BasicTool value : BasicTool.values()) {
            if(value.item.getName().equalsIgnoreCase(name)){
                return value.item;
            }
        }
        return null;
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
