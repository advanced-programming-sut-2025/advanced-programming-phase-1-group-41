package com.CEliconValley.common;

import com.CEliconValley.models.items.Buff;
import com.CEliconValley.models.items.BuffType;

public class BuffData {
    int buffTime;
    int buffAmount;
    int buffTypeInt;

    public BuffData(Buff buff) {
        this.buffTime = buff.getBuffTime();
        this.buffAmount = buff.getBuffAmount();
        this.buffTypeInt = buff.getBuffType().ordinal();
    }

    public Buff getBuff(){
        return new Buff(
            buffTime, buffAmount, BuffType.values()[buffTypeInt]
        );
    }
}
