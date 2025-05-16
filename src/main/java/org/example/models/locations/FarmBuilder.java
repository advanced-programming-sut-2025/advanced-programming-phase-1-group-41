package org.example.models.locations;

import org.example.controllers.WeatherController;
import org.example.models.*;
import org.example.models.buildings.marketplaces.items.MarketplaceItems;
import org.example.models.foragings.Nature.*;
import org.example.models.foragings.*;
import org.example.models.items.CraftableItem;
import org.example.models.items.CraftableMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.example.models.App.MaxHeight;
import static org.example.models.App.MaxLength;

public class FarmBuilder {
    private final Farm farm;
    int rockCount;
    int foragingCropCount;
    int foragingTreeCount;
    int plantCount;

    public FarmBuilder(Farm farm) {
        this.farm = farm;
    }

    public void setCounters(){
        rockCount = 0;
        foragingCropCount = 0;
        foragingTreeCount = 0;
        plantCount = 0;
        for(Cell cell : farm.getCells()){
            if(cell.getObjectMap() instanceof Rock){
                rockCount++;
            }
            else if(cell.getObjectMap() instanceof ForagingCrop){
                foragingCropCount++;
            }
            else if(cell.getObjectMap() instanceof ForagingTree){
                foragingTreeCount++;
            }
            else if(cell.getObjectMap() instanceof Plant){
                plantCount++;
            }
        }

    }

