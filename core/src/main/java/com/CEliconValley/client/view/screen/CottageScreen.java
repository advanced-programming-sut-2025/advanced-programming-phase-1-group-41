package com.CEliconValley.client.view.screen;

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

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class CottageScreen implements Screen {
    private final FarmScreen farmScreen;
    private final Hero hero;
    private final SpriteBatch batch;
    private final Texture background;
    private final CottageMap cottageMap;
    private final Player player;
    private Animation<TextureRegion> currentAnimation;
    private int playerDirection = 3;
    private boolean isActing = false;
    private boolean onRepeat = false;

    private int playerX;
    private int playerY;

    int[][] directions = {
        {0, 1},
        {1, 0},
        {0, -1},
        {-1, 0},
    };

    private float renderX;
    private float renderY;

    private boolean isMoving = false;
    private int targetX;
    private int targetY;

    private final OrthographicCamera camera;
    private float stateTime = 0f;

    public CottageScreen(FarmScreen farmScreen, CottageMap cottageMap, Player player) {
        this.hero = new Hero(cottageMap);
        this.farmScreen = farmScreen;
        this.cottageMap = cottageMap;
        this.player = player;
        this.batch = new SpriteBatch();
        this.background = new Texture("game/Buildings/Screen/Cottage_Screen.png");
        for (Cell cell : cottageMap.getCells()) {
            if (cell == null) continue;
            if (cell.getObjectMap() instanceof Door) {
                this.playerX = cell.getX();
                this.playerY = cell.getY();
                break;
            }
        }
        this.targetX = playerX;
        this.targetY = playerY;
        this.renderX = playerX * CELL_SIZE;
        this.renderY = playerY * CELL_SIZE;
        this.currentAnimation = hero.walk(false, playerDirection);

        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        handleInput();
        if (isMoving) {
            float moveAmount = 600 * delta;
            float targetPixelX = targetX * CELL_SIZE;
            float targetPixelY = targetY * CELL_SIZE;

            if (renderX < targetPixelX) {
                renderX += moveAmount;
                if (renderX > targetPixelX) renderX = targetPixelX;
            } else if (renderX > targetPixelX) {
                renderX -= moveAmount;
                if (renderX < targetPixelX) renderX = targetPixelX;
            }

            if (renderY < targetPixelY) {
                renderY += moveAmount;
                if (renderY > targetPixelY) renderY = targetPixelY;
            } else if (renderY > targetPixelY) {
                renderY -= moveAmount;
                if (renderY < targetPixelY) renderY = targetPixelY;
            }

            if (renderX == targetPixelX && renderY == targetPixelY) {
                playerX = targetX;
                playerY = targetY;
                isMoving = false;
            }
        }

        Gdx.gl.glClearColor(0.8f, 0.9f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(background, CELL_SIZE/2f,CELL_SIZE/2f , CELL_SIZE * 24, CELL_SIZE * 16);

        for (Cell cell : cottageMap.getCells()) {
            int x = cell.getX() * CELL_SIZE;
            int y = cell.getY() * CELL_SIZE;

        }

        if (currentAnimation != null) {
            TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, onRepeat);
            batch.draw(currentFrame, renderX - CELL_SIZE, renderY - CELL_SIZE, CELL_SIZE * 2f, CELL_SIZE * 2f);
        }

        camera.position.set(renderX + CELL_SIZE / 2f, renderY + CELL_SIZE / 2f, 0);
        camera.update();

        batch.end();
        stateTime += delta;
    }

    private void handleInput() {
        if (isActing || isMoving) return;

        boolean moved = false;
        onRepeat = true;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerDirection = 1;
            if (canMoveTo(playerX, playerY + 1)) {
                targetX = playerX;
                targetY = playerY + 1;
                moved = true;
            }
            currentAnimation = hero.walk(moved, playerDirection);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerDirection = 3;
            if (canMoveTo(playerX, playerY - 1)) {
                targetX = playerX;
                targetY = playerY - 1;
                moved = true;
            }
            currentAnimation = hero.walk(moved, playerDirection);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerDirection = 4;
            if (canMoveTo(playerX - 1, playerY)) {
                targetX = playerX - 1;
                targetY = playerY;
                moved = true;
            }
            currentAnimation = hero.walk(moved, playerDirection);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerDirection = 2;
            if (canMoveTo(playerX + 1, playerY)) {
                targetX = playerX + 1;
                targetY = playerY;
                moved = true;
            }
            currentAnimation = hero.walk(moved, playerDirection);
        } else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            onRepeat = false;
            currentAnimation = hero.useTool(3);
            isActing = true;
            stateTime = 0;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            transfer();
        } else {
            currentAnimation = hero.walk(false, playerDirection);
        }

        if (moved) {
            isMoving = true;
        }
    }

    private boolean canMoveTo(int x, int y) {
        for (Cell cell : cottageMap.getCells()) {
            if (cell.getX() == x && cell.getY() == y) {
                if (cell.getObjectMap() instanceof Lake || cell.getObjectMap() instanceof Rock
                    || cell.getObjectMap() instanceof Wall || cell.getObjectMap() instanceof ForagingTree) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public void transfer() {
        Cell cell = Finder.findCellByCoordinatesCottage(playerX, playerY, cottageMap);
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

    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
}
