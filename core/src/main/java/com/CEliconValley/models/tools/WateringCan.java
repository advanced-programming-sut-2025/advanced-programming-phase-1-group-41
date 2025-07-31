package com.CEliconValley.models.tools;

public class WateringCan implements Tool, LevelTool {

    ToolLevel level;
    int tiles;
    public WateringCan() {
        this.level = ToolLevel.Default;
        this.tiles = 40;
    }


    public int getMaxTilesNumberByLevel(){
        return switch (level){
            case Default -> 40;
            case Copper -> 55;
            case Iron -> 70;
            case Gold -> 85;
            case Iridium -> 100;
        };
    }

    public ToolLevel getLevel() {
        return level;
    }

    public void increaseLevel() {
        int levelNum = Math.min(ToolLevel.values().length - 1, level.ordinal() + 1);
        level = ToolLevel.values()[levelNum];
    }

    public void setLevel(ToolLevel level) {
        this.level = level;
    }

    public int getTiles() {
        return tiles;
    }

    public void setTiles(int tiles) {
        this.tiles = tiles;
    }

    public boolean decreaseTiles() {
        if(tiles - 5 < 0){
            return false;
        }
        tiles -= 5;
        return true;
    }

    @Override
    public String getName() {
        return "WateringCan";
    }

    @Override
    public String getChar() {
        return "WC";
    }
    @Override
    public int getID() {
        switch (this.getLevel()) {
            case Default:
                return 90400;
            case Copper:
                return 90401;
            case Iron:
                return 90402;
            case Gold:
                return 90403;
            case Iridium:
                return 90404;
        }
        return -1;
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
