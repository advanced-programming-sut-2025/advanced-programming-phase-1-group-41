package com.CEliconValley.client.view.screen;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.view.screen.maps.VillageMap;
import com.CEliconValley.client.view.screen.randomwalk.Node;
import com.CEliconValley.common.*;
import com.CEliconValley.common.messages.GameCommand;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.controllers.Spawner.*;
import com.CEliconValley.models.*;
import com.CEliconValley.models.foragings.Nature.Grass;
import com.CEliconValley.models.ui.GameAssetManager;
import com.CEliconValley.views.subGames.Rain;
import com.CEliconValley.views.subGames.Snow;
import com.CEliconValley.views.subGames.Thunder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.google.gson.Gson;

import java.util.*;

public class VillageScreen extends GameScreen implements Screen {
    private final SpriteBatch batch;
    private final Rain rain;
    private final Snow snow;
    private final Thunder thunder;
    private MenuBar menuBar;
    private final Player player;
    private final TreeSpawner treeSpawner;
    private final WaterSpawner waterSpawner;
    private final RockSpawner rockSpawner;
    private final BuildingSpawner buildingSpawner;
    private final GroundSpawner groundSpawner;
    private final CropSpawner cropSpawner;
    private VillageMap villageMap;
    private ArrayList<Hero> otherHeroes;
    private GroundBorderSpawner groundBorderSpawner;
    private final List<CellData> visibleCells = new ArrayList<>();
    private String currentSeason = "";

    Map<Cell, TextureRegion> groundCache;

    public static Texture villageTexture = new Texture("game/Buildings/Screen/Village_Screen_Spring.png");
    public static Sprite villageSprite;

    private OrthographicCamera camera;

    public static final float VIRTUAL_WIDTH = 3160f;
    public static final float VIRTUAL_HEIGHT = 1350f;
    public static final int CELLS_IN_WIDTH = 15;
    public static final int CELL_SIZE = (int) ((Gdx.graphics.getWidth() / VIRTUAL_WIDTH) * 160);

    private Animation<TextureRegion>[] walkAnimations;
    private float passiveStateTime = 0f;
    private boolean onRepeat = true;

    public VillageScreen( Player player) {

        super(new InventoryRenderer(player.getInventory()));
        this.menuBar = super.getMenuBar();
        menuBar.setPlayer(player);
        otherHeroes = new ArrayList<>();
        this.villageMap = new VillageMap(AppClient.getGameData().getVillageData());
        this.player = player;
        treeSpawner = new TreeSpawner();
        waterSpawner = new WaterSpawner();
        rockSpawner = new RockSpawner();
        buildingSpawner = new BuildingSpawner();
        groundSpawner = new GroundSpawner();
        cropSpawner = new CropSpawner();
        groundBorderSpawner = new GroundBorderSpawner();

        batch = new SpriteBatch();
        groundCache = new HashMap<>();

        villageSprite = new Sprite(villageTexture);
        villageSprite.setSize(CELL_SIZE, CELL_SIZE);
        camera = new OrthographicCamera();
        thunder = new Thunder();
        snow = new Snow();
        rain = new Rain(thunder);
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);


        Texture playerSheet = new Texture("game/general/character/heroWalk.png");
        int FRAME_COLS = 6, FRAME_ROWS = 3;
        TextureRegion[][] tmp = TextureRegion.split(
            playerSheet,
            playerSheet.getWidth() / FRAME_COLS,
            playerSheet.getHeight() / FRAME_ROWS
        );
        walkAnimations = new Animation[FRAME_ROWS];
        for (int i = 0; i < FRAME_ROWS; i++) {
            int frameCount = (i == 2) ? 4 : FRAME_COLS;
            TextureRegion[] frames = new TextureRegion[frameCount];
            for (int j = 0; j < frameCount; j++) {
                frames[j] = tmp[i][j];
            }
            walkAnimations[i] = new Animation<>(0.15f, frames);
        }

        hero.currentAnimation = walkAnimations[0];

        currentSeason = AppClient.getGameData().getTime().getSeason().name();
        loadVillageBackground(currentSeason);

