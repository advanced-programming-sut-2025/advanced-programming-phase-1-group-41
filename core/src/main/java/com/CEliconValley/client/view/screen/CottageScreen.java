package com.CEliconValley.client.view.screen;

import com.CEliconValley.Main;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Hero;
import com.CEliconValley.models.Player;
//import com.CEliconValley.models.buildings.GreenHouse.Door;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.buildings.Wall;
import com.CEliconValley.models.foragings.ForagingTree;
import com.CEliconValley.models.foragings.Nature.Lake;
import com.CEliconValley.models.foragings.Nature.Rock;
//import com.CEliconValley.models.foragings.Nature.Wall;
import com.CEliconValley.views.maps.CottageMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class CottageScreen extends GameScreen implements Screen {
    private final FarmScreen farmScreen;
    private final Hero hero;
    private final SpriteBatch batch;
    private final Texture background;
    private final CottageMap cottageMap;
    private final Player player;

    int[][] directions = {
        {0, 1},
        {1, 0},
        {0, -1},
        {-1, 0},
    };


    private final OrthographicCamera camera;

    public CottageScreen(FarmScreen farmScreen, CottageMap cottageMap, Player player) {
        // give the correct inventory renderer
        super(null);
        this.hero = new Hero(cottageMap);
        this.farmScreen = farmScreen;
        this.cottageMap = cottageMap;
        this.player = player;
        this.batch = new SpriteBatch();
        this.background = new Texture("game/Buildings/Screen/Cottage_Screen.png");
        for (Cell cell : cottageMap.getCells()) {
            if (cell == null) continue;
            if (cell.getObjectMap() instanceof Door) {
                this.hero.playerX = cell.getX();
                this.hero.playerY = cell.getY();
                break;
            }
        }
        this.hero.targetX = hero.playerX;
        this.hero.targetY = hero.playerY;
        this.hero.renderX = hero.playerX * CELL_SIZE;
        this.hero.renderY = hero.playerY * CELL_SIZE;
        this.hero.currentAnimation = hero.walk(false, hero.currentDirection);

        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        Playeracts.handleInput(hero, cottageMap, stage, delta);
        Playeracts.approach(hero);

        Gdx.gl.glClearColor(0.8f, 0.9f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(background, CELL_SIZE/2f,CELL_SIZE/2f , CELL_SIZE * 24, CELL_SIZE * 16);


        if (hero.currentAnimation != null) {
            TextureRegion currentFrame = hero.currentAnimation.getKeyFrame(hero.stateTime, onRepeat);
            batch.draw(currentFrame, hero.renderX - CELL_SIZE, hero.renderY - CELL_SIZE, CELL_SIZE * 2f, CELL_SIZE * 2f);
        }

        camera.position.set(hero.renderX + CELL_SIZE / 2f, hero.renderY + CELL_SIZE / 2f, 0);
        camera.update();

        batch.end();
        hero.stateTime += delta;
    }
    public void transfer() {
        Cell cell = Finder.findCellByCoordinatesCottage(hero.playerX, hero.playerY, cottageMap);
        if (cell.getObjectMap() instanceof Door) {
            Playeracts.changeScreen(farmScreen);
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
