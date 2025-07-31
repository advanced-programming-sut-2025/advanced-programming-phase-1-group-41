package com.CEliconValley.models.tools;

public class Shear implements Tool {
    @Override
    public String getName() {
        return "Shear";
    }

    @Override
    public double getPrice() {
        return 0;
    }
    public int getID() {

        return 90600;
    }

    @Override
    public String getChar() {
        return "Sh";
    }
}
