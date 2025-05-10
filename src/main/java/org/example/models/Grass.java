package org.example.models;

public class Grass implements ObjectMap{
    @Override
    public String getChar() {
        return Colors.colorize(2,0,"WW");
    }
}
