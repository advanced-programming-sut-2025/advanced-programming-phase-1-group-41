package com.CEliconValley.client.view.screen;

import com.CEliconValley.controllers.Spawner.*;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Hero;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.buildings.*;
import com.CEliconValley.models.buildings.GreenHouse.Greenhouse;
import com.CEliconValley.models.buildings.animalContainer.Barn;
import com.CEliconValley.models.foragings.ForagingTree;
import com.CEliconValley.models.foragings.Nature.*;
import com.CEliconValley.views.maps.BarnMap;
import com.CEliconValley.views.maps.CottageMap;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.views.maps.GreenhouseMap;
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
    private final Hero hero;
    private final InventoryRenderer inventoryRenderer;
    private boolean onRepeat=true;
    private boolean didHit=false;


    Map<Cell, TextureRegion> groundCache;





    public final static Texture grassTexture =new Texture("game/general/tiles/grass.png");
    ;
    boolean flip = false;



    private OrthographicCamera camera;

    public static final float VIRTUAL_WIDTH = 2400f;
    public static final float VIRTUAL_HEIGHT = 1350f;
    public static final int CELLS_IN_WIDTH = 15;
    public static final int CELL_SIZE =  (int) VIRTUAL_WIDTH / CELLS_IN_WIDTH;

//    public static final int CELL_SIZE = Gdx.graphics.getHeight()*144/1000;;

    private Animation<TextureRegion>[] walkAnimations;
    private Animation<TextureRegion>[] coastAnimations;
    private Animation<TextureRegion> currentAnimation;
    private float stateTime = 0f;
    private float passiveStateTime = 0f;

    private int playerX;
    private int playerY;


    private float renderX;
    private float renderY;

    private int playerDirection = 0;

    private boolean isActing = false;
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
        hero=new Hero(this.farm);
        inventoryRenderer = new InventoryRenderer(player.getInventory());


        batch = new SpriteBatch();
        groundCache = new HashMap<>();
        for(Cell cell : farm.getCells()) {
            TextureRegion ground = new TextureRegion(grassTexture);
            groundCache.put(cell, ground);
        }








        camera = new OrthographicCamera();
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



        currentAnimation = walkAnimations[0];
        for(Cell cell: farm.getCells()) {
            Cell doorCell=Finder.findCellByCoordinates(cell.getX(),cell.getY()+1,this.farm);
            Cell homeCell=Finder.findCellByCoordinates(cell.getX(),cell.getY()+2,this.farm);
            if(doorCell!=null&& doorCell.getObjectMap() instanceof Door){
                if(homeCell!=null && homeCell.getObjectMap() instanceof Cottage){
                    playerX = cell.getX();
                    playerY = cell.getY();
                }
            }
        }

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
        int minX = (int)((camera.position.x - camera.viewportWidth / 2) / CELL_SIZE) - 8;
        int maxX = (int)((camera.position.x + camera.viewportWidth / 2) / CELL_SIZE) + 8;
        int minY = (int)((camera.position.y - camera.viewportHeight / 2) / CELL_SIZE) - 8;
        int maxY = (int)((camera.position.y + camera.viewportHeight / 2) / CELL_SIZE) + 8;

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
            int x = (int) (cell.getX() * CELL_SIZE);
            int y = (int) (cell.getY() * CELL_SIZE);
//            batch.draw(grassTexture, x, y, CELL_SIZE, CELL_SIZE);
            batch.draw(groundCache.get(cell), x, y, CELL_SIZE, CELL_SIZE);
        }

        visibleCells.sort(Comparator.comparingInt(Cell::getY).reversed());
        for(Cell cell:visibleCells){


            int x = (int) (cell.getX() * CELL_SIZE);
            int y = (int) (cell.getY() * CELL_SIZE);

            buildingSpawner.renderBuildings(batch,cell,passiveStateTime);
//            rockSpawner.renderRocks(batch,cell,passiveStateTime);
//            cropSpawner.renderCrops(batch,cell,passiveStateTime);
            waterSpawner.renderWater(batch,cell,passiveStateTime);
//            rockSpawner.renderBreakingEffectForCell(batch, cell, delta);
            if(playerX == cell.getX() && playerY == cell.getY()){

                TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, onRepeat);
//                System.out.println("stateTime: " + stateTime + ", frameIndex: " + currentAnimation.getKeyFrameIndex(stateTime));
                if (!onRepeat&&currentAnimation.isAnimationFinished(stateTime)) {
                    currentAnimation = hero.walk(false, playerDirection);
                    isActing=false;


                }

                batch.draw(currentFrame, renderX-CELL_SIZE/2f, renderY-CELL_SIZE/2, CELL_SIZE*2f, CELL_SIZE*2f);
            }
