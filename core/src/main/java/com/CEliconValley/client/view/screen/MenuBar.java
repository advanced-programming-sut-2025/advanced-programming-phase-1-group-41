package com.CEliconValley.client.view.screen;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.InventoryData;
import com.CEliconValley.common.PlayerData;
import com.CEliconValley.common.messages.GameCommand;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.controllers.ItemManager;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.items.*;
import com.CEliconValley.models.tools.Tool;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.Gson;

import java.util.Map;

public class MenuBar {
    private final Texture menuTexture;
    private final Texture miniMapTexture;
    private final TextureRegion[] tabTextures;
    private final int tileWidth;
    private final int tileHeight;
    private Player player;
    BitmapFont font = new BitmapFont();
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    private int startingRow = 0;
    private float startingX;
    private float startingY;
    private String currentTab;
    private OrthographicCamera camera;

    private final String[] tabOrder = {
        "Inventory", "Stats", "Relation",
        "Map", "Crafting", "Artisan",
        "Controll", "Cheat", null
    };

    public MenuBar() {

        menuTexture = new Texture("game/Buildings/Screen/Menu_Screen.png");
        miniMapTexture = new Texture("game/Buildings/Screen/map.png");

        tileWidth = menuTexture.getWidth() / 3;
        tileHeight = menuTexture.getHeight() / 3;


        TextureRegion[][] split = TextureRegion.split(menuTexture, tileWidth, tileHeight);


        tabTextures = new TextureRegion[9];

        tabTextures[0] = split[0][1];
        tabTextures[1] = split[0][2];
        tabTextures[2] = split[1][0];
        tabTextures[3] = split[1][1];
        tabTextures[4] = split[1][2];
        tabTextures[5] = split[2][0];
        tabTextures[6] = split[2][1];
        tabTextures[7] = split[2][2];

        currentTab = "Inventory";
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void render(Batch batch, OrthographicCamera camera) {
        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        Inventory inventory = Finder.getpd().getInventoryData().getInventory();
        this.camera = camera;

        float menuWidth = screenWidth * 0.6f;
        float menuHeight = screenHeight * 0.7f;
        startingX = camera.position.x - menuWidth / 2f;
        startingY = camera.position.y - menuHeight / 2f;

        int tabIndex = getTabIndex(currentTab);
        if (tabIndex >= 0 && tabIndex < tabTextures.length && tabTextures[tabIndex] != null) {
            batch.draw(tabTextures[tabIndex], startingX, startingY, menuWidth, menuHeight);
        }

        switch (currentTab) {
            case "Inventory":
            case "Crafting":
            case "Artisan":
            case "Controll":
            case "Cheat":
                renderInventoryBar(batch, camera, inventory);
                break;
            case "Stats":
                renderStats(batch);
                break;
            case "Relation":
                renderRelations(batch);
                break;
            case "Map":
                renderMap(batch);
                break;
        }
        switch (currentTab) {
            case "Crafting":
                renderCrafting(batch);
                break;
        }
    }


    private void renderInventoryBar(Batch batch, OrthographicCamera camera, Inventory inventory) {
        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        float firstItemX = screenWidth * 0.0370f;
        float firstItemY = screenHeight * 0.5225f;

        int row = 0, startPoint = 0;
        float slotSize = screenWidth * 0.035f;

        float spacingX = slotSize * 0.275f;
        float spacingY = slotSize * 0.6f;

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
                            String amountText = String.valueOf(slot.getQuantity());
                            GlyphLayout layout = new GlyphLayout(font, amountText);
                            float textX = x + 100;
                            float textY = y + 8;
                            font.getData().setScale(2.5f);
                            font.draw(batch, layout, textX, textY);
                            font.getData().setScale(1f);
                        }


                        if (mousePos.x >= x && mousePos.x <= x + slotSize &&
                            mousePos.y >= y && mousePos.y <= y + slotSize) {
                            if (Gdx.input.isButtonJustPressed(0)) {
                                if (item instanceof Tool) {
                                    GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                                        new GameCommand("tools equip " + item.getName(),
                                            AppClient.getUserData().getUsername())
                                    );
                                    AppClient.getClient().send(new Gson().toJson(msg));
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


    private void renderStats(Batch batch) {

    }

    private void renderRelations(Batch batch) {

    }

    private void renderMap(Batch batch) {
        batch.draw(miniMapTexture, startingX, startingY, menuTexture.getWidth() / 3f, menuTexture.getHeight() / 3f);

    }

    private void renderCrafting(Batch batch) {
        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        float minX = menuTexture.getWidth() / 25f;
        float maxX = menuTexture.getWidth() / 2.5f;
        float minY = menuTexture.getHeight() / 40f;
        float maxY = menuTexture.getHeight() / 10f;

        float currentX = minX;
        float currentY = maxY;

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        for (CraftableMachine machine : CraftableMachine.values()) {
            TextureRegion texture = ItemManager.getTexture(machine);
            if (texture == null) continue;

            if (currentX > maxX) {
                float width = screenWidth * 0.02f;
                currentX = minX + width;
                currentY -= menuTexture.getHeight() / 15f;

                if (currentY < minY) break;
            }

            float drawX = startingX + currentX;
            float drawY = startingY + currentY;
            float width = screenWidth * 0.02f;
            float height = width * 2f;


            if (!player.getCraftingRecipes().contains(machine.getRecipe())) {
                batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
            }

            batch.draw(texture, drawX, drawY, width, height);
            batch.setColor(1, 1, 1, 1);

            boolean mouseOver = mousePos.x >= drawX && mousePos.x <= drawX + width &&
                mousePos.y >= drawY && mousePos.y <= drawY + height;

            if (mouseOver && player.getCraftingRecipes().contains(machine.getRecipe())) {
                drawTooltip(batch, machine, drawX, drawY);

                if (Gdx.input.justTouched()) {
                    if (hasAllItems(machine.getRecipe())) {
                        Map<Item, Integer> requiredItems = machine.getRecipe().neededItems;
                        Inventory inventory = player.getInventory();
                        for (Map.Entry<Item, Integer> entry : requiredItems.entrySet()) {
                            Item item = entry.getKey();
                            int Amount = entry.getValue();
                            inventory.removeFromInventory(item, Amount);
                        }
                        inventory.addToInventory(machine, 1);

                    }
                }
            }
            currentX += width * 2.3f;
        }

    }

    private boolean hasAllItems(CraftingRecipe recipe) {
        Map<Item, Integer> requiredItems = recipe.neededItems;
        Inventory inventory = player.getInventory();

        for (Map.Entry<Item, Integer> entry : requiredItems.entrySet()) {
            Item item = entry.getKey();
            int Amount = entry.getValue();
            if (!inventory.doHave(item, Amount)) {
                return false;
            }
        }

        return true;
    }


    private String readableName(String camelCase) {
        return camelCase.replaceAll("([a-z])([A-Z])", "$1 $2");
    }

    private void drawTooltip(Batch batch, CraftableMachine machine, float drawX, float drawY) {
        CraftingRecipe recipe = machine.getRecipe();
        if (recipe == null) return;

        float width = 150;
        float padding = 10;
        float lineHeight = 40;
        float iconSize = 32;

        int itemCount = recipe.getNeededItems().size();
        float height = padding * 2 + lineHeight + itemCount * lineHeight;

        float x = drawX - width - 10;
        float y = drawY + 192 - height;


        batch.end();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0f, 0f, 0f, 0.9f);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();

        batch.begin();


        String title = readableName(machine.name());
        GlyphLayout layout = new GlyphLayout(font, title);
        font.draw(batch, layout, x + padding, y + height - padding);


        int i = 0;
        for (Map.Entry<Item, Integer> entry : recipe.getNeededItems().entrySet()) {
            Item item = entry.getKey();
            int amount = entry.getValue();
            TextureRegion icon = ItemManager.getTexture(item);

            float itemY = y + height - padding - lineHeight * (i + 2);

            if (icon != null) {
                batch.draw(icon, x + padding, itemY, iconSize, iconSize);
            }
            if (player.getInventory().doHave(item, amount)) {
                font.setColor(0f, 0.5f, 1f, 1f);
            } else {
                font.setColor(1f, 0f, 0f, 1f);
            }
            font.draw(batch, "x" + amount, x + padding + iconSize + 10, itemY + iconSize / 2f + 5);
            i++;
            font.setColor(1f, 1f, 1f, 1f);
        }
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

    public void goToNextTab() {
        int currentIndex = getTabIndex(currentTab);
        if (currentIndex >= 0) {
            int nextIndex = (currentIndex + 1) % tabOrder.length;
            if (tabOrder[nextIndex] != null) {
                currentTab = tabOrder[nextIndex];
            } else {

                nextIndex = (nextIndex + 1) % tabOrder.length;
                currentTab = tabOrder[nextIndex];
            }
        }
    }

    public void goToPreviousTab() {
        int currentIndex = getTabIndex(currentTab);
        if (currentIndex >= 0) {
            int prevIndex = (currentIndex - 1 + tabOrder.length) % tabOrder.length;
            if (tabOrder[prevIndex] != null) {
                currentTab = tabOrder[prevIndex];
            } else {

                prevIndex = (prevIndex - 1 + tabOrder.length) % tabOrder.length;
                currentTab = tabOrder[prevIndex];
            }
        }
    }

    private int getTabIndex(String tabName) {
        for (int i = 0; i < tabOrder.length; i++) {
            if (tabName.equals(tabOrder[i])) {
                return i;
            }
        }
        return -1;
    }

    public String getCurrentTab() {
        return currentTab;
    }
}
