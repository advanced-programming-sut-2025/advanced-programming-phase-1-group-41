package com.CEliconValley.models.foragings.Nature;

import com.CEliconValley.models.Season;
import com.CEliconValley.models.foragings.SeedType;

import java.util.ArrayList;

public enum PlantType {
//    BlueJazz(),
    Carrot(),
    // etc
    ;


    private String name;
    private SeedType seed;
    private boolean eatable;
    public final ArrayList<Season> seasonsToGrow = new ArrayList<>();
    public final ArrayList<Integer> stagesCycleTime =  new ArrayList<>();
    private boolean oneTime;
    private boolean canBecomeGiant;
    private int price;


//    PlantType(String name, SeedType seed, boolean eatable, ArrayList<Season> seasonsToGrow, ArrayList<Integer> stagesCycleTime, boolean oneTime, boolean canBecomeGiant, int price) {
//        this.name = name;
//        this.seed = seed;
//        this.eatable = eatable;
//        this.seasonsToGrow = seasonsToGrow;
//        this.stagesCycleTime = stagesCycleTime;
//        this.oneTime = oneTime;
//        this.canBecomeGiant = canBecomeGiant;
//        this.price = price;
//    }


    public String getName() {
        return name;
    }

    public SeedType getSeed() {
        return seed;
    }

    public boolean isEatable() {
        return eatable;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public boolean isCanBecomeGiant() {
        return canBecomeGiant;
    }

    public int getPrice() {
        return price;
    }
}
