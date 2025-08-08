package com.CEliconValley.client.view.screen;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.model.AnimalSprite;
import com.CEliconValley.common.AnimalData;
import com.CEliconValley.common.messages.GameCommand;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.ui.CustomColors;
import com.CEliconValley.models.ui.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.Gson;

import java.util.ArrayList;

public class BarnOrCoopMenuBar {
    private final Texture menuTexture;
    private final Texture animalTexture;
    private final Texture buttonTexture;
    private final int tileWidth;
    private final int tileHeight;

    private int selectedIndex = 0;
    private final int visibleAnimalsCount = 4;

    private Player player;

    BitmapFont font = new BitmapFont();
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    private float startingX;
    private float startingY;
    private String currentTab;
    ArrayList<AnimalData> animalsData = null;
    private OrthographicCamera camera;

    private GameScreen screen;

    public BarnOrCoopMenuBar(GameScreen screen) {
        this.screen = screen;
        menuTexture = GameAssetManager.getGameAssetManager().getScreenTexture("CoopOrBarnMenu_Screen.png");
        animalTexture = GameAssetManager.getGameAssetManager().getBackgroundTexture("LabelBg1.png");
        buttonTexture = GameAssetManager.getGameAssetManager().getBackgroundTexture("LabelBg4.png");

        tileWidth = menuTexture.getWidth() / 3;
        tileHeight = menuTexture.getHeight() / 3;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void render(Batch batch, OrthographicCamera camera) {
        if (screen instanceof BarnScreen barnScreen) {
            animalsData = Finder.getbdByid(barnScreen.getId()).getAnimalsData();
        } else if (screen instanceof CoopScreen coopScreen) {
            animalsData = Finder.getcdByid(coopScreen.getId()).getAnimalsData();
        } else {
            animalsData = new ArrayList<>();
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
        float y = startingY + screenHeight * 0.6f;

        float spacing = screenWidth * 0.08f;
        float animalSize = screenHeight * 0.08f;

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        int endIndex = Math.min(selectedIndex + visibleAnimalsCount, animalsData.size());
        for (int i = selectedIndex; i < endIndex; i++) {
            AnimalData animalData = animalsData.get(i);
            String name = animalData.getName();
            String type = animalData.getAnimalType();

            double size = 1.5;
            float animalX = x;
            float animalY = y - animalSize + 5;

            batch.draw(animalTexture, animalX, animalY, animalSize * 4, animalSize);

            String animalName = name + " (" + type + ")";
            font.getData().setScale(2f);
            font.draw(batch, animalName, animalX + animalSize * 2f - animalName.length() * font.getScaleX() * 7.5f / 2f,
                animalY + animalSize / 1.5f);

            boolean hovered = mousePos.x >= animalX && mousePos.x <= animalX + animalSize * 4f &&
                mousePos.y >= animalY && mousePos.y <= animalY + animalSize;

            boolean clicked = false;

            if (hovered) {
                if (Gdx.input.isButtonJustPressed(0)) {
                    clicked = true;
                }
                String animalSound = Animal.getAnimalSound(animalData.getAnimalType()) + "   " + animalData.getFriendShip();

                GlyphLayout tooltipLayout = new GlyphLayout(font, animalSound);

                float animalSoundWidth = tooltipLayout.width + 40;
                float animalSoundHeight = tooltipLayout.height + 30;

                float animalSoundX = animalX + animalSize * 2f - animalSoundWidth / 2f;
                float animalSoundY = animalY + animalSize + 10;

                batch.end();
                shapeRenderer.setProjectionMatrix(camera.combined);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0, 0, 0, 0.85f);
                shapeRenderer.rect(animalSoundX, animalSoundY, animalSoundWidth, animalSoundHeight);
                shapeRenderer.end();
                batch.begin();

                font.setColor(CustomColors.SWAMP_COLOR);

                font.draw(batch, animalSound, animalSoundX + 20, animalSoundY + animalSoundHeight - 15);

                font.setColor(Color.WHITE);
            }
            if (clicked  ) {
                //TODO Nothing Ig
            }

            animalX += screenWidth / 5f;

            batch.draw(buttonTexture, animalX, animalY, (float) (animalSize * size), animalSize);

            font.getData().setScale(2f);
            font.draw(batch, "Pet", animalX + (float) size * animalSize / 2 - "Pet".length() * font.getScaleX() * 7.5f / 2f,
                animalY + animalSize / 1.5f);

            hovered = mousePos.x >= animalX && mousePos.x <= animalX + animalSize * 2f &&
                mousePos.y >= animalY && mousePos.y <= animalY + animalSize;

            clicked = false;

            if (hovered) {
                if (Gdx.input.isButtonJustPressed(0)) {
                    clicked = true;
                }
                String animalSound = "Amoo Nazam Mikoni? :)";
                if(animalData.isPetToday()){
                    animalSound = "Eee Dobareee? <3";
                }

                GlyphLayout tooltipLayout = new GlyphLayout(font, animalSound);

                float animalSoundWidth = tooltipLayout.width + 40;
                float animalSoundHeight = tooltipLayout.height + 30;

                float animalSoundX = animalX + (float) size * animalSize / 2f - animalSoundWidth / 2f;
                float animalSoundY = animalY + animalSize + 10;

                batch.end();
                shapeRenderer.setProjectionMatrix(camera.combined);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0, 0, 0, 0.85f);
                shapeRenderer.rect(animalSoundX, animalSoundY, animalSoundWidth, animalSoundHeight);
                shapeRenderer.end();
                batch.begin();

                if(animalData.isPetToday()){
                    font.setColor(Color.GREEN);
                } else{
                    font.setColor(Color.RED);
                }

                font.draw(batch, animalSound, animalSoundX + 20, animalSoundY + animalSoundHeight - 15);

                font.setColor(Color.WHITE);
            }
            if (clicked  ) {
                GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                    new GameCommand("pet -w inside -n "+name, AppClient.getUserData().getUsername()));
                AppClient.getClient().send(new Gson().toJson(msg));
                //TODO Pet
            }

            animalX += screenWidth / 12.5f;

            batch.draw(buttonTexture, animalX, animalY, (float) (animalSize * size), animalSize);

            font.getData().setScale(2f);
            font.draw(batch, "Feed", animalX + (float) size * animalSize / 2 - "Feed".length() * font.getScaleX() * 7.5f / 2f,
                animalY + animalSize / 1.5f);

            hovered = mousePos.x >= animalX && mousePos.x <= animalX + animalSize * 2f &&
                mousePos.y >= animalY && mousePos.y <= animalY + animalSize;

            clicked = false;

            if (hovered) {
                if (Gdx.input.isButtonJustPressed(0)) {
                    clicked = true;
                }
                String animalSound = "Ghoshname O_o";
                if(animalData.isFedToday()){
                    animalSound = "Againnn, Yummyyy =D";
                }


                GlyphLayout tooltipLayout = new GlyphLayout(font, animalSound);

                float animalSoundWidth = tooltipLayout.width + 40;
                float animalSoundHeight = tooltipLayout.height + 30;

                float animalSoundX = animalX + (float) size * animalSize / 2f - animalSoundWidth / 2f;
                float animalSoundY = animalY + animalSize + 10;

                batch.end();
                shapeRenderer.setProjectionMatrix(camera.combined);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0, 0, 0, 0.85f);
                shapeRenderer.rect(animalSoundX, animalSoundY, animalSoundWidth, animalSoundHeight);
                shapeRenderer.end();
                batch.begin();

                if(animalData.isFedToday()){
                    font.setColor(Color.GREEN);
                } else{
                    font.setColor(Color.RED);
                }

                font.draw(batch, animalSound, animalSoundX + 20, animalSoundY + animalSoundHeight - 15);

                font.setColor(Color.WHITE);
            }
            if (clicked  ) {
                //TODO Feed
            }

