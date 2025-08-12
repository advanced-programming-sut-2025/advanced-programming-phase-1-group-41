package com.CEliconValley.client.view.screen;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.FriendshipData;
import com.CEliconValley.common.NPCData;
import com.CEliconValley.common.PlayerData;
import com.CEliconValley.common.QuestData;
import com.CEliconValley.common.messages.GameCommand;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.common.messages.ResultSender;
import com.CEliconValley.controllers.ItemManager;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Gift;
import com.CEliconValley.models.PlayerMessage;
import com.CEliconValley.models.Result;
import com.CEliconValley.models.items.Food;
import com.CEliconValley.models.items.Inventory;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.tools.Tool;
import com.CEliconValley.models.ui.CustomColors;
import com.CEliconValley.models.ui.FakeCheckbox;
import com.CEliconValley.models.ui.GameAssetManager;
import com.CEliconValley.models.ui.InventoryBarActor;
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
import com.badlogic.gdx.scenes.scene2d.Touchable;
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

    private final BitmapFont font = new BitmapFont();
    private int startingRow = 0;
    public boolean isGifting = false, isChatting = false;
    private final Image avatarImage;
    private final Label nameLabel;

    // Navigation buttons
    private final TextButton chatTab, giftTab, hugOrQuestTab, tradeTab, backTab;

    // Shared
    private final Label messageLabel;

    public final boolean isPlayer;
    public final PlayerData playerData;
    public final NPCData npcData;
    private FriendshipData friendshipData;
    private int friendShipLevel;
    private ArrayList<QuestData> questsData;

    // Chat form
    public final TextField chatTextField;
    public Table chatTable;
    private ScrollPane chatScrollPane;

    // Gift form
    public final InventoryBarActor invActor;

    // Quest form
    public final ArrayList<TextButton> securityQuestions = new ArrayList<>();
    public final TextField securityAnswer;
    public final TextButton securitySubmitButton;

    // Layout
    private final Table mainTable;
    private final Stack formStack;
    public final Table chatForm, giftForm, hugOrQuestForm, tradeForm;

    public FriendshipStageHandler(GameScreen screen, Stage stage, PlayerData playerData, NPCData npcData) {
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();

        stage.clear();
        Gdx.input.setInputProcessor(stage);
        this.stage = stage;
        this.screen = screen;

        OrthographicCamera camera = screen.camera;

        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

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
            avatarImage = new Image(new Texture(Gdx.files.internal(playerData.getAvatarPath())));
            nameLabel = new Label(playerData.getUsername(), skin);
            nameLabel.setColor(CustomColors.SWAMP_COLOR);
        } else{
            friendShipLevel = npcData.getFriendShipData().get(Objects.requireNonNull(Finder.getpd()).getUsername()) / 200;
            avatarImage = new Image(GameAssetManager.getGameAssetManager().getNPCImage(npcData.getName(), friendShipLevel));
            nameLabel = new Label(npcData.getName(), skin);
            nameLabel.setColor(CustomColors.SWAMP_COLOR);
            questsData = npcData.getQuestsdata();
        }

        // --- Tabs
        chatTab = new TextButton("Chat", skin);
        giftTab = new TextButton("Gift", skin);
        if(isPlayer){
            hugOrQuestTab = new TextButton("Hug", skin);
        } else{
            hugOrQuestTab = new TextButton("Quest", skin);
        }
        tradeTab = new TextButton("Trade", skin);
        backTab = new TextButton("Back", skin);

        // --- Message
        messageLabel = new Label("", skin);
        messageLabel.setColor(Color.RED);
        messageLabel.setAlignment(Align.center);

        if(friendShipLevel <= 1){
            giftTab.getLabel().setColor(Color.RED);
            hugOrQuestTab.getLabel().setColor(Color.RED);
        } else if(friendShipLevel == 2){
            hugOrQuestTab.getLabel().setColor(Color.RED);
        }

        // --- Chat Fields
        chatTextField = new TextField("", skin);
        chatTextField.setMessageText("Type Something...");
        chatTextField.setWidth(200);

        chatScrollPane = new ScrollPane(chatTable, GameAssetManager.getGameAssetManager().getSkin(), "hiddenScroll");
        chatScrollPane.setFadeScrollBars(false);
        chatScrollPane.setScrollingDisabled(true, false);

        chatScrollPane.setActor(chatTable);
        Gdx.app.postRunnable(() -> {
            chatScrollPane.validate();
            chatScrollPane.setScrollPercentY(1f);
        });


        // --- Gift Fields
        invActor = new InventoryBarActor(this, camera, Finder.getpd().getInventoryData().getInventory(), font);
        invActor.setVisible(false);

        // --- Quest Fields
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
        hugOrQuestForm = new Table();
        tradeForm = new Table();


        Gdx.input.setInputProcessor(stage);
        stage.addActor(invActor);
        stage.addActor(mainTable);
        stage.addActor(avatarImage);

        avatarImage.setPosition(screenWidth * 0.15f, screenHeight * 0.75f);

        buildUI();
    }

    public void updateChat(){
        chatTable.clear();
        chatTable = new Table();
        if(AppClient.getGameData() == null ) return;
        //TODO Players Chat update
        if(isPlayer){
            FriendshipData fsd = null;
            String playername = Finder.getpd().getUsername();

            for(FriendshipData friendshipData1 : Finder.getpd().getFriendshipsData()){
                if(friendshipData1.getPlayer1Name().equals(playerData.getUsername())
                    || friendshipData1.getPlayer2Name().equals(playerData.getUsername())){
                    fsd = friendshipData1;
                    break;
                }
            }
            if(fsd == null) return;
            for(ArrayList<String> message : fsd.getTalks()){
                assert AppClient.getUserData() != null;
                if(message.get(0).equals(AppClient.getUserData().getUsername())){
                    Label label = new Label(message.get(1) + "-", GameAssetManager.getGameAssetManager().getSkin());
                    label.setWrap(true);
                    label.setAlignment(Align.right);
                    label.setColor(CustomColors.GAMEGREENCOLOR);
                    chatTable.add(label).width(380).right().padBottom(5).row();
                } else{
                    Label label = new Label(message.get(0) + ": " + message.get(1), GameAssetManager.getGameAssetManager().getSkin());
                    label.setAlignment(Align.left);
                    if(label.getText().toString().matches(".+@"+AppClient.getUserData().getUsername()+".+")){
                        label.setColor(Color.BLUE);
                    }
                    chatTable.add(label).width(380).left().padBottom(5).row();
                }
            }
        }
        chatTable.row();
        chatTable.row().row();
        chatTable.padBottom(20);
        chatScrollPane.setActor(chatTable);
        chatScrollPane.setFadeScrollBars(false);
        chatScrollPane.setScrollingDisabled(true, false);
        Gdx.app.postRunnable(() -> {
            chatScrollPane.validate();
            chatScrollPane.setScrollPercentY(1f);
        });
    }

    private void setupChatUI(){
        chatTable = new Table();
        chatTable.setFillParent(true);
        //TODO Players Chat
        if(isPlayer){
            FriendshipData fsd = null;
            String playername = playerData.getUsername();
            System.out.println("playername is "+playername);
            for (FriendshipData friendshipsDatum : Finder.getpd().getFriendshipsData()) {
                System.out.println("checking for "+friendshipsDatum.getPlayer1Name());
                if(friendshipsDatum.getPlayer1Name().equals(playername) || friendshipsDatum.getPlayer2Name().equals(playername)){
                    fsd = friendshipsDatum;
                    break;
                }
            }
            if(fsd == null) return;
            for(ArrayList<String> message : fsd.getTalks()){
                assert AppClient.getUserData() != null;
                if(message.get(0).equals(AppClient.getUserData().getUsername())){
                    Label label = new Label(message.get(1) + "-", GameAssetManager.getGameAssetManager().getSkin());
                    label.setWrap(true);
                    label.setAlignment(Align.right);
                    label.setColor(CustomColors.GAMEGREENCOLOR);
                    chatTable.add(label).width(380).right().padBottom(5).row();
                } else{
                    Label label = new Label(message.get(0) + ": " + message.get(1), GameAssetManager.getGameAssetManager().getSkin());
                    label.setAlignment(Align.left);
                    if(label.getText().toString().matches(".+@"+AppClient.getUserData().getUsername()+".+")){
                        label.setColor(Color.BLUE);
                    }
                    chatTable.add(label).width(380).left().padBottom(5).row();
                }
            }
        }
        chatScrollPane = new ScrollPane(chatTable, GameAssetManager.getGameAssetManager().getSkin(), "hiddenScroll");
        chatScrollPane.setFadeScrollBars(false);
        chatScrollPane.setScrollingDisabled(true, false);

        Table container = new Table();
        container.setFillParent(true);
        container.bottom().left().pad(10);
        container.add(chatScrollPane).width(400).height(325).row();
        container.add(chatTextField).width(400).height(60);

        container.setPosition(stage.getWidth() / 3.5f, stage.getHeight() / 4.25f);
        chatForm.addActor(container);
    }

    private void buildUI() {
        // Tab Row
        Table tabRow = new Table();
        tabRow.add(chatTab).width(200).pad(10);
        tabRow.add(giftTab).width(200).pad(10);
        tabRow.add(hugOrQuestTab).width(200).pad(10);
        tabRow.add(tradeTab).width(200).pad(10);
        tabRow.add(backTab).width(200).pad(10);

        chatForm.clear();
        setupChatUI();

        // Login Form Layout
        giftForm.clear();

        // Forgot Form Layout
        hugOrQuestForm.clear();

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
        formStack.add(hugOrQuestForm);
        formStack.add(tradeForm);

        mainTable.clear();
        mainTable.top();
        mainTable.add(tabRow).padTop(200).row();
        mainTable.add(messageLabel).pad(10).padBottom(200).row();
        mainTable.add(formStack).padTop(10);

        switchForm("gift");

        setupListeners();

    }

    public void switchForm(String formName) {
        chatForm.setVisible(formName.equals("chat"));
        giftForm.setVisible(formName.equals("gift"));
        hugOrQuestForm.setVisible(formName.equals("hugOrQuest"));
        tradeForm.setVisible(formName.equals("trade"));
        emptyFields();
    }

    private void setupListeners(){
        chatTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchForm("chat");
                isChatting = true;
                if(isPlayer){
                    for (FriendshipData fsd : Finder.getpd().getFriendshipsData()) {
                        System.out.println(fsd.getFriendshipXp()+" "+fsd.getPlayer1Name()+" "+fsd.getPlayer2Name());
                    }
                }
            }
        });

        giftTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(friendShipLevel <= 1 && isPlayer){
                    setMessage("Your friendship level should be at least 2 to gift.", Color.RED);
                    return;
                }
                switchForm("gift");
                isGifting = true;
                Gdx.app.postRunnable(() -> {
                    invActor.setVisible(true);
                    invActor.setTouchable(Touchable.enabled);
                    stage.setKeyboardFocus(invActor);
                    invActor.setVisible(true);
                    invActor.setTouchable(Touchable.enabled);
                    invActor.toFront(); // Ensure it's drawn above other actors
                    screen.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                    screen.camera.update();
                    stage.setKeyboardFocus(invActor);
                    stage.setScrollFocus(invActor);
                });


                stage.act(Gdx.graphics.getDeltaTime());
                stage.draw();
                Gdx.input.setInputProcessor(stage);


            }
        });

        hugOrQuestTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(friendShipLevel <= 2 && isPlayer){
                    setMessage("Your friendship level should be at least 3 to hug.", Color.RED);
                    return;
                }
                switchForm("hugOrQuest");
                //TODO hugOrQuest
            }
        });

        tradeTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchForm("trade");
            }
        });

        backTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.friendshipMode = false;
                Gdx.input.setInputProcessor(screen.stage);
                emptyFields();
            }
        });
    }

    public void setMessage(String message, Color color) {
        messageLabel.setText(message);
        messageLabel.setColor(color);
    }

    public void setMessage(ResultSender result) {
        messageLabel.setText(result.message);
        if(result.code){
            messageLabel.setColor(Color.GREEN);
        } else{
            messageLabel.setColor(Color.RED);
        }
    }

    public void emptyFields() {
        chatTextField.setText("");
        setMessage("", Color.CLEAR);
        invActor.setVisible(false);
        isGifting = false;
        isChatting = false;
    }
}
