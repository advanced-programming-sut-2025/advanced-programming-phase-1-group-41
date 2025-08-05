package com.CEliconValley.client.view.screen;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.model.AnimalSprite;
import com.CEliconValley.common.messages.GameCommand;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.models.Hero;
import com.CEliconValley.models.Result;
import com.CEliconValley.models.locations.Location;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.google.gson.Gson;

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
        if(screen.isMenuOpen){
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                screen.menuBar.goToPreviousTab();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                screen.menuBar.goToNextTab();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                screen.menuBar.scrollUp();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                screen.menuBar.scrollDown();
            }
        }else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                screen.inventoryRenderer.shiftRight();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                screen.inventoryRenderer.shiftLeft();
            }
        }
        if (hero.isActing.get() ||hero.isMoving.get()) {
//            System.out.println(hero.isActing+" "+hero.isMoving);
            return new Result(false,"act-move");
        }





        boolean moved = false;
        screen.onRepeat=true;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            hero.currentDirection = 1;
            if (screen.canMoveTo(hero.playerX.get(), hero.playerY.get() + 1, location)) {
                hero.targetX.set(hero.playerX.get());
                hero.targetY.set(hero.playerY.get() + 1);
                moved = true;
                GameMessage<GameCommand> msg = new GameMessage<>("game-command", new GameCommand("walk up", AppClient.getUserData().getUsername()));
                AppClient.getClient().send(new Gson().toJson(msg));
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            hero.currentDirection = 3;
            if (screen.canMoveTo(hero.playerX.get(), hero.playerY.get() - 1, location)) {
                hero.targetX.set(hero.playerX.get());
                hero.targetY.set(hero.playerY.get() - 1);
                moved = true;
                GameMessage<GameCommand> msg = new GameMessage<>("game-command", new GameCommand("walk down", AppClient.getUserData().getUsername()));
                AppClient.getClient().send(new Gson().toJson(msg));
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            hero.currentDirection = 4;
            if (screen.canMoveTo(hero.playerX.get() - 1, hero.playerY.get(), location)) {
                hero.targetX.set(hero.playerX.get() - 1);
                hero.targetY.set(hero.playerY.get());
                screen.flip = true;
                moved = true;
                GameMessage<GameCommand> msg = new GameMessage<>("game-command", new GameCommand("walk left", AppClient.getUserData().getUsername()));
                AppClient.getClient().send(new Gson().toJson(msg));
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            hero.currentDirection = 2;
            screen.flip = false;
            if (screen.canMoveTo(hero.playerX.get() + 1, hero.playerY.get(), location)) {
                hero.targetX.set(hero.playerX.get() + 1);
                hero.targetY.set(hero.playerY.get());
                moved = true;
                GameMessage<GameCommand> msg = new GameMessage<>("game-command", new GameCommand("walk right", AppClient.getUserData().getUsername()));
                AppClient.getClient().send(new Gson().toJson(msg));
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            screen.onRepeat=false;
            hero.currentAnimation = hero.useTool(3);
            hero.isActing.set(true);
            hero.stateTime = 0;
            if(screen instanceof FarmScreen farmScreen){
                farmScreen.hit(hero.currentDirection, hero.playerX.get(), hero.playerY.get());
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
            hero.isMoving.set(true);
            hero.currentAnimation = hero.walk(true, hero.currentDirection);
        } else if (!hero.isMoving.get()) {
            hero.currentAnimation = hero.walk(false, hero.currentDirection);
        }
        return new Result(true,";)");
    }

    public static void approach(Hero hero){
        if (hero.isMoving.get()) {
            float targetPixelX = hero.targetX.get() * CELL_SIZE;
            float targetPixelY = hero.targetY.get() * CELL_SIZE;

            float moveAmount = (float) CELL_SIZE / 8;

            hero.renderX = approach(hero.renderX, targetPixelX, moveAmount);
            hero.renderY = approach(hero.renderY, targetPixelY, moveAmount);

            if (hero.renderX == targetPixelX && hero.renderY == targetPixelY) {
                hero.playerX.set(hero.targetX.get());
                hero.playerY.set(hero.targetY.get());
                hero.isMoving.set(false);
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
