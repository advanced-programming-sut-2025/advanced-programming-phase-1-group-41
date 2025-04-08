package models.foragings;

import models.Season;

import java.util.ArrayList;

public enum Tree implements Foraging {
    Apricot(),
    Cherry(),
    Banana()
    //etc
    ;
    String name;
    Seed seed;
    boolean eatable;
    ArrayList<Season> seasonsToGrow;
    ArrayList<Integer> stagesCycleTime;
    boolean isStruckByLightning;
//    boolean oneTime;
//    boolean canBecomeGiant;
    int price;

    Tree(String name, Seed seed, boolean eatable, ArrayList<Season> seasonsToGrow, ArrayList<Integer> stagesCycleTime, int price) {
        this.name = name;
        this.seed = seed;
        this.eatable = eatable;
        this.seasonsToGrow = seasonsToGrow;
        this.stagesCycleTime = stagesCycleTime;
        this.price = price;
        this.isStruckByLightning = false;
    }
}
