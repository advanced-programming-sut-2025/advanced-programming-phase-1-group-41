package com.CEliconValley.client.view.screen;

import com.CEliconValley.Main;
import com.CEliconValley.client.AppClient;
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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class GameScreen implements Screen {
    protected boolean onRepeat = true;
    protected boolean flip = false;
    protected boolean isMenuOpen = false;
    protected MenuBar menuBar=new MenuBar();
    protected InventoryRenderer inventoryRenderer;
    protected boolean cheatMode = false;
    protected Image overlay;
    protected TextField cheatCodeField;
    protected Stage stage;
    public abstract void transfer();
    protected Hero hero;
    protected Texture hudTexture = new Texture(Gdx.files.internal("game/Clock/Clock.png"));
    protected Image hudImage;
    protected TimeScreen timeScreen;


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
        hudImage = new Image(new TextureRegion(hudTexture));
        hudImage.setSize(hudImage.getWidth()*4, hudImage.getHeight()*4);
        float posX = stage.getWidth() - hudImage.getWidth() - 10;
        float posY = stage.getHeight() - hudImage.getHeight() - 10;
        stage.addActor(hudImage);
        timeScreen = new TimeScreen(GameAssetManager.getGameAssetManager().getSkin(), stage,
            posX, posY, hudImage);
        hudImage.setTouchable(Touchable.disabled);
        hudImage.setPosition(posX, posY);
        timeScreen.dateLabel.setPosition(posX + 120, posY + 180);
        timeScreen.timeLabel.setPosition(posX + 120, posY + 90);
        timeScreen.goldLabel.setPosition(posX + 87.5f, posY + 10);
        timeScreen.goldLabel.setAlignment(Align.right);
        timeScreen.goldLabel.setFontScale(1.18f);
        timeScreen.dateLabel.setFontScale(0.8f);
        stage.addActor(timeScreen.dateLabel);
        stage.addActor(timeScreen.timeLabel);
        stage.addActor(timeScreen.goldLabel);
        stage.addActor(timeScreen.getHudTable());
        timeScreen.getHudTable().setPosition(0, -stage.getHeight() / 21f);
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
    public MenuBar getMenuBar() {
        return menuBar;
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        hudImage.setPosition(stage.getWidth() - hudImage.getWidth(), stage.getHeight() - hudImage.getHeight());
    }

    public TimeScreen getTimeScreen() {
        return timeScreen;
    }
}
