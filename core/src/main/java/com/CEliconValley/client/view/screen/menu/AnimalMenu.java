package com.CEliconValley.client.view.screen.menu;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.view.screen.GameScreen;
import com.CEliconValley.client.view.screen.VillageScreen;
import com.CEliconValley.common.messages.GameCommand;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.ui.CustomColors;
import com.CEliconValley.models.ui.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnimalMenu {
    private Texture menuTexture;
    private Texture infoTexture;
    private int startingRow=0;
    private final BitmapFont font = new BitmapFont();
    private final GameScreen screen;
    private OrthographicCamera camera;
    private Map<String, Integer> selectedQuantities = new HashMap<>();
    ArrayList<String> animalNames = new ArrayList<>();
    ArrayList<TextureRegion> animalTextures = new ArrayList<>();
    int index;

    public AnimalMenu(GameScreen screen) {
        animalNames.add("Sheep");
        animalNames.add("Cow");
        animalNames.add("Goat");
        animalNames.add("Pig");
        animalNames.add("Chicken");
            animalNames.add("Duck");
        animalNames.add("Rabbit");
        animalNames.add("Dinosaur");
        infoTexture = new Texture("game/Hero/NPC/Animals.png");
        int animalsCount = 8;
        int animalWidth = infoTexture.getWidth() / animalsCount;
        int animalHeight = infoTexture.getHeight();

        TextureRegion[][] split = TextureRegion.split(infoTexture, animalWidth, animalHeight);

        for (int i = 0; i < animalsCount; i++) {
            animalTextures.add(split[0][i]);
        }
        this.screen = screen;
        menuTexture = GameAssetManager.getGameAssetManager().getScreenTexture("CoopOrBarnMenu_Screen.png");
        infoTexture = GameAssetManager.getGameAssetManager().getBackgroundTexture("Info_Background1.png");

    }

    public void renderShopMenu(Batch batch, OrthographicCamera camera) {

        if(screen instanceof VillageScreen && ((VillageScreen) screen).isTypingAnimalName){
            if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && !screen.cheatCodeField.getText().trim().isEmpty()){
                assert AppClient.getUserData() != null;
                GameMessage<GameCommand> msg = new GameMessage<>(
                    "game-command",
                    new GameCommand("buy animal -a "+animalNames.get(index)+" -n "+screen.cheatCodeField.getText().trim(),
                        AppClient.getUserData().getUsername())
                );
                AppClient.getClient().send(new Gson().toJson(msg));
                screen.cheatCodeField.setText("");
                screen.cheatCodeField.setVisible(false);
                ((VillageScreen) screen).isTypingAnimalName = false;
            }
            return;
        }

        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        this.camera = camera;

        float menuWidth = screenWidth * 0.6f;
        float menuHeight = screenHeight * 0.7f;
        float startingX = camera.position.x - menuWidth / 2f;
        float startingY = camera.position.y - menuHeight / 2f;


            batch.draw(menuTexture, startingX, startingY, menuWidth, menuHeight);



        float firstItemX = startingX + menuWidth * 0.05f;
//        float firstItemX = screenWidth * 0.0370f;
        float firstItemY  = startingY + menuHeight*0.70f;

        float slotSize = screenWidth * 0.05f;
        float spacingY = slotSize * 0.6f;

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        int columns = 3;
        float spacingX = slotSize * 3.5f;

        for (int i = 0; i < 8; i++) {

            int maxQuantity = 100;//todo?
            int selected = selectedQuantities.getOrDefault(animalNames.get(i), 1);


            TextureRegion texture = animalTextures.get(i);
            if (texture == null) continue;

            int col = i % columns;
            int row = i / columns;
            if(row>=4) continue;
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
            String name = readableName(animalNames.get(i));
            font.draw(batch, name, x + slotSize + 20, y + slotSize / 1.5f);

//            font.getData().setScale(1.2f);
//            if (slot.getQuantity() ==0) {
//                font.setColor(Color.RED);
//                font.draw(batch, "SOLD OUT", x + slotSize + 20, y + slotSize / 3f);
//            }else if (slot.getQuantity() <=4) {
//                font.setColor(Color.ORANGE);
//                font.draw(batch, "x" + slot.getQuantity(), x + slotSize + 20, y + slotSize / 3f);
//            }else if (slot.getQuantity() ==9) {
//                font.setColor(Color.YELLOW);
//                font.draw(batch, "x" + slot.getQuantity(), x + slotSize + 20, y + slotSize / 3f);
//            }else {
//                font.setColor(Color.GREEN);
//                font.draw(batch, "x" + slot.getQuantity(), x + slotSize + 20, y + slotSize / 3f);
//            }font.setColor(Color.WHITE);

//            font.getData().setScale(1.2f);
//            font.draw(batch, "$" + sd.getPrice(), x + slotSize + 120, y + slotSize / 2f);

            font.getData().setScale(1f);
            if (isMouseOver( x + slotSize , y-10, 20, 20)) {
                if (Gdx.input.isButtonJustPressed(0) && selected > 0) {
                    selectedQuantities.put(animalNames.get(i), selected - 1);
                }
            }
            font.setColor(selected > 0 ? Color.WHITE : Color.GRAY);
            font.draw(batch,"-", x + slotSize + 10, y);
            font.setColor(Color.WHITE);
            font.draw(batch, String.valueOf(selected), x + slotSize + 35, y );
            if (isMouseOver( x + slotSize + 45, y-10, 20, 20)) {
                if (Gdx.input.isButtonJustPressed(0) && selected < maxQuantity) {
                    selectedQuantities.put(animalNames.get(i), selected + 1);
                }
            }
            font.setColor(selected < maxQuantity ? Color.WHITE : Color.GRAY);
            font.draw(batch,"+", x + slotSize + 55, y);
            font.setColor(Color.WHITE);

            if (mousePos.x >= x && mousePos.x <= x + slotSize &&
                mousePos.y >= y && mousePos.y <= y + slotSize) {

                font.getData().setScale(2f);
                font.setColor(CustomColors.GAMEGREENCOLOR);
                font.draw(batch,"Buy " + animalNames.get(i), drawX, drawY + slotSize);
                font.setColor(Color.WHITE);
                font.getData().setScale(1f);

                if (Gdx.input.isButtonJustPressed(0)) {
                    if(screen instanceof VillageScreen){
                        screen.cheatCodeField.setText("");
                        screen.cheatCodeField.setMessageText("Enter Animal Name");
                        ((VillageScreen) screen).isTypingAnimalName = true;
                        screen.cheatCodeField.setVisible(true);
                        index = i;
                    }
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

    public boolean isMouseOver(float x, float y, float width, float height) {
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);
        return mousePos.x >= x && mousePos.x <= x + width &&
            mousePos.y >= y && mousePos.y <= y + height;
    }

}
