package com.CEliconValley.client.view.screen;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Hero;
import com.CEliconValley.models.Player;
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

public class BarnScreen implements Screen {
    private final FarmScreen farmScreen;
    private final Hero hero;
    private final SpriteBatch batch;
    private final TextureRegion background;
    private final BarnMap barn;
    private final Player player;
    private Animation<TextureRegion> currentAnimation;
    private int playerDirection = 3;
    private boolean isActing = false;
    private boolean onRepeat = false;

    private int playerX;
    private int playerY;
    private float renderX;
    private float renderY;
    private boolean isMoving = false;
    private int targetX;
    private int targetY;

    private final OrthographicCamera camera;
    private float stateTime = 0f;
    public static final int CELL_SIZE = 160;

    public BarnScreen(FarmScreen farmScreen, BarnMap barn, Player player) {
        this.hero = new Hero(barn);
        this.farmScreen = farmScreen;
        this.barn = barn;
        this.player = player;
        this.batch = new SpriteBatch();
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


        for (Cell cell : barn.getCells()) {
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

        camera = new OrthographicCamera();
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
        batch.draw(background, CELL_SIZE / 2f, CELL_SIZE , CELL_SIZE * 24, CELL_SIZE * 16);

        for (Cell cell : barn.getCells()) {
            int x = cell.getX() * CELL_SIZE;
            int y = cell.getY() * CELL_SIZE;
        }

        if (currentAnimation != null) {
            TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, onRepeat);
            batch.draw(currentFrame, renderX - CELL_SIZE / 2f, renderY - CELL_SIZE / 2f, CELL_SIZE * 2f, CELL_SIZE * 2f);
        }

        camera.position.set(renderX + CELL_SIZE / 2f, renderY + CELL_SIZE / 2f, 0);
        camera.update();
        batch.end();
        stateTime += delta;
    }

    private void handleInput() {
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
//                flip = true;
                moved = true;
            }
            currentAnimation = hero.walk(moved,playerDirection);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//            currentAnimation = hero.walk(canMoveTo(playerX+1, playerY),2);
            playerDirection = 2;
//            flip = false;
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
//            didHit=true;
//            hit(playerDirection,playerX,playerY);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
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
        Cell cell = Finder.findCellByCoordinatesBarn(playerX, playerY, this.barn);
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
