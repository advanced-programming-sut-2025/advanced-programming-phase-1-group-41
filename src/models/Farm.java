package models;

import java.util.ArrayList;

public enum Farm {
    Mountain(),
    Swamp(),
    Jungle(),
    ;

    Greenhouse greenhouse = null;
    Cottage cottage = null;
    ArrayList<Point> Rock;
    ArrayList<Point> Tree;
    ArrayList<Point> water;
}
