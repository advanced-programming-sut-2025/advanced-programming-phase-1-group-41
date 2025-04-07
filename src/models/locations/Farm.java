package models.locations;

import models.objects.Rock;
import models.objects.Tree;
import models.buildings.Building;

import java.util.ArrayList;

public enum Farm {
    Mountain(),
    Swamp(),
    Jungle(),
    ;


    ArrayList<Building> buildings;
    ArrayList<Rock> rocks;
    ArrayList<Tree> trees;
}
