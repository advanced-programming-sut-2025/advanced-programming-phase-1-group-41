package com.CEliconValley.client.view.screen;

import com.CEliconValley.client.model.AnimalSprite;
import com.CEliconValley.client.view.screen.randomwalk.Node;
import com.CEliconValley.client.view.screen.randomwalk.SimplePathFinder;
import com.CEliconValley.common.AnimalData;
import com.CEliconValley.common.BarnData;
import com.CEliconValley.controllers.Spawner.InventoryRenderer;
import com.CEliconValley.models.*;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.client.view.screen.maps.BarnMap;
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

public class BarnScreen extends GameScreen implements Screen {
    private final FarmScreen farmScreen;
    private final SpriteBatch batch;
    private final TextureRegion background;
    private final BarnMap barn;
    private BarnOrCoopMenuBar barnMenuBar;
    private boolean isBarnMenuOpen = false;
    private ArrayList<AnimalSprite> animalSprites;
    private BarnData barnData;
    private final OrthographicCamera camera;
    private int id;
    private AnimalSprite lastAnimal = null;

    public void updateAnimalSprites(BarnData barnData) {
        if (animalSprites == null) {
            animalSprites = new ArrayList<>();
        }
        if (barnData.getAnimalsData().size() == this.animalSprites.size()) {
            return;
        }
        this.animalSprites = new ArrayList<>();
        this.barnData = barnData;
        for (int i = 0; i < barnData.getAnimalsData().size(); i++) {
            AnimalData ad = barnData.getAnimalsData().get(i);
            animalSprites.add(new AnimalSprite(barn, ad,
                hero.playerX.get() + (3 * i % 5), hero.playerY.get() + (i % 7)));
        }
    }

    public BarnScreen(FarmScreen farmScreen, BarnMap barn, Player player, BarnData barnData) {
        super(new InventoryRenderer(player.getInventory()));
        this.menuBar = super.getMenuBar();
        menuBar.setPlayer(player);
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
        updateAnimalSprites(barnData);
        this.id = barnData.getId();

        Texture barnTexture = GameAssetManager.getGameAssetManager().getScreenTexture("Barn_Screen.png");

        int tileWidth = barnTexture.getWidth();
        int tileHeight = barnTexture.getHeight() / 3;

        TextureRegion[][] split = TextureRegion.split(barnTexture, tileWidth, tileHeight);

        TextureRegion topRegion = split[0][0];
        TextureRegion middleRegion = split[1][0];
        TextureRegion bottomRegion = split[2][0];
        switch (barn.getBarnType()) {
            case Big -> background = middleRegion;
            case Deluxe -> background = bottomRegion;
            default -> background = topRegion;
        }

        this.hero.targetX.set(hero.playerX.get());
        this.hero.targetY.set(hero.playerY.get());
        this.hero.renderX = hero.playerX.get() * CELL_SIZE;
        this.hero.renderY = hero.playerY.get() * CELL_SIZE;
        this.hero.currentAnimation = hero.walk(false, hero.currentDirection);
        for (AnimalSprite animalSprite : this.animalSprites) {
            animalSprite.currentAnimation = animalSprite.walk(false, animalSprite.currentDirection);
            animalSprite.renderX = animalSprite.x * CELL_SIZE;
            animalSprite.renderY = animalSprite.y * CELL_SIZE;
        }

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void randomMovement() {
        SimplePathFinder spf = new SimplePathFinder(barn);
        for (AnimalSprite animalSprite : animalSprites) {
            if (animalSprite.reachedDestination() && !animalSprite.randomSetter) {
                int delayTime = new Random().nextInt(1000, 5000);
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
            if (animalSprite.isMoving) continue;
            Node node = spf.findPath(animalSprite.x, animalSprite.y, animalSprite.randomX, animalSprite.randomY);
            animalSprite.targetX = node.x;
            animalSprite.targetY = node.y;
            animalSprite.currentDirection = node.getDirection() != 0 ? node.getDirection() : animalSprite.currentDirection;
            animalSprite.isMoving = true;
        }
    }


    @Override
    public void render(float delta) {
        if (isGameFinished) return;
        Result result = PlayerActs.handleInput(hero, barn, stage, delta);
        if (!result.success()) {
            if (result.message().equals("cheat")) {
                return;
            }
        }
        randomMovement();
        PlayerActs.approach(hero);
        PlayerActs.animalApproach(animalSprites, delta);
        Gdx.gl.glClearColor(0.8f, 0.9f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, CELL_SIZE / 2f, CELL_SIZE, CELL_SIZE * 24, CELL_SIZE * 16);

        for (Cell cell : barn.getCells()) {
            int x = cell.getX() * CELL_SIZE;
            int y = cell.getY() * CELL_SIZE;
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
                if(lastAnimal != null){
                    lastAnimal.x = hero.playerX.get();
                    lastAnimal.y = hero.playerY.get();
                    lastAnimal.renderX = hero.renderX;
                    lastAnimal.renderY = hero.renderY;
                    lastAnimal.targetX = hero.targetX.get();
                    lastAnimal.targetY = hero.targetY.get();
                    lastAnimal = null;
                }
            }
            batch.draw(currentFrame, hero.renderX - CELL_SIZE / 2f, hero.renderY - CELL_SIZE / 2f, CELL_SIZE * 2f, CELL_SIZE * 2f);
        }

        for (AnimalSprite animalSprite : animalSprites) {
            if (animalSprite.currentAnimation != null) {
                if(animalSprite == lastAnimal && hero.isActing.get()) {
                    System.out.println(animalSprite.animalData.getName()+" is here");
                    continue;
                }
                TextureRegion currentFrame = animalSprite.currentAnimation.getKeyFrame(animalSprite.stateTime, onRepeat);
                batch.draw(currentFrame, animalSprite.renderX - CELL_SIZE / 2f, animalSprite.renderY - CELL_SIZE / 2f, CELL_SIZE * 1.5f, CELL_SIZE * 1.5f);
            }
        }

        if (isBarnMenuOpen) {
            barnMenuBar.render(batch, camera);
        }

        if (isMenuOpen) {
//                menuBar.render(batch, menuX, menuY, menuWidth, menuHeight);
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
        animalSprites.forEach(animalSprite -> {
            animalSprite.stateTime += delta;
        });
    }


    public void transfer() {
        Cell cell = Finder.findCellByCoordinatesBarn(hero.playerX.get(), hero.playerY.get(), this.barn);
        System.out.println("current cell " + cell.getObjectMap().getName() + " " + cell.getX() + " " + cell.getY());
        if (cell.getObjectMap() instanceof Door) {
            ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(farmScreen);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {
        batch.dispose();
//        background.dispose();
    }

    @Override
    public void show() {
        PlayerActs.setScreen(this);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    public boolean isBarnMenuOpen() {
        return isBarnMenuOpen;
    }

    public void setBarnMenuOpen(boolean barnMenuOpen) {
        isBarnMenuOpen = barnMenuOpen;
    }

    public ArrayList<AnimalSprite> getAnimalSprites() {
        return animalSprites;
    }

    public int getId() {
        return id;
    }


    public AnimalSprite getLastAnimal() {
        return lastAnimal;
    }

    public void setLastAnimal(AnimalSprite lastAnimal) {
        this.lastAnimal = lastAnimal;
    }
}
