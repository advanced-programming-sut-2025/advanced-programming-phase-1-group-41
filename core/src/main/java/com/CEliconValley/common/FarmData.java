package com.CEliconValley.common;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.ObjectMap;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.Cottage;
import com.CEliconValley.models.buildings.animalContainer.Barn;
import com.CEliconValley.models.buildings.animalContainer.Coop;
import com.CEliconValley.models.foragings.Crop;
import com.CEliconValley.models.foragings.Nature.Grass;
import com.CEliconValley.models.foragings.Nature.Tree;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.models.locations.FarmType;
import dev.morphia.annotations.Embedded;

import java.util.ArrayList;

@Embedded
public class FarmData {
    int farmTypeInt;
    int id;
    //    ArrayList<BuildingData> buildingsData;
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
    boolean isGreenHouseUnlocked;
    RefrigeratorData refrigeratorData;

    int rockCount;
    int foragingTreeCount;
    int plantCount;
    int foragingCropCount;
    int greenhouseX;
    int greenhouseY;
    int cottageX;
    int cottageY = 4;
    int mineLengthX;
    int mineLengthY;

    public FarmData() {
    }

    public FarmData(Farm farm) {
        farmTypeInt = farm.getFarmType().ordinal();
        id = farm.getId();
        rockCount = farm.getRockCount();
        foragingTreeCount = farm.getForagingTreeCount();
        plantCount = farm.getPlantCount();
        foragingCropCount = farm.getForagingCropCount();
        greenhouseX = farm.getGreenhouse().getX();
        greenhouseY = farm.getGreenhouse().getY();
        for (Building building : farm.getBuildings()) {
            if (building instanceof Cottage cottage) {
                cottageX = cottage.getX();
                break;
            }
        }
        mineLengthX = farm.getMine().getxLength();
        mineLengthY = farm.getMine().getyLength();
//        buildingsData = new ArrayList<>();
//        for (Building building : farm.getBuildings()) {
//            buildingsData.add(new BuildingData(building));
//        }
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
        this.isGreenHouseUnlocked = farm.getGreenhouse().isUnlocked();
        Cottage cottage = null;
        for (Building building : farm.getBuildings()) {
            if (building instanceof Cottage c) {
                cottage = c;
            }
        }
        this.refrigeratorData = new RefrigeratorData(
                cottage.getRefrigerator()
        );
    }

