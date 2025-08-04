package com.CEliconValley.client.view.screen;

import com.CEliconValley.Main;
import com.CEliconValley.client.view.screen.maps.*;
import com.CEliconValley.common.CellData;
import com.CEliconValley.controllers.Spawner.InventoryRenderer;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Hero;
import com.CEliconValley.models.buildings.Wall;
import com.CEliconValley.models.foragings.ForagingTree;
import com.CEliconValley.models.foragings.Nature.Lake;
import com.CEliconValley.models.foragings.Nature.Obstacle;
import com.CEliconValley.models.foragings.Nature.Rock;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.models.locations.Location;
import com.CEliconValley.models.ui.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class GameScreen implements Screen {
    protected boolean onRepeat = true;
    protected boolean flip = false;
    protected boolean isMenuOpen = false;
    protected InventoryRenderer inventoryRenderer;
    protected boolean cheatMode = false;
    protected Image overlay;
    protected TextField cheatCodeField;
    protected Stage stage;
    public abstract void transfer();
    protected Hero hero;
    public GameScreen(InventoryRenderer inventoryRenderer) {
        stage = new Stage(new ScreenViewport(), Main.getBatch());
        Gdx.input.setInputProcessor(stage);
        this.inventoryRenderer = inventoryRenderer;
        cheatCodeField = new TextField("", GameAssetManager.getGameAssetManager().getSkin());
        cheatCodeField.setMessageText("Enter cheat code");
        cheatCodeField.setVisible(false);
        cheatCodeField.setWidth(600);
        cheatCodeField.setPosition(stage.getWidth()/2  - cheatCodeField.getWidth() / 2, stage.getHeight() / 2 - cheatCodeField.getHeight() / 2);
        stage.addActor(cheatCodeField);
        this.hero = new Hero();
    }


    public boolean canMoveTo(int x, int y, Location location) {
        if(location instanceof FarmMap farmMap){
            for (CellData cd : farmMap.farmData.getCells()) {
                if (cd.getX() == x && cd.getY() == y) {
                    Cell cell = cd.extractData();
                    if (cell.getObjectMap() instanceof Lake || cell.getObjectMap() instanceof Rock ||cell.getObjectMap() instanceof Wall ||cell.getObjectMap() instanceof Obstacle) {
                        System.out.println(cd.getObjectName());
                        return false;
                    }
                    return true;
                }
            }
            return false;
        }
        if(location instanceof CottageMap cottageMap){
            for (Cell cell : cottageMap.getCells()) {
                if (cell.getX() == x && cell.getY() == y) {
                    if (cell.getObjectMap() instanceof Lake || cell.getObjectMap() instanceof Rock
                        || cell.getObjectMap() instanceof Wall || cell.getObjectMap() instanceof ForagingTree) {
                        return false;
                    }
                    return true;
                }
            }
            return false;
        }
        if(location instanceof GreenhouseMap greenHouseMap){
            for (Cell cell : greenHouseMap.getCells()) {
                if (cell.getX() == x && cell.getY() == y) {
                    if (cell.getObjectMap() instanceof Lake || cell.getObjectMap() instanceof Rock ||cell.getObjectMap() instanceof Wall ||cell.getObjectMap() instanceof ForagingTree) {
                        return false;
                    }
                    return true;
                }
            }
        }
        if(location instanceof CoopMap coopMap) {
            for (Cell cell : coopMap.getCells()) {
                if (cell.getX() == x && cell.getY() == y) {
                    if (cell.getObjectMap() instanceof Lake || cell.getObjectMap() instanceof Rock || cell.getObjectMap() instanceof Wall) {
                        return false;
                    }
                    return true;
                }
            }
        }
        if(location instanceof BarnMap barnMap){
            for (Cell cell : barnMap.getCells()) {
                if (cell.getX() == x && cell.getY() == y) {
                    if (cell.getObjectMap() instanceof Lake || cell.getObjectMap() instanceof Rock || cell.getObjectMap() instanceof Wall) {
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }


    public void handleCheatCode(Stage stage) {
        cheatMode = true;
        cheatCodeField.setVisible(true);
        stage.setKeyboardFocus(cheatCodeField);
        cheatCodeField.setText("");

        overlay = new Image(new TextureRegionDrawable(new TextureRegion(GameAssetManager
            .getGameAssetManager()
            .getBackgroundTexture("Field3.png"))));

//        overlay.setColor(0, 0, 0, 0.5f);
        overlay.setSize(stage.getWidth(), stage.getHeight());
        overlay.setPosition(0, 0);

        overlay.getColor().a = 0;
        overlay.addAction(Actions.fadeIn(0.5f));


        stage.addActor(overlay);
        overlay.toBack();
    }

    public Hero getHero() {
        return hero;
    }
}
