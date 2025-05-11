package org.example.models.foragings.Nature;

import org.example.models.*;
import org.example.models.locations.Farm;

import java.util.Random;

public class Rock implements Nature, Obstacle {
    @Override
    public String getChar() {
        return Colors.colorize(250,0,"OO");
    }

    @Override
    public String getName() {
        return "Rock";
    }

    private final RockType rockType;
    public Rock(int x, int y, Farm farm) {
        Random rand = new Random();
        if(y >= 40){
            rockType = RockType.BigRock;
        } else{
            rockType = RockType.SmallRock;
        }
        Cell cell = Finder.findCellByCoordinates(x, y, farm);
        assert cell != null;
        cell.setObjectMap(this);
        if(rockType.equals(RockType.BigRock)){
            Cell cell2 = Finder.findCellByCoordinates(x + 1, y, farm);
            if(cell2 != null&& cell2.getObjectMap().getChar().equals(new Grass().getChar())){
                cell2.setObjectMap(this);
            }
            Cell cell3 = Finder.findCellByCoordinates(x + 1, y + 1, farm);
            if(cell3 != null && cell3.getObjectMap().getChar().equals(new Grass().getChar())){
                cell3.setObjectMap(this);
            }
            Cell cell4 = Finder.findCellByCoordinates(x, y + 1, farm);
            if(cell4 != null && cell4.getObjectMap().getChar().equals(new Grass().getChar())){
                cell4.setObjectMap(this);
            }
        }
    }
    public RockType getRockType() {
        return rockType;
    }
}