    private void makeCellData(Farm farm) {
        this.cells = new ArrayList<>();
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

    public Farm getFarm(Player owner) {
        Farm farm = new Farm(id, FarmType.values()[farmTypeInt], rockCount, foragingTreeCount,
                plantCount, foragingCropCount, greenhouseX, greenhouseY, cottageX, mineLengthX, mineLengthY);
        // need rebuilding on map
        farm.setBarns(getBarns(owner, farm));
        farm.setCoops(getCoops(owner, farm));
//        int counter = 0;
//        for (Cell cell : farm.getCells()) {
//            if(farm.getTransferCells().contains(cell)) {
//                System.out.print("tt");
//            }else if(farm.getStartPoints().contains(cell)) {
//                System.out.print("ss");
//            }
//            else{
//                System.out.print(cell.getObjectMap().getChar());
//            }
//            counter++;
//            if(counter % 60 == 0){
//                System.out.println();
//            }
//        }
//        System.out.println("that was "+farm.getId());
//        for (Cell transferCell : farm.getTransferCells()) {
//            System.out.println(transferCell.getX()+" "+transferCell.getY());
//        }
        farm.getGreenhouse().setUnlocked(isGreenHouseUnlocked);
        loadTreesAndCrops(farm);
//        System.out.println("loaded trees");
        loadRefrigerator(farm);
//        System.out.println("loaded refrigerator");
        loadCells(farm);
//        System.out.println("loaded cells");
        farm.setTransferCells(getTransferCells(farm));
        farm.setStartPoints(getStartCells(farm));
        return farm;
    }

    private CellData getCellData(Cell cell) {
        for (CellData cellData : cells) {
            if (cellData.x == cell.getX() && cellData.y == cell.getY()) {
                return cellData;
            }
        }
        return null;
    }

    // some efficiency lol
    private CellData getCellData(Cell cell, int i) {
        if (cells.get(i).x == cell.getX() && cells.get(i).y == cell.getY()) {
            return cells.get(i);
        }
        for (CellData cellData : cells) {
            if (cellData.x == cell.getX() && cellData.y == cell.getY()) {
                return cellData;
            }
        }
        return null;
    }


    private void loadCells(Farm farm) {
        for (int i = 0; i < farm.getCells().size(); i++) {
            Cell cell = farm.getCells().get(i);
            if(!(cell.getObjectMap() instanceof Grass)) continue;
            ObjectMap temp = cell.getObjectMap();
            cell.setObjectMap(getCellData(cell, i).extractData().getObjectMap());
            if(cell.getObjectMap() == null){
                System.out.println("null but "+temp.getName()+" "+i);
            }
        }
    }

    private void loadRefrigerator(Farm farm) {
        for (Building building : farm.getBuildings()) {
            if(building instanceof Cottage cottage){
                cottage.setRefrigerator(refrigeratorData.getRefrigerator());
            }
        }
    }

    private ArrayList<Barn> getBarns(Player owner, Farm farm) {
        ArrayList<Barn> barns = new ArrayList<>();
        if(barnsData == null) return barns;
        for (BarnData barnsDatum : barnsData) {
            barns.add(barnsDatum.getBarn(owner, farm));
        }
        return barns;
    }
    private ArrayList<Coop> getCoops(Player owner, Farm farm) {
        ArrayList<Coop> coops = new ArrayList<>();
        if(coopsData == null) return coops;
        for (CoopData coopsDatum : coopsData) {
            coops.add(coopsDatum.getCoop(owner, farm));
        }
        return coops;
    }

    private ArrayList<Cell> getTransferCells(Farm farm) {
        ArrayList<Cell> transferCells = new ArrayList<>();
        for (CellData transferCell : this.transferCells) {
            transferCells.add(
                    Finder.findCellByCoordinates(transferCell.x, transferCell.y, farm));
        }
        return transferCells;
    }

    private ArrayList<Cell> getStartCells(Farm farm) {
        ArrayList<Cell> startingCells = new ArrayList<>();
        for (CellData sc : this.startPoints) {
            startingCells.add(
                    Finder.findCellByCoordinates(sc.x, sc.y, farm));
        }
        return startingCells;
    }
    private void loadTreesAndCrops(Farm farm) {
        if(treesData == null) treesData = new ArrayList<>();
        if(cropsData == null) cropsData = new ArrayList<>();
        for (TreeData td : treesData) {
            Cell cell = Finder.findCellByCoordinates(td.getX(), td.getY(), farm);
            cell.setObjectMap(td.getTree());
        }
        for (CropData cd : cropsData) {
            Cell cell = Finder.findCellByCoordinates(cd.getX(), cd.getY(), farm);
            cell.setObjectMap(cd.getCrop());
        }
    }

    public ArrayList<BarnData> getBarnsData() {
        return barnsData;
    }

    public ArrayList<CellData> getCells() {
        return cells;
    }

    public ArrayList<CoopData> getCoopsData() {
        return coopsData;
    }

    public int getCottageX() {
        return cottageX;
    }

    public ArrayList<CropData> getCropsData() {
        return cropsData;
    }

    public int getFarmTypeInt() {
        return farmTypeInt;
    }

    public int getForagingCropCount() {
        return foragingCropCount;
    }

    public int getForagingTreeCount() {
        return foragingTreeCount;
    }

    public int getGreenhouseX() {
        return greenhouseX;
    }

    public int getGreenhouseY() {
        return greenhouseY;
    }

    public int getId() {
        return id;
    }

    public boolean isGreenHouseUnlocked() {
        return isGreenHouseUnlocked;
    }

    public int getMineLengthX() {
        return mineLengthX;
    }

    public int getMineLengthY() {
        return mineLengthY;
    }

    public int getPlantCount() {
        return plantCount;
    }

    public RefrigeratorData getRefrigeratorData() {
        return refrigeratorData;
    }

    public int getRockCount() {
        return rockCount;
    }

    public ArrayList<CellData> getStartPoints() {
        return startPoints;
    }

    public ArrayList<CellData> getTransferCells() {
        return transferCells;
    }

    public ArrayList<TreeData> getTreesData() {
        return treesData;
    }

    public int getCottageY() {
        return cottageY;
    }
}
