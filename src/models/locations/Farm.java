package models.locations;

import models.foragings.Foraging;
import models.objects.Rock;
import models.buildings.Building;

import java.util.ArrayList;

public enum Farm {
    Mountain(),
    Swamp(),
    Jungle(),
    ;


    ArrayList<Building> buildings;
    ArrayList<Foraging> foragings;
}
