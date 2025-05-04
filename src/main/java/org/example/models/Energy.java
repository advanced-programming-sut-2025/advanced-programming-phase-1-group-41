package org.example.models;

public class Energy {
    private int value;


    public Energy(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void increaseEnergy(int value) {
        this.value += value;
        if(this.value > 200){
            this.value = 200;
        }
    }
}
