package com.CEliconValley.client.view.screen;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.controller.spawners.ItemSpawner;
import com.CEliconValley.client.view.screen.maps.FarmMap;
import com.CEliconValley.common.CellData;
import com.CEliconValley.controllers.Spawner.BuildingSpawner;
import com.CEliconValley.controllers.Spawner.CropSpawner;
import com.CEliconValley.controllers.Spawner.InventoryRenderer;
import com.CEliconValley.controllers.Spawner.TreeSpawner;
import com.CEliconValley.models.*;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.client.view.screen.maps.GreenhouseMap;
import com.CEliconValley.models.buildings.GreenHouse.Greenhouse;
import com.CEliconValley.models.ui.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class GreenHouseScreen extends GameScreen implements Screen {
    private final FarmScreen farmScreen;
    private final Texture background;
    public final GreenhouseMap greenHouseMap;
    private final Player player;

    private final TreeSpawner treeSpawner;
    private final CropSpawner cropSpawner;
    private final ItemSpawner itemSpawner;
    private FarmMap farmMap;
    private final List<CellData> greenhouseCells = new ArrayList<>();

    int[][] directions = {
        {0, 1},
        {1, 0},
        {0, -1},
        {-1, 0},
    };




    //    private final TreeSpawner treeSpawner;
//    private final RockSpawner rockSpawner;
//    private final BuildingSpawner buildingSpawner;
    public final OrthographicCamera camera;

    public GreenHouseScreen(FarmScreen farmScreen,GreenhouseMap greenHouse, Player player) {
        super(new InventoryRenderer(player.getInventory()));
        this.menuBar = super.getMenuBar();
        menuBar.setPlayer(player);
        this.farmScreen=farmScreen;
        this.greenHouseMap = greenHouse;
        this.player = player;
        this.background = GameAssetManager.getGameAssetManager().getScreenTexture("GreenHouse_Screen.png");
        for(Cell cell : greenHouse.getCells()) {
            if(cell==null){continue;}
            if(cell.getObjectMap() instanceof Door) {
                this.hero.playerX.set(cell.getX());
                this.hero.playerY.set(cell.getY());
                break;
            }
        }
        this.hero.targetX.set(hero.playerX.get());
        this.hero.targetY.set(hero.playerY.get());
        this.hero.renderX = hero.playerX.get() * CELL_SIZE;
        this.hero.renderY = hero.playerY.get() * CELL_SIZE;
        this.hero.currentAnimation = hero.walk(false, hero.currentDirection);

        treeSpawner = new TreeSpawner();
        cropSpawner = new CropSpawner();
        itemSpawner = new ItemSpawner();
        this.farmMap = new FarmMap(Finder.getFarmDataById(AppClient.getGameData(), AppClient.getUserData().getUsername()));



        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom /= 2;
    }

    @Override
    public void render(float delta) {
        if(isGameFinished) return;
        Result result = PlayerActs.handleInput(hero, greenHouseMap, stage, delta);
        if(!result.success()){
            if (result.message().equals("cheat") ||
                result.message().equals("chat") ||
                result.message().equals("friendship") ||
                result.message().equals("scoreboard")) {
                return;
            }
        }
        PlayerActs.approach(hero);
        Gdx.gl.glClearColor(0.8f, 0.9f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(background, CELL_SIZE/2f, CELL_SIZE/2f,CELL_SIZE* Greenhouse.getGreenhouseLength(),CELL_SIZE*Greenhouse.getGreenhouseHeight());

        greenhouseCells.clear();
        farmMap.farmData = Finder.getfd();
        for(CellData cellData : farmMap.farmData.getCells()){
            if(cellData.getX() >= farmMap.farmData.getGreenhouseX() && cellData.getY() >= farmMap.farmData.getGreenhouseY()
                && cellData.getX() < farmMap.farmData.getGreenhouseX() + Greenhouse.getGreenhouseLength()
                && cellData.getY() < farmMap.farmData.getGreenhouseY() + Greenhouse.getGreenhouseHeight()){
                greenhouseCells.add(cellData);
            }
        }
        for (CellData cellData : greenhouseCells) {
            cropSpawner.renderCrops(batch, cellData, this);
            itemSpawner.renderItems(batch, cellData, farmMap.farmData);
            treeSpawner.renderTrees(batch, cellData, 0, this);
        }

        if (hero.currentAnimation != null) {
            TextureRegion currentFrame = hero.currentAnimation.getKeyFrame(hero.stateTime, onRepeat);
            //                System.out.println("stateTime: " + stateTime + ", frameIndex: " + currentAnimation.getKeyFrameIndex(stateTime));
            if (!onRepeat && hero.currentAnimation.isAnimationFinished(hero.stateTime)) {
                System.out.println("im here for a reason im not sure " + hero.stateTime);
                hero.currentAnimation = hero.walk(false, hero.currentDirection);
                onRepeat = true;
                hero.isActing.set(false);
                hero.stateTime = 0f;
            }
            batch.draw(currentFrame, hero.renderX - CELL_SIZE / 2f, hero.renderY - CELL_SIZE / 2f, CELL_SIZE, CELL_SIZE);
        }
        if (isMenuOpen) {
            menuBar.render(batch, camera);
        } else {
            inventoryRenderer.render(batch, camera);
        }
        camera.position.set(hero.renderX + CELL_SIZE / 2f, hero.renderY + CELL_SIZE / 2f, 0);
        camera.update();
        batch.end();
        stage.act(delta);
        stage.draw();
        hero.stateTime += delta;
    }

    public void transfer() {
        Cell cell=Finder.findCellByCoordinatesGreenHouse(hero.playerX.get(), hero.playerY.get(),this.greenHouseMap);
        if (cell.getObjectMap() instanceof Door) {
            ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(farmScreen);
        }
    }


    @Override public void resize(int width, int height) {

    }

    @Override public void dispose() {
        batch.dispose();
        background.dispose();
    }

    @Override public void show() {
        PlayerActs.setScreen(this);
    }
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
}
