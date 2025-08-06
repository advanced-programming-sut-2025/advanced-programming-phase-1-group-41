package com.CEliconValley.client.view.screen;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.view.screen.maps.*;
import com.CEliconValley.common.*;
import com.CEliconValley.controllers.Spawner.*;
import com.CEliconValley.models.*;
import com.CEliconValley.models.buildings.*;
import com.CEliconValley.models.buildings.GreenHouse.Greenhouse;
import com.CEliconValley.models.buildings.animalContainer.Barn;
import com.CEliconValley.models.buildings.animalContainer.BarnType;
import com.CEliconValley.models.buildings.animalContainer.Coop;
import com.CEliconValley.models.buildings.animalContainer.CoopType;
import com.CEliconValley.models.foragings.Nature.Grass;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.models.ui.GameAssetManager;
import com.CEliconValley.views.subGames.Rain;
import com.CEliconValley.views.subGames.Snow;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.*;

public class FarmScreen extends GameScreen implements Screen {
    private final SpriteBatch batch;
    private final Rain rain;
    private final Snow snow;
    private MenuBar menuBar;
    private final Player player;
    private final TreeSpawner treeSpawner;
    private final WaterSpawner waterSpawner;
    private final RockSpawner rockSpawner;
    private final BuildingSpawner buildingSpawner;
    private final GroundSpawner groundSpawner;
    private final CropSpawner cropSpawner;
    private FarmMap farmMap;
    private ArrayList<Hero> otherHeroes;
    private final List<CellData> visibleCells = new ArrayList<>();



    Map<Cell, TextureRegion> groundCache;

    public static Texture farmTexture =new Texture("game/Buildings/Screen/Farm_Screen.png");
    public static Sprite farmSprite;
    ;

    private OrthographicCamera camera;

    public static final float VIRTUAL_WIDTH = 3160f ;
    public static final float VIRTUAL_HEIGHT = 1350f;
    public static final int CELLS_IN_WIDTH = 15;
//    public static final int CELL_SIZE =  (int) VIRTUAL_WIDTH / CELLS_IN_WIDTH;
    public static final int CELL_SIZE =  (int) ((Gdx.graphics.getWidth() / VIRTUAL_WIDTH) * 160);
//    public static final int CELL_SIZE = Gdx.graphics.getHeight()*144/1000;;

    private Animation<TextureRegion>[] walkAnimations;
    private Animation<TextureRegion>[] coastAnimations;
    private float passiveStateTime = 0f;

    private int prevMinX;
    private int prevMaxX;
    private int prevMinY;
    private int prevMaxY;


    public void updateFarmData(){
        // maybe needs change?
        Gdx.app.postRunnable(() -> {
            this.farmMap = new FarmMap(Finder.getFarmDataById(AppClient.getGameData(), AppClient.getUserData().getUsername()));
        });
    }

    public void updateOtherHeroes(){
        Gdx.app.postRunnable(() -> {
            otherHeroes.clear();
            for (PlayerData pd : AppClient.getGameData().getPlayersData()) {
                if(pd.getUsername().equals(AppClient.getUserData().getUsername())) continue;
                if(pd.getInFarmId() == farmMap.farmData.getId()){
                    // todo important : maybe create smth new instead of hero ;)
                    otherHeroes.add(new Hero());
                }
            }
        });
    }
    private static final float MOVE_SPEED = 100f;

