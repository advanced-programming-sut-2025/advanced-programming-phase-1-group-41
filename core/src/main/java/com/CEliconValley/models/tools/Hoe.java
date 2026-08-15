package com.CEliconValley.models.tools;

public class Hoe implements Tool, LevelTool{
    ToolLevel level;

    public Hoe() {
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
        return "Hoe";
    }

    @Override
    public String getChar() {
        return "Ho";
    }

    @Override
    public double getPrice() {
        return 0;
    }
    public int getID() {
        switch (this.getLevel()) {
            case Default:
                return 90100;
            case Copper:
                return 90101;
            case Iron:
                return 90102;
            case Gold:
                return 90103;
            case Iridium:
                return 90104;
        }
        return -1;
    }
}
