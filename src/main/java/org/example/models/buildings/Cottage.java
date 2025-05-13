package org.example.models.buildings;

import org.example.models.Cell;
import org.example.models.Colors;
import org.example.models.Finder;
import org.example.models.items.Item;
import org.example.models.locations.Farm;

import java.util.HashSet;


public class Cottage implements Building {
    @Override
    public String getChar() {
        return Colors.colorize(178,0,"..");
    }
    private int x;
    private int y;
    private Refrigerator refrigerator;
    private HashSet<Item> machines;
    @Override
    public String getName() {
        return "Cottage";
    }

    public Cottage(int x, int y, Farm farm) {
        this.refrigerator = new Refrigerator();
        this.machines = new HashSet<>();
        this.x = x;
        this.y = y;
        System.out.println( "cottage in : "+x+" "+y);
        int xWall;
        int yWall;
        yWall = y;
        while(yWall <= y + 5) {
            for (int i = x; i <= x + 5; i++) {
                Cell cell = Finder.findCellByCoordinates(i, yWall, farm);
                assert cell != null;
                cell.setObjectMap(new Wall());
                if(i == x + 2 && yWall == y + 5){
                    cell.setObjectMap(new Door());
                }
            }
            yWall+=5;
        }
        xWall = x;
        while(xWall<=x+5) {
            for (int j = y + 1; j <= y + 5; j++) {
                Cell cell = Finder.findCellByCoordinates(xWall, j, farm);
                assert cell != null;
                cell.setObjectMap(new Wall());
            }
            xWall += 5;
        }
        x++;
        y++;
        int xLength=4;
        int yLength=4;
        for(int i = x; i < xLength + x; i++) {
            for(int j = y; j < yLength + y; j++) {
                Cell cell=Finder.findCellByCoordinates(i, j, farm);
                assert cell != null;
                cell.setObjectMap(this);
            }
        }
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

    public Refrigerator getRefrigerator() {
        return refrigerator;
    }

    public void setRefrigerator(Refrigerator refrigerator) {
        this.refrigerator = refrigerator;
    }

    public HashSet<Item> getMachines() {
        return machines;
    }
}
