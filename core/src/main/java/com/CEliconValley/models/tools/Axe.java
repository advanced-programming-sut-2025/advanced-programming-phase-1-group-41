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

    @Override
    public int getID() {
        switch (this.getLevel()) {
            case Default:
                return 90300;
            case Copper:
                return 90301;
            case Iron:
                return 90302;
            case Gold:
                return 90303;
            case Iridium:
                return 90304;
        }
        return -1;
    }
}
