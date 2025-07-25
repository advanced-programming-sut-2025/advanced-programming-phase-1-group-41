package com.CEliconValley.models.locations;

import com.CEliconValley.controllers.Spawner.*;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.buildings.Cottage;
import com.CEliconValley.models.buildings.Wall;
import com.CEliconValley.models.foragings.ForagingTree;
import com.CEliconValley.models.foragings.Nature.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.*;

public class FarmScreen implements Screen {
    private final SpriteBatch batch;
    private final Farm farm;
    private final Player player;
    private final TreeSpawner treeSpawner;
    private final WaterSpawner waterSpawner;
    private final RockSpawner rockSpawner;
    private final BuildingSpawner buildingSpawner;
    private final GroundSpawner groundSpawner;
    private final CropSpawner cropSpawner;


    Map<Cell, TextureRegion> groundCache;





    public final static Texture grassTexture =new Texture("game/general/tiles/grass.png");
    ;
    boolean flip = false;



    private OrthographicCamera camera;

    public static final int CELL_SIZE = 150;

    private Animation<TextureRegion>[] walkAnimations;
    private Animation<TextureRegion>[] coastAnimations;
    private Animation<TextureRegion> currentAnimation;
    private float stateTime = 0f;
    private float passiveStateTime = 0f;

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

    private int playerDirection = 0;

    private boolean isMoving = false;
    private int targetX;
    private int targetY;

    private static final float MOVE_SPEED = 600f;

    @SuppressWarnings("unchecked")
    public FarmScreen(Farm farm, Player player) {
        this.farm = farm;
        this.player = player;
        treeSpawner=new TreeSpawner(this.farm);
        waterSpawner=new WaterSpawner(this.farm);
        rockSpawner=new RockSpawner(this.farm);
        buildingSpawner=new BuildingSpawner(this.farm);
        groundSpawner=new GroundSpawner(this.farm);
        cropSpawner=new CropSpawner(this.farm);


        batch = new SpriteBatch();
        groundCache = new HashMap<>();
        for(Cell cell : farm.getCells()) {
            TextureRegion ground = new TextureRegion(grassTexture);
            groundCache.put(cell, ground);
        }








        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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



        currentAnimation = walkAnimations[0];

        playerX = farm.getCells().get(0).getX();
        playerY = farm.getCells().get(0).getY();

        renderX = playerX * CELL_SIZE;
        renderY = playerY * CELL_SIZE;

        targetX = playerX;
        targetY = playerY;

    }




    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(0.8f, 0.9f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        if (isMoving) {
            float targetPixelX = targetX * CELL_SIZE;
            float targetPixelY = targetY * CELL_SIZE;

            float moveAmount = MOVE_SPEED * delta;

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
//                stateTime = 0f;
            }
        }

        batch.begin();
        int minX = (int)((camera.position.x - camera.viewportWidth / 2) / CELL_SIZE) - 10;
        int maxX = (int)((camera.position.x + camera.viewportWidth / 2) / CELL_SIZE) + 10;
        int minY = (int)((camera.position.y - camera.viewportHeight / 2) / CELL_SIZE) - 10;
        int maxY = (int)((camera.position.y + camera.viewportHeight / 2) / CELL_SIZE) + 10;

        List<Cell> visibleCells = new ArrayList<>();

        for (Cell cell : farm.getCells()) {
            int cellX = cell.getX();
            int cellY = cell.getY();

            if (cellX < minX || cellX > maxX || cellY < minY || cellY > maxY) {
                continue;


            }visibleCells.add(cell);
        }
        visibleCells.sort(Comparator.comparingInt(Cell::getY).reversed());
        for(Cell cell:visibleCells) {
            int x = cell.getX() * CELL_SIZE;
            int y = cell.getY() * CELL_SIZE;
//            batch.draw(grassTexture, x, y, CELL_SIZE, CELL_SIZE);
            batch.draw(groundCache.get(cell), x, y, CELL_SIZE, CELL_SIZE);
        }

        visibleCells.sort(Comparator.comparingInt(Cell::getY).reversed());
        for(Cell cell:visibleCells){


            int x = cell.getX() * CELL_SIZE;
            int y = cell.getY() * CELL_SIZE;

            buildingSpawner.renderBuildings(batch,cell,passiveStateTime);
            rockSpawner.renderRocks(batch,cell,passiveStateTime);
            cropSpawner.renderCrops(batch,cell,passiveStateTime);
            waterSpawner.renderWater(batch,cell,passiveStateTime);
            if(playerX == cell.getX() && playerY == cell.getY()){

                TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, true);
                if (currentFrame.isFlipX() != flip) {
                    currentFrame.flip(true, false);
                }
                batch.draw(currentFrame, renderX, renderY, CELL_SIZE, CELL_SIZE);
            }
            treeSpawner.renderTrees(batch,cell,passiveStateTime);




        }

        stateTime += delta;
        passiveStateTime+=delta;


        camera.position.set(renderX + CELL_SIZE / 2f, renderY + CELL_SIZE / 2f, 0);
        camera.update();




        batch.end();
    }





    private void handleInput() {
        if (isMoving) return;

        boolean moved = false;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (canMoveTo(playerX, playerY + 1)) {
                targetX = playerX;
                targetY = playerY + 1;
                playerDirection = 1;
                moved = true;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if (canMoveTo(playerX, playerY - 1)) {
                targetX = playerX;
                targetY = playerY - 1;
                playerDirection = 0;
                moved = true;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (canMoveTo(playerX - 1, playerY)) {
                targetX = playerX - 1;
                targetY = playerY;
                playerDirection = 2;
                flip = false;
                moved = true;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (canMoveTo(playerX + 1, playerY)) {
                targetX = playerX + 1;
                targetY = playerY;
                playerDirection = 2;
                flip = true;
                moved = true;
            }
        }

        if (moved) {
            isMoving = true;
            currentAnimation = walkAnimations[playerDirection];
        }
    }

    private boolean canMoveTo(int x, int y) {
        for (Cell cell : farm.getCells()) {
            if (cell.getX() == x && cell.getY() == y) {
                if (cell.getObjectMap() instanceof Lake || cell.getObjectMap() instanceof Rock||cell.getObjectMap() instanceof Wall||cell.getObjectMap() instanceof ForagingTree) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }






    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }





    @Override
    public void dispose() {
        batch.dispose();


        grassTexture.dispose();
    }

    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
}
