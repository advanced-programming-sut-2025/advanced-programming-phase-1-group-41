package org.example.models.foragings;

import org.example.models.Season;

import java.util.ArrayList;

public enum TreeType {
//    Apricot(),
//    Cherry(),
//    Banana()
    //etc
    ;
    String name;
    SeedType seed;
    boolean eatable;
    ArrayList<Season> seasonsToGrow;
    ArrayList<Integer> stagesCycleTime;
    boolean isStruckByLightning;
//    boolean oneTime;
//    boolean canBecomeGiant;
    int price;

    TreeType(String name, SeedType seed, boolean eatable, ArrayList<Season> seasonsToGrow, ArrayList<Integer> stagesCycleTime, int price) {
        this.name = name;
        this.seed = seed;
        this.eatable = eatable;
        this.seasonsToGrow = seasonsToGrow;
        this.stagesCycleTime = stagesCycleTime;
        this.price = price;
        this.isStruckByLightning = false;
    }
}
