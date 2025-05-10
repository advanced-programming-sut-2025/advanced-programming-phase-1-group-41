package org.example.models;

public class Grass implements ObjectMap{
    @Override
    public String getChar() {
        return Colors.BLACK.applyBackGround(Colors.GREEN.applyForeGround("WW"));
    }
    //TODO
}
