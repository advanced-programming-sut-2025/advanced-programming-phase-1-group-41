package com.CEliconValley.models.locations;

import com.CEliconValley.models.*;
import com.CEliconValley.models.foragings.Crop;
import com.CEliconValley.models.foragings.Foraging;
import com.CEliconValley.models.foragings.ForagingCrop;
import com.CEliconValley.models.foragings.ForagingTree;
import com.CEliconValley.models.foragings.Nature.*;

import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.Cottage;
import com.CEliconValley.models.buildings.GreenHouse.Greenhouse;
import com.CEliconValley.models.buildings.animalContainer.Barn;
import com.CEliconValley.models.buildings.animalContainer.BarnType;
import com.CEliconValley.models.buildings.animalContainer.Coop;
import com.CEliconValley.models.buildings.animalContainer.CoopType;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static com.CEliconValley.models.App.MaxHeight;
import static com.CEliconValley.models.App.MaxLength;


public class Farm {
    private FarmType farmType;
    private final FarmBuilder farmBuilder;
    private ArrayList<Cell> cells = new ArrayList<>();
    private int id;
    private final ArrayList<Building> buildings = new ArrayList<>();
    private final ArrayList<Foraging> foragings = new ArrayList<>();
    private final ArrayList<Lake> lakes = new ArrayList<>();
    private final ArrayList<Bush> bushes = new ArrayList<>();
    private final ArrayList<Barn> barns = new ArrayList<>();
    private final ArrayList<Coop> coops = new ArrayList<>();
    private final ArrayList<Crop> crops = new ArrayList<>();
    private final ArrayList<Tree> trees = new ArrayList<>();
    private final int rockCount;
    private final int foragingTreeCount;
    private final int plantCount;
    private final int foragingCropCount;
    private final ArrayList<Cell> transferCells = new ArrayList<>();
    private final ArrayList<Cell> startPoints = new ArrayList<>();
    private Mine mine;
    private final Greenhouse greenhouse;

    public Farm(int id) {
        farmBuilder = new FarmBuilder(this);
        this.id = id;
        Random rand = new Random();
        for(int i=0;i<MaxLength;i++){
            for(int j=0;j<MaxHeight;j++){
                Grass grass = new Grass();
                setSand(i, j, grass);
                cells.add(new Cell(grass, j, i));
            }
        }
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                int x1 = 10 + ((7 + rand.nextInt(7)) * i);
                int y1 = 10 + ((10 + rand.nextInt(9)) * j);
//                Objects.requireNonNull(Finder.findCellByCoordinates(x1, y1, this)).setObjectMap(new Grass(x1, y1, this));
                farmBuilder.createGroundGrass(x1, y1);
            }

        }
//        Objects.requireNonNull(Finder.findCellByCoordinates(20, 50, this)).setObjectMap(new Grass(20, 50, this));
        farmType = FarmType.values()[rand.nextInt(FarmType.values().length)];
        greenhouse = new Greenhouse(4,24 + rand.nextInt(4),this);
        buildings.add(greenhouse);
        buildings.add(new Cottage(30 + rand.nextInt(4), 4,this));
        mine = new Mine(3,3,this);

        int lakeCount = farmType.LakeCoefficient;
        int bushCount = 1 + rand.nextInt(farmType.treeCoefficient/2 + 1);


        for(int i = 0; i < lakeCount; i++){
            lakes.add(new Lake(25 + 10 * i + rand.nextInt(5 + 5 * i), 25 + 10 * i + rand.nextInt(5 + 5 * i), this));
        }
        lakes.add(new Lake(30 + rand.nextInt(10), 35 + rand.nextInt(10), this));
        for(int i = 0; i < bushCount; i++){
            bushes.add(new Bush(20 + rand.nextInt(10) + i * 20, 20 + rand.nextInt(10), this));
        }
        bushes.add(new Bush(35 + rand.nextInt(10), 10, this));
        bushes.add(new Bush(20 + rand.nextInt(10), 35 + rand.nextInt(10), this));
        bushes.add(new Bush(25 + rand.nextInt(10), 40 + rand.nextInt(10), this));

        rockCount = (35 + rand.nextInt(10)) * farmType.rockCoefficient;
        foragingTreeCount = (35 + rand.nextInt(10)) * farmType.treeCoefficient;
        plantCount = (40 + rand.nextInt(10)) * farmType.treeCoefficient;
        foragingCropCount = (30 + rand.nextInt(5));
