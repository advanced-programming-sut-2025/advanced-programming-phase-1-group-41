package com.CEliconValley.client.view.screen;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.controller.spawners.ItemSpawner;
import com.CEliconValley.client.view.screen.maps.*;
import com.CEliconValley.client.view.screen.menu.ShippingBinBar;
import com.CEliconValley.client.view.screen.randomwalk.Node;
import com.CEliconValley.client.view.screen.randomwalk.SimplePathFinder;
import com.CEliconValley.common.*;
import com.CEliconValley.common.messages.GameCommand;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.controllers.Spawner.*;
import com.CEliconValley.models.*;
import com.CEliconValley.models.animals.Fish;
import com.CEliconValley.models.animals.FishType;
import com.CEliconValley.models.buildings.*;
import com.CEliconValley.models.buildings.GreenHouse.Greenhouse;
import com.CEliconValley.models.buildings.animalContainer.Barn;
import com.CEliconValley.models.buildings.animalContainer.BarnType;
import com.CEliconValley.models.buildings.animalContainer.Coop;
import com.CEliconValley.models.buildings.animalContainer.CoopType;
import com.CEliconValley.models.foragings.Nature.Grass;
import com.CEliconValley.models.foragings.Nature.Lake;
import com.CEliconValley.models.items.BuffType;
import com.CEliconValley.models.items.CraftableMachine;
import com.CEliconValley.models.items.craftablemachines.FishSmoker;
import com.CEliconValley.models.items.craftablemachines.Machine;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.models.tools.FishingRod;
import com.CEliconValley.models.tools.FishingRodLevel;
import com.CEliconValley.models.ui.GameAssetManager;
import com.CEliconValley.views.subGames.FishingMiniGame;
import com.CEliconValley.views.subGames.Rain;
import com.CEliconValley.views.subGames.Snow;
import com.CEliconValley.views.subGames.Thunder;
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
import com.badlogic.gdx.math.MathUtils;
import com.google.gson.Gson;

import java.util.*;

public
class FarmScreen extends GameScreen implements Screen {
    private final SpriteBatch batch;
    private final Rain rain;
    private final Snow snow;
    private final Thunder thunder;
    private MenuBar menuBar;
    private ArtisanMenu artisanMenu;
    private final Player player;
    private final TreeSpawner treeSpawner;
    private final WaterSpawner waterSpawner;
    private final RockSpawner rockSpawner;
    private final BuildingSpawner buildingSpawner;
    private final GroundSpawner groundSpawner;
    private final CropSpawner cropSpawner;
    private final ItemSpawner itemSpawner;
    private boolean isFishing=false;
    private FarmMap farmMap;
    private ArrayList<Hero> otherHeroes;
    private GroundBorderSpawner groundBorderSpawner;
    private final List<CellData> visibleCells = new ArrayList<>();
    private String currentSeason = "";


    Map<Cell, TextureRegion> groundCache;

    public static Texture farmTexture =GameAssetManager.getGameAssetManager().getScreenTexture("Farm_Screen_Spring.png");
    public static Sprite farmSprite;
    ;


    public OrthographicCamera camera;
    FishingMiniGame fishingMiniGame ;

    public static final float VIRTUAL_WIDTH = 3160f;
    public static final float VIRTUAL_HEIGHT = 1350f;
    public static final int CELLS_IN_WIDTH = 15;
    //    public static final int CELL_SIZE =  (int) VIRTUAL_WIDTH / CELLS_IN_WIDTH;
    public static final int CELL_SIZE = (int) ((Gdx.graphics.getWidth() / VIRTUAL_WIDTH) * 160);
//    public static final int CELL_SIZE = Gdx.graphics.getHeight()*144/1000;;

    private Animation<TextureRegion>[] walkAnimations;
    private Animation<TextureRegion>[] coastAnimations;
    private float passiveStateTime = 0f;


