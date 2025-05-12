package org.example.models.tools;

import org.example.models.items.Item;

public enum NormalTools implements Tool {
    Scythe,
    MilkPail,
    Shear;

    public static Item parseNormalTool(String name) {
        return switch (name) {
            case "Scythe" -> Scythe;
            case "MilkPail" -> MilkPail;
            case "Shear" -> Shear;
            default -> null;
        };
    }

    @Override
    public String getChar() {
        return "NT";
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
