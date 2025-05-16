package org.example.models.locations;

import org.example.controllers.WeatherController;
import org.example.models.*;
import org.example.models.animals.Animal;
import org.example.models.buildings.Door;
import org.example.models.buildings.Well;
import org.example.models.foragings.Nature.*;
import org.example.models.buildings.Building;
import org.example.models.buildings.Cottage;
import org.example.models.buildings.GreenHouse.Greenhouse;
import org.example.models.buildings.animalContainer.Barn;
import org.example.models.buildings.animalContainer.BarnType;
import org.example.models.buildings.animalContainer.Coop;
import org.example.models.buildings.animalContainer.CoopType;
import org.example.models.foragings.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static org.example.models.App.MaxHeight;
import static org.example.models.App.MaxLength;


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
                cells.add(new Cell(new Grass(),j,i));
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
            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).getObjectMap().getChar(), new Grass().getChar())){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).setObjectMap(new Rock(x, y, this));
            } else{
                i--;
            }
        }
        for(int i = 0; i < foragingTreeCount ;i++){
            int y = rand.nextInt(MaxLength - 4) + 4;
            int x = rand.nextInt(MaxHeight - 4) + 4;
            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).getObjectMap().getChar(), new Grass().getChar())){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).setObjectMap(new ForagingTree(x, y, this));
            } else{
                i--;
            }
        }
        for(int i = 0; i < plantCount ;i++){
            int y = rand.nextInt(MaxLength - 4) + 4;
            int x = rand.nextInt(MaxHeight - 4) + 4;
            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).getObjectMap().getChar(), new Grass().getChar())){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).setObjectMap(new Plant(x, y, this));
            } else{
                i--;
            }
        }
        for(int i = 0; i < foragingCropCount ;i++){
            int y = rand.nextInt(MaxLength - 4) + 4;
            int x = rand.nextInt(MaxHeight - 4) + 4;
            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).getObjectMap().getChar(), new Grass().getChar())){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).setObjectMap(new ForagingCrop(x, y, this));
            } else{
                i--;
            }
        }
//        for(int i = 0; i < 400 ;i++){
//            int y = rand.nextInt(MaxLength - 4) + 4;
//            int x = rand.nextInt(MaxHeight - 4) + 4;
//            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).getObjectMap().getChar(), new Grass().getChar())){
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
//            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).getObjectMap().getChar(), new Grass().getChar())){
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
                    cell.setObjectMap(new Grass());
                    transferCells.add(cell);
                }
            }
        }else if(this.getId()==2){
            for(Cell cell : cells){
                if(cell.getX()==0&&cell.getY()>=73||cell.getY()==74&&cell.getX()<=1){
                    cell.setObjectMap(new Grass());
                    transferCells.add(cell);
                }
            }
        }else if(this.getId()==3){
            for(Cell cell : cells){
                if(cell.getX()==59&&cell.getY()<=1||cell.getY()==0&&cell.getX()>=58){
                    cell.setObjectMap(new Grass());
                    transferCells.add(cell);
                }
            }
        } else if(this.getId()==4){
            for(Cell cell : cells){
                if(cell.getX()==0&&cell.getY()<=1||cell.getY()==0&&cell.getX()<=1){
                    cell.setObjectMap(new Grass());
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

    public void printMap(int x , int y ,int squareSize){
        int counter = 0;
        for(int i=x;i<x+squareSize;i++) {
            for (int j = y; j < y + squareSize; j++) {
                Cell cell = getCell(i, j);
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
