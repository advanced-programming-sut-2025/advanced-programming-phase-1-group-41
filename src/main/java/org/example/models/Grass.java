package org.example.models;

public class Grass implements ObjectMap{
    @Override
    public String getChar() {
        return Colors.colorize(2,0,"ww");
    }

    @Override
    public String getName() {
        return "Grass";
    }

    //TODO Make it TRUE!
    private boolean isFarmland = true;

    public boolean isFarmland() {
        return isFarmland;
    }
    public void setFarmland(boolean isFarmland) {
        this.isFarmland = isFarmland;
    }
}
