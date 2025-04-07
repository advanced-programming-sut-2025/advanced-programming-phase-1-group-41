package models.offsprings;

import enums.Season;
import models.Result;
import models.TimeLine;

import java.util.regex.Matcher;

public abstract class Offspring {
    String name;
    Seed seed;
    TimeLine cycleTime;
    Season seasonToGrow;

    boolean eatable;

    // TODO shahab
    int energy;

    public Result getPriceByQualtity;

    public Result getDetails(Matcher matcher);


}
