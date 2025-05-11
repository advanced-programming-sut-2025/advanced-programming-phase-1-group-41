package org.example.models.buildings.Nature;

import org.example.models.*;
import org.example.models.locations.Farm;

import java.util.Random;

public class Bush implements ObjectMap {
    @Override
    public String getChar() {
        return Colors.colorize(64,64,"%%%%");
    }
    private static final int WIDTH = 12;
    private static final int HEIGHT = 12;
    private static final int ITERATIONS = 5;
    private static final double INITIAL_WATER_CHANCE = 0.6;

    public Bush(int startX, int startY, Farm farm) {
        boolean[][] map = new boolean[WIDTH][HEIGHT];

        Random random = new Random();
        for (int x = 0; x < WIDTH - random.nextInt(7); x++) {
            for (int y = 0; y < HEIGHT - random.nextInt(7); y++) {
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
                    if (cell != null && cell.getObjectMap().getChar().equals(new Grass().getChar())) {
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