    public FarmScreen(Farm farm, Player player) {
        super(new InventoryRenderer(player.getInventory()));
        this.menuBar=super.getMenuBar();
        menuBar.setPlayer(player);
        otherHeroes = new ArrayList<>();
        this.farmMap = new FarmMap(Finder.getFarmDataById(AppClient.getGameData(), AppClient.getUserData().getUsername()));
        this.player = player;
        treeSpawner=new TreeSpawner();
        waterSpawner=new WaterSpawner();
        rockSpawner=new RockSpawner();
        buildingSpawner=new BuildingSpawner();
        groundSpawner=new GroundSpawner();
        cropSpawner=new CropSpawner();

        batch = new SpriteBatch();
        groundCache = new HashMap<>();

        farmSprite = new Sprite(farmTexture);
        farmSprite.setSize(CELL_SIZE, CELL_SIZE);
        camera = new OrthographicCamera();
        rain = new Rain();
        snow = new Snow();
//        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        int FRAME_COLS;
        int FRAME_ROWS;

        Texture playerSheet = new Texture("game/general/character/heroWalk.png");
        FRAME_COLS = 6;
        FRAME_ROWS = 3;

        TextureRegion[][] tmp = TextureRegion.split(playerSheet,
            playerSheet.getWidth() / FRAME_COLS,
            playerSheet.getHeight() / FRAME_ROWS);

        walkAnimations = new Animation[3];
        for (int i = 0; i < FRAME_ROWS; i++) {
            int frameCount = (i == 2) ? 4 : FRAME_COLS;
            TextureRegion[] frames = new TextureRegion[frameCount];
            for (int j = 0; j < frameCount; j++) {
                frames[j] = tmp[i][j];
            }
            walkAnimations[i] = new Animation<>(0.15f, frames);
        }



        hero.currentAnimation = walkAnimations[0];
        for (CellData cell : farmMap.farmData.getCells()) {
            CellData doorCellData = Finder.getcdByFarmData(cell.getX(), cell.getY() + 1, farmMap.farmData);
            CellData homeCellData = Finder.getcdByFarmData(cell.getX(), cell.getY() + 2, farmMap.farmData);
            if(doorCellData!=null&& doorCellData.getObjectName().equals(new Door().getName())){
                if(homeCellData!=null && homeCellData.getObjectName().equals(new Cottage().getName())){
                    // TODO important send this data to server as well
                    hero.playerX.set(cell.getX());
                    hero.playerY.set(cell.getY());
                }
            }
        }
//        for(Cell cell: farm.getCells()) {
//            Cell doorCell=Finder.findCellByCoordinates(cell.getX(),cell.getY()+1,this.farm);
//            Cell homeCell=Finder.findCellByCoordinates(cell.getX(),cell.getY()+2,this.farm);
//            if(doorCell!=null&& doorCell.getObjectMap() instanceof Door){
//                if(homeCell!=null && homeCell.getObjectMap() instanceof Cottage){
//                    hero.playerX = cell.getX();
//                    hero.playerY = cell.getY();
//                }
//            }
//        }

        hero.renderX = hero.playerX.get() * CELL_SIZE;
        hero.renderY = hero.playerY.get() * CELL_SIZE;

        hero.targetX.set(hero.playerX.get());
        hero.targetY.set(hero.playerY.get());

    }




