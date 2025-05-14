package org.example.models.buildings;

import org.example.models.Cell;
import org.example.models.Colors;
import org.example.models.Finder;
import org.example.models.foragings.Nature.Lake;
import org.example.models.items.Item;
import org.example.models.locations.Farm;

import java.util.HashSet;


public class Well implements Building{
    @Override
    public String getChar() {
//        return Colors.colorize(255,0,"🌑");
        return Colors.colorize(247,234,"**");
    }
    private int x;
    private int y;
    @Override
    public String getName() {
        return "Well";
    }

    public Well(){

    }
    public Well(int x, int y, Farm farm) {
        this.x = x;
        this.y = y;
        System.out.println( "well in : "+x+" "+y);
        int xLength=3;
        int yLength=3;
        for(int i = x; i < xLength + x; i++) {
            for(int j = y; j < yLength + y; j++) {
                Cell cell=Finder.findCellByCoordinates(i, j, farm);
                assert cell != null;
                cell.setObjectMap(this);
            }
        }
        Cell cell = Finder.findCellByCoordinates(x + 1, y + 1, farm);
        assert cell != null;
        cell.setObjectMap(new Lake(x + 1, y + 1, farm, 0));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
