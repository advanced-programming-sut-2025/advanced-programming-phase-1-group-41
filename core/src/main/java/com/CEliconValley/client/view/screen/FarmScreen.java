package com.CEliconValley.client.view.screen;

import com.CEliconValley.Main;
import com.CEliconValley.client.controller.CheatCodeController;
import com.CEliconValley.client.view.screen.maps.CoopMap;
import com.CEliconValley.controllers.Spawner.*;
import com.CEliconValley.models.*;
import com.CEliconValley.models.buildings.*;
import com.CEliconValley.models.buildings.GreenHouse.Greenhouse;
import com.CEliconValley.models.buildings.animalContainer.Barn;
import com.CEliconValley.models.buildings.animalContainer.Coop;
import com.CEliconValley.models.foragings.ForagingTree;
import com.CEliconValley.models.foragings.Nature.*;
import com.CEliconValley.models.ui.GameAssetManager;
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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.math.MathUtils;

import java.util.*;

public class FarmScreen extends GameScreen implements Screen {
    private final SpriteBatch batch;
    private MenuBar menuBar;
    private final Farm farm;
    private final Player player;
    private final TreeSpawner treeSpawner;
    private final WaterSpawner waterSpawner;
    private final RockSpawner rockSpawner;
    private final BuildingSpawner buildingSpawner;
    private final GroundSpawner groundSpawner;
    private final CropSpawner cropSpawner;
    private final Hero hero;

    private final List<Cell> visibleCells = new ArrayList<>();



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
    private float stateTime = 0f;
    private float passiveStateTime = 0f;

    private int prevMinX;
    private int prevMaxX;
    private int prevMinY;
    private int prevMaxY;





    private static final float MOVE_SPEED = 100f;

    @SuppressWarnings("unchecked")
    public FarmScreen(Farm farm, Player player) {
        super(new InventoryRenderer(player.getInventory()));



        stage.addActor(cheatCodeField);

        this.farm = farm;
        this.player = player;
        treeSpawner=new TreeSpawner(this.farm);
        waterSpawner=new WaterSpawner(this.farm);
        rockSpawner=new RockSpawner(this.farm);
        buildingSpawner=new BuildingSpawner(this.farm);
        groundSpawner=new GroundSpawner(this.farm);
        cropSpawner=new CropSpawner(this.farm);
        hero=new Hero(this.farm);


        batch = new SpriteBatch();
        groundCache = new HashMap<>();





        farmSprite = new Sprite(farmTexture);
        farmSprite.setSize(CELL_SIZE, CELL_SIZE);
        camera = new OrthographicCamera();
//        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        menuBar = new MenuBar();




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
        for(Cell cell: farm.getCells()) {
            Cell doorCell=Finder.findCellByCoordinates(cell.getX(),cell.getY()+1,this.farm);
            Cell homeCell=Finder.findCellByCoordinates(cell.getX(),cell.getY()+2,this.farm);
            if(doorCell!=null&& doorCell.getObjectMap() instanceof Door){
                if(homeCell!=null && homeCell.getObjectMap() instanceof Cottage){
                    hero.playerX = cell.getX();
                    hero.playerY = cell.getY();
                }
            }
        }

        hero.renderX = hero.playerX * CELL_SIZE;
        hero.renderY = hero.playerY * CELL_SIZE;

        hero.targetX = hero.playerX;
        hero.targetY = hero.playerY;

    }




