package org.example.models.buildings.Nature;

import org.example.models.Season;
import org.example.models.foragings.SeedType;

import java.util.ArrayList;

public enum TreeType {
    Acorns(),
    MapleSeeds(),
    PineCones(),
    MahoganySeeds(),
    MushroomTreeSeeds();
    //etc
    ;
    String name;
    SeedType sapling;
    int totalHarvestTime;
    boolean eatable;
    ArrayList<Season> seasonsToGrow;
    ArrayList<Integer> stagesCycleTime;
    boolean isStruckByLightning;
//    boolean oneTime;
//    boolean canBecomeGiant;
    int price;

//    TreeType(String name, SeedType seed, boolean eatable, ArrayList<Season> seasonsToGrow, ArrayList<Integer> stagesCycleTime, int price) {
//        this.name = name;
//        this.seed = seed;
//        this.eatable = eatable;
//        this.seasonsToGrow = seasonsToGrow;
//        this.stagesCycleTime = stagesCycleTime;
//        this.price = price;
//        this.isStruckByLightning = false;
//    }
}
