package models.locations;

import models.foragings.Foraging;
import models.buildings.Building;

import java.util.ArrayList;

public enum Farm {
    Mountain(),
    Swamp(),
    Jungle(),

    // TODO
    ;


    public final ArrayList<Building> buildings;
    public final ArrayList<Foraging> foragings;

    Farm(ArrayList<Building> buildings) {
        this.buildings = buildings;
        this.foragings = new ArrayList<>();
    }


}