    @Override
    public void render(float delta) {
        batch.setColor(ApplyFog(getTimeColor(AppClient.getGameData().getTime().getHour())));
        Gdx.gl.glClearColor(0.8f, 0.9f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Result result = Playeracts.handleInput(hero, farmMap, stage, delta);
        if(!result.success()){
            if(result.message().equals("cheat")){
                return;
            }
        }

        hero.stateTime += delta;

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Playeracts.approach(hero);

        batch.begin();
        int minX = (int)((camera.position.x - camera.viewportWidth / 2) / CELL_SIZE) - 8;
        int maxX = (int)((camera.position.x + camera.viewportWidth / 2) / CELL_SIZE) + 8;
        int minY = (int)((camera.position.y - camera.viewportHeight / 2) / CELL_SIZE) - 8;
        int maxY = (int)((camera.position.y + camera.viewportHeight / 2) / CELL_SIZE) + 8;

        visibleCells.clear();
//        Gdx.app.postRunnable(() -> {
            for (CellData cell : farmMap.farmData.getCells()) {
                int cellX = cell.getX();
                int cellY = cell.getY();

                if (cellX < minX || cellX > maxX || cellY < minY || cellY > maxY) {
    //                visibleCells.remove(cell);
                    continue;
                }
                visibleCells.add(cell);
            }
            visibleCells.sort(Comparator.comparingInt(CellData::getY).reversed());
//        });

        batch.draw(farmTexture,0,0,farmSprite.getWidth()*2*75,farmSprite.getHeight()*2*60);

        Texture grassTexture = GameAssetManager.getGameAssetManager().getTileTexture("grass.png");
        Texture groundTexture = GameAssetManager.getGameAssetManager().getTileTexture("ground.png");
        Texture sandTexture = GameAssetManager.getGameAssetManager().getTileTexture("sand.png");
        Texture thunderedTexture = GameAssetManager.getGameAssetManager().getTileTexture("thundered.png");
        Texture farmlandTexture = GameAssetManager.getGameAssetManager().getTileTexture("farmland.png");
        Texture bombedTexture = GameAssetManager.getGameAssetManager().getTileTexture("bombed.png");
        for(CellData cellDate:visibleCells) {
            Cell cell = cellDate.extractData();
            int x = (int) (cell.getX() * CELL_SIZE);
            int y = (int) (cell.getY() * CELL_SIZE);
//            batch.draw(grassTexture, x, y, CELL_SIZE, CELL_SIZE);
            if(cell.getObjectMap() instanceof Grass){
                Grass grass = (Grass) cell.getObjectMap();
                if(grass.isThundered()){
                    batch.draw(thunderedTexture,x,y, CELL_SIZE, CELL_SIZE);
                } else if(grass.isBombed()){
                    batch.draw(bombedTexture,x,y, CELL_SIZE, CELL_SIZE);
                } else if(grass.isFarmland()){
                    batch.draw(farmlandTexture,x,y, CELL_SIZE, CELL_SIZE);
                } else if(grass.isGround()){
                    batch.draw(groundTexture, x, y, CELL_SIZE, CELL_SIZE);
                } else if(grass.isSand()){
                    batch.draw(sandTexture, x, y, CELL_SIZE, CELL_SIZE);
                } else {
                }
            }
//            batch.draw(groundCache.get(cell), x, y, CELL_SIZE, CELL_SIZE);
        }


        prevMinX = minX;
        prevMaxX = maxX;
        prevMinY = minY;
        prevMaxY = maxY;
        visibleCells.sort(Comparator.comparingInt(CellData::getY).reversed());
        for(CellData cellData:visibleCells){
            buildingSpawner.renderBuildings(batch,cellData, farmMap.farmData);
            rockSpawner.renderRocks(batch,cellData,passiveStateTime);
            cropSpawner.renderCrops(batch,cellData,farmMap.farmData);
            waterSpawner.renderWater(batch,cellData,passiveStateTime, farmMap.farmData);
            rockSpawner.renderBreakingEffectForCell(batch, cellData, delta);
            if(hero.playerX.get() == cellData.getX() && hero.playerY.get() == cellData.getY()){

                TextureRegion currentFrame = hero.currentAnimation.getKeyFrame(hero.stateTime, onRepeat);
//                System.out.println("stateTime: " + stateTime + ", frameIndex: " + currentAnimation.getKeyFrameIndex(stateTime));
                if (!onRepeat&&hero.currentAnimation.isAnimationFinished(hero.stateTime)) {
                    System.out.println("im here for a reason im not sure "+hero.stateTime);
                    hero.currentAnimation = hero.walk(false, hero.currentDirection);
                    onRepeat = true;
                    hero.isActing.set(false);
                    hero.stateTime = 0f;

                }

                batch.draw(currentFrame, hero.renderX-CELL_SIZE/2f, hero.renderY-CELL_SIZE/2f, CELL_SIZE*2f, CELL_SIZE*2f);
            }
            treeSpawner.renderTrees(batch,cellData,passiveStateTime);
//            if (didHit) {
//                hit(hero.currentDirection, hero.playerX, hero.playerY);
//                didHit = false;
//            }




        }
        if(AppClient.getGameData().getWeatherType().equals(WeatherType.Snowy)){
            snow.render(batch,camera);
        }
        if(AppClient.getGameData().getWeatherType().equals(WeatherType.Rainy)){
            rain.render(batch,camera);
        }
        batch.setColor(Color.WHITE);
        if(isMenuOpen){
//                menuBar.render(batch, menuX, menuY, menuWidth, menuHeight);
                menuBar.render(batch,camera);
            }else {
                inventoryRenderer.render(batch, camera);
            }

        passiveStateTime+=delta;

        float halfViewportWidth = camera.viewportWidth * camera.zoom / 2;
        float halfViewportHeight = camera.viewportHeight * camera.zoom / 2;

//        camera.position.x = MathUtils.clamp(camera.position.x, halfViewportWidth, mapWidth - halfViewportWidth);
//        camera.position.y = MathUtils.clamp(camera.position.y, halfViewportHeight, mapHeight - halfViewportHeight);


        camera.position.set(hero.renderX + CELL_SIZE / 2f, hero.renderY + CELL_SIZE / 2f, 0);
        camera.update();


        batch.end();
        stage.act(delta);
        stage.draw();



    }







    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }
    public void hit(int direction,int cellX,int cellY) {
        switch (direction) {
            case 1 -> {
                CellData cd = Finder.getcdByFarmData(cellX, cellY+1, farmMap.farmData);
                rockSpawner.hitRock(cd, farmMap.farmData);
            }
            case 2 -> {
                CellData cd = Finder.getcdByFarmData(cellX+1, cellY, farmMap.farmData);
                rockSpawner.hitRock(cd, farmMap.farmData);
            }
            case 3 -> {
                CellData cd = Finder.getcdByFarmData(cellX, cellY-1, farmMap.farmData);
                rockSpawner.hitRock(cd, farmMap.farmData);
            } case 4 -> {
                CellData cd = Finder.getcdByFarmData(cellX-1, cellY, farmMap.farmData);
                rockSpawner.hitRock(cd, farmMap.farmData);
            }
        }
    }
    public void transfer(){

        CellData cd = Finder.getcdByFarmData(hero.playerX.get(), hero.playerY.get(), farmMap.farmData);
        Cell cell= cd.extractData();
        if(cell.getObjectMap() instanceof Door) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    CellData around =Finder.getcdByFarmData(hero.playerX.get() + i, hero.playerY.get() + j, farmMap.farmData);
                    if(around.getObjectName().equals(new Greenhouse().getName())){
                        ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new GreenHouseScreen(this, new GreenhouseMap(0, 0), player));
                    }else if(around.getObjectName().equals(new Cottage().getName())){
                        ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new CottageScreen(this, new CottageMap(0, 0), player));
                    }else if(around.getObjectName().equals(new Barn().getName())){
                        ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new BarnScreen(this,
                            new BarnMap(0,0, BarnType.values()[findBarnByDoor().getBarnTypeInt()]), player));
                    }else if(around.getObjectName().equals(new Coop().getName())){
                        ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new CoopScreen(this,
                            new CoopMap(0,0, CoopType.values()[findCoopByDoor().getCoopTypeInt()]), player));
                    }
                }
            }
        }
    }
    private Color getTimeColor(int hour) {
        switch (hour) {

            case 9: return new Color(1f, 1f, 1f, 1f);
            case 10: return new Color(1f, 1f, 0.95f, 1f);
            case 11: return new Color(1f, 0.98f, 0.9f, 1f);
            case 12: return new Color(1f, 0.95f, 0.85f, 1f);
            case 13: return new Color(1f, 0.95f, 0.8f, 1f);
            case 14: return new Color(1f, 0.92f, 0.75f, 1f);
            case 15: return new Color(0.95f, 0.9f, 0.7f, 1f);
            case 16: return new Color(0.9f, 0.85f, 0.65f, 1f);
            case 17: return new Color(0.85f, 0.8f, 0.6f, 1f);
            case 18: return new Color(0.8f, 0.7f, 0.55f, 1f);
            case 19: return new Color(0.6f, 0.6f, 0.75f, 1f);
            case 20: return new Color(0.4f, 0.4f, 0.6f, 1f);
            case 21: return new Color(0.3f, 0.3f, 0.5f, 1f);
            case 22: return new Color(0.2f, 0.2f, 0.4f, 1f);
            case 23: return new Color(0.15f, 0.15f, 0.3f, 1f);
            default: return new Color(1f, 1f, 1f, 1f);
        }
    }
    private Color ApplyFog(Color color){
        if(AppClient.getGameData().getWeatherType().equals(WeatherType.Rainy)) {
            return new Color(color.r * 0.6f, color.g * 0.6f, color.b * 0.6f, color.a);
        }
        return color;
    }




    @Override
    public void dispose() {

            batch.dispose();

//        grassTexture.dispose();


    }

    @Override public void show() {
        Playeracts.setScreen(this);
        System.out.println("im here ;)");
    }
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}

    private BarnData findBarnByDoor(){
        FarmData fd = this.farmMap.farmData;
        for (BarnData bd : fd.getBarnsData()) {
            int dx = Math.abs(bd.getX() - hero.playerX.get());
            int dy = Math.abs(bd.getY() - hero.playerY.get());
            System.out.println("dx "+dx);
            System.out.println("dy "+dy);
            if(dx < 3 && dy < 3){
                return bd;
            }
        }
        return null;
    }
    private CoopData findCoopByDoor(){
        FarmData fd = this.farmMap.farmData;
        for (CoopData cd : fd.getCoopsData()) {
            int dx = Math.abs(cd.getX() - hero.playerX.get());
            int dy = Math.abs(cd.getY() - hero.playerY.get());
            System.out.println("dx "+dx);
            System.out.println("dy "+dy);
            if(dx < 3 && dy < 3){
                return cd;
            }
        }
        return null;

    }

}
