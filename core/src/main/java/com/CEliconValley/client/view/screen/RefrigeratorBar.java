package com.CEliconValley.client.view.screen;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.AnimalData;
import com.CEliconValley.common.messages.GameCommand;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.controllers.ItemManager;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.buildings.Refrigerator;
import com.CEliconValley.models.items.Inventory;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;
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
import com.badlogic.gdx.math.Vector3;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

public class RefrigeratorBar {
    private final Texture menuTexture;

    private final int tileWidth;
    private final int tileHeight;


    private Refrigerator refrigerator;

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

    public RefrigeratorBar(GameScreen screen) {
        this.screen = screen;
        menuTexture = GameAssetManager.getGameAssetManager().getScreenTexture("Refrigerator.png");

        tileWidth = menuTexture.getWidth() / 3;
        tileHeight = menuTexture.getHeight() / 3;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void render(Batch batch, OrthographicCamera camera) {
        refrigerator = Finder.getfd().getRefrigeratorData().getRefrigerator();

        foodsData = refrigerator.slots;

        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        float firstItemX = screenWidth * 0.03f;
        float firstItemY = screenHeight * 0.58f;

        int row = 0, startPoint = 0;
        float slotSize = screenWidth * 0.036f;

        float spacingX = slotSize * 0.275f;
        float spacingY = slotSize * 0.6f;

        float menuWidth = screenWidth * 0.6f;
        float menuHeight = screenHeight * 0.7f;
        startingX = camera.position.x - menuWidth / 2f;
        startingY = camera.position.y - menuHeight / 2f;

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        batch.draw(menuTexture, startingX, startingY, menuWidth, menuHeight);

        for (int col = 0; row < 3; ) {
            int index = col + (startingRow + row) * 12;
            if (foodsData.size() > index) {
                Slot slot = foodsData.get(index);
                Item item = slot.getItem();
                TextureRegion texture;

                if (item != null) {
                    texture = ItemManager.getTexture(item);
                    if (texture != null) {
                        float x = startingX + firstItemX + col * (slotSize + spacingX);
                        float y = startingY + firstItemY - row * (slotSize + spacingY) * 0.9f;

                        float originalWidth = texture.getRegionWidth() * 0.9f;
                        float originalHeight = texture.getRegionHeight() * 0.9f;

                        float aspectRatio = originalWidth / originalHeight;

                        float drawWidth, drawHeight;

                        if (originalWidth > originalHeight) {
                            drawWidth = slotSize;
                            drawHeight = slotSize / aspectRatio;
                        } else {
                            drawHeight = slotSize;
                            drawWidth = slotSize * aspectRatio;
                        }

                        float drawX = x + (slotSize - drawWidth) / 2f;
                        float drawY = y + (slotSize - drawHeight) / 2f;

                        batch.draw(texture, drawX, drawY, drawWidth, drawHeight);


                        if (slot.getQuantity()>1) {
                            font.getData().setScale(2.5f);
                            String amountText = String.valueOf(slot.getQuantity());
                            GlyphLayout layout = new GlyphLayout(font, amountText);
                            float textX = drawX + slotSize * 3 / 4f;
                            float textY = drawY + layout.height / 3f;
                            font.draw(batch, layout, textX, textY);
                            font.getData().setScale(1f);
                        }


                        if (mousePos.x >= x && mousePos.x <= x + slotSize &&
                            mousePos.y >= y && mousePos.y <= y + slotSize) {
                            if (Gdx.input.isButtonJustPressed(0)) {
//                                if (item instanceof Tool) {
//                                    GameMessage<GameCommand> msg = new GameMessage<>("game-command",
//                                        new GameCommand("tools equip " + item.getName(),
//                                            AppClient.getUserData().getUsername())
//                                    );
//                                    AppClient.getClient().send(new Gson().toJson(msg));
//                                }
                                //TODO Select Food
                            }


                            String name = readableName(item.getName());
                            GlyphLayout layout = new GlyphLayout(font, name);
                            float tooltipWidth = layout.width + 20;
                            float tooltipHeight = layout.height + 10;

                            float tooltipX = x + slotSize / 2f - tooltipWidth / 2f;
                            float tooltipY = y + slotSize + 10;


                            batch.end();
                            shapeRenderer.setProjectionMatrix(camera.combined);
                            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                            shapeRenderer.setColor(0, 0, 0, 0.8f);
                            shapeRenderer.rect(tooltipX, tooltipY, tooltipWidth, tooltipHeight);
                            shapeRenderer.end();
                            batch.begin();

                            font.draw(batch, layout, tooltipX + 10, tooltipY + tooltipHeight - 5);
                        }
                    }
                }
            }

            col++;
            if (col == 12) {
                col = 0;
                row++;
                if (row == 2) {
                    startingY += 22;
                }
            }
        }
        renderInventoryBar(batch, camera, Objects.requireNonNull(Finder.getpd()).getInventoryData().getInventory());
    }

    private void renderInventoryBar(Batch batch, OrthographicCamera camera, Inventory inventory) {
        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        float firstItemX = screenWidth * 0.03f;
        float firstItemY = screenHeight * 0.21f;

        int row = 0, startPoint = 0;
        float slotSize = screenWidth * 0.036f;

        float spacingX = slotSize * 0.275f;
        float spacingY = slotSize * 0.63f;

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        for (int col = 0; row < 3; ) {
            int index = col + (startingRow + row) * 12;
            if (inventory.getBackpack().getSize() > index) {
                Slot slot = inventory.getSlots().get(index);
                Item item = slot.getItem();
                TextureRegion texture;

                if (item != null) {
                    texture = ItemManager.getTexture(item);
                    if (texture != null) {
                        float x = startingX + firstItemX + col * (slotSize + spacingX);
                        float y = startingY + firstItemY - row * (slotSize + spacingY) * 0.9f;
//                        batch.draw(texture, x, y, slotSize, slotSize);
                        float originalWidth = texture.getRegionWidth() * 0.9f;
                        float originalHeight = texture.getRegionHeight() * 0.9f;

                        float aspectRatio = originalWidth / originalHeight;

                        float drawWidth, drawHeight;

                        if (originalWidth > originalHeight) {
                            drawWidth = slotSize;
                            drawHeight = slotSize / aspectRatio;
                        } else {
                            drawHeight = slotSize;
                            drawWidth = slotSize * aspectRatio;
                        }

                        float drawX = x + (slotSize - drawWidth) / 2f;
                        float drawY = y + (slotSize - drawHeight) / 2f;

                        batch.draw(texture, drawX, drawY, drawWidth, drawHeight);


                        if (slot.getQuantity()>1) {
                            font.getData().setScale(2.5f);
                            String amountText = String.valueOf(slot.getQuantity());
                            GlyphLayout layout = new GlyphLayout(font, amountText);
                            float textX = drawX + slotSize * 3 / 4f;
                            float textY = drawY + layout.height / 3f;
                            font.draw(batch, layout, textX, textY);
                            font.getData().setScale(1f);
                        }


                        if (mousePos.x >= x && mousePos.x <= x + slotSize &&
                            mousePos.y >= y && mousePos.y <= y + slotSize) {
                            if (Gdx.input.isButtonJustPressed(0)) {
                                if (item instanceof Tool) {
                                    // TODO Select Inventory
                                }
                            }


                            String name = readableName(item.getName());
                            GlyphLayout layout = new GlyphLayout(font, name);
                            float tooltipWidth = layout.width + 20;
                            float tooltipHeight = layout.height + 10;

                            float tooltipX = x + slotSize / 2f - tooltipWidth / 2f;
                            float tooltipY = y + slotSize + 10;


                            batch.end();
                            shapeRenderer.setProjectionMatrix(camera.combined);
                            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                            shapeRenderer.setColor(0, 0, 0, 0.8f);
                            shapeRenderer.rect(tooltipX, tooltipY, tooltipWidth, tooltipHeight);
                            shapeRenderer.end();
                            batch.begin();

                            font.draw(batch, layout, tooltipX + 10, tooltipY + tooltipHeight - 5);
                        }
                    }
                }
            }

            col++;
            if (col == 12) {
                col = 0;
                row++;
                if (row == 2) {
                    startingY += 22;
                }
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
