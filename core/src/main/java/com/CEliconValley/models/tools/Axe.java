package com.CEliconValley.models.tools;

public class Axe implements Tool, LevelTool{
    ToolLevel level;

    public Axe() {
        this.level = ToolLevel.Default;
    }

    public ToolLevel getLevel() {
        return level;
    }

    public void increaseLevel() {
        int levelNum = Math.min(ToolLevel.values().length-1,level.ordinal()+1);
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