        hero.renderX = 47*CELL_SIZE;
        hero.renderY = 32*CELL_SIZE;
        hero.playerX.set(47);
        hero.playerY.set(32);
        setDest();

        hero.targetX.set(hero.playerX.get());
        hero.targetY.set(hero.playerY.get());
    }

    @Override
    public void render(float delta) {
        if (isGameFinished) return;
        String season = AppClient.getGameData().getTime().getSeason().name();
        if (!season.equals(currentSeason)) {
            currentSeason = season;
            loadVillageBackground(season);
        }
        batch.setColor(Color.WHITE);
        Gdx.gl.glClearColor(0.8f, 0.9f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Result result = PlayerActs.handleInput(hero, villageMap, stage, delta);
        if (!result.success() && result.message().equals("cheat")) return;
        hero.stateTime += delta;

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        PlayerActs.approach(hero);

        batch.begin();
        int minX = (int) ((camera.position.x - camera.viewportWidth / 2) / CELL_SIZE) - 8;
        int maxX = (int) ((camera.position.x + camera.viewportWidth / 2) / CELL_SIZE) + 8;
        int minY = (int) ((camera.position.y - camera.viewportHeight / 2) / CELL_SIZE) - 8;
        int maxY = (int) ((camera.position.y + camera.viewportHeight / 2) / CELL_SIZE) + 8;

        visibleCells.clear();
        for (CellData cell : villageMap.villageData.getCellsData()) {
            if (cell.getX() < minX || cell.getX() > maxX || cell.getY() < minY || cell.getY() > maxY) {
                continue;
            }
            visibleCells.add(cell);
        }
        visibleCells.sort(Comparator.comparingInt(CellData::getY).reversed());

        batch.draw(villageTexture, 0, 0, CELL_SIZE * 95, CELL_SIZE * 65);

        Texture grassTexture = GameAssetManager.getGameAssetManager().getTileTexture("grass.png");
//        Texture groundTexture = GameAssetManager.getGameAssetManager().getTileTexture("Village_Tile.png");
        Texture sandTexture = GameAssetManager.getGameAssetManager().getTileTexture("sand.png");
        Texture thunderedTexture = GameAssetManager.getGameAssetManager().getTileTexture("thundered.png");
        Texture farmlandTexture = GameAssetManager.getGameAssetManager().getTileTexture("farmland.png");
        Texture bombedTexture = GameAssetManager.getGameAssetManager().getTileTexture("bombed.png");
        for (CellData cellData : visibleCells) {
            Cell cell = cellData.extractData();
            int x = cell.getX() * CELL_SIZE;
            int y = cell.getY() * CELL_SIZE;
            if (cell.getObjectMap() instanceof Grass grass) {
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
                }
            }
        }
        visibleCells.sort(Comparator.comparingInt(CellData::getY).reversed());
        for (CellData cellData : visibleCells){
            groundBorderSpawner.renderGround(batch, cellData, villageMap.villageData);
            waterSpawner.renderWater(batch, cellData, passiveStateTime, villageMap.villageData);
        }
        for (CellData cellData : visibleCells) {

//            rockSpawner.renderBreakingEffectForCell(batch, cellData, delta);
//            treeSpawner.renderTrees(batch, cellData, passiveStateTime);

            if (hero.playerX.get() == cellData.getX() && hero.playerY.get() == cellData.getY()) {
                TextureRegion frame = hero.currentAnimation.getKeyFrame(hero.stateTime, onRepeat);
                if (!onRepeat && hero.currentAnimation.isAnimationFinished(hero.stateTime)) {
                    hero.currentAnimation = hero.walk(false, hero.currentDirection);
                    onRepeat = true;
                    hero.isActing.set(false);
                    hero.stateTime = 0f;
                }
                batch.draw(frame, hero.renderX - CELL_SIZE / 2f, hero.renderY - CELL_SIZE / 2f, CELL_SIZE * 2f, CELL_SIZE * 2f);
            }
            buildingSpawner.renderBuildings(batch, cellData, villageMap.villageData);
        }

        if (AppClient.getGameData().getWeatherType().equals(WeatherType.Snowy)) snow.render(batch, camera);
        if (AppClient.getGameData().getWeatherType().equals(WeatherType.Rainy)) rain.render(batch, camera,200,false);
        if (AppClient.getGameData().getWeatherType().equals(WeatherType.Stormy)) rain.render(batch, camera,500,true);
        thunder.render(batch, camera);

        if (isMenuOpen) menuBar.render(batch, camera);
        else inventoryRenderer.render(batch, camera);

        passiveStateTime += delta;

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



    public void transfer() {
//        CellData cd = Finder.getcdByVillageData(hero.playerX.get(), hero.playerY.get(), villageMap.villageData);
//        CellData start = Finder.getfdVillage().getStartPoints().get(0);
//        if (start.getX() == hero.playerX.get() && start.getY() == hero.playerY.get()) {
//            ((com.badlogic.gdx.Game)Gdx.app.getApplicationListener())
//                .setScreen(new CottageScreen(this, new CottageMap(0,0), player));
//        } else {
//            Cell cell = cd.extractData();
//            if (cell.getObjectMap() instanceof Door) {
//                for (int i=-1; i<=1; i++) for (int j=-1; j<=1; j++) {
//                    CellData around = Finder.getcdByVillageData(hero.playerX.get()+i, hero.playerY.get()+j, villageMap.villageData);
//                    if (around.getObjectName().equals(new Greenhouse().getName())) {
//                        ((com.badlogic.gdx.Game)Gdx.app.getApplicationListener())
//                            .setScreen(new GreenHouseScreen(this, new GreenhouseMap(0,0), player));
//                    } else if (around.getObjectName().equals(new Barn().getName())) {
//                        ((com.badlogic.gdx.Game)Gdx.app.getApplicationListener())
//                            .setScreen(new BarnScreen(this, new BarnMap(0,0, BarnType.values()[findBarnByDoor().getBarnTypeInt()]), player));
//                    } else if (around.getObjectName().equals(new Coop().getName())) {
//                        ((com.badlogic.gdx.Game)Gdx.app.getApplicationListener())
//                            .setScreen(new CoopScreen(this, new CoopMap(0,0, CoopType.values()[findCoopByDoor().getCoopTypeInt()]), player));
//                    }
//                }
//            }
//        }
    }

    private VillageData findBarnByDoor() { return null; }
    private VillageData findCoopByDoor() {  return null; }

    private void loadVillageBackground(String season) {
        if (villageTexture != null) villageTexture.dispose();
        villageTexture = new Texture("game/Buildings/Screen/Village_Screen_" + season + ".png");
        villageSprite = new Sprite(villageTexture);
        villageSprite.setSize(CELL_SIZE, CELL_SIZE);
    }

    public void setDest() {

        hero.destX = 32;
        hero.destY = 47;
//        hero.movementQueue = new SimplePathFinder(villageMap).getPathQueue(hero.playerX.get(), hero.playerY.get(), hero.destX, hero.destY);
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
        Node next = hero.movementQueue.poll();
        if (next != null) {
            hero.targetX.set(next.x);
            hero.targetY.set(next.y);
            hero.currentDirection = next.getDirection()!=0 ? next.getDirection() : hero.currentDirection;
            hero.isMoving.set(true);
            hero.currentAnimation = hero.walk(true, hero.currentDirection);
            onRepeat = true;
            String command = switch(hero.currentDirection) {
                case 1 -> "walk up"; case 2 -> "walk right";
                case 3 -> "walk down"; case 4 -> "walk left";
                default -> "";
            };
            GameMessage<GameCommand> m = new GameMessage<>("game-command", new GameCommand(command, AppClient.getUserData().getUsername()));
            AppClient.getClient().send(new Gson().toJson(m));
        }
    }

    @Override public void dispose() {
        batch.dispose();
        villageTexture.dispose();
    }
    @Override public void show() { PlayerActs.setScreen(this); }
    @Override public void hide() { }
    @Override public void pause() { }
    @Override public void resume() { }
}
