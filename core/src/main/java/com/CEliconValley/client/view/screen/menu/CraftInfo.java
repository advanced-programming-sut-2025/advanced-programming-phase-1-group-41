package com.CEliconValley.client.view.screen.menu;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.view.screen.GameScreen;
import com.CEliconValley.common.messages.GameCommand;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.controllers.ItemManager;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.foragings.FruitType;
import com.CEliconValley.models.items.*;
import com.CEliconValley.models.ui.CustomColors;
import com.CEliconValley.models.ui.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.Gson;

import java.util.Objects;

public class CraftInfo {
    private final Texture menuTexture;

    BitmapFont font = new BitmapFont();
    private float startingX;
    private float startingY;
    private OrthographicCamera camera;

    private GameScreen screen;

    public CraftInfo(GameScreen screen) {
        this.screen = screen;
        menuTexture = GameAssetManager.getGameAssetManager().getBackgroundTexture("Info_Background2.png");
        font.getData().setScale(2f);
        font.setColor(CustomColors.SWAMP_COLOR);
    }

    public void render(Batch batch, OrthographicCamera camera, String craftInfo) {
        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        float menuWidth = screenWidth * 0.6f;
        float menuHeight = menuWidth * 0.775f;

        float x = camera.position.x - menuWidth / 8f;
        float y = camera.position.y + menuHeight / 2.75f;

        startingX = camera.position.x - menuWidth / 2f;
        startingY = camera.position.y - menuHeight / 2f;

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        batch.draw(menuTexture, startingX, startingY, menuWidth, menuHeight);

        font.draw(batch, craftInfo, x, y);

    }

    public void dispose() {
        menuTexture.dispose();
    }
}