    public void updateForagings(){
        setCounters();
        Random rand = new Random();
        rockCount = farm.getRockCount() - rockCount - 5 + rand.nextInt(11);
        foragingTreeCount = farm.getForagingTreeCount() - foragingTreeCount - 5 + rand.nextInt(11);
        plantCount = farm.getPlantCount() - plantCount - 5 + rand.nextInt(11);
        foragingCropCount = farm.getForagingCropCount() - foragingCropCount - 5 + rand.nextInt(11);
        for(int i = 0; i < rockCount ;i++){
            int y = rand.nextInt(MaxLength - 4) + 2;
            int x = rand.nextInt(MaxHeight - 4) + 2;
            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, farm)).getObjectMap().getChar(), new Grass().getChar())){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, farm)).setObjectMap(new Rock(x, y, farm));
            } else{
                i--;
            }
        }
        for(int i = 0; i < foragingTreeCount ;i++){
            int y = rand.nextInt(MaxLength - 4) + 4;
            int x = rand.nextInt(MaxHeight - 4) + 4;
            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, farm)).getObjectMap().getChar(), new Grass().getChar())){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, farm)).setObjectMap(new ForagingTree(x, y, farm));
            } else{
                i--;
            }
        }
        for(int i = 0; i < plantCount ;i++){
            int y = rand.nextInt(MaxLength - 4) + 4;
            int x = rand.nextInt(MaxHeight - 4) + 4;
            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, farm)).getObjectMap().getChar(), new Grass().getChar())){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, farm)).setObjectMap(new Plant(x, y, farm));
            } else{
                i--;
            }
        }
        for(int i = 0; i < foragingCropCount ;i++){
            int y = rand.nextInt(MaxLength - 4) + 4;
            int x = rand.nextInt(MaxHeight - 4) + 4;
            if(Objects.equals(Objects.requireNonNull(Finder.findCellByCoordinates(x, y, farm)).getObjectMap().getChar(), new Grass().getChar())){
                Objects.requireNonNull(Finder.findCellByCoordinates(x, y, farm)).setObjectMap(new ForagingCrop(x, y, farm));
            } else{
                i--;
            }
        }
    }
    public void growCrops(){
        List<Crop> toRemove = new ArrayList<>();
        for (Crop crop : farm.getCrops()) {
            crop.increaseStage();
            if (crop.shouldBeRemoved(farm)) {
                toRemove.add(crop);
            }
        }
        for(Crop crop : toRemove){
            farm.removeCrop(crop);
        }

        Random rand = new Random();
        Player player = App.getGame().getCurrentPlayer();
//        player.getInventory().addToInventory(new Fertilizer(FertilizerType.PlantGrow), 100);
//        player.getInventory().addToInventory(new Fertilizer(FertilizerType.BasicRetainingSoil), 100);
//        player.getInventory().addToInventory(new Fertilizer(FertilizerType.QualityRetainingSoil), 100);
//        player.getInventory().addToInventory(new Fertilizer(FertilizerType.DeluxeRetainingSoil), 100);
//        player.getInventory().addToInventory(new Fertilizer(FertilizerType.GrassStarter), 100);
//        player.getInventory().addToInventory(new Seed(SeedType.ApricotSapling), 100);
//        player.getInventory().addToInventory(new Seed(SeedType.Mixed), 100);
//        player.getInventory().addToInventory(new Seed(SeedType.StarfruitSeed), 100);
//        player.getInventory().addToInventory(new Seed(SeedType.CauliflowerSeed), 100);
//        player.getInventory().addToInventory(new Seed(SeedType.CoffeeBeanSeed), 100);
//        player.getInventory().addToInventory(CraftableMachine.DeluxeScarecrow, 100);
//        player.getInventory().addToInventory(CraftableMachine.Scarecrow, 100);
//        player.getInventory().addToInventory(new Crop(Flower.Poppy.getCropType()), 100);
//        player.getInventory().addToInventory(MarketplaceItems.Bouquet, 100);
//        player.getInventory().addToInventory(MarketplaceItems.WeddingRing, 100);
//        player.incMoney(100000);
    }
    public void growTrees(){
        List<Tree> toRemove = new ArrayList<>();
        for (Tree tree : farm.getTrees()) {
            tree.increaseStage();
            tree.setAttacked(false);
            if (tree.shouldBeRemoved()) {
                toRemove.add(tree);
            }
        }
        for(Tree tree : toRemove){
            farm.removeTree(tree);
        }
    }
    public void weatherUpdates(){
        for(Crop crop : farm.getCrops()){
            crop.nextDay();
        }
        for(Tree tree : farm.getTrees()){
            tree.nextDay();
        }
        if(App.getGame().getWeatherType().equals(WeatherType.Rainy) || App.getGame().getWeatherType().equals(WeatherType.Stormy)){
            for(Crop crop : farm.getCrops()){
                if(!farm.getGreenhouse().isGreenHouse(crop.getX(), crop.getY())){
                    crop.water();
                }
            }
            for(Tree tree : farm.getTrees()) {
                if(!farm.getGreenhouse().isGreenHouse(tree.getX(), tree.getY())){
                    tree.water();
                }
            }
        }
        if(App.getGame().getWeatherType().equals(WeatherType.Stormy)){
            WeatherController.strikeThunder(farm);
        }
    }
    public void scarecrowUpdate(){
        for(Cell cell : farm.getCells()){
            int radius = 8;
            if(cell.getObjectMap().getName().equals(CraftableMachine.DeluxeScarecrow.getName())){
                radius = 12;
            }
            if(cell.getObjectMap().getName().equals(CraftableMachine.Scarecrow.getName())
                || cell.getObjectMap().getName().equals(CraftableMachine.DeluxeScarecrow.getName())){
                for(int i = cell.getX() - radius; i <= cell.getX() + radius; i++){
                    for(int j = cell.getY() - radius; j <= cell.getY() + radius; j++){
                        Cell cell1 = Finder.findCellByCoordinates(i, j , farm);
                        if(cell1 != null && cell1.getObjectMap() != null){
                            if(cell1.getObjectMap() instanceof Tree tree){
                                tree.setIsProtected(true);
                            }
                            if(cell1.getObjectMap() instanceof Crop crop){
                                crop.setIsProtected(true);
                            }
                        }
                    }
                }
            }
        }
    }
    public void crowAttacks(){
        int items = farm.getCrops().size() + farm.getTrees().size();
//        System.out.println("Items: " + items);
        int crows = items / 16;
        Random rand = new Random();
        ArrayList<Crop> removedCrops = new ArrayList<>();
        boolean message = true;
        for(int i = 0; i < crows; i++){
            if(rand.nextInt(4) == 0){
                if(message){
                    System.out.println("Farm " + farm.getId() + "crow attacks last night:");
                    message = false;
                }
                if(rand.nextInt(2) == 0 && !farm.getCrops().isEmpty()){
                    Crop crop = farm.getCrops().get(rand.nextInt(farm.getCrops().size()));
                    if(farm.getGreenhouse().isGreenHouse(crop.getX(), crop.getY())){
                        System.out.println("A crow tried to attack your " + crop.getName() +
                                " at " + crop.getX() + ", " + crop.getY() + ". But it was in Greenhouse.");
                    } else if(crop.isProtected()){
                        System.out.println("A crow tried to attack your " + crop.getName() +
                                " at " + crop.getX() + ", " + crop.getY() + ". But it was protected by Scarecrow.");
                    } else{
                        System.out.println("A crow attacked your " + crop.getName() +
                                " at " + crop.getX() + ", " + crop.getY() + ". Now it's dead.");
                        removedCrops.add(crop);
                        Objects.requireNonNull(Finder.findCellByCoordinates(crop.getX(), crop.getY(), farm)).setObjectMap(new Grass());
                    }
                } else if(!farm.getTrees().isEmpty()) {
                    Tree tree = farm.getTrees().get(rand.nextInt(farm.getTrees().size()));
                    if(farm.getGreenhouse().isGreenHouse(tree.getX(), tree.getY())){
                        System.out.println("A crow tried to attack your " + tree.getName() +
                                " at " + tree.getX() + ", " + tree.getY() + ". But it was in Greenhouse.");
                    } else if(tree.isProtected()){
                        System.out.println("A crow tried to attack your " + tree.getName() +
                                " at " + tree.getX() + ", " + tree.getY() + ". But it was protected by Scarecrow.");
                    } else{
                        System.out.println("A crow attacked your " + tree.getName() +
                                " at " + tree.getX() + ", " + tree.getY() + ". It won't have fruit tomorrow.");
                        tree.setAttacked(true);
                    }
                }
            }
        }
//        System.out.println("Crops: " + farm.getCrops().size() + " Trees: " + farm.getTrees().size() + " Rm: " + removedCrops.size());
        farm.getCrops().removeAll(removedCrops);
    }

    private static final int WIDTH = 12;
    private static final int HEIGHT = 12;
    private static final int ITERATIONS = 5;
    private static final double INITIAL_WATER_CHANCE = 0.6;

    public void createGroundGrass(int startX, int startY){
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
                        cell.setObjectMap(new Grass(startX, startY, farm));
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
