package com.CEliconValley.common;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.locations.Farm;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Transient;

import java.util.ArrayList;

@Entity("farmdata")
public class FarmData {
    Farm farm;
    ArrayList<CellData> cells;
    ArrayList<CellData> transferCells;
    ArrayList<CellData> startPoints;

    public FarmData(Farm farm) {
        this.farm = farm;
        makeCellData();
    }

    private void makeCellData(){
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
