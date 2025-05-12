package org.example.models.tools;

public class Scythe implements Tool{

    @Override
    public String getName() {
        return "Scythe";
    }

    @Override
    public String getChar() {
        return "Sy";
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
