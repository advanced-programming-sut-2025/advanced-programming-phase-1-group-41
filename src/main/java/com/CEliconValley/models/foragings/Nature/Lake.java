package com.CEliconValley.models.foragings.Nature;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.ObjectMap;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.models.locations.Village;

import java.util.Random;

public class Lake implements ObjectMap, Obstacle {
    @Override
    public String getChar() {
        return Colors.colorize(39,39,"LL");
    }
    private static final int WIDTH = 15;
    private static final int HEIGHT = 15;
    private static final int ITERATIONS = 6;
    private static final double INITIAL_WATER_CHANCE = 0.5;

    @Override
    public String getName() {
        return "Lake";
    }

    public Lake(int x, int y, Village village, int YoHaHa) {
        Cell cell = Finder.findCellByCoordinatesVillage(x, y, village);
        if (cell != null) {
            cell.setObjectMap(this);
        }
    }
    public Lake(){

    }
    public Lake(int x, int y, Farm farm, int Khordamet) {
        Cell cell = Finder.findCellByCoordinates(x, y, farm);
        if (cell != null) {
            cell.setObjectMap(this);
        }
    }
    public Lake(int startX, int startY1, Village village) {
        boolean[][] map = new boolean[WIDTH][HEIGHT];
        double startY = (int) Math.sqrt(startX * 55) - 7;
        double theta = 2 * Math.PI * startY / 15;
        startY *=  (Math.cos(theta) / 10 + 1);
        startX -= 2;
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                map[x][y] = x + y < 8;
            }
        }
        if(startX > 70 && startX < 80){
            startX = 140 - startX;
        }
        if (startX > 80) {
            startX -= 20;
        }


        for (int i = 0; i < ITERATIONS; i++) {
            map = simulateStep(map);
        }

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (map[x][y]) {
                    Cell cell = Finder.findCellByCoordinatesVillage(startX + x, (int) startY + y, village);
                    if (cell != null) {
                        cell.setObjectMap(this);
                    }
                }
            }
        }
    }
    public Lake(int startX, int startY, Farm farm) {
        boolean[][] map = new boolean[WIDTH][HEIGHT];

        Random random = new Random();
        for (int x = 0; x < WIDTH - random.nextInt(5); x++) {
            for (int y = 0; y < HEIGHT - random.nextInt(5); y++) {
                map[x][y] = random.nextDouble() < INITIAL_WATER_CHANCE;
            }
        }

        for (int i = 0; i < ITERATIONS; i++) {
            map = simulateStep(map);
        }

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (map[x][y]) {
                    Cell cell = Finder.findCellByCoordinates(startX + x, startY + y, farm);
                    if (cell != null) {
                        cell.setObjectMap(this);
                    }
                }
            }
        }
    }

    private boolean[][] simulateStep(boolean[][] map) {
        boolean[][] newMap = new boolean[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                int waterNeighbors = countWaterNeighbors(map, x, y);
                if (map[x][y]) {
                    newMap[x][y] = waterNeighbors >= 4;
                } else {
                    newMap[x][y] = waterNeighbors >= 5;
                }
            }
        }

        return newMap;
    }

    private int countWaterNeighbors(boolean[][] map, int x, int y) {
        int count = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                int nx = x + dx, ny = y + dy;
                if (nx >= 0 && ny >= 0 && nx < WIDTH && ny < HEIGHT && map[nx][ny]) {
                    count++;
                }
            }
        }
        return count;
    }
}
