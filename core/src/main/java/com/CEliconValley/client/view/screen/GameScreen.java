package com.CEliconValley.client.view.screen;

import com.CEliconValley.controllers.Spawner.InventoryRenderer;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Hero;
import com.CEliconValley.models.buildings.Wall;
import com.CEliconValley.models.foragings.Nature.Lake;
import com.CEliconValley.models.foragings.Nature.Obstacle;
import com.CEliconValley.models.foragings.Nature.Rock;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.models.locations.Location;
import com.CEliconValley.models.ui.GameAssetManager;
import com.CEliconValley.views.maps.BarnMap;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public abstract class GameScreen implements Screen {
    protected boolean onRepeat = true;
    protected boolean flip = false;
    protected boolean isMenuOpen = false;
    protected InventoryRenderer inventoryRenderer;
    protected boolean cheatMode = false;
    protected Image overlay;
    protected TextField cheatCodeField;
    public abstract void transfer();
    public GameScreen(InventoryRenderer inventoryRenderer) {
        this.inventoryRenderer = inventoryRenderer;
    }

    public boolean canMoveTo(int x, int y, Location location) {
        if(location instanceof Farm farm){
            for (Cell cell : farm.getCells()) {
                if (cell.getX() == x && cell.getY() == y) {
                    if (cell.getObjectMap() instanceof Lake || cell.getObjectMap() instanceof Rock ||cell.getObjectMap() instanceof Wall ||cell.getObjectMap() instanceof Obstacle) {
                        return false;
                    }
                    return true;
                }
            }
            return false;
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


}