    public void updateFarmData() {
        // maybe needs change?
        Gdx.app.postRunnable(() -> {
            this.farmMap = new FarmMap(Finder.getFarmDataById(AppClient.getGameData(), AppClient.getUserData().getUsername()));
        });
    }

    public void updateOtherHeroes() {
        Gdx.app.postRunnable(() -> {
            otherHeroes.clear();
            for (PlayerData pd : AppClient.getGameData().getPlayersData()) {
                if (pd.getUsername().equals(AppClient.getUserData().getUsername())) continue;
                if (pd.getInFarmId() == farmMap.farmData.getId()) {
                    // todo important : maybe create smth new instead of hero ;)
                    otherHeroes.add(new Hero());
                }
            }
        });
    }

    private static final float MOVE_SPEED = 100f;

    public FarmScreen(Farm farm, Player player) {
        super(new InventoryRenderer(player.getInventory()));
        this.menuBar = super.getMenuBar();
        menuBar.setPlayer(player);
        artisanMenu=super.getArtisanMenu();
        artisanMenu.setPlayer(player);
        otherHeroes = new ArrayList<>();
        this.farmMap = new FarmMap(Finder.getFarmDataById(AppClient.getGameData(), AppClient.getUserData().getUsername()));
        System.out.println("this farm map " + this.farmMap.farmData.getId());
        this.player = player;
        treeSpawner = new TreeSpawner();
        waterSpawner = new WaterSpawner();
        rockSpawner = new RockSpawner();
        buildingSpawner = new BuildingSpawner();
        groundSpawner = new GroundSpawner();
        cropSpawner = new CropSpawner();
        groundBorderSpawner = new GroundBorderSpawner();
        itemSpawner = new ItemSpawner();

        batch = new SpriteBatch();
        groundCache = new HashMap<>();

        farmSprite = new Sprite(farmTexture);
        farmSprite.setSize(CELL_SIZE, CELL_SIZE);
        camera = new OrthographicCamera();

        thunder = new Thunder();
        snow = new Snow();
        rain = new Rain(thunder);
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
            if (doorCellData != null && doorCellData.getObjectName().equals(new Door().getName())) {
                if (homeCellData != null && homeCellData.getObjectName().equals(new Cottage().getName())) {
                    // TODO important send this data to server as well
                    hero.playerX.set(cell.getX());
                    hero.playerY.set(cell.getY());
                }
            }
        }
        currentSeason = AppClient.getGameData().getTime().getSeason().name();
        loadFarmBackground(currentSeason);
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


