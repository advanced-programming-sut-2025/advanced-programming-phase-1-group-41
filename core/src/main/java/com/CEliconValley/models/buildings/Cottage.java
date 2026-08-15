package com.CEliconValley.models.buildings;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.ui.TerminalColors;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.locations.Farm;

import java.util.ArrayList;
import java.util.HashSet;


public class Cottage implements Building {
    @Override
    public String getChar() {
        return TerminalColors.colorize(178,0,"..");
    }
    private int x;
    private int y;
    private int anchorX;
    private int anchorY;
    private Refrigerator refrigerator;
    private HashSet<Item> machines;
    @Override
    public String getName() {
        return "Cottage";
    }

    public Cottage() {
    }

    public Cottage(int x, int y, Farm farm) {
        anchorX = x+4;
        anchorY = y+1;
        this.refrigerator = new Refrigerator();
        this.machines = new HashSet<>();
        this.x = x;
        this.y = y;
//        System.out.println( "cottage in : "+x+" "+y);
        int xWall;
        int yWall;
        yWall = y;
        ArrayList<Cell> startPoints = new ArrayList<>();
        while(yWall <= y + 3) {
            for (int i = x; i <= x + 5; i++) {
                Cell cell = Finder.findCellByCoordinates(i, yWall, farm);
                assert cell != null;
                cell.setObjectMap(new Wall());
                if(i == x + 3 && yWall == y ){
                    cell.setObjectMap(new Door());
                    startPoints.add(cell);
                    farm.setStartPoints(startPoints);
                }
            }
            yWall+=3;
        }
        xWall = x;
        while(xWall<=x+5) {
            for (int j = y + 1; j <= y + 3; j++) {
                Cell cell = Finder.findCellByCoordinates(xWall, j, farm);
                assert cell != null;
                cell.setObjectMap(new Wall());
            }
            xWall += 5;
        }
        x++;
        y++;
        int xLength=4;
        int yLength=2;
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

    public int getAnchorY(){
        return anchorY;
    }
    public int getAnchorX(){
        return anchorX;
    }

    public static int getCottageLength() {
        return 5;}
}