////        rockCount = (5 + rand.nextInt(10)) * farmType.rockCoefficient;
////        foragingTreeCount = (5 + rand.nextInt(10)) * farmType.treeCoefficient;
////        plantCount = (4 + rand.nextInt(10)) * farmType.treeCoefficient;
////        foragingCropCount = (3 + rand.nextInt(5));
//        rockCount = 0;
//        foragingTreeCount = 0;
//        foragingCropCount = 0;
//        plantCount = 0;

        for(int i = 0; i < rockCount ;i++){
            int y = rand.nextInt(MaxLength - 4) + 2;
            int x = rand.nextInt(MaxHeight - 4) + 2;
            if(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).getObjectMap() instanceof Grass){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).setObjectMap(new Rock(x, y, this));
            } else{
                i--;
            }
        }
        for(int i = 0; i < foragingTreeCount ;i++){
            int y = rand.nextInt(MaxLength - 4) + 2;
            int x = rand.nextInt(MaxHeight - 4) + 2;
            if(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).getObjectMap() instanceof Grass){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).setObjectMap(new ForagingTree(x, y, this));
            } else{
                i--;
            }
        }
        for(int i = 0; i < plantCount ;i++){
            int y = rand.nextInt(MaxLength - 4) + 2;
            int x = rand.nextInt(MaxHeight - 4) + 2;
            if(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).getObjectMap() instanceof Grass){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).setObjectMap(new Plant(x, y, this));
            } else{
                i--;
            }
        }
        for(int i = 0; i < foragingCropCount ;i++){
            int y = rand.nextInt(MaxLength - 4) + 2;
            int x = rand.nextInt(MaxHeight - 4) + 2;
            if(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).getObjectMap() instanceof Grass){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).setObjectMap(new ForagingCrop(x, y, this));
            } else{
                i--;
            }
        }