        shippingBinBar = new ShippingBinBar(this);
    }


    @Override
    public void render(float delta) {
        if(isGameFinished) return;
        String season = AppClient.getGameData().getTime().getSeason().name();
        if (!season.equals(currentSeason)) {
            currentSeason = season;
            loadFarmBackground(season);
        }
        batch.setColor(ApplyFog(getTimeColor(AppClient.getGameData().getTime().getHour())));
        Gdx.gl.glClearColor(0.8f, 0.9f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Result result = PlayerActs.handleInput(hero, farmMap, stage, delta);
        if (!result.success()) {
            if (result.message().equals("cheat") ||
                result.message().equals("chat") ||
                result.message().equals("friendship") ||
                result.message().equals("scoreboard")) {
                return;
            }
        }

        hero.stateTime += delta;

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        PlayerActs.approach(hero);

        batch.begin();
        int minX = (int) ((camera.position.x - camera.viewportWidth / 2) / CELL_SIZE) - 2;
        int maxX = (int) ((camera.position.x + camera.viewportWidth / 2) / CELL_SIZE) + 8;
        int minY = (int) ((camera.position.y - camera.viewportHeight / 2) / CELL_SIZE) - 8;
        int maxY = (int) ((camera.position.y + camera.viewportHeight / 2) / CELL_SIZE);

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

        batch.draw(farmTexture, 0, 0, (float) (CELL_SIZE * 60), (float) (CELL_SIZE * 75));

        Texture grassTexture = GameAssetManager.getGameAssetManager().getTileTexture("grass.png");
        Texture groundTexture = GameAssetManager.getGameAssetManager().getTileTexture("ground.png");
        Texture sandTexture = GameAssetManager.getGameAssetManager().getTileTexture("sand.png");
        Texture thunderedTexture = GameAssetManager.getGameAssetManager().getTileTexture("thundered.png");
        Texture farmlandTexture = GameAssetManager.getGameAssetManager().getTileTexture("farmland.png");
        Texture bombedTexture = GameAssetManager.getGameAssetManager().getTileTexture("bombed.png");
        for (CellData cellDate : visibleCells) {
            Cell cell = cellDate.extractData();
            int x = (int) (cell.getX() * CELL_SIZE);
            int y = (int) (cell.getY() * CELL_SIZE);
//            batch.draw(grassTexture, x, y, CELL_SIZE, CELL_SIZE);
            if (cell.getObjectMap() instanceof Grass) {
                Grass grass = (Grass) cell.getObjectMap();
                if (grass.isThundered()) {
                    batch.draw(thunderedTexture, x, y, CELL_SIZE, CELL_SIZE);
                } else if (grass.isBombed()) {
                    batch.draw(bombedTexture, x, y, CELL_SIZE, CELL_SIZE);
                } else if (grass.isFarmland()) {
                    batch.draw(farmlandTexture, x, y, CELL_SIZE, CELL_SIZE);
                } else if (grass.isGround()) {
//                    batch.draw(groundTexture, x, y, CELL_SIZE, CELL_SIZE);
                } else if (grass.isSand()) {
                    batch.draw(sandTexture, x, y, CELL_SIZE, CELL_SIZE);
                } else {
                }
            }
//            batch.draw(groundCache.get(cell), x, y, CELL_SIZE, CELL_SIZE);
        }

        visibleCells.sort(Comparator.comparingInt(CellData::getY).reversed());
        for (CellData cellData : visibleCells){
            groundBorderSpawner.renderGround(batch, cellData, farmMap.farmData);
            waterSpawner.renderWater(batch, cellData, passiveStateTime, farmMap.farmData);
        }
        for (CellData cellData : visibleCells) {
            Cell cell = cellData.extractData();

//            if(!(hero.isMoving.get() && ((hero.playerX.get() == cellData.getX() + 1 && hero.playerY.get() == cellData.getY())
//                || (hero.playerX.get() == cellData.getX() - 1 && hero.playerY.get() == cellData.getY()))
//                || (hero.playerX.get() == cellData.getX() && hero.playerY.get() == cellData.getY() + 1)
//                || (hero.playerX.get() == cellData.getX() && hero.playerY.get() == cellData.getY() - 1))) {
//            }


            buildingSpawner.renderBuildings(batch, cellData, farmMap.farmData);
            rockSpawner.renderRocks(batch, cellData, passiveStateTime);
            cropSpawner.renderCrops(batch, cellData, farmMap.farmData);
            rockSpawner.renderBreakingEffectForCell(batch, cellData, delta);
            itemSpawner.renderItems(batch, cellData, farmMap.farmData);
            if (hero.playerX.get() == cellData.getX() && hero.playerY.get() == cellData.getY()) {

                TextureRegion currentFrame = hero.currentAnimation.getKeyFrame(hero.stateTime, onRepeat);
//                System.out.println("stateTime: " + stateTime + ", frameIndex: " + currentAnimation.getKeyFrameIndex(stateTime));
                if (!onRepeat && hero.currentAnimation.isAnimationFinished(hero.stateTime)) {
                    System.out.println("im here for a reason im not sure " + hero.stateTime);
                    hero.currentAnimation = hero.walk(false, hero.currentDirection);
                    onRepeat = true;
                    hero.isActing.set(false);
                    hero.stateTime = 0f;

                }

                batch.draw(currentFrame, hero.renderX - CELL_SIZE / 2f, hero.renderY - CELL_SIZE / 2f, CELL_SIZE * 2f, CELL_SIZE * 2f);
            }
            treeSpawner.renderTrees(batch, cellData, passiveStateTime);
//            if (didHit) {
//                hit(hero.currentDirection, hero.playerX, hero.playerY);
//                didHit = false;
//            }


        }
        if (AppClient.getGameData().getWeatherType().equals(WeatherType.Snowy)) {
            snow.render(batch, camera);
        }
        if (AppClient.getGameData().getWeatherType().equals(WeatherType.Rainy)) {
            rain.render(batch, camera,200,false);
        }if (AppClient.getGameData().getWeatherType().equals(WeatherType.Stormy)) {
            rain.render(batch, camera,500,true);
        }
        batch.setColor(Color.WHITE);
        thunder.render(batch, camera);
        if (isFishing) {

            fishingMiniGame.update(Gdx.graphics.getDeltaTime());
            fishingMiniGame.render(batch);


            if (fishingMiniGame.isFinished()) {
                isFishing = false;
                if (fishingMiniGame.isSuccess()) {
                    fishing();
                } else {

                }
            }
        }
        if (isMenuOpen) {
//                menuBar.render(batch, menuX, menuY, menuWidth, menuHeight);
            menuBar.render(batch, camera);

        } else if(isArtisanMenuOpen){
            artisanMenu.render(batch, camera, cm);
        }else if(sellmode){
            shippingBinBar.render(batch, camera, true);
        }else if(trashmode){
            shippingBinBar.render(batch, camera, false);
        }
        else{
            inventoryRenderer.render(batch, camera);
        }

        passiveStateTime += delta;

        float halfViewportWidth = camera.viewportWidth * camera.zoom / 2;
        float halfViewportHeight = camera.viewportHeight * camera.zoom / 2;

        camera.position.x = MathUtils.clamp(camera.position.x, halfViewportWidth, farmTexture.getWidth() - halfViewportWidth);
        camera.position.y = MathUtils.clamp(camera.position.y, halfViewportHeight, farmTexture.getHeight() - halfViewportHeight);


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

    public void hit(int direction, int cellX, int cellY) {
        switch (direction) {
            case 1 -> {
                CellData cd = Finder.getcdByFarmData(cellX, cellY + 1, farmMap.farmData);
                rockSpawner.hitRock(cd, farmMap.farmData);
            }
            case 2 -> {
                CellData cd = Finder.getcdByFarmData(cellX + 1, cellY, farmMap.farmData);
                rockSpawner.hitRock(cd, farmMap.farmData);
            }
            case 3 -> {
                CellData cd = Finder.getcdByFarmData(cellX, cellY - 1, farmMap.farmData);
                rockSpawner.hitRock(cd, farmMap.farmData);
            }
            case 4 -> {
                CellData cd = Finder.getcdByFarmData(cellX - 1, cellY, farmMap.farmData);
                rockSpawner.hitRock(cd, farmMap.farmData);
            }
        }
    }

    public void transfer() {

        CellData cd = Finder.getcdByFarmData(hero.playerX.get(), hero.playerY.get(), farmMap.farmData);
        CellData startingpoint = Finder.getfd().getStartPoints().get(0);
        if (startingpoint.getX() == hero.playerX.get() && startingpoint.getY() == hero.playerY.get()) {
            ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new CottageScreen(this, new CottageMap(0, 0), player));
        } else {
            Cell cell = cd.extractData();
            if (cell.getObjectMap() instanceof Door) {
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        CellData around = Finder.getcdByFarmData(hero.playerX.get() + i, hero.playerY.get() + j, farmMap.farmData);
                        if (around.getObjectName().equals(new Greenhouse().getName())) {
                            ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new GreenHouseScreen(this, new GreenhouseMap(0, 0), player));
                        } else if (around.getObjectName().equals(new Barn().getName())) {
                            ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new BarnScreen(this,
                                new BarnMap(0, 0, BarnType.values()[findBarnByDoor().getBarnTypeInt()]), player,
                                findBarnByDoor()));
                        } else if (around.getObjectName().equals(new Coop().getName())) {
                            ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new CoopScreen(this,
                                new CoopMap(0, 0, CoopType.values()[findCoopByDoor().getCoopTypeInt()]), player,
                                findCoopByDoor()
                                ));
                        }
                    }
                }
            }
        }
    }

    private Color getTimeColor(int hour) {
        switch (hour) {

            case 9:
                return new Color(1f, 1f, 1f, 1f);
            case 10:
                return new Color(1f, 1f, 0.95f, 1f);
            case 11:
                return new Color(1f, 0.98f, 0.9f, 1f);
            case 12:
                return new Color(1f, 0.95f, 0.85f, 1f);
            case 13:
                return new Color(1f, 0.95f, 0.8f, 1f);
            case 14:
                return new Color(1f, 0.92f, 0.75f, 1f);
            case 15:
                return new Color(0.95f, 0.9f, 0.7f, 1f);
            case 16:
                return new Color(0.9f, 0.85f, 0.65f, 1f);
            case 17:
                return new Color(0.85f, 0.8f, 0.6f, 1f);
            case 18:
                return new Color(0.8f, 0.7f, 0.55f, 1f);
            case 19:
                return new Color(0.6f, 0.6f, 0.75f, 1f);
            case 20:
                return new Color(0.4f, 0.4f, 0.6f, 1f);
            case 21:
                return new Color(0.3f, 0.3f, 0.5f, 1f);
            case 22:
                return new Color(0.2f, 0.2f, 0.4f, 1f);
            case 23:
                return new Color(0.15f, 0.15f, 0.3f, 1f);
            case 0:
                return new Color(0.12f, 0.12f, 0.25f, 1f);
            default:
                return new Color(1f, 1f, 1f, 1f);
        }
    }

    private Color ApplyFog(Color color) {
        if (AppClient.getGameData().getWeatherType().equals(WeatherType.Rainy)) {
            return new Color(color.r * 0.6f, color.g * 0.6f, color.b * 0.6f, color.a);
        }
        if (AppClient.getGameData().getWeatherType().equals(WeatherType.Snowy)) {
            return new Color(color.r * 0.8f, color.g * 0.8f, color.b * 0.8f, color.a);
        }
        if (AppClient.getGameData().getWeatherType().equals(WeatherType.Stormy)) {
            return new Color(color.r * 0.4f, color.g * 0.4f, color.b * 0.4f, color.a);
        }
        return color;
    }


    @Override
    public void dispose() {

        batch.dispose();

//        grassTexture.dispose();


    }

    @Override
    public void show() {
        PlayerActs.setScreen(this);
        System.out.println("im here ;)");
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    private BarnData findBarnByDoor() {
        FarmData fd = this.farmMap.farmData;
        for (BarnData bd : fd.getBarnsData()) {
            int dx = Math.abs(bd.getX() - hero.playerX.get());
            int dy = Math.abs(bd.getY() - hero.playerY.get());
            System.out.println("dx " + dx);
            System.out.println("dy " + dy);
            if (dx < 3 && dy < 3) {
                return bd;
            }
        }
        return null;
    }

    private CoopData findCoopByDoor() {
        FarmData fd = this.farmMap.farmData;
        for (CoopData cd : fd.getCoopsData()) {
            int dx = Math.abs(cd.getX() - hero.playerX.get());
            int dy = Math.abs(cd.getY() - hero.playerY.get());
            System.out.println("dx " + dx);
            System.out.println("dy " + dy);
            if (dx < 3 && dy < 3) {
                return cd;
            }
        }
        return null;

    }

    public FarmMap getFarmMap() {
        return farmMap;
    }

    public Thunder getThunder() {
        return thunder;
    }
    private void loadFarmBackground(String season) {

        if (farmTexture != null) farmTexture.dispose();


        farmTexture = GameAssetManager.getGameAssetManager().getScreenTexture("Farm_Screen_" + season + ".png");
        farmSprite = new Sprite(farmTexture);
        farmSprite.setSize(CELL_SIZE, CELL_SIZE);
    }

    public void setDest(){
        CellData startingPoint = Finder.getfd().getStartPoints().get(0);
        hero.destX = startingPoint.getX();
        hero.destY = startingPoint.getY();

        SimplePathFinder spf = new SimplePathFinder(farmMap);
        hero.movementQueue = spf.getPathQueue(hero.playerX.get(), hero.playerY.get(), hero.destX, hero.destY);


    }

    public void nextMovement() {
        if (hero.reachedDestination()) {
            GameMessage<GameCommand> msg = new GameMessage<>("game-command", new GameCommand("at home", AppClient.getUserData().getUsername()));
            AppClient.getClient().send(new Gson().toJson(msg));
            transfer();
            hero.movementQueue.clear();
            return;
        }

        if (hero.isMoving.get()) return;

        Node nextNode = hero.movementQueue.poll();
        if (nextNode != null) {
            hero.targetX.set(nextNode.x);
            hero.targetY.set(nextNode.y);
            hero.currentDirection = nextNode.getDirection() != 0 ? nextNode.getDirection() : hero.currentDirection;
            hero.isMoving.set(true);
            hero.currentAnimation = hero.walk(true, hero.currentDirection);
            onRepeat = true;
            String command = "";
            switch (hero.currentDirection){
                case 1 -> command = "walk up";
                case 2 -> command = "walk right";
                case 3 -> command = "walk down";
                case 4 -> command = "walk left";
            }
            GameMessage<GameCommand> msg = new GameMessage<>("game-command", new GameCommand(command, AppClient.getUserData().getUsername()));
            AppClient.getClient().send(new Gson().toJson(msg));
        }
    }


    public boolean isFishing() {
        return isFishing;
    }

    public void setFishing(boolean fishing) {
        isFishing = fishing;
    }
    public void startFishing(FishType fishType) {
        fishingMiniGame = new FishingMiniGame(camera, fishType);
        isFishing = true;
    }
    public boolean isLakeAhead(){
        boolean isThatLake=false;
         switch (Finder.getpd().currentDirection) {
             case 1 -> {
                 isThatLake= Finder.getcdByFarmData(getHero().playerX.get(), getHero().playerY.get() + 1, Finder.getfd()).extractData().getObjectMap().getName().equals(new Lake().getName());
             }
             case 2 ->
                isThatLake=Finder.getcdByFarmData(getHero().playerX.get() + 1, getHero().playerY.get() , Finder.getfd()).extractData().getObjectMap() instanceof Lake;
            case 3 ->
                    isThatLake= Finder.getcdByFarmData(getHero().playerX.get(), getHero().playerY.get() +-1, Finder.getfd()).extractData().getObjectMap().getName().equals(new Lake().getName());
            case 4 ->
                    isThatLake= Finder.getcdByFarmData(getHero().playerX.get()-1, getHero().playerY.get() , Finder.getfd()).extractData().getObjectMap().getName().equals(new Lake().getName());
        };
         return isThatLake;
    }
    public Result fishing(){
        GameData game=AppClient.getGameData();
        Player player = Finder.getpd().getPlayer();

        FishingRod fishingRod = (FishingRod) player.getCurrentTool();

        double weatherEffect;
        switch(game.getWeatherType()){
            case Sunny:
                weatherEffect=1.5;
                break;
            case Rainy:
                weatherEffect=1.2;
                break;
            case Stormy:
                weatherEffect=0.5;
                break;
            default:
                weatherEffect=1.0;
                break;
        }

        int quantityOfFish=(int)Math.floor(Math.random()*weatherEffect*(player.getFishingSkill().getLevel() + 2));
        Fish caughtFish = null;
        double fishQuality=1.0;
        if(fishingRod.getLevel() == FishingRodLevel.Training){
            switch(game.getTime().getSeason()){
                case Spring:
                    caughtFish=new Fish(FishType.Herring);
                    break;
                case Summer:
                    caughtFish=new Fish(FishType.Sunfish);
                    break;
                case Autumn:
                    caughtFish=new Fish(FishType.Sardine);
                    break;
                case Winter:
                    caughtFish=new Fish(FishType.Perch);
                    break;
            }
        }
        else{
            int chance = 1 + (int)(Math.random() * 100);
            switch(game.getTime().getSeason()){
                case Spring:
                    if(chance<7&&player.getFishingSkill().isMaxLevel()){
                        caughtFish=new Fish(FishType.Legend);
                    }
                    else if(chance<30){
                        caughtFish=new Fish(FishType.Flounder);
                    }
                    else if(chance<50){
                        caughtFish=new Fish(FishType.Lionfish);
                    }
                    else if(chance<75){
                        caughtFish=new Fish(FishType.Herring);
                    }
                    else{
                        caughtFish=new Fish(FishType.Ghostfish);
                    }
                    break;
                case Summer:
                    if(chance<7&&player.getFishingSkill().isMaxLevel()){
                        caughtFish=new Fish(FishType.Crimsonfish);
                    }
                    else if(chance<30){
                        caughtFish=new Fish(FishType.Tilapia);
                    }
                    else if(chance<50){
                        caughtFish=new Fish(FishType.Dorado);
                    }
                    else if(chance<75){
                        caughtFish=new Fish(FishType.Sunfish);
                    }
                    else{
                        caughtFish=new Fish(FishType.RainbowTrout);
                    }
                    break;
                case Autumn:
                    if(chance<7&&player.getFishingSkill().isMaxLevel()){
                        caughtFish=new Fish(FishType.Angler);
                    }
                    else if(chance<30){
                        caughtFish=new Fish(FishType.Sardine);
                    }
                    else if(chance<50){
                        caughtFish=new Fish(FishType.Shad);
                    }
                    else if(chance<75){
                        caughtFish=new Fish(FishType.BlueDiscus);
                    }
                    else{
                        caughtFish=new Fish(FishType.Salmon);
                    }
                    break;
                case Winter:
                    if(chance<20&&player.getFishingSkill().isMaxLevel()){
                        caughtFish=new Fish(FishType.Glacierfish);
                    }
                    else if(chance<30){
                        caughtFish=new Fish(FishType.MidnightCarp);
                    }
                    else if(chance<50){
                        caughtFish=new Fish(FishType.Perch);
                    }
                    else if(chance<75){
                        caughtFish=new Fish(FishType.Tuna);
                    }
                    else{
                        caughtFish=new Fish(FishType.Squid);
                    }
                    break;

            }
        }
        int value =player.getFishingSkill().isMaxLevel() ? -1 : 0;
        int energy = fishingRod.getLevel().getEnergyUsage() + value;
        if(player.getBuff() != null){
            if(player.getBuff().getBuffType().equals(BuffType.Fishing)){
                System.out.println("since you have a buff you're gonna lose 1 less energy");
                if(energy >= 1){
                    energy--;
                }
            }
        }
//        if(energy > App.getGame().getCurrentPlayer().getEnergy()){
//            new Result(false, "your energy is too low..");
//        }
        fishQuality=Math.floor(Math.random()*( App.getGame().getCurrentPlayer().getFishingSkill().getLevel() + 2)*fishingRod.getLevel().getPole())/(7-weatherEffect);
        assert caughtFish != null;
        caughtFish.setQuality(fishQuality);
        player.getFishingSkill().increaseXp(quantityOfFish * 5);
        //todo dec of energy is not checked here
        player.getInventory().addToInventory(caughtFish,quantityOfFish,(int)fishQuality);
        return new Result(true,"You have "+quantityOfFish+" fresh fish of "+caughtFish.getFishType().getName());

    }

}
