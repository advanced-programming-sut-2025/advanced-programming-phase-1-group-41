package com.CEliconValley.models.tools;

public class Scythe implements Tool{

    @Override
    public String getName() {
        return "Scythe";
    }

    @Override
    public String getChar() {
        return "Sy";
    }
    public int getID() {
        return 90601;
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