            animalX += screenWidth / 12.5f;
            batch.draw(buttonTexture, animalX, animalY, (float) (animalSize * size), animalSize);

            font.getData().setScale(2f);
            font.setColor(Color.RED);
            font.draw(batch, "Sell", animalX + (float) size * animalSize / 2 - "Sell".length() * font.getScaleX() * 7.5f / 2f,
                animalY + animalSize / 1.5f);
            font.setColor(Color.WHITE);

            hovered = mousePos.x >= animalX && mousePos.x <= animalX + animalSize * 2f &&
                mousePos.y >= animalY && mousePos.y <= animalY + animalSize;

            clicked = false;

            if (hovered) {
                if (Gdx.input.isButtonJustPressed(0)) {
                    clicked = true;
                }
                //TODO Right Price?
                int price = animalData.getBuyPrice();

                GlyphLayout tooltipLayout = new GlyphLayout(font,"Price: " + price);

                float animalSoundWidth = tooltipLayout.width + 40;
                float animalSoundHeight = tooltipLayout.height + 30;

                float animalSoundX = animalX + (float) size * animalSize / 2f - animalSoundWidth / 2f;
                float animalSoundY = animalY + animalSize + 10;

                batch.end();
                shapeRenderer.setProjectionMatrix(camera.combined);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0, 0, 0, 0.85f);
                shapeRenderer.rect(animalSoundX, animalSoundY, animalSoundWidth, animalSoundHeight);
                shapeRenderer.end();
                batch.begin();

