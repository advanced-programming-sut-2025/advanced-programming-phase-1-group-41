package com.CEliconValley.client.view.screen;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.FriendshipData;
import com.CEliconValley.common.NPCData;
import com.CEliconValley.common.PlayerData;
import com.CEliconValley.common.messages.GameCommand;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.controllers.ItemManager;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.items.Food;
import com.CEliconValley.models.items.Inventory;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.tools.Tool;
import com.CEliconValley.models.ui.FakeCheckbox;
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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

public class FriendshipStageHandler {
    private Stage stage;
    private GameScreen screen;
    private final Texture backgroundTexture = new Texture(GameAssetManager.getGameAssetManager().CEliconValleyBackground);
    private final Image background = new Image(backgroundTexture);

    private final BitmapFont font = new BitmapFont();
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private float startingX;
    private float startingY;
    private int startingRow = 0;
    public boolean isGifting = false;

    // Navigation buttons
    private final TextButton chatTab, giftTab, hugTab, tradeTab, backTab;

    // Shared
    private final Label messageLabel;

    private final boolean isPlayer;
    private final PlayerData playerData;
    private final NPCData npcData;
    private FriendshipData friendshipData;
    private int friendShipLevel;

    // Register form
    public final TextField chatTextField;

    // Forgot Password form
    public final TextField forgotUsername, forgotAnswer, newPassword;
    public final Label securityQuestionLabel;
    public final TextButton forgotSubmitButton;

    // Security Question form
    public final ArrayList<TextButton> securityQuestions = new ArrayList<>();
    public final TextField securityAnswer;
    public final TextButton securitySubmitButton;

    // Layout
    private final Table mainTable;
    private final Stack formStack;
    public final Table chatForm, giftForm, hugForm, tradeForm;

    public FriendshipStageHandler(GameScreen screen, Stage stage, PlayerData playerData, NPCData npcData) {
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();

        this.stage = stage;
        this.screen = screen;

        OrthographicCamera camera = screen.camera;

        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;
        float menuWidth = screenWidth * 0.6f;
        float menuHeight = screenHeight * 0.7f;
        startingX = camera.position.x - menuWidth / 2f;
        startingY = camera.position.y - menuHeight / 2f;

        isPlayer = playerData != null;
        this.playerData = playerData;
        this.npcData = npcData;

        if(isPlayer){
            for(FriendshipData friendshipData1 : playerData.getFriendshipsData()){
                if(friendshipData1.getPlayer1Name().equals(Finder.getpd().getUsername())
                    || friendshipData1.getPlayer2Name().equals(Finder.getpd().getUsername())){
                    friendshipData = friendshipData1;
                    friendShipLevel = friendshipData1.getLevel();
                    break;
                }
            }
        } else{
            friendShipLevel = npcData.getFriendShipData().get(Objects.requireNonNull(Finder.getpd()).getUsername());
        }

        // --- Tabs
        chatTab = new TextButton("Chat", skin);
        giftTab = new TextButton("Gift", skin);
        hugTab = new TextButton("Hug", skin);
        tradeTab = new TextButton("Trade", skin);
        backTab = new TextButton("Back", skin);

        // --- Message
        messageLabel = new Label("", skin);
        messageLabel.setColor(Color.RED);
        messageLabel.setAlignment(Align.center);

        if(friendShipLevel <= 1){
            giftTab.getLabel().setColor(Color.RED);
            hugTab.getLabel().setColor(Color.RED);
        } else if(friendShipLevel == 2){
            hugTab.getLabel().setColor(Color.RED);
        }

        // --- Chat Fields
        chatTextField = new TextField("", skin);
        chatTextField.setMessageText("Type Something...");

        // --- Gift Fields

        // --- Forgot Fields
        forgotUsername = new TextField("", skin);
        forgotUsername.setMessageText("Username");

        securityQuestionLabel = new Label("Your Security Question", skin);
        forgotAnswer = new TextField("", skin);
        forgotAnswer.setMessageText("Answer");

        newPassword = new TextField("", skin);
        newPassword.setMessageText("New Password");

        forgotSubmitButton = new TextButton("Show Security Question", skin);

        // --- Security Fields
        securityAnswer = new TextField("", skin);
        securityAnswer.setMessageText("Security Question Answer");
        securityAnswer.setColor(Color.YELLOW);

        securitySubmitButton = new TextButton("Submit Question And Answer", skin);
        securitySubmitButton.setColor(Color.ORANGE);

        // --- Layouts
        mainTable = new Table();
        mainTable.setFillParent(true);

        formStack = new Stack();
        chatForm = new Table();
        giftForm = new Table();
        hugForm = new Table();
        tradeForm = new Table();

        Gdx.input.setInputProcessor(stage);
        stage.addActor(mainTable);
//        stage.addActor(background);

        buildUI();
    }

