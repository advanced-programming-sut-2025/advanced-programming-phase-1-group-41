package com.CEliconValley.models.items;

public class Buff {
    private int buffTime = 0;
    private int buffAmount = 0;
    private final BuffType buffType;


    public Buff(int buffTime, int buffAmount, BuffType buffType) {
        this.buffTime = buffTime;
        this.buffAmount = buffAmount;
        this.buffType = buffType;
    }

    public int getBuffTime() {
        return buffTime;
    }

    public int getBuffAmount() {
        return buffAmount;
    }

    public void hourPass(){
        buffTime--;
    }

    public BuffType getBuffType() {
        return buffType;
    }
}
