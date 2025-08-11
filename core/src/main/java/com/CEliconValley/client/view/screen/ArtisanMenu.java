package com.CEliconValley.client.view.screen;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.messages.GameCommand;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.controllers.ItemManager;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.foragings.FruitType;
import com.CEliconValley.models.items.*;
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
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ArtisanMenu {
//    private final Texture menuTexture;
    private final Texture artisanTexture;
    private final Texture barTexture;
    private final Texture inventoryTexture;
    private final TextureRegion[] artisanElementsTexture;
    final float DESIGN_WIDTH = 142f;
    final float DESIGN_HEIGHT = 80f;
    private Slot produce;

    private boolean startFill = false;
    private float fillTimer = 0f;
    private float fillDuration = 30f;


    private final float texX1 = 61, texY1 = 38;
    private final float texX2 = 76, texY2 = 50;


    boolean[] buttonActive;
    float[][] buttonBounds=new float[][]{
        {46, 59, 54, 70},
        {113, 17, 123, 26},
        {42, 13, 56, 28}
    };;
    float[] neededItemBound=new float[]{
        60,13,98,26
    };

//    private final int tileWidth;
//    private final int tileHeight;



    private int startingRow = 0;
    private int selectedIndex = 0;
    private final int visibleAnimalsCount = 4;

    private Player player;

    BitmapFont font = new BitmapFont();
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    private float startingX;
    private float startingY;
    ArrayList<Slot> foodsData = null;
    private OrthographicCamera camera;

    private GameScreen screen;

    public ArtisanMenu(GameScreen screen) {
        this.screen = screen;
        artisanTexture = GameAssetManager.getGameAssetManager().getScreenTexture("Artisan_Background.png");
        barTexture = GameAssetManager.getGameAssetManager().getScreenTexture("BarTexture.png");
        inventoryTexture = GameAssetManager.getGameAssetManager().getScreenTexture("ArtisanInventory.png");

        TextureRegion[][] initialize = TextureRegion.split(artisanTexture,artisanTexture.getWidth(),artisanTexture.getHeight()/9);
        artisanElementsTexture = new TextureRegion[9];
        artisanElementsTexture[0]=initialize[0][0];
        artisanElementsTexture[1]=initialize[1][0];
        artisanElementsTexture[2]=initialize[2][0];
        artisanElementsTexture[3]=initialize[3][0];
        artisanElementsTexture[4]=initialize[4][0];
        artisanElementsTexture[5]=initialize[5][0];
        artisanElementsTexture[6]=initialize[6][0];
        artisanElementsTexture[7]=initialize[7][0];
        artisanElementsTexture[8]=initialize[8][0];



         buttonActive = new boolean[3];

    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void render(Batch batch, OrthographicCamera camera, CraftableMachine craftableMachine) {
        HashMap<Item, Integer> neededItems = craftableMachine.getRecipe().getNeededItems() ;

        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;


        float menuWidth = screenWidth * 0.6f;
        float menuHeight = screenHeight * 0.5f;
        startingX = camera.position.x - menuWidth / 2f;
        startingY = camera.position.y;

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);


        batch.draw(artisanElementsTexture[0], startingX, startingY, menuWidth, menuHeight);



         float realN1 = startingX + (neededItemBound[0]/DESIGN_WIDTH)*menuWidth;
         float realM1 =startingY + (neededItemBound[1] / DESIGN_HEIGHT) * menuHeight;
         float realN2 =  startingX + (neededItemBound[2]/DESIGN_WIDTH)*menuWidth;
         float realM2 = startingY + (neededItemBound[3]/DESIGN_HEIGHT)*menuHeight;
         float itemSize=realN2-realN1/neededItems.size();
         AtomicInteger counter = new AtomicInteger();
         neededItems.forEach((item,quantity)->{
             batch.draw(ItemManager.getTexture(item),realN1+ (counter.getAndIncrement()) *itemSize,realM1,itemSize,itemSize);
         });
        for (int i = 0; i < buttonBounds.length; i++) {

            float realX1 = startingX + (buttonBounds[i][0] / DESIGN_WIDTH) * menuWidth;
            float realY1 = startingY + (buttonBounds[i][1] / DESIGN_HEIGHT) * menuHeight;
            float realX2 = startingX + (buttonBounds[i][2] / DESIGN_WIDTH) * menuWidth;
            float realY2 = startingY + (buttonBounds[i][3] / DESIGN_HEIGHT) * menuHeight;


            boolean isHover = mousePos.x >= realX1 && mousePos.x <= realX2 &&
                mousePos.y >= realY1 && mousePos.y <= realY2;




            if (isHover && Gdx.input.isButtonJustPressed(0)) {
                // TODO idk what
                buttonActive[i] = !buttonActive[i];
                System.out.println("Clicked artisan button " + (i + 1));
            }
            int texIndex = buttonActive[i] ? (2 + i * 2) : (1 + i * 2);
            batch.draw(artisanElementsTexture[texIndex], startingX, startingY, menuWidth, menuHeight);
            batch.draw(artisanElementsTexture[7],startingX, startingY, menuWidth, menuHeight);
            if (buttonActive[2] && !startFill) {
                startFill = true;
                fillTimer = 0f;
            }

            if (startFill) {
                fillTimer += Gdx.graphics.getDeltaTime();
                float progress = Math.min(fillTimer / fillDuration, 1f);


                 realX1 = startingX + (texX1 / DESIGN_WIDTH) * menuWidth;
                 realY1 = startingY + (texY1 / DESIGN_HEIGHT) * menuHeight;
                 realX2 = startingX + (texX2 / DESIGN_WIDTH) * menuWidth;
                realY2 = startingY + (texY2 / DESIGN_HEIGHT) * menuHeight;

                float barWidth = realX2 - realX1;
                float barHeight = realY2 - realY1;


                int texDrawWidth = (int)(barTexture.getWidth() * progress);


                TextureRegion filledRegion = new TextureRegion(
                    barTexture,
                    barTexture.getWidth() - texDrawWidth,
                    0,
                    texDrawWidth,
                    barTexture.getHeight()
                );


                float drawX = realX2 - (barWidth * progress);

                batch.draw(filledRegion, drawX, realY1, barWidth * progress, barHeight);

                if (progress >= 1f) {
                    startFill = false;
                }
            }



        }
        renderInventoryBar(batch, camera, player.getInventory());
    }



    private void renderInventoryBar(Batch batch, OrthographicCamera camera, Inventory inventory) {
        float menuWidth = camera.viewportWidth * 0.6f;
        float menuHeight = camera.viewportHeight * 0.35f;
        startingY=startingY-menuHeight*1.25f;
        batch.draw(inventoryTexture,startingX,startingY,menuWidth,menuHeight);
        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        float firstItemX = screenWidth * 0.03f;
        float firstItemY = screenHeight * 0.23f;

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
                                // TODO click on inventory
//                                if(isFood(item)) {
//                                    GameMessage<GameCommand> msg = new GameMessage<>("game-command",
//                                        new GameCommand("cooking refrigerator put " + item.getName(),
//                                            AppClient.getUserData().getUsername())
//                                    );
//                                    AppClient.getClient().send(new Gson().toJson(msg));
//                                }
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
        artisanTexture.dispose();
    }

    public void scrollDown() {
        if (selectedIndex > 0) selectedIndex--;
    }

    public void scrollUp() {
        if (selectedIndex < foodsData.size() - visibleAnimalsCount)
            selectedIndex++;
    }

    public boolean isFood(Item item){
        Food food = Food.parseFood(item.getName());
        FruitType fruitType = FruitType.parseFruitType(item.getName());
        if(item instanceof CraftableItem ci){
            if(!ci.isEatable()){
                return false;
            }
        }else if(food == null && fruitType == null && !(item instanceof Eatable)){
            return false;
        }
        if(food != null){
            return true;
        }else if(fruitType != null){
            return true;
        }else if(item instanceof CraftableItem ci){
            return true;
        }
        return false;
    }
}
