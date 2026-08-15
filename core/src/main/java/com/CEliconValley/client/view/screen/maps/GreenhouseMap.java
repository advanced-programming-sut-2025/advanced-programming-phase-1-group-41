package com.CEliconValley.client.view.screen.maps;


import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.FarmData;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.buildings.GreenHouse.Greenhouse;
import com.CEliconValley.models.foragings.Nature.Grass;
import com.CEliconValley.models.locations.Location;
import dev.morphia.annotations.Transient;

import java.util.ArrayList;

    public class GreenhouseMap implements Location {

        @Transient
        private final ArrayList<Cell> cells = new ArrayList<>();
        private final int offsetX;
        private final int offsetY;

        public GreenhouseMap(int offsetX, int offsetY) {
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            FarmData farmData = Finder.getFarmDataById(AppClient.getGameData(),
                AppClient.getUserData().getUsername());
            System.out.println("gx "+farmData.getGreenhouseX());
            System.out.println("gy "+farmData.getGreenhouseY());
            for (int i = 1; i < Greenhouse.getGreenhouseHeight(); i++) {
                for (int j = 1; j < Greenhouse.getGreenhouseLength() + 1; j++) {
                    Grass grass = new Grass();
                    Cell cell = new Cell(grass, offsetY + i, offsetX + j);
                    cells.add(cell);
                }
            }
            Door door = new Door();
            Cell cell = getCell(offsetX + Greenhouse.getGreenhouseLength() / 2 + 1, offsetY+1);
            cell.setObjectMap(door);
            cell = getCell(offsetX + Greenhouse.getGreenhouseLength() / 2 + 2, offsetY+1);
            cell.setObjectMap(door);
        }

        public ArrayList<Cell> getCells() {
            return cells;
        }

        public Cell getCell(int x, int y) {
            for (Cell cell : cells) {
                if (cell.getX() == x && cell.getY() == y) {
                    return cell;
                }
            }
            return null;
        }

        public boolean isInsideGreenhouse(int x, int y) {
            return x >= offsetX && x < offsetX + 18 &&
                y >= offsetY && y < offsetY + 16;
        }

        public int getOffsetX() {
            return offsetX;
        }

        public int getOffsetY() {
            return offsetY;
        }
    }

