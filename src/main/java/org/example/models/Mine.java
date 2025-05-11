package org.example.models;

import org.example.models.buildings.Nature.Mineral;
import org.example.models.locations.Farm;

import java.util.Random;

public class Mine implements ObjectMap {
    @Override
    public String getChar() {
        return Colors.colorize(233,237,"^.");
    }

    @Override
    public String getName() {
        return "Mine";
    }
    public Mine(int x, int y, Farm farm) {
        x++;
        y++;
        Random rand = new Random();
        int xLength = 7 + rand.nextInt(7);
        int yLength = 7 + rand.nextInt(6);
        for(int i=x; i<xLength+x; i++) {
            for(int j=y; j<yLength+y; j++) {
                Cell cell=Finder.findCellByCoordinates(i, j, farm);
                if(rand.nextInt(4) != 0) {
                    assert cell != null;
                    cell.setObjectMap(this);
                } else{
                    assert cell != null;
                    cell.setObjectMap(new Mineral(i, j, farm));
                }
            }
        }
    }
}