//        for(int i = 0; i < 400 ;i++){
//            int y = rand.nextInt(MaxLength - 4) + 4;
//            int x = rand.nextInt(MaxHeight - 4) + 4;
//            if(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).getObjectMap() instanceof Grass){
//                Crop crop = new Crop(x, y, this, CropType.values()[rand.nextInt(CropType.values().length)]);
//                (Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this))).setObjectMap(crop);
//                crops.add(crop);
//            } else{
//                i--;
//            }
//        }
//        for(int i = 0; i < 300 ;i++){
//            int y = rand.nextInt(MaxLength - 4) + 4;
//            int x = rand.nextInt(MaxHeight - 4) + 4;
//            if(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).getObjectMap() instanceof Grass){
//                Tree tree = new Tree(x, y, this, TreeType.values()[rand.nextInt(TreeType.values().length)]);
//                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).setObjectMap(tree);
//                trees.add(tree);
//            } else{
//                i--;
//            }
//        }
        if(this.getId()==1){
            for(Cell cell : cells){
                if(cell.getX()==59&&cell.getY()>=73||cell.getY()==74&&cell.getX()>=58){
                    Grass grass = new Grass();
                    grass.setSand(true);
                    cell.setObjectMap(grass);
                    transferCells.add(cell);
                }
            }
        }else if(this.getId()==2){
            for(Cell cell : cells){
                if(cell.getX()==0&&cell.getY()>=73||cell.getY()==74&&cell.getX()<=1){
                    Grass grass = new Grass();
                    grass.setSand(true);
                    cell.setObjectMap(grass);
                    transferCells.add(cell);
                }
            }
        }else if(this.getId()==3){
            for(Cell cell : cells){
                if(cell.getX()==59&&cell.getY()<=1||cell.getY()==0&&cell.getX()>=58){
                    Grass grass = new Grass();
                    grass.setSand(true);
                    cell.setObjectMap(grass);
                    transferCells.add(cell);
                }
            }
        } else if(this.getId()==4){
            for(Cell cell : cells){
                if(cell.getX()==0&&cell.getY()<=1||cell.getY()==0&&cell.getX()<=1){
                    Grass grass = new Grass();
                    grass.setSand(true);
                    cell.setObjectMap(grass);
                    transferCells.add(cell);
                }
            }
        }
        for(Cell cell : cells){
            if(cell.getY()>2&&getCell(cell.getX(),cell.getY()-1).getObjectMap() instanceof Door&&getCell(cell.getX(),cell.getY()-2).getObjectMap() instanceof Cottage){
                cell.setObjectMap(new Grass());
                startPoints.add(cell);
//                Objects.requireNonNull(Finder.findPlayerByFarm(this)).setX(cell.getX());
//                Objects.requireNonNull(Finder.findPlayerByFarm(this)).setY(cell.getY());
            }
        }
    }

    private void setSand(int i, int j, Grass grass){
        int dx = i - MaxLength / 2;
        int dy = j - MaxHeight / 2;
        if(id == 1 && (((j >= MaxHeight - 5 || i >= MaxLength - 5) && (dx * dx + dy * dy >= (MaxLength / 2 + 10) * (MaxHeight / 2 + 10)))
                || (j >= MaxHeight - 2 || i >= MaxLength - 2) || ((j == MaxHeight - 6 || j == MaxHeight - 7) && i == 0))) {
            grass.setSand(true);
            if(j <= 5){
                if((j != 5 && j != 4) || i != MaxLength - 1){
                    grass.setSand(false);
                }
            }
        } else if(id == 2 && (((i >= MaxLength - 5 || j <= 6) && (dx * dx + dy * dy >= (MaxHeight / 2 + 10) * (MaxLength / 2 + 10)))
                || (i >= MaxLength - 2 || j <= 1) || ((i == MaxLength - 6 || j == MaxLength - 7) && j == MaxHeight - 1))) {
            grass.setSand(true);
            if(i <= 5){
                if((i != 5 && i != 4) || j != 0){
                    grass.setSand(false);
                }
            }
        } else if(id == 3 && (((j >= MaxHeight - 5 || i <= 6) && (dx * dx + dy * dy >= (MaxLength / 2 + 10) * (MaxHeight / 2 + 10)))
                || (j >= MaxHeight - 2 || i <= 1) || ((j == MaxHeight - 6 || j == MaxHeight - 7) && i == MaxLength - 1))) {
            grass.setSand(true);
            if(j <= 5){
                if((j != 5 && j != 4) || i != 0){
                    grass.setSand(false);
                }
            }
        } else if(id == 4 && (((i <= 4 || j <= 4) && (dx * dx + dy * dy >= (MaxHeight / 2 + 10) * (MaxLength / 2 + 10)))
                || (i <= 1 || j <= 1) || ((i == 5 || i == 6) && j == MaxHeight - 1))) {
            grass.setSand(true);
            if(i >= MaxLength - 6){
                if((i != MaxLength - 6 && i != MaxLength - 5) || j != 0){
                    grass.setSand(false);
                }
            }
        }
    }

    public ArrayList<Barn> getBarns() {
        return barns;
    }

    public ArrayList<Coop> getCoops() {
        return coops;
    }

    public void creatNewBarn(int x, int y, BarnType barnType){
        barns.add(new Barn(x, y, this, barnType));
    }

    public void creatNewCoop(int x,int y,CoopType coopType){
        coops.add(new Coop(x, y, this, coopType));
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }


    public boolean isLakeAround(){
        for(int i=-1;i<=1;i++){
            for(int j=-1;j<=1;j++){
                Player player = App.getGame().getCurrentPlayer();
                Cell cell = getCell(player.getX()+i,player.getY()+j);
                if(cell == null) continue;
                if(cell.getObjectMap() instanceof Lake){
                    return true;
                }
            }
        }
        return false;
    }
    public Cell getCell(int x , int y){
        for(Cell cell : cells){
            if(cell.getX() == x && cell.getY() == y){
                return cell;
            }
        }
        return null;
    }


    public void printMap(){
        int counter = 0;
        for(Cell cell:cells){
            ArrayList<Player> players = Finder.findPlayersByFarm(this);
            boolean found = false;
            for(Player player : players){
                if(cell.getX() == player.getX() && cell.getY() == player.getY()){
                    System.out.printf(player.getChar());
                    found = true;
                    break;
                }
            }
            if(found){
                counter++;
                if(counter % 60 == 0){
                    System.out.println();
                }
                continue;
            }
            if(cell.getX()==App.getGame().getCurrentPlayer().getX()&&cell.getY()==App.getGame().getCurrentPlayer().getY()){System.out.printf(App.getGame().getCurrentPlayer().getChar());}
             else if(isAnimalHere(cell)){}
             else if(isSpecialPoint(cell)){}
             else {
                System.out.printf(cell.getObjectMap().getChar());
            }
            counter++;
            if(counter % 60 == 0){
                System.out.println();
            }
        }
    }

    public void printMap(int y , int x ,int squareSize){
        int counter = 0;
        for(int i=x;i<x+squareSize;i++) {
            for (int j = y; j < y + squareSize; j++) {
                Cell cell = getCell(j, i);
                if(cell == null) continue;
                ArrayList<Player> players = Finder.findPlayersByFarm(this);
                boolean found = false;
                for(Player player : players){
                    if(cell.getX() == player.getX() && cell.getY() == player.getY()){
                        System.out.printf(player.getChar());
                        found = true;
                    }
                }
                if(found){
                    continue;
                }
                 if (cell.getX() == App.getGame().getCurrentPlayer().getX() && cell.getY() == App.getGame().getCurrentPlayer().getY()) {
                    System.out.printf(App.getGame().getCurrentPlayer().getChar());}
                    else if (isAnimalHere(cell)) {}
                    else if(isSpecialPoint(cell)){}
                    else if(isSpecialPoint(cell)){}

                 else {
                    System.out.printf(cell.getObjectMap().getChar());
                }
                counter++;
                if (counter % squareSize == 0) {
                    System.out.println();
                }
            }
        }

    }
    public boolean isSpecialPoint(Cell cell){
        if(transferCells.contains(cell)){
            printFlashSign();
            return true;
        }
        else if(startPoints.contains(cell)){
            printStartSign();
            return true;
        }
        return false;

    }
    private void printStartSign(){
        System.out.printf(Colors.colorize(15,33,"xx"));
    }

    private void printFlashSign(){
        if(this.getId()==1){
            System.out.printf(Colors.colorize(15,196,"↘↘"));
        }
        else if(this.getId()==2){
            System.out.printf(Colors.colorize(15,196,"↙↙"));
        }
        else if(this.getId()==3){
            System.out.printf(Colors.colorize(15,196,"↗↗"));
        }
        else if(this.getId()==4){
            System.out.printf(Colors.colorize(15,196,"↖↖"));
        }
    }
    public ArrayList<Foraging> getForagings() {
        return foragings;
    }

    public FarmType getFarmType() {
        return farmType;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public ArrayList<Lake> getLakes() {
        return lakes;
    }

    public ArrayList<Bush> getBushes() {
        return bushes;
    }

    public Mine getMine() {
        return mine;
    }

    public Greenhouse getGreenhouse() {
        return greenhouse;
    }

    public void setFarmType(FarmType farmType) {
        this.farmType = farmType;
    }

    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMine(Mine mine) {
        this.mine = mine;
    }

    public ArrayList<Crop> getCrops() {
        return crops;
    }

    public void addCrop(Crop crop){
        crops.add(crop);
    }

    private boolean isAnimalHere(Cell cell){
        for(Barn barn : barns){
            for(Animal animal:barn.getAnimals()){
                if(animal.getX()==cell.getX()&&animal.getY()==cell.getY()){
                    System.out.printf(animal.getChar());
                    return true;
                }
            }
        }
        for(Coop coop : coops){
            for(Animal animal:coop.getAnimals()){
                if(animal.getX()==cell.getX()&&animal.getY()==cell.getY()){
                    System.out.printf(animal.getChar());
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Cell> getStartPoints(){
        return startPoints;
    }
    public void removeCrop(Crop crop) {
        if(!greenhouse.isGreenHouse(crop.getX(), crop.getY())){
            Objects.requireNonNull(Finder.findCellByCoordinates(crop.getX(), crop.getY(), this)).setObjectMap(new Grass());
        } else{
            Objects.requireNonNull(Finder.findCellByCoordinates(crop.getX(), crop.getY(), this)).setObjectMap(new Greenhouse());
        }
        crops.remove(crop);
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public void addTree(Tree tree){
        trees.add(tree);
    }

    public void removeTree(Tree tree){
        if(!greenhouse.isGreenHouse(tree.getX(), tree.getY())){
            Objects.requireNonNull(Finder.findCellByCoordinates(tree.getX(), tree.getY(), this)).setObjectMap(new Grass());
        } else{
            Objects.requireNonNull(Finder.findCellByCoordinates(tree.getX(), tree.getY(), this)).setObjectMap(new Greenhouse());
        }
        trees.remove(tree);
    }
    public void update(){
        farmBuilder.updateForagings();
        farmBuilder.growCrops();
        farmBuilder.growTrees();
        farmBuilder.weatherUpdates();
        farmBuilder.scarecrowUpdate();
        farmBuilder.crowAttacks();
        mine.renewMinerals();
    }
    public int getRockCount() {
        return rockCount;
    }
    public int getForagingCropCount() {
        return foragingCropCount;
    }
    public int getForagingTreeCount() {
        return foragingTreeCount;
    }
    public int getPlantCount() {
        return plantCount;
    }

    //    public void setCells(ArrayList<Cell> cells) {
//        this.cells = cells;
//    }


    public ArrayList<Cell> getTransferCells() {
        return transferCells;
    }
}
