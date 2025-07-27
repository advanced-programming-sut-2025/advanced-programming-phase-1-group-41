package com.CEliconValley.common;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.animalContainer.Barn;
import com.CEliconValley.models.buildings.animalContainer.Coop;
import com.CEliconValley.models.foragings.Crop;
import com.CEliconValley.models.foragings.Nature.Tree;
import com.CEliconValley.models.locations.Farm;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Transient;

import java.util.ArrayList;

public class FarmData {
    int farmTypeInt;
    int id;
    ArrayList<BuildingData> buildingsData;
    // foragings
    // lakes
    // bushes
    ArrayList<BarnData> barnsData;
    ArrayList<CoopData> coopsData;
    ArrayList<TreeData> treesData;
    ArrayList<CropData> cropsData;
    ArrayList<CellData> cells;
    ArrayList<CellData> transferCells;
    ArrayList<CellData> startPoints;
    // mine
    // greenhouse
    boolean isGreenHouseLocked;


    public FarmData(Farm farm) {
        farmTypeInt = farm.getFarmType().ordinal();
        id = farm.getId();
        buildingsData = new ArrayList<>();
        for (Building building : farm.getBuildings()) {
            buildingsData.add(new BuildingData(building));
        }
        makeCellData(farm);
        this.barnsData = new ArrayList<>();
        for (Barn barn : farm.getBarns()) {
            barnsData.add(new BarnData(barn));
        }
        this.coopsData = new ArrayList<>();
        for (Coop coop : farm.getCoops()) {
            coopsData.add(new CoopData(coop));
        }
        this.treesData = new ArrayList<>();
        for (Tree tree : farm.getTrees()) {
            treesData.add(new TreeData(tree));
        }
        this.cropsData = new ArrayList<>();
        for (Crop crop : farm.getCrops()) {
            cropsData.add(new CropData(crop));
        }
        this.isGreenHouseLocked = farm.getGreenhouse().isUnlocked();
    }

    private void makeCellData(Farm farm){
        for (Cell cell : farm.getCells()) {
            cells.add(new CellData(cell));
        }
        transferCells = new ArrayList<>();
        for (Cell transferCell : farm.getTransferCells()) {
            transferCells.add(getCellData(transferCell));
        }
        startPoints = new ArrayList<>();
        for (Cell startPoint : farm.getStartPoints()) {
            startPoints.add(getCellData(startPoint));
        }
    }


    private CellData getCellData(Cell cell){
        for (CellData cellData : cells) {
            if(cellData.x == cell.getX() && cellData.y == cell.getY()){
                return cellData;
            }
        }
        return null;
    }

}
