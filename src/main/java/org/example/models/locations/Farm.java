package org.example.models.locations;

import org.example.models.*;
import org.example.models.animals.Animal;
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
    private int rockCount;
    private int foragingTreeCount;
    private int plantCount;
    private int foragingCropCount;
    private Mine mine;

    public Farm(int id) {
        farmBuilder = new FarmBuilder(this);
        this.id = id;
        for(int i=0;i<MaxLength;i++){
            for(int j=0;j<MaxHeight;j++){
                cells.add(new Cell(new Grass(),j,i));
            }
        }
        Random rand = new Random();
        farmType = FarmType.values()[rand.nextInt(FarmType.values().length)];
        buildings.add(new Greenhouse(4,24 + rand.nextInt(4),this));
        buildings.add(new Cottage(30 + rand.nextInt(4), 4,this));
        mine = new Mine(3,3,this);

        int lakeCount = farmType.LakeCoefficient;
        int bushCount = 1+rand.nextInt(farmType.treeCoefficient/2 + 1);

        for(int i = 0; i < lakeCount; i++){
            lakes.add(new Lake(25 + 10 * i + rand.nextInt(5 + 5 * i), 25 + 10 * i + rand.nextInt(5 + 5 * i), this));
        }
        lakes.add(new Lake(30 + rand.nextInt(10), 35 + rand.nextInt(10), this));
        for(int i = 0; i < bushCount; i++){
            bushes.add(new Bush(20 + rand.nextInt(10) + i * 20, 20 + rand.nextInt(10), this));
        }
//        bushes.add(new Bush(35 + rand.nextInt(10), 10, this));
//        bushes.add(new Bush(20 + rand.nextInt(10), 35 + rand.nextInt(10), this));
//        bushes.add(new Bush(25 + rand.nextInt(10), 40 + rand.nextInt(10), this));
//        int rockCount = (35 + rand.nextInt(10)) * farmType.rockCoefficient;
//        int foragingTreeCount = (35 + rand.nextInt(10)) * farmType.treeCoefficient;
//        int plantCount = (40 + rand.nextInt(10)) * farmType.treeCoefficient;
//        int foragingCropCount = (30 + rand.nextInt(5));
//        rockCount = (0 + rand.nextInt(10)) * farmType.rockCoefficient;
//        foragingTreeCount = (0 + rand.nextInt(10)) * farmType.treeCoefficient;
//        plantCount = (0 + rand.nextInt(10)) * farmType.treeCoefficient;
//        foragingCropCount = (0 + rand.nextInt(5));
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
//        for(int i = 0; i < 50 ;i++){
//            int y = rand.nextInt(MaxLength - 4) + 4;
//            int x = rand.nextInt(MaxHeight - 4) + 4;
//            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).getObjectMap().getChar(), new Grass().getChar())){
//                Crop crop = new Crop(x, y, this, CropType.values()[rand.nextInt(CropType.values().length)]);
//                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, this)).setObjectMap(crop);
//                crops.add(crop);
//            } else{
//                i--;
//            }
//        }
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
            if(isAnimalHere(cell)){
            }
            else if(cell.getX()==App.getGame().getCurrentPlayer().getX()&&cell.getY()==App.getGame().getCurrentPlayer().getY()){
                System.out.printf(App.getGame().getCurrentPlayer().getChar());
            }
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
                if (isAnimalHere(cell)) {
                } else if (cell.getX() == App.getGame().getCurrentPlayer().getX() && cell.getY() == App.getGame().getCurrentPlayer().getY()) {
                    System.out.printf(App.getGame().getCurrentPlayer().getChar());
                } else {
                    System.out.printf(cell.getObjectMap().getChar());
                }
                counter++;
                if (counter % squareSize == 0) {
                    System.out.println();
                }
            }
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

    public void removeCrop(Crop crop) {
        Objects.requireNonNull(Finder.findCellByCoordinates(crop.getX(), crop.getY(), this)).setObjectMap(new Grass());
        crops.remove(crop);
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public void addTree(Tree tree){
        trees.add(tree);
    }

    public void removeTree(Tree tree){
        Objects.requireNonNull(Finder.findCellByCoordinates(tree.getX(), tree.getY(), this)).setObjectMap(new Grass());
        trees.remove(tree);
    }
    public void update(){
        farmBuilder.updateForagings();
        farmBuilder.growCrops();
        farmBuilder.growTrees();
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
}
