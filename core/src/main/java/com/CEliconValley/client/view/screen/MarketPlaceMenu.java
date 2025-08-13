package com.CEliconValley.client.view.screen;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.MarketPlaceData;
import com.CEliconValley.common.PlayerData;
import com.CEliconValley.common.SlotData;
import com.CEliconValley.common.messages.GameCommand;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.controllers.ItemManager;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.animals.FishType;
import com.CEliconValley.models.buildings.marketplaces.Marketplace;
import com.CEliconValley.models.items.Inventory;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.ui.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MarketPlaceMenu {
    private Texture menuTexture;
    private Texture infoTexture;
    private TextureRegion[] tabTextures;
    private int startingRow=0;
    private boolean hideSoldOuts=false;
    private TextureRegion tabTexture;
    private final BitmapFont font = new BitmapFont();
    private final GameScreen screen;
    private OrthographicCamera camera;
    public MarketPlaceMenu(GameScreen screen) {
        this.screen = screen;
        menuTexture = GameAssetManager.getGameAssetManager().getScreenTexture("MarketPlaceMenu_Screen.png");
        infoTexture = GameAssetManager.getGameAssetManager().getBackgroundTexture("Info_Background1.png");



        int tileWidth = menuTexture.getWidth() / 2;
        int tileHeight = menuTexture.getHeight();


        TextureRegion[][] split = TextureRegion.split(menuTexture, tileWidth, tileHeight);


        tabTextures = new TextureRegion[2];

        tabTextures[0] = split[0][0];
        tabTextures[1] = split[0][1];
    }

    public void renderShopMenu(Batch batch, OrthographicCamera camera, Marketplace shop) {
        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;
        MarketPlaceData mpd = null;
        for (MarketPlaceData marketplacesDatum : AppClient.getGameData().getVillageData().getMarketplacesData()) {
            if(marketplacesDatum.getName().equals(shop.getName())) {
                mpd = marketplacesDatum;
                break;
            }
        }

        this.camera = camera;

        float menuWidth = screenWidth * 0.6f;
        float menuHeight = screenHeight * 0.7f;
        float startingX = camera.position.x - menuWidth / 2f;
        float startingY = camera.position.y - menuHeight / 2f;

        if(!hideSoldOuts) {
            batch.draw(tabTextures[0], startingX, startingY, menuWidth, menuHeight);
        }else{
            batch.draw(tabTextures[1], startingX, startingY, menuWidth, menuHeight);
        }


        float firstItemX = startingX + menuWidth * 0.05f;
//        float firstItemX = screenWidth * 0.0370f;
        float firstItemY  = startingY + menuHeight*0.70f;

        float slotSize = screenWidth * 0.05f;
        float spacingY = slotSize * 0.6f;

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);


        ArrayList<SlotData> shopItems = mpd.getItemsForSaleData();

//        for(SlotData slot:shopItems){
//            System.out.println(slot.getSlot().getItem().getName());
//        }
//        System.out.println("anddddddddddddddddddddddddddd"+shopItems.size());
        if(hideSoldOuts){
            ArrayList<SlotData> notSoldOutItems = new ArrayList<>();
            for(SlotData slotData : shopItems){
                if(slotData.getSlot().getQuantity()>0){
                    notSoldOutItems.add(slotData);
                }
            }
            shopItems = notSoldOutItems;
        }
        int columns = 3;
        float spacingX = slotSize * 3.5f;

        for (int i = 0; i < shopItems.size(); i++) {
            if(shopItems.size()<=i+startingRow*3){continue;}
            SlotData sd = shopItems.get(i+startingRow*3);
            Slot slot = sd.getSlot();
            Item item = slot.getItem();
            if (item == null) continue;

            TextureRegion texture = ItemManager.getTexture(item);
            if (texture == null) continue;

            int col = i % columns;
            int row = i / columns;
            if(row>=4)continue;
            float x = firstItemX + col * spacingX;
            float y = firstItemY - row * (slotSize + spacingY);

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

            font.getData().setScale(1.5f);
            String name = readableName(item.getName());
            font.draw(batch, name, x + slotSize + 20, y + slotSize / 1.5f);

                font.getData().setScale(1.2f);
            if (slot.getQuantity() ==0) {
                font.setColor(Color.RED);
                font.draw(batch, "SOLD OUT", x + slotSize + 20, y + slotSize / 3f);
            }else if (slot.getQuantity() <=4) {
                font.setColor(Color.ORANGE);
                font.draw(batch, "x" + slot.getQuantity(), x + slotSize + 20, y + slotSize / 3f);
            }else if (slot.getQuantity() ==9) {
                font.setColor(Color.YELLOW);
                font.draw(batch, "x" + slot.getQuantity(), x + slotSize + 20, y + slotSize / 3f);
            }else {
                font.setColor(Color.GREEN);
                font.draw(batch, "x" + slot.getQuantity(), x + slotSize + 20, y + slotSize / 3f);
            }font.setColor(Color.WHITE);

            font.getData().setScale(1.2f);
            font.draw(batch, "$" + sd.getPrice(), x + slotSize + 120, y + slotSize / 2f);

            font.getData().setScale(1f);

            if (mousePos.x >= x && mousePos.x <= x + slotSize &&
                mousePos.y >= y && mousePos.y <= y + slotSize) {
                if (Gdx.input.isButtonJustPressed(0)) {
                    assert AppClient.getUserData() != null;
                    GameMessage<GameCommand> msg = new GameMessage<>(
                        "game-command",
                        new GameCommand("purchase " + item.getName(),
                            AppClient.getUserData().getUsername())
                    );
                    AppClient.getClient().send(new Gson().toJson(msg));
                }
            }
        }

    }


    private int getItemPrice(Item item) {

        return 50;
    }
    private String readableName(String camelCase) {
        return camelCase.replaceAll("([a-z])([A-Z])", "$1 $2");
    }
    public void scrollDown() {
        startingRow++;

    }

    public void scrollUp() {
        if (startingRow > 0) startingRow--;


    }
    public void hideSoldOuts(){
        hideSoldOuts=!hideSoldOuts;
    }

}
