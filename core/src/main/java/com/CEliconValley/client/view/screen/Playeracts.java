package com.CEliconValley.client.view.screen;

import com.CEliconValley.client.controller.CheatCodeController;
import com.CEliconValley.client.model.AnimalSprite;
import com.CEliconValley.models.Hero;
import com.CEliconValley.models.Result;
import com.CEliconValley.models.locations.Location;
import com.CEliconValley.models.ui.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class Playeracts {
    public static GameScreen screen;
    public static void setScreen(GameScreen screen){
        Playeracts.screen = screen;
    }
    public static Result handleInput(Hero hero, Location location, Stage stage, float delta){
        if (screen.cheatMode) {
            stage.act(delta);
            stage.draw();

            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                String code = screen.cheatCodeField.getText();
                System.out.println("Cheat code entered: " + code);

                //TODO Cheat code handling
//                CheatCodeController.cheatCodeHandler(code);

                screen.cheatCodeField.setText("");
                screen.cheatCodeField.setVisible(false);
                screen.cheatMode = false;

                screen.overlay.addAction(Actions.sequence(
                    Actions.fadeOut(0.5f),
                    Actions.run(() -> screen.overlay.remove())
                ));

                if (screen.overlay != null) {
                    screen.overlay.remove();
                    screen.overlay = null;
                }

            }
            return new Result(false,"cheat");
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            screen.handleCheatCode(stage);
            return new Result(true,"cheat");
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
            screen.isMenuOpen=!screen.isMenuOpen;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            screen.inventoryRenderer.shiftRight();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            screen.inventoryRenderer.shiftLeft();
        }
        if (hero.isActing||hero.isMoving) return new Result(false,"act-move");


        boolean moved = false;
        screen.onRepeat=true;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            hero.currentDirection = 1;
            if (screen.canMoveTo(hero.playerX, hero.playerY + 1, location)) {
                hero.targetX = hero.playerX;
                hero.targetY = hero.playerY + 1;
                moved = true;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            hero.currentDirection = 3;

            if (screen.canMoveTo(hero.playerX, hero.playerY - 1, location)) {
                hero.targetX = hero.playerX;
                hero.targetY = hero.playerY - 1;
                moved = true;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            hero.currentDirection = 4;
            if (screen.canMoveTo(hero.playerX - 1, hero.playerY, location)) {
                hero.targetX = hero.playerX - 1;
                hero.targetY = hero.playerY;
                screen.flip = true;
                moved = true;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            hero.currentDirection = 2;
            screen.flip = false;
            if (screen.canMoveTo(hero.playerX + 1, hero.playerY, location)) {
                hero.targetX = hero.playerX + 1;
                hero.targetY = hero.playerY;
                moved = true;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            screen.onRepeat=false;
            hero.currentAnimation = hero.useTool(3);
            hero.isActing=true;
            hero.stateTime = 0;
            if(screen instanceof FarmScreen farmScreen){
                farmScreen.hit(hero.currentDirection,hero.playerX,hero.playerY);
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP) &&
        screen instanceof FarmScreen farmScreen) {
            farmScreen.transfer();
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && !(
            screen instanceof FarmScreen
            )){
            System.out.println(screen);
            screen.transfer();
        }

        if (moved) {
            hero.isMoving = true;
            hero.currentAnimation = hero.walk(true, hero.currentDirection);
        } else if (!hero.isMoving) {
            hero.currentAnimation = hero.walk(false, hero.currentDirection);
        }
        return new Result(true,";)");
    }

    public static void approach(Hero hero){
        if (hero.isMoving) {
            float targetPixelX = hero.targetX * CELL_SIZE;
            float targetPixelY = hero.targetY * CELL_SIZE;

            float moveAmount = (float) CELL_SIZE / 4;

            hero.renderX = approach(hero.renderX, targetPixelX, moveAmount);
            hero.renderY = approach(hero.renderY, targetPixelY, moveAmount);

            if (hero.renderX == targetPixelX && hero.renderY == targetPixelY) {
                hero.playerX = hero.targetX;
                hero.playerY = hero.targetY;
                hero.isMoving = false;
//                stateTime = 0f;
            }
        }
    }

    private static float approach(float current, float target, float delta) {
//        if((hero.currentDirection == 1 || hero.currentDirection == 2) && target < current) {
//            return current;
//        } else if((hero.currentDirection == 3 || hero.currentDirection == 4) && target > current) {
//            return current;
//        }
        if (current < target) {
            return Math.min(current + delta, target);
        } else {
            return Math.max(current - delta, target);
        }
    }

    public static void changeScreen(GameScreen screen){
        ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(screen);
//        screen.show();
    }

    public static void animalApproach(ArrayList<AnimalSprite> animalSprites,
                                      float delta){
        animalSprites.forEach(animalSprite -> {
            if(animalSprite.isMoving){
                float moveAmount = 400 * delta;

                float targetPixelX = animalSprite.targetX * CELL_SIZE;
                float targetPixelY = animalSprite.targetY * CELL_SIZE;
                animalSprite.currentAnimation = animalSprite.walk(true, animalSprite.currentDirection);
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
                    animalSprite.currentAnimation = animalSprite.walk(false, animalSprite.currentDirection);
                }
            }
        });

    }

}
