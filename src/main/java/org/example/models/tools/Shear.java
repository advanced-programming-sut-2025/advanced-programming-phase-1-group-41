package org.example.models.tools;

public class Shear implements Tool {
    @Override
    public String getName() {
        return "Shear";
    }

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public String getChar() {
        return "Sh";
    }
}
