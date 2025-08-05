package com.CEliconValley.client.view.screen;

import com.CEliconValley.common.InventoryData;
import com.CEliconValley.common.PlayerData;
import com.CEliconValley.controllers.ItemManager;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.items.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MenuBar {
    private final Texture menuTexture;
    private final Texture miniMapTexture;
    private final TextureRegion[] tabTextures;
    private final int tileWidth;
    private final int tileHeight;
    private Player player;

    private int startingRow = 0;
    private float startingX;
    private float startingY;
    private String currentTab;

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

    public void render(Batch batch, OrthographicCamera camera ) {
        Inventory inventory=player.getInventory();
        startingX = camera.position.x - camera.viewportWidth/2 + (camera.viewportWidth - tileWidth*2)/2;
        startingY = camera.position.y - camera.viewportHeight/2 + (camera.viewportHeight - tileHeight*2)/2;


        int tabIndex = getTabIndex(currentTab);
        if (tabIndex >= 0 && tabIndex < tabTextures.length && tabTextures[tabIndex] != null) {
            batch.draw(tabTextures[tabIndex], startingX, startingY, tileWidth*2, tileHeight*2);
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
        switch (currentTab){
            case "Crafting":
                renderCraftings(batch);
                break;
        }
    }


    private void renderInventoryBar(Batch batch, OrthographicCamera camera, Inventory inventory) {

        float firstItemX = (2 * tileWidth * 49) / 856f;
        float firstItemY = (2 * tileHeight * 484) / 648f;
        int row = 0, startPoint = 0;
        float slotSize = tileWidth * 56 * 2 / 856f;

        for (int col = 0; row < 3; ) {
            if(inventory.getBackpack().getSize()>col + (startingRow + row) * 12) {
                Slot slot = inventory.getSlots().get(col + (startingRow + row) * 12);
                Item item = slot.getItem();
                TextureRegion texture;
                if (item != null) {
                    texture = ItemManager.getTexture(item);
                    if (texture != null) {
                        float x = startingX + firstItemX + col * (slotSize + 16);
                        float y = startingY + firstItemY - row * (slotSize + 47);
                        batch.draw(texture, x, y, slotSize, slotSize);
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
        batch.draw(miniMapTexture, startingX, startingY, menuTexture.getWidth()/3f, menuTexture.getHeight()/3f);

    }

    private void renderCraftings(Batch batch) {

        float minX = 50;
        float maxX = 1650;
        float minY = 21;
        float maxY = 272;

        float currentX = minX;
        float currentY = maxY;



        for (CraftableMachine machine : CraftableMachine.values()) {
            TextureRegion texture = ItemManager.getTexture(machine);
            if (texture == null) continue;




            if (currentX  > maxX) {
                currentX = minX;
                currentY -= 192;


                if (currentY < minY) break;
            }
            if(!player.getCraftingRecipes().contains(machine.getRecipe())){
                batch.setColor(0.5f,0.5f,0.5f,0.5f);
            }
            batch.draw(texture, startingX + currentX, startingY + currentY, 96,192);
            batch.setColor(1,1,1,1);


            currentX +=96+55;
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
