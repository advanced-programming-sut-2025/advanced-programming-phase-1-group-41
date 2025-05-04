package org.example.models.items;

public enum Buff {
//    MaxEnergy,
//    Farming,
//    Foraging,
//    Fishing,
//    Mining,
    // etc
    // TODO
    ;
    private int buffTime;
    private int buffAmount;


    Buff(int buffTime, int buffAmount) {
        this.buffTime = buffTime;
        this.buffAmount = buffAmount;
    }

    public int getBuffTime() {
        return buffTime;
    }

    public int getBuffAmount() {
        return buffAmount;
    }
}
