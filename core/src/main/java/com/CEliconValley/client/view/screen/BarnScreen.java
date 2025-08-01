package com.CEliconValley.client.view.screen;

import com.CEliconValley.client.model.AnimalSprite;
import com.CEliconValley.client.view.screen.randomwalk.Node;
import com.CEliconValley.client.view.screen.randomwalk.SimplePathFinder;
import com.CEliconValley.common.AnimalData;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Hero;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.animals.animalKinds.Cow;
import com.CEliconValley.models.animals.animalKinds.Pig;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.buildings.Wall;
import com.CEliconValley.models.foragings.Nature.Lake;
import com.CEliconValley.models.foragings.Nature.Rock;
import com.CEliconValley.views.maps.BarnMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.bytebuddy.pool.TypePool;
import org.bson.io.BsonOutput;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class BarnScreen implements Screen {
    private final FarmScreen farmScreen;
    private final Hero hero;
    private final SpriteBatch batch;
    private final TextureRegion background;
    private final BarnMap barn;
    private Animation<TextureRegion> currentAnimation;
    private int playerDirection = 3;
    private boolean isActing = false;
    private boolean onRepeat = false;
    private ArrayList<AnimalSprite> animalSprites;

    private final OrthographicCamera camera;
    public static final int CELL_SIZE = 160;

    public BarnScreen(FarmScreen farmScreen, BarnMap barn, Player player) {
        this.hero = new Hero(barn);
        this.farmScreen = farmScreen;
        this.barn = barn;
        this.batch = new SpriteBatch();
        for (Cell cell : barn.getCells()) {
            if (cell == null) continue;
            if (cell.getObjectMap() instanceof Door) {
                this.hero.playerX = cell.getX();
                this.hero.playerY = cell.getY();
                break;
            }
        }
        this.animalSprites = new ArrayList<>();
        this.animalSprites.add(new AnimalSprite(barn,
            new AnimalData(new Cow(null,"mamad")),
            hero.playerX, hero.playerY + 2
            ));
        this.animalSprites.add(new AnimalSprite(barn,
            new AnimalData(new Cow(null,"asghar")),
            hero.playerX+3, hero.playerY + 4
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



        this.hero.targetX = hero.playerX;
        this.hero.targetY = hero.playerY;
        this.hero.renderX = hero.playerX * CELL_SIZE;
        this.hero.renderY = hero.playerY * CELL_SIZE;
        this.hero.currentAnimation = hero.walk(false, playerDirection);
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
               System.out.println("before inside of here!");
               new Timer().schedule(new TimerTask() {
                   @Override
                   public void run() {
                       System.out.println("inside of here!");
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
           animalSprite.walk(true, animalSprite.currentDirection);
           System.out.println("target: "+animalSprite.targetX + " " + animalSprite.targetY);
           System.out.println("current: "+animalSprite.x + " " + animalSprite.y);
           System.out.println("random: "+animalSprite.randomX + " " + animalSprite.randomY);
            System.out.println("direction: "+animalSprite.currentDirection);
           System.out.println();

        }
    }



    @Override
    public void render(float delta) {
        handleInput();
        randomMovement();
        if (hero.isMoving) {
            float moveAmount = 600 * delta;

            float targetPixelX = hero.targetX * CELL_SIZE;
            float targetPixelY = hero.targetY * CELL_SIZE;

            if (hero.renderX < targetPixelX) {
                hero.renderX += moveAmount;
                if (hero.renderX > targetPixelX) hero.renderX = targetPixelX;
            } else if (hero.renderX > targetPixelX) {
                hero.renderX -= moveAmount;
                if (hero.renderX < targetPixelX) hero.renderX = targetPixelX;
            }

            if (hero.renderY < targetPixelY) {
                hero.renderY += moveAmount;
                if (hero.renderY > targetPixelY) hero.renderY = targetPixelY;
            } else if (hero.renderY > targetPixelY) {
                hero.renderY -= moveAmount;
                if (hero.renderY < targetPixelY) hero.renderY = targetPixelY;
            }

            if (hero.renderX == targetPixelX && hero.renderY == targetPixelY) {
                hero.playerX = hero.targetX;
                hero.playerY = hero.targetY;
                hero.isMoving = false;
            }
        }
        animalSprites.forEach(animalSprite -> {
            if(animalSprite.isMoving){
                float moveAmount = 600 * delta;

                float targetPixelX = animalSprite.targetX * CELL_SIZE;
                float targetPixelY = animalSprite.targetY * CELL_SIZE;

                if (animalSprite.renderX < targetPixelX) {
                    animalSprite.renderX += moveAmount;
                    if (animalSprite.renderX > targetPixelX) animalSprite.renderX = targetPixelX;
                } else if (animalSprite.renderX > targetPixelX) {
                    animalSprite.renderX -= moveAmount;
                    if (animalSprite.renderX < targetPixelX) animalSprite.renderX = targetPixelX;
                }

                if (animalSprite.renderY < targetPixelY) {
                    animalSprite.renderY += moveAmount;
                    if (animalSprite.renderY > targetPixelY) animalSprite.renderY = targetPixelY;
                } else if (animalSprite.renderY > targetPixelY) {
                    animalSprite.renderY -= moveAmount;
                    if (animalSprite.renderY < targetPixelY) animalSprite.renderY = targetPixelY;
                }

                if (animalSprite.renderX == targetPixelX && animalSprite.renderY == targetPixelY) {
                    animalSprite.x = animalSprite.targetX;
                    animalSprite.y = animalSprite.targetY;
                    // TODO need to change the animaldata as well perhaps
                    animalSprite.isMoving = false;
                }
            }
        });

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
            batch.draw(currentFrame, hero.renderX - CELL_SIZE / 2f, hero.renderY - CELL_SIZE / 2f, CELL_SIZE * 2f, CELL_SIZE * 2f);
        }
        for (AnimalSprite animalSprite : animalSprites) {
            if(animalSprite.currentAnimation != null){
                TextureRegion currentFrame = animalSprite.currentAnimation.getKeyFrame(animalSprite.stateTime, onRepeat);
                batch.draw(currentFrame, animalSprite.renderX - CELL_SIZE / 2f, animalSprite.renderY - CELL_SIZE / 2f, CELL_SIZE * 2f, CELL_SIZE * 2f);
            }
        }

        camera.position.set(hero.renderX + CELL_SIZE / 2f, hero.renderY + CELL_SIZE / 2f, 0);
        camera.update();
        batch.end();
        hero.stateTime += delta;
        animalSprites.forEach(animalSprite -> {
            animalSprite.stateTime += delta;
        });
    }

    private void handleInput() {
        if (isActing||hero.isMoving) return;

        boolean moved = false;
        onRepeat=true;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//            hero.currentAnimation = hero.walk(canMoveTo(hero.playerX, hero.playerY+1),1);
            playerDirection = 1;
            if (canMoveTo(hero.playerX, hero.playerY + 1)) {
                hero.targetX = hero.playerX;
                hero.targetY = hero.playerY + 1;
                moved = true;
            }
            hero.currentAnimation = hero.walk(moved,playerDirection);

        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//            hero.currentAnimation = hero.walk(canMoveTo(hero.playerX, hero.playerY-1),3);
            playerDirection = 3;

            if (canMoveTo(hero.playerX, hero.playerY - 1)) {
                hero.targetX = hero.playerX;
                hero.targetY = hero.playerY - 1;
                moved = true;
            }
            hero.currentAnimation = hero.walk(moved,playerDirection);

        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//            hero.currentAnimation = hero.walk(canMoveTo(hero.playerX-1, hero.playerY),4);
            playerDirection = 4;
            if (canMoveTo(hero.playerX - 1, hero.playerY)) {
                hero.targetX = hero.playerX - 1;
                hero.targetY = hero.playerY;
//                flip = true;
                moved = true;
            }
            hero.currentAnimation = hero.walk(moved,playerDirection);

        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//            hero.currentAnimation = hero.walk(canMoveTo(hero.playerX+1, hero.playerY),2);
            playerDirection = 2;
//            flip = false;
            if (canMoveTo(hero.playerX + 1, hero.playerY)) {
                hero.targetX = hero.playerX + 1;
                hero.targetY = hero.playerY;
                moved = true;
            }
            hero.currentAnimation = hero.walk(moved,playerDirection);

        } else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            onRepeat=false;
            hero.currentAnimation = hero.useTool(3);
            isActing=true;
            hero.stateTime = 0;
//            didHit=true;
//            hit(playerDirection,hero.playerX,hero.playerY);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            transfer();
        }
        else{
            hero.currentAnimation = hero.walk(false,playerDirection);
            animalSprites.forEach(animalSprite -> {
                animalSprite.currentAnimation = animalSprite.walk(false, animalSprite.currentDirection);
            });
        }

        if (moved) {
            hero.isMoving = true;

//            hero.currentAnimation = walkAnimations[playerDirection];
        }


    }

    private boolean canMoveTo(int x, int y) {
        for (Cell cell : barn.getCells()) {
            if (cell.getX() == x && cell.getY() == y) {
                if (cell.getObjectMap() instanceof Lake || cell.getObjectMap() instanceof Rock || cell.getObjectMap() instanceof Wall) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public void transfer() {
        Cell cell = Finder.findCellByCoordinatesBarn(hero.playerX, hero.playerY, this.barn);
        if (cell.getObjectMap() instanceof Door) {
            ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(farmScreen);
        }
    }

    @Override public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override public void dispose() {
        batch.dispose();
//        background.dispose();
    }

    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
}
