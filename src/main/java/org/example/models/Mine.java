package org.example.models;

import org.example.models.locations.Farm;

import java.util.Random;

public class Mine implements ObjectMap {
    public Mine(int x, int y, Farm farm) {
        x++;
        y++;
        Random rand = new Random();
        int xLength = 8 + rand.nextInt(8);
        int yLength = 7 + rand.nextInt(7);
        for(int i=x; i<xLength+x; i++) {
            for(int j=y; j<yLength+y; j++) {
                Cell cell=Finder.findCellByCoordinates(i, j, farm);
                assert cell != null;
                cell.setObjectMap(this);
            }
        }
    }
}
