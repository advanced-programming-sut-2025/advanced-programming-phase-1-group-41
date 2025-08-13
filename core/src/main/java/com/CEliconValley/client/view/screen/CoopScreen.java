package com.CEliconValley.client.view.screen;

import com.CEliconValley.client.model.AnimalSprite;
import com.CEliconValley.client.view.screen.maps.CoopMap;
import com.CEliconValley.client.view.screen.randomwalk.Node;
import com.CEliconValley.client.view.screen.randomwalk.SimplePathFinder;
import com.CEliconValley.common.AnimalData;
import com.CEliconValley.common.CoopData;
import com.CEliconValley.models.*;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.buildings.animalContainer.CoopType;
import com.CEliconValley.models.ui.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class CoopScreen extends GameScreen implements Screen {
    private final FarmScreen farmScreen;
    private final SpriteBatch batch;
    private final TextureRegion background;
    private final CoopMap coop;
    private BarnOrCoopMenuBar coopMenuBar;
    private boolean isCoopMenuOpen = false;
    private ArrayList<AnimalSprite> animalSprites;
    private CoopData coopData;
    private int id;
    public void updateAnimalSprites(CoopData coopData) {
        if(animalSprites == null) {
            animalSprites = new ArrayList<>();
        }
        if(coopData.getAnimalsData().size() == this.animalSprites.size()) {
            return;
        }
        this.animalSprites = new ArrayList<>();
        this.coopData = coopData;
        for (int i = 0; i < coopData.getAnimalsData().size(); i++) {
            AnimalData ad = coopData.getAnimalsData().get(i);
            animalSprites.add(new AnimalSprite(coop, ad,
                hero.playerX.get()+ (3*i % 5), hero.playerY.get()- (i % 7)));
        }
    }

    public CoopScreen(FarmScreen farmScreen, CoopMap coop, Player player, CoopData coopData) {
        super(null);
        this.coopMenuBar = super.getBarnOrCoopMenuBar();
        coopMenuBar.setPlayer(player);
        this.farmScreen = farmScreen;
        this.coop = coop;
        this.coopData = coopData;
        this.batch = new SpriteBatch();
        for (Cell cell : coop.getCells()) {
            if (cell == null) continue;
            if (cell.getObjectMap() instanceof Door) {
                this.hero.playerX.set(cell.getX());
                this.hero.playerY.set(cell.getY());
                break;
            }
        }
        updateAnimalSprites(coopData);
        this.id = coopData.getId();
//        this.animalSprites.add(new AnimalSprite(coop,
//            new AnimalData(new Pig(null,"asghar")),
//            hero.playerX-2, hero.playerY + 3
//        ));
//        this.animalSprites.add(new AnimalSprite(coop,
//            new AnimalData(new Goat(null,"asghar")),
//            hero.playerX-4, hero.playerY + 1
//        ));
//        this.animalSprites.add(new AnimalSprite(barn,new AnimalData(
//            new Pig(null, "asghar")), hero.playerX+1, hero.playerY + 3
//        ));


//        this.background=TextureRegion.split(GameAssetManager.getGameAssetManager().getScreenTexture("Barn_Screen.png"),);
//        this.background =
        Texture coopTexture = GameAssetManager.getGameAssetManager().getScreenTexture("Coop_Screen.png");

        int tileWidth = coopTexture.getWidth() ;
        int tileHeight = coopTexture.getHeight()/3;

        TextureRegion[][] split = TextureRegion.split(coopTexture, tileWidth, tileHeight);

        TextureRegion topRegion = split[0][0];
        TextureRegion middleRegion = split[1][0];
        TextureRegion bottomRegion = split[2][0];
        switch (coop.getCoopType()){
            case Big -> background= middleRegion;
            case Deluxe ->  background= bottomRegion;
            default -> background= topRegion;
        }



        this.hero.targetX.set(hero.playerX.get());
        this.hero.targetY.set(hero.playerY.get());
        this.hero.renderX = hero.playerX.get() * CELL_SIZE;
        this.hero.renderY = hero.playerY.get() * CELL_SIZE;
        this.hero.currentAnimation = hero.walk(false, hero.currentDirection);
        for (AnimalSprite animalSprite : this.animalSprites) {
            animalSprite.currentAnimation = animalSprite.walk(false, animalSprite.currentDirection);
            animalSprite.renderX = animalSprite.x*CELL_SIZE;
            animalSprite.renderY = animalSprite.y*CELL_SIZE;
        }

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void randomMovement(){
        SimplePathFinder spf = new SimplePathFinder(coop);
        for (AnimalSprite animalSprite : animalSprites) {
            if(animalSprite.reachedDestination() && !animalSprite.randomSetter){
                int delayTime = new Random().nextInt(1000,5000);
                animalSprite.randomSetter = true;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        animalSprite.setRandomPoint();
                        animalSprite.randomSetter = false;
                        this.cancel();
                    }
                }, delayTime, 1000);
            }
            if(animalSprite.isMoving) continue;
            Node node = spf.findPath(animalSprite.x, animalSprite.y, animalSprite.randomX, animalSprite.randomY);
            animalSprite.targetX = node.x;
            animalSprite.targetY = node.y;
            animalSprite.currentDirection = node.getDirection() != 0 ? node.getDirection() : animalSprite.currentDirection;
            animalSprite.isMoving = true;
        }
    }



    @Override
    public void render(float delta) {
        if(isGameFinished) return;
        Result result = PlayerActs.handleInput(hero, coop, stage, delta);
        if(!result.success()){
            if(result.message().equals("cheat")){
                return;
            }
        }
        PlayerActs.approach(hero);
        randomMovement();
        PlayerActs.animalApproach(animalSprites,delta);
        Gdx.gl.glClearColor(0.8f, 0.9f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        if(coop.getCoopType().equals(CoopType.Normal)) {
            batch.draw(background, 0, -CELL_SIZE / 3f, CELL_SIZE * 13, CELL_SIZE * 9);
        }else if(coop.getCoopType().equals(CoopType.Big)) {
            batch.draw(background, -CELL_SIZE/2, -CELL_SIZE *2/ 3f, CELL_SIZE * 16, CELL_SIZE * 16*9/13f);
        }
        else{
            batch.draw(background, 0, 0, CELL_SIZE * 20, CELL_SIZE * 20*9/13f);
        }


        if (hero.currentAnimation != null) {
            TextureRegion currentFrame = hero.currentAnimation.getKeyFrame(hero.stateTime, onRepeat);
            batch.draw(currentFrame, hero.renderX - CELL_SIZE / 2f, hero.renderY - CELL_SIZE / 2f, CELL_SIZE * 2f, CELL_SIZE * 2f);
        }
        for (AnimalSprite animalSprite : animalSprites) {
            if(animalSprite.currentAnimation != null){
                TextureRegion currentFrame = animalSprite.currentAnimation.getKeyFrame(animalSprite.stateTime, onRepeat);
                batch.draw(currentFrame, animalSprite.renderX - CELL_SIZE / 2f, animalSprite.renderY - CELL_SIZE / 2f, CELL_SIZE * 1.5f, CELL_SIZE * 1.5f);
            }
        }

        if(isCoopMenuOpen){
            coopMenuBar.render(batch, camera);
        }

        camera.position.set(hero.renderX + CELL_SIZE / 2f, hero.renderY + CELL_SIZE / 2f, 0);
        camera.update();

        batch.end();
        stage.act(delta);
        stage.draw();
        hero.stateTime += delta;
        animalSprites.forEach(animalSprite -> {
            animalSprite.stateTime += delta;
        });
    }


    public void transfer() {
        Cell cell = Finder.findCellByCoordinatesCoop(hero.playerX.get(), hero.playerY.get(), this.coop);
        if (cell.getObjectMap() instanceof Door) {
            ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(farmScreen);
        }
    }

    @Override public void resize(int width, int height) {

    }

    @Override public void dispose() {
        batch.dispose();
//        background.dispose();
    }

    @Override public void show() {
        PlayerActs.setScreen(this);
    }
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}

    public boolean isCoopMenuOpen() {
        return isCoopMenuOpen;
    }

    public void setCoopMenuOpen(boolean coopMenuOpen) {
        isCoopMenuOpen = coopMenuOpen;
    }

    public ArrayList<AnimalSprite> getAnimalSprites() {return animalSprites;}

    public int getId() {
        return id;
    }
}
