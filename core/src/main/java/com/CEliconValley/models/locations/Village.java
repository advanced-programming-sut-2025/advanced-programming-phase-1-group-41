package com.CEliconValley.models.locations;

import com.CEliconValley.models.*;
import com.CEliconValley.models.buildings.marketplaces.*;
import com.CEliconValley.models.npc.npchomes.*;

import com.CEliconValley.models.buildings.Bridge;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.buildings.ShippingBin;
import com.CEliconValley.models.buildings.animalContainer.Barn;
import com.CEliconValley.models.buildings.animalContainer.Coop;
import com.CEliconValley.models.foragings.Nature.Grass;
import com.CEliconValley.models.foragings.Nature.Lake;
import com.CEliconValley.models.npc.npcCharacters.NPC;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class Village { ;
    private ArrayList<Cell> cells = new ArrayList<>();

    private final ArrayList<Building> buildings = new ArrayList<>();
    ArrayList<Cell> transferCells = new ArrayList<>();
    ArrayList<Cell> startPoints = new ArrayList<>();
    ArrayList<NPC> NPCs = new ArrayList<>();



    public Village() {

        for (int i = 0; i < 65; i++) {
            for (int j = 0; j < 95; j++) {
                cells.add(new Cell(new Grass(), j, i));
            }
        }
        for(int i = 0; i < 95; i++){
            for(int j = 0; j < 65; j++){
                boolean isGrass = false;
                boolean isGround = false;
                if(Math.abs(i / 3 - j / 2) < 2 && i > 5 && j > 3){
                    if(Finder.findCellByCoordinatesVillage(i, j, this) != null){
                        if(Finder.findCellByCoordinatesVillage(i, j, this).getObjectMap() instanceof Grass){
                            isGrass = true;
                            isGround = ((Grass) Finder.findCellByCoordinatesVillage(i, j, this).getObjectMap()).isGround();
                        }
                    }
                }
                if(Finder.findCellByCoordinatesVillage(i, j, this) != null && i / 3 == j / 2){
                    Objects.requireNonNull(Finder.findCellByCoordinatesVillage(i, j, this)).setObjectMap(new Lake(i, j, this));
                }
                if(isGrass){
                    Grass grass = new Grass();
                    if(isGround){
                        grass.setGround(true);
                    }
                    Objects.requireNonNull(Finder.findCellByCoordinatesVillage(i, j, this)).setObjectMap(grass);
                }
                int radius = i * i / 9 + j * j / 4;
                if(radius <= 70){
                    Grass grass = new Grass();
                    grass.setGround(false);
                    grass.setSand(true);
                    Objects.requireNonNull(Finder.findCellByCoordinatesVillage(i, j, this)).setObjectMap(grass);
                }if(radius <= 50 || (i >= 75 && j >= 63) || (i == 8 && j == 15) || (i == 9 && j == 15)
                        || (i == 8 && j == 14) || (i == 8 && j == 13) || (i == 8 && j == 12) || (i == 9 && j == 14)
                        || (i == 9 && j == 12) || (i == 10 && j == 15) || (i == 9 && j == 13)){
                    Objects.requireNonNull(Finder.findCellByCoordinatesVillage(i, j, this)).setObjectMap(new Lake(i, j, this, 0));
                }
            }
        }
        for(int i = 70 ; i < 95; i++){
            getCell(i,63).setObjectMap(new Grass());
            getCell(i,64).setObjectMap(new Grass());
        }
        buildPath();
        getCell(42,35).setObjectMap(new Grass());
        transferCells.add(getCell(43,35));
        transferCells.add(getCell(44,35));
        transferCells.add(getCell(43,36));
        transferCells.add(getCell(44,36));
        startPoints.add(getCell(47,32));

        buildings.add(new Blacksmith(80,54,this));
        buildings.add(new FishShop(22,0,this));
        buildings.add(new GeneralStore(38,3,this));
        buildings.add(new Saloon(80,20,this));
        buildings.add(new MarnieRanch(80,0,this));
        buildings.add(new CarpenterShop(67,0,this));
        buildings.add(new Barn(77,0,this));
        buildings.add(new Coop(77,4,this));
        buildings.add(new Jojamart(84,10,this));
        buildings.add(new AbigailHome(9,43,this));
        buildings.add(new LiaHome(0,51,this));
        buildings.add(new SebastienHome(12,58,this));
        buildings.add(new RobinHome(24,58,this));
        buildings.add(new HarveyHome(59,54,this));




    }
        Random rand = new Random();
    public void buildPath(){
        makeOvalInCenter(30,20,0.8,1.2,true);
        makeOvalInCenter(6,4,0.7,1.3,true);

        makeGrass(getCell(38,11));
        for(int i=73;i<=89;i++){
            makeGround(getCell(i,19));
            makeGround(getCell(i,18));
        }
        for(int j=19;j<55;j++){
            makeGround(getCell(89,j));
            makeGround(getCell(90,j));
        }
        makeGround(getCell(89,55));
        makeGround(getCell(88,55));
        makeGround(getCell(88,56));
        makeGround(getCell(87,56));
        makeGround(getCell(86,56));

        for(int j=8;j<12;j++){
            makeGround(getCell(36,j));
            makeGround(getCell(35,j));
        }
        for(int i=35;i>19;i--){
            makeGround(getCell(i,8));
            makeGround(getCell(i,7));
        }
        for(int j=53;j<=56;j++){
            makeGround(getCell(40,j));
            makeGround(getCell(41,j));
        }
        for(int i=41;i>=12;i--){
            makeGround(getCell(i,56));
            makeGround(getCell(i,57));
        }
        makeGround(getCell(11,56));
        makeGround(getCell(11,55));
        makeGround(getCell(10,55));
        makeGround(getCell(9,55));
        makeGround(getCell(10,54));
        makeGround(getCell(9,54));
        makeGround(getCell(10,53));
        makeGround(getCell(43,34));
        for(int j=54;j>=52;j--){
            makeGround(getCell(10,j));
            makeGround(getCell(9,j));
        }
        makeGround(getCell(10,51));
        makeGround(getCell(11,52));
        makeGround(getCell(10,56));
        makeGround(getCell(11,57));
        for(int i=11;i<=30;i++){
            makeGround(getCell(i,50));
            makeGround(getCell(i,51));
        }
        makeGround(getCell(10,51));
        makeGround(getCell(42,56));
        for(int j=57;j<59;j++){
            makeGround(getCell(43,j));
            makeGround(getCell(42,j));
        }
        for(int i=53;i<76;i++){
            makeGround(getCell(i,32));
            makeGround(getCell(i,33));
            makeGround(getCell(i,31));
        }
        for(int i=53;i>=42;i--){
            makeGround(getCell(i,32));
        }
        for(int j=36;j>=28;j--){
            makeGround(getCell(47,j));
        }
        for(int i=43;i<=62;i++){
            makeGround(getCell(i,58));
            makeGround(getCell(i,59));
        }
        makeGround(getCell(19,7));

        for(int j=7;j>=0;j--){
            makeGround(getCell(19,j));
        }
        for(int i=65;i<=71;i++){
            makeGround(getCell(i,14));
            makeGround(getCell(i,15));
        }
        for(int i=81;i<=85;i++){
            makeGround(getCell(i,8));
        }
        for(int j=14;j>8;j--){
            makeGround(getCell(71,j));
            makeGround(getCell(72,j));
        }
        for(int i=72;i<=80;i++){
            makeGround(getCell(i,9));
            makeGround(getCell(i,8));
        }
        makeGround(getCell(80,10));
        makeGround(getCell(81,10));
        makeGround(getCell(82,10));
        makeGround(getCell(82,11));
        makeGround(getCell(83,11));
        makeGround(getCell(20,2));
        makeGround(getCell(21,2));
        getCell(48, 31).setObjectMap(new ShippingBin());

    }
    private void makeGround(Cell cell){
        if(cell.getObjectMap() instanceof Grass){
            ((Grass) cell.getObjectMap()).setGround(true);
            ((Grass) cell.getObjectMap()).setSand(false);
        }
        else if(cell.getObjectMap() instanceof Lake){
            cell.setObjectMap(new Bridge());
        }
    }
    private void makeGrass(Cell cell){
        if(cell.getObjectMap() instanceof Grass){
            ((Grass) cell.getObjectMap()).setGround(false);
            ((Grass) cell.getObjectMap()).setSand(false);
        }
    }
    private void makeOvalInCenter(int a, int b,double c,double d,boolean isRing) {
        int centerX = 95 / 2;
        int centerY = 65 / 2;

        for (int i = 0; i < 95; i++) {
            for (int j = 0; j < 65; j++) {

                double distance = Math.pow(i - centerX, 2) / Math.pow(a, 2) + Math.pow(j - centerY, 2) / Math.pow(b, 2);
                if(isRing&&distance <= d && distance >= c||!isRing&&distance <= d){
                    Cell cell = getCell(i, j);
                    if (cell != null && cell.getObjectMap() instanceof Grass) {

                        ((Grass) cell.getObjectMap()).setGround(true);
                    }
                    else if(cell != null && cell.getObjectMap() instanceof Lake) {
                        cell.setObjectMap(new Bridge());
                    }

                }
            }
        }
    }








    public ArrayList<Cell> getCells() {
        return cells;
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
            boolean checker = false;
            for (Player player : App.getGame().getPlayers()) {
                if(!player.isPlayerIsInVillage()){
                    continue;
                }
                if(cell.getX()==player.getX()&&cell.getY()==player.getY()){
                    System.out.printf(player.getChar());
                    checker = true;
                    break;
                }
            }
            if(checker){
                counter++;
                continue;
            }
            if(isSpecialPoint(cell)) {
            }else
             {
                System.out.printf(cell.getObjectMap().getChar());
            }
            counter++;
            if(counter % 95 == 0){
                System.out.println();
            }
        }
        for (Cell cell : cells) {
            if(cell.getObjectMap() instanceof Door){
                System.out.println("door: "+cell.getX()+","+cell.getY());
            }
        }
    }

    public void setNPCs(ArrayList<NPC> NPCs) {
        this.NPCs = NPCs;
    }

    public boolean isSpecialPoint(Cell cell){
        for(NPC npc : NPCs){
            if(cell.getY()==npc.getY()&&cell.getX()==npc.getX()){
                System.out.printf(npc.getChar());
                return true;
            }
        }

        if(transferCells.contains(cell)){
            showTransferCell(cell);
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
    private void showTransferCell(Cell cell){
         if(transferCells.get(0).equals(cell)){
            System.out.printf(Colors.colorize(15,196,"↓↓"));
        }
        else if(transferCells.get(1).equals(cell)){
            System.out.printf(Colors.colorize(15,196,"↙↙"));
        }
        else if(transferCells.get(2).equals(cell)){
            System.out.printf(Colors.colorize(0,39,"⛴ "));
        }
        else if(transferCells.get(3).equals(cell)){
            System.out.printf(Colors.colorize(15,196,"←←"));
        }
    }



    public ArrayList<Building> getBuildings() {
        return buildings;
    }


    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }

    public ArrayList<Cell> getStartPoints() {
        return startPoints;
    }

    public ArrayList<Cell> getTransferCells() {
        return transferCells;
    }

    public ArrayList<NPC> getNPCs() {
        return NPCs;
    }

    public NPC getnpcByName(String name) {
        for (NPC npc : NPCs) {
            if(npc.getName().equalsIgnoreCase(name)){
                return npc;
            }
        }
        return null;
    }

    //    public void setCells(ArrayList<Cell> cells) {
//        this.cells = cells;
//    }
}
