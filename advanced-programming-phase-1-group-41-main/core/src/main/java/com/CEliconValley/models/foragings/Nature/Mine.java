package com.CEliconValley.models.foragings.Nature;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.ObjectMap;
import com.CEliconValley.models.locations.Farm;

import java.util.Random;

public class Mine implements ObjectMap {
    private int mineralCount;
    private Farm farm;
    private int xLength;
    private int yLength;
    private int x;
    private int y;

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
        this.farm = farm;
        mineralCount = 0;
        Random rand = new Random();
        xLength = 7 + rand.nextInt(7);
        yLength = 7 + rand.nextInt(6);
        for(int i=x; i<xLength+x; i++) {
            for(int j=y; j<yLength+y; j++) {
                Cell cell= Finder.findCellByCoordinates(i, j, farm);
                if(rand.nextInt(4) != 0) {
                    assert cell != null;
                    cell.setObjectMap(this);
                } else{
                    assert cell != null;
                    cell.setObjectMap(new Mineral(i, j, farm));
                    mineralCount++;
                }
            }
        }
    }
    public Mine(int x, int y, Farm farm, int i) {
        Random rand = new Random();
        Cell cell= Finder.findCellByCoordinates(x, y, farm);
        assert cell != null;
        cell.setObjectMap(this);
    }
    public void renewMinerals(){
        int nowCount = 0;
        for(Cell cell : farm.getCells()){
            if(cell.getObjectMap() instanceof Mineral){
                nowCount++;
            }
        }
        Random rand = new Random();
        for(int i = 0; i < mineralCount - nowCount; i++) {
            Cell cell = Finder.findCellByCoordinates(x + rand.nextInt(xLength) + rand.nextInt(5), y + rand.nextInt(yLength) + rand.nextInt(5), farm);
            assert cell != null;
            if(cell.getObjectMap() instanceof Mine){
                cell.setObjectMap(new Mineral(cell.getX(), cell.getY(), farm));
            } else{
                i--;
            }
        }
    }
}
