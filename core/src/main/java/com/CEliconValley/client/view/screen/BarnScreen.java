package com.CEliconValley.client.view.screen;

import com.CEliconValley.client.model.AnimalSprite;
import com.CEliconValley.client.view.screen.randomwalk.Node;
import com.CEliconValley.client.view.screen.randomwalk.SimplePathFinder;
import com.CEliconValley.common.AnimalData;
import com.CEliconValley.models.*;
import com.CEliconValley.models.animals.animalKinds.Cow;
import com.CEliconValley.models.animals.animalKinds.Goat;
import com.CEliconValley.models.animals.animalKinds.Pig;
import com.CEliconValley.models.animals.animalKinds.Sheep;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.client.view.screen.maps.BarnMap;
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

public class BarnScreen extends GameScreen implements Screen {
    private final FarmScreen farmScreen;
    private final SpriteBatch batch;
    private final TextureRegion background;
    private final BarnMap barn;
    private BarnOrCoopMenuBar barnMenuBar;
    private boolean isBarnMenuOpen = false;
    private boolean onRepeat = true;
    private ArrayList<AnimalSprite> animalSprites;

    private final OrthographicCamera camera;

    public BarnScreen(FarmScreen farmScreen, BarnMap barn, Player player) {
        super(null);
        this.barnMenuBar = super.getBarnOrCoopMenuBar();
        barnMenuBar.setPlayer(player);
        this.farmScreen = farmScreen;
        this.barn = barn;
        this.batch = new SpriteBatch();
        for (Cell cell : barn.getCells()) {
            if (cell == null) continue;
            if (cell.getObjectMap() instanceof Door) {
                this.hero.playerX.set(cell.getX());
                this.hero.playerY.set(cell.getY());
                break;
            }
        }
        this.animalSprites = new ArrayList<>();
        this.animalSprites.add(new AnimalSprite(barn,
            new AnimalData(new Cow(null,"mamad")),
            hero.playerX.get(), hero.playerY.get() + 2
            ));
        this.animalSprites.add(new AnimalSprite(barn,
            new AnimalData(new Sheep(null,"asghar")),
            hero.playerX.get() +3, hero.playerY.get() + 4
            ));
        this.animalSprites.add(new AnimalSprite(barn,
            new AnimalData(new Pig(null,"asghar")),
            hero.playerX.get() -2, hero.playerY.get() + 3
            ));
        this.animalSprites.add(new AnimalSprite(barn,
            new AnimalData(new Goat(null,"asghar")),
            hero.playerX.get() -4, hero.playerY.get() + 1
            ));
//        this.animalSprites.add(new AnimalSprite(barn,new AnimalData(
//            new Pig(null, "asghar")), hero.playerX+1, hero.playerY + 3
//        ));


//        this.background=TextureRegion.split(new Texture("game/Buildings/Screen/Barn_Screen.png"),);
//        this.background =
        Texture barnTexture = new Texture("game/Buildings/Screen/Barn_Screen.png");

        int tileWidth = barnTexture.getWidth();
        int tileHeight = barnTexture.getHeight() / 3;

        TextureRegion[][] split = TextureRegion.split(barnTexture, tileWidth, tileHeight);

        TextureRegion topRegion = split[0][0];
        TextureRegion middleRegion = split[1][0];
        TextureRegion bottomRegion = split[2][0];
        switch (barn.getBarnType()){
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
        SimplePathFinder spf = new SimplePathFinder(barn);
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
        Result result = Playeracts.handleInput(hero, barn, stage, delta);
        if(!result.success()){
            if(result.message().equals("cheat")){
                return;
            }
        }
        randomMovement();
        Playeracts.approach(hero);
        Playeracts.animalApproach(animalSprites,delta);
        Gdx.gl.glClearColor(0.8f, 0.9f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, CELL_SIZE / 2f, CELL_SIZE , CELL_SIZE * 24, CELL_SIZE * 16);

        for (Cell cell : barn.getCells()) {
            int x = cell.getX() * CELL_SIZE;
            int y = cell.getY() * CELL_SIZE;
        }

        if (hero.currentAnimation != null) {
            TextureRegion currentFrame = hero.currentAnimation.getKeyFrame(hero.stateTime, onRepeat);
            batch.draw(currentFrame, hero.renderX - CELL_SIZE / 2f, hero.renderY - CELL_SIZE / 2f, CELL_SIZE*2 , CELL_SIZE*2 );
        }
        for (AnimalSprite animalSprite : animalSprites) {
            if(animalSprite.currentAnimation != null){
                TextureRegion currentFrame = animalSprite.currentAnimation.getKeyFrame(animalSprite.stateTime, onRepeat);
                batch.draw(currentFrame, animalSprite.renderX - CELL_SIZE / 2f, animalSprite.renderY - CELL_SIZE / 2f, CELL_SIZE*1.5f , CELL_SIZE*1.5f );
            }
        }

        if(isBarnMenuOpen){
            barnMenuBar.render(batch, camera);
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
        Cell cell = Finder.findCellByCoordinatesBarn(hero.playerX.get(), hero.playerY.get(), this.barn);
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
        Playeracts.setScreen(this);
    }
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}

    public boolean isBarnMenuOpen() {
        return isBarnMenuOpen;
    }

    public void setBarnMenuOpen(boolean barnMenuOpen) {
        isBarnMenuOpen = barnMenuOpen;
    }
}
