package com.CEliconValley.client.view.screen.menu;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.view.screen.GameScreen;
import com.CEliconValley.common.PlayerData;
import com.CEliconValley.common.messages.Emotion;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Player;
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

import java.util.ArrayList;

public class ReactionBar {
    private final Texture menuTexture;
    private final Texture emoteTexture;

    private ArrayList<String> allTextReactions;
    private ArrayList<TextureRegion> allEmoteTextures;
    private ArrayList<String> playerReactions;


    private int startingRow = 0;
    private int selectedIndex = 0;
    private final int visibleAnimalsCount = 4;

    private Player player;

    BitmapFont font = new BitmapFont();
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    private float startingX;
    private float startingY;

    private OrthographicCamera camera;

    private GameScreen screen;

    public ReactionBar(GameScreen screen) {
        this.screen = screen;
        menuTexture = GameAssetManager.getGameAssetManager().getScreenTexture("Reaction.png");
        emoteTexture = GameAssetManager.getGameAssetManager().getScreenTexture("EmoteScreen.png");

        font.getData().setScale(2f);

        this.allTextReactions = screen.allTextReactions;
        this.allEmoteTextures = screen.allEmoteTextures;

        playerReactions = allTextReactions;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void render(Batch batch, OrthographicCamera camera) {

        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        float menuWidth = screenWidth * 0.6f;
        float menuHeight = screenHeight * 0.7f;

        startingX = camera.position.x - menuWidth / 2f;
        startingY = camera.position.y - menuHeight / 2f;

        float x = startingX + screenWidth * 0.03f;
        float y = startingY + menuHeight * 0.7f;

        float reactionSize = menuWidth * 0.2f;

        float spacingY = reactionSize * 0.3f;

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        batch.draw(emoteTexture, startingX - menuWidth / 3f, startingY - menuHeight / 4.4f, screenWidth, screenHeight * 1.05f);
        batch.draw(menuTexture, startingX, startingY, menuWidth, menuHeight);

        int col = 0;
        PlayerData playerData = Finder.getpd();
//        font.getData().setScale(2.5f);
        for (Emotion emotion : playerData.emotions) {
            if (emotion.isEmote) {
                batch.draw(allEmoteTextures.get(emotion.index), x, y - spacingY, reactionSize / 2.5f, reactionSize / 2.5f);

                if (mousePos.x >= x && mousePos.x <= x + reactionSize / 2 &&
                    mousePos.y >= y - spacingY * 2 && mousePos.y <= y) {
                    if (Gdx.input.isButtonJustPressed(0)) {
                        GameMessage<Emotion> msg = new GameMessage<>("remove-emotion", emotion);
                        AppClient.getClient().send(new Gson().toJson(msg));
                    } else if (Gdx.input.isButtonJustPressed(1)) {
                        GameMessage<Emotion> msg = new GameMessage<>("emote", emotion);
                        AppClient.getClient().send(new Gson().toJson(msg));
                        screen.reactionMode = false;
                    }

                    GlyphLayout layout = new GlyphLayout(font, "React");
                    float textWidth = layout.width + 20;
                    float textHeight = layout.height + 10;

                    float textX = x + reactionSize / 2f - textWidth / 2f;
                    float textY = y + reactionSize / 10f + 10;

                    batch.end();
                    shapeRenderer.setProjectionMatrix(camera.combined);
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                    shapeRenderer.setColor(0, 0, 0, 0.8f);
                    shapeRenderer.rect(textX, textY, textWidth, textHeight);
                    shapeRenderer.end();
                    batch.begin();

                    font.draw(batch, layout, textX + 10, textY + textHeight - 5);
                }
            } else {
                font.draw(batch, allTextReactions.get(emotion.index), x, y);

                if (mousePos.x >= x && mousePos.x <= x + reactionSize &&
                    mousePos.y >= y - spacingY * 2 && mousePos.y <= y) {
                    if (Gdx.input.isButtonJustPressed(0)) {
                        GameMessage<Emotion> msg = new GameMessage<>("remove-emotion", emotion);
                        AppClient.getClient().send(new Gson().toJson(msg));
                    } else if (Gdx.input.isButtonJustPressed(1)) {
                        GameMessage<Emotion> msg = new GameMessage<>("emote", emotion);
                        AppClient.getClient().send(new Gson().toJson(msg));
                        screen.reactionMode = false;
                    }

                    GlyphLayout layout = new GlyphLayout(font, "React " + allTextReactions.get(emotion.index));
                    float textWidth = layout.width + 20;
                    float textHeight = layout.height + 10;

                    float textX = x + reactionSize / 2f - textWidth / 2f;
                    float textY = y + reactionSize / 10f + 10;

                    batch.end();
                    shapeRenderer.setProjectionMatrix(camera.combined);
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                    shapeRenderer.setColor(0, 0, 0, 0.8f);
                    shapeRenderer.rect(textX, textY, textWidth, textHeight);
                    shapeRenderer.end();
                    batch.begin();

                    font.draw(batch, layout, textX + 10, textY + textHeight - 5);
                }
            }

            x += reactionSize;
            col++;
        }

//        font.getData().setScale(2f);
        col = 0;
        y = startingY + menuHeight / 2.4f;

        x = startingX + screenWidth * 0.03f;

        for (String reaction : allTextReactions) {
            font.draw(batch, reaction, x, y);

            if (mousePos.x >= x && mousePos.x <= x + reactionSize &&
                mousePos.y >= y - spacingY / 1.75 && mousePos.y <= y) {
                if (Gdx.input.isButtonJustPressed(0)) {
                    int index = allTextReactions.indexOf(reaction);
                    GameMessage<Emotion> msg = new GameMessage<>("add-emotion",
                        new Emotion(index, false, AppClient.getUserData().getUsername()));
                    AppClient.getClient().send(new Gson().toJson(msg));
                }
                GlyphLayout layout = new GlyphLayout(font, "Select: " + reaction);
                float textWidth = layout.width + 20;
                float textHeight = layout.height + 10;

                float textX = x + reactionSize / 2f - textWidth / 2f;
                float textY = y + reactionSize / 10f + 10;

                batch.end();
                shapeRenderer.setProjectionMatrix(camera.combined);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0, 0, 0, 0.8f);
                shapeRenderer.rect(textX, textY, textWidth, textHeight);
                shapeRenderer.end();
                batch.begin();

                font.draw(batch, layout, textX + 10, textY + textHeight - 5);
            }

            x += reactionSize;
            col++;
            if (col == 5) {
                col = 0;
                y -= spacingY / 1.75f;
                x = startingX + screenWidth * 0.03f;
            }
        }

        x = startingX + screenWidth * 0.03f;

        y -= spacingY;

        col = 0;
        for (TextureRegion textureRegion : allEmoteTextures) {
            batch.draw(textureRegion, x, y, reactionSize / 4f, reactionSize / 4f);

            if (mousePos.x >= x && mousePos.x <= x + reactionSize &&
                mousePos.y >= y && mousePos.y <= y + spacingY) {
                if (Gdx.input.isButtonJustPressed(0)) {
                    int index = allEmoteTextures.indexOf(textureRegion);
                    GameMessage<Emotion> msg = new GameMessage<>("add-emotion",
                        new Emotion(index, true, AppClient.getUserData().getUsername()));
                    AppClient.getClient().send(new Gson().toJson(msg));
                }
                GlyphLayout layout = new GlyphLayout(font, "Select");
                float textWidth = layout.width + 20;
                float textHeight = layout.height + 10;

                float textX = x + reactionSize / 2f - textWidth / 2f;
                float textY = y + reactionSize / 10f + 10;

                batch.end();
                shapeRenderer.setProjectionMatrix(camera.combined);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0, 0, 0, 0.8f);
                shapeRenderer.rect(textX, textY, textWidth, textHeight);
                shapeRenderer.end();
                batch.begin();

                font.draw(batch, layout, textX + 10, textY + textHeight - 5);
            }

            x += reactionSize;
            col++;
            if (col == 5) {
                col = 0;
                y -= spacingY;
                x = startingX + screenWidth * 0.03f;
            }
        }
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
}
