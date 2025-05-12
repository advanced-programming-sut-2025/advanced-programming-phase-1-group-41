package org.example.models.tools;

public class WateringCan implements Tool{

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
        int levelNum = Math.max(ToolLevel.values().length - 1, level.ordinal() + 1);
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
}
