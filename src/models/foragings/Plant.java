package models.foragings;

import models.Season;

import java.util.ArrayList;

public enum Plant implements Foraging {
    BlueJazz(),
    Carrot(),
    // etc
    ;


    String name;
    Seed seed;
    boolean eatable;
    ArrayList<Season> seasonsToGrow;
    ArrayList<Integer> stagesCycleTime;
    boolean oneTime;
    boolean canBecomeGiant;
    int price;


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
}
