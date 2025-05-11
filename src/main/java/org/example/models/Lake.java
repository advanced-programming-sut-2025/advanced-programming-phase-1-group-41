package org.example.models;

import org.example.models.locations.Farm;

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