    @Override
    public void render(float delta) {
        Result result = Playeracts.handleInput(hero, farm, stage, delta);
        if(!result.success()){
            if(result.message().equals("cheat")){
                return;
            }
        }

        Gdx.gl.glClearColor(0.8f, 0.9f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
            for (Cell cell : farm.getCells()) {
                int cellX = cell.getX();
                int cellY = cell.getY();

                if (cellX < minX || cellX > maxX || cellY < minY || cellY > maxY) {
    //                visibleCells.remove(cell);
                    continue;
                }
                visibleCells.add(cell);
            }
            visibleCells.sort(Comparator.comparingInt(Cell::getY).reversed());
//        });
//        for(Cell cell:visibleCells) {
//            int x = (int) (cell.getX() * CELL_SIZE);
//            int y = (int) (cell.getY() * CELL_SIZE);
////            batch.draw(grassTexture, x, y, CELL_SIZE, CELL_SIZE);
//            batch.draw(groundCache.get(cell), x, y, CELL_SIZE, CELL_SIZE);
//        }

        batch.draw(farmTexture,0,0,farmSprite.getWidth()*2*75,farmSprite.getHeight()*2*60);

        prevMinX = minX;
        prevMaxX = maxX;
        prevMinY = minY;
        prevMaxY = maxY;
        visibleCells.sort(Comparator.comparingInt(Cell::getY).reversed());
        for(Cell cell:visibleCells){


            int x = (int) (cell.getX() * CELL_SIZE);
            int y = (int) (cell.getY() * CELL_SIZE);

            buildingSpawner.renderBuildings(batch,cell,passiveStateTime);
            rockSpawner.renderRocks(batch,cell,passiveStateTime);
            cropSpawner.renderCrops(batch,cell,passiveStateTime);
            waterSpawner.renderWater(batch,cell,passiveStateTime);
            rockSpawner.renderBreakingEffectForCell(batch, cell, delta);
            if(hero.playerX == cell.getX() && hero.playerY == cell.getY()){

                TextureRegion currentFrame = hero.currentAnimation.getKeyFrame(stateTime, onRepeat);
//                System.out.println("stateTime: " + stateTime + ", frameIndex: " + currentAnimation.getKeyFrameIndex(stateTime));
                if (!onRepeat&&hero.currentAnimation.isAnimationFinished(stateTime)) {
                    hero.currentAnimation = hero.walk(false, hero.currentDirection);
                    hero.isActing=false;


                }

                batch.draw(currentFrame, hero.renderX-CELL_SIZE/2f, hero.renderY-CELL_SIZE/2f, CELL_SIZE*2f, CELL_SIZE*2f);
            }
            treeSpawner.renderTrees(batch,cell,passiveStateTime);
//            if (didHit) {
//                hit(hero.currentDirection, hero.playerX, hero.playerY);
//                didHit = false;
//            }




        }
            if(isMenuOpen){
//                menuBar.render(batch, menuX, menuY, menuWidth, menuHeight);
                menuBar.render(batch,camera,player.getInventory());
            }else {
                inventoryRenderer.render(batch, camera);
            }

        stateTime += delta;
        passiveStateTime+=delta;

        float halfViewportWidth = camera.viewportWidth * camera.zoom / 2;
        float halfViewportHeight = camera.viewportHeight * camera.zoom / 2;

//        camera.position.x = MathUtils.clamp(camera.position.x, halfViewportWidth, mapWidth - halfViewportWidth);
//        camera.position.y = MathUtils.clamp(camera.position.y, halfViewportHeight, mapHeight - halfViewportHeight);


        camera.position.set(hero.renderX + CELL_SIZE / 2f, hero.renderY + CELL_SIZE / 2f, 0);
        camera.update();




        batch.end();



    }







    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }
    public void hit(int direction,int cellX,int cellY) {
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
        Cell cell=Finder.findCellByCoordinates(hero.playerX,hero.playerY,this.farm);
        if(cell.getObjectMap() instanceof Door) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (Finder.findCellByCoordinates(hero.playerX + i, hero.playerY + j, this.farm).getObjectMap() instanceof Greenhouse) {

                        ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new GreenHouseScreen(this, new GreenhouseMap(0, 0), player));
                    } else if (Finder.findCellByCoordinates(hero.playerX + i, hero.playerY + j, this.farm).getObjectMap() instanceof Cottage) {
                        ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new CottageScreen(this, new CottageMap(0, 0), player));
                    }else if (Finder.findCellByCoordinates(hero.playerX + i, hero.playerY + j, this.farm).getObjectMap() instanceof Barn ) {
                        Barn barn = (Barn) Finder.findCellByCoordinates(hero.playerX + i, hero.playerY + j, this.farm).getObjectMap();
                        ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new BarnScreen(this, new BarnMap(0,0,barn.getBarnType()), player));
                    }else if (Finder.findCellByCoordinates(hero.playerX + i, hero.playerY + j, this.farm).getObjectMap() instanceof Coop coop) {
                        ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new CoopScreen(this, new CoopMap(0,0,coop.getCoopType()), player));
                    }
                }
            }
        }
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


}
