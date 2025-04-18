package models.foragings;

import models.Season;

import java.util.ArrayList;

public enum Plant implements Foraging {
    BlueJazz(),
    Carrot(),
    // etc
    ;


    private String name;
    private Seed seed;
    private boolean eatable;
    public final ArrayList<Season> seasonsToGrow;
    public final ArrayList<Integer> stagesCycleTime;
    private boolean oneTime;
    private boolean canBecomeGiant;
    private int price;


    Plant(String name, Seed seed, boolean eatable, ArrayList<Season> seasonsToGrow, ArrayList<Integer> stagesCycleTime, boolean oneTime, boolean canBecomeGiant, int price) {
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

    public Seed getSeed() {
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