                font.setColor(CustomColors.GOLD);

                font.draw(batch, "Price: " + price, animalSoundX + 20, animalSoundY + animalSoundHeight - 15);

                font.setColor(Color.WHITE);
            }
            if (clicked  ) {
                //TODO Sell
            }

            animalX += screenWidth / 12.5f;
            batch.draw(buttonTexture, animalX, animalY, (float) (animalSize * size), animalSize);

            font.getData().setScale(2f);
            font.draw(batch, "Collect", animalX + (float) size * animalSize / 2 - "Collect".length() * font.getScaleX() * 7.5f / 2f,
                animalY + animalSize / 1.5f);

            hovered = mousePos.x >= animalX && mousePos.x <= animalX + animalSize * 2f &&
                mousePos.y >= animalY && mousePos.y <= animalY + animalSize;

            clicked = false;
            // TODO Is Ready
            boolean isReady = false;

            if (hovered) {
                if (Gdx.input.isButtonJustPressed(0)) {
                    clicked = true;
                }
                String animalProduct = animalData.getProductName();
                // TODO Nadarim Null e :/
                if (animalProduct == null) {
                    animalProduct = "Nmd :/";
                }

                GlyphLayout tooltipLayout = new GlyphLayout(font, animalProduct);

                float animalSoundWidth = tooltipLayout.width + 40;
                float animalSoundHeight = tooltipLayout.height + 30;

                float animalSoundX = animalX + (float) size * animalSize / 2f - animalSoundWidth / 2f;
                float animalSoundY = animalY + animalSize + 10;

                batch.end();
                shapeRenderer.setProjectionMatrix(camera.combined);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0, 0, 0, 0.85f);
                shapeRenderer.rect(animalSoundX, animalSoundY, animalSoundWidth, animalSoundHeight);
                shapeRenderer.end();
                batch.begin();

                if(isReady){
                    font.setColor(Color.GREEN);
                } else{
                    font.setColor(Color.RED);
                }

                font.draw(batch, animalProduct, animalSoundX + 20, animalSoundY + animalSoundHeight - 15);

                font.setColor(Color.WHITE);
            }
            if (clicked  ) {
                //TODO Collect Product
            }

            animalX += screenWidth / 12.5f;

            y -= spacing;
        }

        font.getData().setScale(1f);
    }

    private String readableName(String camelCase) {
        return camelCase.replaceAll("([a-z])([A-Z])", "$1 $2");
    }


    public void dispose() {
        menuTexture.dispose();
    }

    public void scrollDown() {
        if (selectedIndex > 0) selectedIndex--;
    }

    public void scrollUp() {
        if (selectedIndex < animalsData.size() - visibleAnimalsCount)
            selectedIndex++;
    }
}
