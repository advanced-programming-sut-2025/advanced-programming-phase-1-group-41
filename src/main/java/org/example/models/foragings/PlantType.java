package org.example.models.foragings;

import org.example.models.Season;

import java.util.ArrayList;

public enum PlantType implements Foraging {
//    BlueJazz(),
//    Carrot(),
    // etc
    ;


    private String name;
    private SeedType seed;
    private boolean eatable;
    public final ArrayList<Season> seasonsToGrow;
    public final ArrayList<Integer> stagesCycleTime;
    private boolean oneTime;
    private boolean canBecomeGiant;
    private int price;


    PlantType(String name, SeedType seed, boolean eatable, ArrayList<Season> seasonsToGrow, ArrayList<Integer> stagesCycleTime, boolean oneTime, boolean canBecomeGiant, int price) {
        this.name = name;
        this.seed = seed;
        this.eatable = eatable;
        this.seasonsToGrow = seasonsToGrow;
        this.stagesCycleTime = stagesCycleTime;
        this.oneTime = oneTime;
        this.canBecomeGiant = canBecomeGiant;
        this.price = price;
    }


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