    private void buildUI() {
        // Tab Row
        Table tabRow = new Table();
        tabRow.add(chatTab).width(200).pad(10);
        tabRow.add(giftTab).width(200).pad(10);
        tabRow.add(hugTab).width(200).pad(10);
        tabRow.add(tradeTab).width(200).pad(10);
        tabRow.add(backTab).width(200).pad(10);

        chatForm.clear();
        chatForm.add(chatTextField).width(400).row();

        // Login Form Layout
        giftForm.clear();

        // Forgot Form Layout
        hugForm.clear();
        hugForm.add(securityQuestionLabel).padTop(10).row();
        hugForm.add(forgotUsername).width(500).row();
        hugForm.add(forgotAnswer).width(500).padTop(10).row();
        hugForm.add(newPassword).width(500).padTop(10).row();
        hugForm.add(forgotSubmitButton).width(500).padTop(20);

        // Security Form Layout
        tradeForm.clear();
        int i = 1;
        for(String securityQuestion : AppClient.questions){
            TextButton button = new TextButton(securityQuestion, GameAssetManager.getGameAssetManager().getSkin());
            securityQuestions.add(button);
            if(i++ % 2 == 0){
                tradeForm.add(button).width(800).padLeft(10).padTop(10).row();
            } else{
                tradeForm.add(button).width(800).padRight(10).padTop(10);
            }
        }
        tradeForm.add(securityAnswer).width(700).padTop(10);
        tradeForm.add(securitySubmitButton).width(700).padTop(10);

        formStack.clear();
        formStack.add(chatForm);
        formStack.add(giftForm);
        formStack.add(hugForm);
        formStack.add(tradeForm);

        mainTable.clear();
        mainTable.top();
        mainTable.add(tabRow).padTop(200).padBottom(340).row();
        mainTable.add(messageLabel).pad(10).row();
        mainTable.add(formStack).padTop(10);

        switchForm("gift");

        setupListeners();

    }

    public void switchForm(String formName) {
        chatForm.setVisible(formName.equals("chat"));
        giftForm.setVisible(formName.equals("gift"));
        hugForm.setVisible(formName.equals("hug"));
        tradeForm.setVisible(formName.equals("trade"));
        emptyFields();
    }

    private void setupListeners(){
        chatTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchForm("chat");
                setMessage("", Color.CLEAR);
            }
        });

        giftTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchForm("gift");
                setMessage("", Color.CLEAR);
                isGifting = true;
            }
        });

        hugTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchForm("hug");
                setMessage("", Color.CLEAR);
            }
        });

        tradeTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchForm("trade");
                setMessage("", Color.CLEAR);
            }
        });

        backTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.friendshipMode = false;
                Gdx.input.setInputProcessor(screen.stage);
            }
        });
    }

    public void setMessage(String message, Color color) {
        messageLabel.setText(message);
        messageLabel.setColor(color);
    }

    public void emptyFields() {
        forgotUsername.setText("");
        forgotAnswer.setText("");
        newPassword.setText("");
        chatTextField.setText("");
        isGifting = false;
    }

    public void renderInventoryBar(Batch batch, OrthographicCamera camera, Inventory inventory) {
        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        float firstItemX = screenWidth * 0.0370f;
        float firstItemY = screenHeight * 0.5225f;

        int row = 0, startPoint = 0;
        float slotSize = screenWidth * 0.035f;

        float spacingX = slotSize * 0.275f;
        float spacingY = slotSize * 0.525f;

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);
        batch.begin();

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
                            float textX = drawX + slotSize / 2f;
                            float textY = drawY + layout.height / 3f;
                            font.draw(batch, layout, textX, textY);
                            font.getData().setScale(1f);
                        }


                        if (mousePos.x >= x && mousePos.x <= x + slotSize &&
                            mousePos.y >= y && mousePos.y <= y + slotSize) {
                            if (Gdx.input.isButtonJustPressed(0)) {
                                if (item instanceof Tool) {
                                    assert AppClient.getUserData() != null;
                                    GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                                        new GameCommand("tools equip " + item.getName(),
                                            AppClient.getUserData().getUsername())
                                    );
                                    AppClient.getClient().send(new Gson().toJson(msg));
                                }else if(Food.parseFood(item.getName()) != null){
                                    assert AppClient.getUserData() != null;
                                    GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                                        new GameCommand("eat " + item.getName(),
                                            AppClient.getUserData().getUsername())
                                    );
                                    AppClient.getClient().send(new Gson().toJson(msg));
                                    screen.isMenuOpen = !screen.isMenuOpen;
                                    screen.onRepeat = false;
                                    screen.hero.currentAnimation = screen.hero.eat();
                                    screen.hero.isActing.set(true);
                                    screen.hero.stateTime = 0;
                                }
                            }
                            else if(Gdx.input.isButtonJustPressed(1)){
                                screen.hero.selectedItemName = item.getName();
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
        batch.end();
    }
    private String readableName(String camelCase) {
        return camelCase.replaceAll("([a-z])([A-Z])", "$1 $2");
    }

}
