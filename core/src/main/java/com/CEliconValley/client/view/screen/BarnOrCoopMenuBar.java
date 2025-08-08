package com.CEliconValley.client.view.screen;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.model.AnimalSprite;
import com.CEliconValley.common.AnimalData;
import com.CEliconValley.common.PlayerData;
import com.CEliconValley.common.messages.GameCommand;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.common.messages.VoteMessage;
import com.CEliconValley.controllers.ItemManager;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.items.*;
import com.CEliconValley.models.tools.Tool;
import com.CEliconValley.models.ui.CustomColors;
import com.CEliconValley.models.ui.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BarnOrCoopMenuBar {
    private final Texture menuTexture;
    private final Texture animalTexture;
    private final int tileWidth;
    private final int tileHeight;

    private float scrollOffset = 0f;
    private float maxScroll = 500f; // بسته به تعداد حیوانات، اینو بعداً حساب کن
    private float scrollAmount = 10f;

    private Player player;

    BitmapFont font = new BitmapFont();
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    private int startingRow = 0;
    private float startingX;
    private float startingY;
    private String currentTab;
    private OrthographicCamera camera;

    private ArrayList<AnimalSprite> animalSprites;
    private GameScreen screen;

    public BarnOrCoopMenuBar(GameScreen screen) {
        this.screen = screen;
        menuTexture = GameAssetManager.getGameAssetManager().getScreenTexture("CoopOrBarnMenu_Screen.png");
        animalTexture = GameAssetManager.getGameAssetManager().getBackgroundTexture("LabelBg1.png");

        tileWidth = menuTexture.getWidth() / 3;
        tileHeight = menuTexture.getHeight() / 3;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void render(Batch batch, OrthographicCamera camera) {
        scrollOffset += scrollAmount * 10f;
        scrollOffset = Math.max(0, Math.min(scrollOffset, maxScroll));

        if(screen instanceof BarnScreen) {
            animalSprites = ((BarnScreen) screen).getAnimalSprites();
        } else if(screen instanceof CoopScreen) {
            animalSprites = ((CoopScreen) screen).getAnimalSprites();
        } else {
            animalSprites = new ArrayList<>();
        }

        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;
        this.camera = camera;

        float menuWidth = screenWidth * 0.6f;
        float menuHeight = screenHeight * 0.7f;
        startingX = camera.position.x - menuWidth / 2f;
        startingY = camera.position.y - menuHeight / 2f;

        batch.draw(menuTexture, startingX, startingY, menuWidth, menuHeight);

        float x = startingX + screenWidth * 0.025f;
        float y = startingY + screenHeight * 0.7f;

        float spacing = screenWidth * 0.14f;
        float animalSize = screenHeight * 0.08f;

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        for (AnimalSprite animalSprite : animalSprites) {
            AnimalData animalData = animalSprite.animalData;
            String name = animalData.getName();
            String type = animalData.getAnimalType();

            float animalX = x;
            float animalY = y - animalSize + 5;

            batch.draw(animalTexture, animalX, animalY, animalSize * 3, animalSize);

            String animalName = name + " (" + type + ")";
            font.getData().setScale(2f);
            font.draw(batch, animalName, animalX + animalSize * 1.5f - animalName.length() * font.getScaleX() * 7.5f / 2f,
                animalY + animalSize / 1.5f);

            boolean hovered = mousePos.x >= animalX && mousePos.x <= animalX + animalSize * 3f &&
                mousePos.y >= animalY && mousePos.y <= animalY + animalSize;

            boolean clicked = false;

            if (hovered) {
                if(Gdx.input.isButtonJustPressed(0)){
                    clicked = true;
                }
                String animalSound = Animal.getAnimalSound(animalData.getAnimalType());

                GlyphLayout tooltipLayout = new GlyphLayout(font, animalSound);

                float tooltipWidth = tooltipLayout.width + 40;
                float tooltipHeight = tooltipLayout.height + 30;

                float tooltipX = animalX + animalSize * 1.5f - tooltipWidth / 2f;
                float tooltipY = animalY + animalSize + 20;

                batch.end();
                shapeRenderer.setProjectionMatrix(camera.combined);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0, 0, 0, 0.85f);
                shapeRenderer.rect(tooltipX, tooltipY, tooltipWidth, tooltipHeight);
                shapeRenderer.end();
                batch.begin();

                font.setColor(CustomColors.SWAMP_COLOR);

                font.draw(batch, animalSound, tooltipX + 20, tooltipY + tooltipHeight - 15);

                font.setColor(Color.WHITE);
            }
            if(clicked && AppClient.getGameData().getPlayersData().size() > 1){
                //TODO
            }

            y += spacing;
        }

        font.getData().setScale(1f);

        spacing = screenHeight * 0.1f;
        maxScroll = Math.max(0, animalSprites.size() * spacing - screenHeight * 0.7f);
    }

    private String readableName(String camelCase) {
        return camelCase.replaceAll("([a-z])([A-Z])", "$1 $2");
    }


    public void dispose() {
        menuTexture.dispose();
    }

    public void scrollDown() {
        startingRow++;

    }

    public void scrollUp() {
        if (startingRow > 0) {
            startingRow--;
        }
    }

    public String getCurrentTab() {
        return currentTab;
    }
}
