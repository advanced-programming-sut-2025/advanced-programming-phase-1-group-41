package com.CEliconValley.client.view.screen.menu;

import com.CEliconValley.client.view.screen.GameScreen;
import com.CEliconValley.controllers.ItemManager;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.ui.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class ReactionBar {
    private final Texture menuTexture;

    private final int tileWidth;
    private final int tileHeight;

    private final ArrayList<String> allReactions = new ArrayList<>();
    private final ArrayList<String> playerReactions = new ArrayList<>();


    private int startingRow = 0;
    private int selectedIndex = 0;
    private final int visibleAnimalsCount = 4;

    private Player player;

    BitmapFont font = new BitmapFont();
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    private float startingX;
    private float startingY;
    private String currentTab;
    ArrayList<Slot> foodsData = null;
    private OrthographicCamera camera;

    private GameScreen screen;

    public ReactionBar(GameScreen screen) {
        this.screen = screen;
        menuTexture = GameAssetManager.getGameAssetManager().getScreenTexture("Reaction.png");

        font.getData().setScale(2f);

        tileWidth = menuTexture.getWidth() / 3;
        tileHeight = menuTexture.getHeight() / 3;

        allReactions.add("Salam");
        allReactions.add("Salam");
        allReactions.add("Salam");
        allReactions.add("Salam");
        allReactions.add("Salam");
        allReactions.add("Salam");
        allReactions.add("Salam");
        allReactions.add("Khobi");
        allReactions.add("Man Kir Mikham");
        allReactions.add("Jooon");
        allReactions.add("Sex");
        playerReactions.addAll(allReactions);
        allReactions.add("Dokhtar Bazi");
        allReactions.add("Oouf");
        allReactions.add("Damn");
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
        float y = startingY + screenHeight * 0.6f;

        float reactionSize = screenWidth * 0.08f;

        float spacingY = reactionSize * 0.6f;

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        batch.draw(menuTexture, startingX, startingY, menuWidth, menuHeight);

        int col = 0;
        for (String reaction : playerReactions) {
            font.draw(batch, reaction, x, y);

            if (mousePos.x >= x && mousePos.x <= x + reactionSize &&
                mousePos.y >= y && mousePos.y <= y + reactionSize) {
                if (Gdx.input.isButtonJustPressed(0)) {
                    //TODO Left Click in Player Reactions
                } else if (Gdx.input.isButtonJustPressed(1)) {
                    //TODO Right Click in Player Reactions
                }

                GlyphLayout layout = new GlyphLayout(font, "React: " + reaction);
                float tooltipWidth = layout.width + 20;
                float tooltipHeight = layout.height + 10;

                float tooltipX = x + reactionSize / 2f - tooltipWidth / 2f;
                float tooltipY = y + reactionSize + 10;


                batch.end();
                shapeRenderer.setProjectionMatrix(camera.combined);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0, 0, 0, 0.8f);
                shapeRenderer.rect(tooltipX, tooltipY, tooltipWidth, tooltipHeight);
                shapeRenderer.end();
                batch.begin();

                font.draw(batch, layout, tooltipX + 10, tooltipY + tooltipHeight - 5);
            }

            x += reactionSize;
            col++;
            if (col == 5) {
                col = 0;
                y += spacingY;
                x = startingX + screenWidth * 0.03f;
            }
        }
        col = 0;
        y = startingY + menuHeight / 3f;
        for (String reaction : allReactions) {
            font.draw(batch, reaction, x, y);

            if (mousePos.x >= x && mousePos.x <= x + reactionSize &&
                mousePos.y >= y && mousePos.y <= y + reactionSize) {
                if (Gdx.input.isButtonJustPressed(0)) {
                    //TODO Left Click in Player Reactions
                } else if (Gdx.input.isButtonJustPressed(1)) {
                    //TODO Right Click in Player Reactions
                }

                GlyphLayout layout = new GlyphLayout(font, "React: " + reaction);
                float tooltipWidth = layout.width + 20;
                float tooltipHeight = layout.height + 10;

                float tooltipX = x + reactionSize / 2f - tooltipWidth / 2f;
                float tooltipY = y + reactionSize + 10;


                batch.end();
                shapeRenderer.setProjectionMatrix(camera.combined);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0, 0, 0, 0.8f);
                shapeRenderer.rect(tooltipX, tooltipY, tooltipWidth, tooltipHeight);
                shapeRenderer.end();
                batch.begin();

                font.draw(batch, layout, tooltipX + 10, tooltipY + tooltipHeight - 5);
            }

            x += reactionSize;
            col++;
            if (col == 5) {
                col = 0;
                y += spacingY;
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

    public void scrollUp() {
        if (selectedIndex < foodsData.size() - visibleAnimalsCount)
            selectedIndex++;
    }
}
