package com.CEliconValley.client.view.screen;

import com.CEliconValley.models.*;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.client.view.screen.maps.GreenhouseMap;
import com.CEliconValley.models.buildings.GreenHouse.Greenhouse;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class GreenHouseScreen extends GameScreen implements Screen {
    private final FarmScreen farmScreen;
    private final SpriteBatch batch;
    private final Texture background;
    private final GreenhouseMap greenHouse;
    private final Player player;


    int[][] directions = {
        {0, 1},
        {1, 0},
        {0, -1},
        {-1, 0},
    };




    //    private final TreeSpawner treeSpawner;
//    private final RockSpawner rockSpawner;
//    private final BuildingSpawner buildingSpawner;
    private final OrthographicCamera camera;



    public GreenHouseScreen(FarmScreen farmScreen,GreenhouseMap greenHouse, Player player) {
        super(null);
        this.farmScreen=farmScreen;
        this.greenHouse = greenHouse;
        this.player = player;
        this.batch = new SpriteBatch();
        this.background = new Texture("game/Buildings/Screen/GreenHouse_Screen.png");
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

//        treeSpawner = new TreeSpawner(greenHouse);
//        rockSpawner = new RockSpawner(greenHouse);
//        buildingSpawner = new BuildingSpawner(greenHouse);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom /= 2;
    }

    @Override
    public void render(float delta) {
        Result result = Playeracts.handleInput(hero, greenHouse, stage, delta);
        if(!result.success()){
            if(result.message().equals("cheat")){
                return;
            }
        }
        Playeracts.approach(hero);
        Gdx.gl.glClearColor(0.8f, 0.9f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();


        batch.draw(background, CELL_SIZE/2f, CELL_SIZE/2f,CELL_SIZE* Greenhouse.getGreenhouseLength(),CELL_SIZE*Greenhouse.getGreenhouseHeight());

        if (hero.currentAnimation != null) {
            TextureRegion currentFrame = hero.currentAnimation.getKeyFrame(hero.stateTime, onRepeat);
            batch.draw(currentFrame, hero.renderX - CELL_SIZE / 2f, hero.renderY - CELL_SIZE / 2f, CELL_SIZE, CELL_SIZE);
        }
        camera.position.set(hero.renderX + CELL_SIZE / 2f, hero.renderY + CELL_SIZE / 2f, 0);
        camera.update();

        batch.end();
        hero.stateTime += delta;
    }

    public void transfer() {
        Cell cell=Finder.findCellByCoordinatesGreenHouse(hero.playerX.get(), hero.playerY.get(),this.greenHouse);
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
        Playeracts.setScreen(this);
    }
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
}
