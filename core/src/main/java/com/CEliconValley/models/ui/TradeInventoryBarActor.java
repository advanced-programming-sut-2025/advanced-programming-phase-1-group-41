package com.CEliconValley.models.ui;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.view.screen.FriendshipStageHandler;
import com.CEliconValley.common.PlayerData;
import com.CEliconValley.common.TradeData;
import com.CEliconValley.common.messages.GameCommand;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.controllers.ItemManager;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Trade;
import com.CEliconValley.models.items.Inventory;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.google.gson.Gson;

public class TradeInventoryBarActor extends Actor {

    private OrthographicCamera camera;
    private Inventory inventory;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;
    private final Texture menuTexture;
    private final Texture giftTexture;
    private final FriendshipStageHandler friendshipStageHandler;

    private float startingX = 0;
    private float startingY = 0;
    private int startingRow = 0;

    public TradeInventoryBarActor(FriendshipStageHandler friendshipStageHandler, OrthographicCamera camera, Inventory inventory, BitmapFont font) {
        this.friendshipStageHandler = friendshipStageHandler;
        this.camera = camera;
        this.inventory = inventory;
        this.font = font;
        this.shapeRenderer = new ShapeRenderer();
        menuTexture = GameAssetManager.getGameAssetManager().getScreenTexture("ShippingBin.png");
        giftTexture = GameAssetManager.getGameAssetManager().getInventoryTexture("skills/Ruby.png");
        setTouchable(Touchable.enabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        toFront();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        inventory = Finder.getpd().getInventoryData().getInventory();
        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        float menuWidth = screenWidth * 0.6f;
        float menuHeight = screenHeight * 0.35f;

        startingX = menuWidth / 3f;
        startingY = menuHeight / 1.5f;

        float firstItemX = screenWidth * 0.03f;
        float firstItemY = screenHeight * 0.23f;

        float historyX = startingY - screenWidth / 5f;
        float historyY = firstItemY;

        int row = 0;
        float slotSize = screenWidth * 0.035f;

        float spacingX = slotSize * 0.31f;
        float spacingY = slotSize * 0.65f;

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        batch.draw(menuTexture, startingX, startingY, menuWidth, menuHeight);

        font.draw(batch, "Trade History:", historyX, historyY);

        historyY += spacingY;

        PlayerData mainPlayerData = Finder.getpd();
        for(TradeData tradeData : friendshipStageHandler.playerData.getTotalTradesListData()){
            if(tradeData.getFromName().equals(mainPlayerData.getUsername())){
                Trade trade = tradeData.getTrade(mainPlayerData.getPlayer(), friendshipStageHandler.playerData.getPlayer());
                font.setColor(CustomColors.SWAMP_COLOR);
                String done = " (Done)";
                if(trade.isRejected()){
                    done = " (Rejected)";
                }
                if(trade.isPaidInMoney()){
                    if(trade.isRequest()){
                        font.draw(batch, trade.getItem().getItem().getName() + " for " + trade.getPrice() +"$" + done, historyX, historyY);
                    } else{
                        font.draw(batch, trade.getPrice() + "$ for " + trade.getItem().getItem().getName() + done, historyX, historyY);
                    }
                }
            } else if(tradeData.getToName().equals(mainPlayerData.getUsername())){
                Trade trade = tradeData.getTrade(friendshipStageHandler.playerData.getPlayer(), mainPlayerData.getPlayer());
                font.setColor(CustomColors.JUNGLE_COLOR);
                String done = " (Done)";
                if(trade.isRejected()){
                    done = " (Rejected)";
                }
                if(trade.isPaidInMoney()){
                    if(trade.isRequest()){
                        font.draw(batch, trade.getItem().getItem().getName() + " for " + trade.getPrice() +"$" + done, historyX, historyY);
                    } else{
                        font.draw(batch, trade.getPrice() + "$ for " + trade.getItem().getItem().getName() + done, historyX, historyY);
                    }
                }
            }
        }

        for (int col = 0; row < 3; ) {
            int index = col + (startingRow + row) * 12;
            if (inventory.getBackpack().getSize() > index) {
                Slot slot = inventory.getSlots().get(index);
                Item item = slot.getItem();
                if (item != null) {
                    TextureRegion texture = ItemManager.getTexture(item);
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

                        if (slot.getQuantity() > 1) {
                            font.getData().setScale(2.5f);
                            String amountText = String.valueOf(slot.getQuantity());
                            GlyphLayout layout = new GlyphLayout(font, amountText);
                            float textX = drawX + slotSize / 2f;
                            float textY = drawY + layout.height / 3f;
                            font.draw(batch, layout, textX, textY);
                            font.getData().setScale(1f);
                        }

                        boolean clicked = false;
                        if (mousePos.x >= x && mousePos.x <= x + slotSize &&
                            mousePos.y >= y && mousePos.y <= y + slotSize) {
                            drawTooltip(batch, x, y, slotSize, "Trade Request: " + readableName(item.getName()) + " for " + item.getPrice());
                            if (Gdx.input.isButtonJustPressed(0)) {
                                String name;
                                if(friendshipStageHandler.isPlayer){
                                    name = friendshipStageHandler.playerData.getUsername();
                                    GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                                        new GameCommand("trade -u "+name+" -t offer -i "+item.getName()+" -a 1 -p " + (int)item.getPrice(),
                                            AppClient.getUserData().getUsername()));
                                    AppClient.getClient().send(new Gson().toJson(msg));
                                }
                            }else if (Gdx.input.isButtonJustPressed(1)) {
                                String name;
                                if(friendshipStageHandler.isPlayer){
                                    name = friendshipStageHandler.playerData.getUsername();
                                    GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                                        new GameCommand("trade -u "+name+" -t request -i "+item.getName()+" -a 1 -p " + (int)item.getPrice(),
                                            AppClient.getUserData().getUsername()));
                                    AppClient.getClient().send(new Gson().toJson(msg));
                                }
                            }
                        }
                    }
                }
            }

            col++;
            if (col == 12) {
                col = 0;
                row++;
            }
        }
    }

    private void drawTooltip(Batch batch, float x, float y, float slotSize, String text) {
        batch.end();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        GlyphLayout layout = new GlyphLayout(font, text);
        float tooltipWidth = layout.width + 20;
        float tooltipHeight = layout.height + 10;

        float tooltipX = x + slotSize / 2f - tooltipWidth / 2f;
        float tooltipY = y + slotSize + 10;

        shapeRenderer.setColor(0, 0, 0, 0.8f);
        shapeRenderer.rect(tooltipX, tooltipY, tooltipWidth, tooltipHeight);
        shapeRenderer.end();

        batch.begin();
        font.draw(batch, layout, tooltipX + 10, tooltipY + tooltipHeight - 5);
        batch.draw(giftTexture, tooltipX + 10, tooltipY + tooltipHeight + layout.height);
    }

    private String readableName(String camelCase) {
        return camelCase.replaceAll("([a-z])([A-Z])", "$1 $2");
    }
}
