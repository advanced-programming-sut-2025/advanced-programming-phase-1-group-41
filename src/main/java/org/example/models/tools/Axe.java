package org.example.models.tools;

public class Axe implements Tool{
    ToolLevel level;

    public Axe() {
        this.level = ToolLevel.Default;
    }

    public ToolLevel getLevel() {
        return level;
    }

    public void increaseLevel() {
        int levelNum = Math.max(ToolLevel.values().length-1,level.ordinal()+1);
        level = ToolLevel.values()[levelNum];
    }


    @Override
    public String getName() {
        return "Axe";
    }

    @Override
    public String getChar() {
        return "Ax";
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