//            treeSpawner.renderTrees(batch,cell,passiveStateTime);
//            if (didHit) {
//                hit(playerDirection, playerX, playerY);
//                didHit = false;
//            }
            inventoryRenderer.render(batch,camera);




        }

        stateTime += delta;
        passiveStateTime+=delta;


        camera.position.set(renderX + CELL_SIZE / 2f, renderY + CELL_SIZE / 2f, 0);
        camera.update();




        batch.end();
    }





    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            inventoryRenderer.shiftRight();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            inventoryRenderer.shiftLeft();
        }
        if (isActing||isMoving) return;


        boolean moved = false;
        onRepeat=true;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//            currentAnimation = hero.walk(canMoveTo(playerX, playerY+1),1);
                playerDirection = 1;
            if (canMoveTo(playerX, playerY + 1)) {
                targetX = playerX;
                targetY = playerY + 1;
                moved = true;
            }
            currentAnimation = hero.walk(moved,playerDirection);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//            currentAnimation = hero.walk(canMoveTo(playerX, playerY-1),3);
                playerDirection = 3;

            if (canMoveTo(playerX, playerY - 1)) {
                targetX = playerX;
                targetY = playerY - 1;
                moved = true;
            }
            currentAnimation = hero.walk(moved,playerDirection);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//            currentAnimation = hero.walk(canMoveTo(playerX-1, playerY),4);
                playerDirection = 4;
            if (canMoveTo(playerX - 1, playerY)) {
                targetX = playerX - 1;
                targetY = playerY;
                flip = true;
                moved = true;
            }
            currentAnimation = hero.walk(moved,playerDirection);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//            currentAnimation = hero.walk(canMoveTo(playerX+1, playerY),2);
                playerDirection = 2;
                flip = false;
            if (canMoveTo(playerX + 1, playerY)) {
                targetX = playerX + 1;
                targetY = playerY;
                moved = true;
            }
            currentAnimation = hero.walk(moved,playerDirection);
        } else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            onRepeat=false;
            currentAnimation = hero.useTool(3);
            isActing=true;
            stateTime = 0;
            didHit=true;
            hit(playerDirection,playerX,playerY);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            transfer();
        }
        else{
            currentAnimation = hero.walk(false,playerDirection);
        }

        if (moved) {
            isMoving = true;
//            currentAnimation = walkAnimations[playerDirection];
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
    private void hit(int direction,int cellX,int cellY) {
        switch (direction) {
            case 1:
                rockSpawner.hitRock(Finder.findCellByCoordinates(cellX, cellY+1,this.farm));
                return;
            case 2:
                rockSpawner.hitRock(Finder.findCellByCoordinates(cellX+1, cellY,this.farm));
                return;
            case 3:
                rockSpawner.hitRock(Finder.findCellByCoordinates(cellX, cellY-1, this.farm));
                return;
            case 4:
                rockSpawner.hitRock(Finder.findCellByCoordinates(cellX-1, cellY, this.farm));
                return;
        }
    }
    public void transfer(){
        Cell cell=Finder.findCellByCoordinates(playerX,playerY,this.farm);
        if(cell.getObjectMap() instanceof Door) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (Finder.findCellByCoordinates(playerX + i, playerY + j, this.farm).getObjectMap() instanceof Greenhouse) {

                        ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new GreenHouseScreen(this, new GreenhouseMap(0, 0), player));
                    } else if (Finder.findCellByCoordinates(playerX + i, playerY + j, this.farm).getObjectMap() instanceof Cottage) {
                        ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new CottageScreen(this, new CottageMap(0, 0), player));
                    }else if (Finder.findCellByCoordinates(playerX + i, playerY + j, this.farm).getObjectMap() instanceof Barn ) {
                        Barn barn = (Barn) Finder.findCellByCoordinates(playerX + i, playerY + j, this.farm).getObjectMap();
                        ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new BarnScreen(this, new BarnMap(0,0,barn.getBarnType()), player));
                    }
                }
            }
        }
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
