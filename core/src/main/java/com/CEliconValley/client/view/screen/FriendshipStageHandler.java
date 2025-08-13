package com.CEliconValley.client.view.screen;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.*;
import com.CEliconValley.common.messages.GameCommand;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.common.messages.Messagenpc;
import com.CEliconValley.common.messages.ResultSender;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.ui.CustomColors;
import com.CEliconValley.models.ui.GameAssetManager;
import com.CEliconValley.models.ui.InventoryBarActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
    private final Texture questTexture;
    private final Label nameLabel;

    // Navigation buttons
    private final TextButton chatTab, giftTab, proposeOrQuestTab, tradeTab, backTab;

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
    public final Table questsTable;

    // Layout
    private final Table mainTable;
    public final Table chatForm, giftForm, proposeOrQuestForm, tradeForm;

    public FriendshipStageHandler(GameScreen screen, Stage stage, PlayerData playerData, NPCData npcData) {
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();

        questTexture = GameAssetManager.getGameAssetManager().getBackgroundTexture("Quest_Background.png");

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

        if (isPlayer) {
            for (FriendshipData friendshipData1 : playerData.getFriendshipsData()) {
                if (friendshipData1.getPlayer1Name().equals(Finder.getpd().getUsername())
                    || friendshipData1.getPlayer2Name().equals(Finder.getpd().getUsername())) {
                    friendshipData = friendshipData1;
                    friendShipLevel = friendshipData1.getLevel();
                    break;
                }
            }
            avatarImage = new Image(new Texture(Gdx.files.internal(playerData.getAvatarPath())));
            avatarImage.setSize(screenWidth / 20f, screenHeight / 20f);
            nameLabel = new Label(playerData.getUsername(), skin);
            nameLabel.setColor(CustomColors.SWAMP_COLOR);
        } else {
            friendShipLevel = npcData.getFriendShipData().get(Objects.requireNonNull(Finder.getpd()).getUsername()) / 200;
            avatarImage = new Image(GameAssetManager.getGameAssetManager().getNPCImage(npcData.getName(), friendShipLevel));
            avatarImage.setSize(screenHeight / 20f, screenHeight / 20f);
            nameLabel = new Label(npcData.getName(), skin);
            nameLabel.setColor(CustomColors.SWAMP_COLOR);
            questsData = npcData.getQuestsdata();
        }

        // --- Tabs
        chatTab = new TextButton("Chat", skin);
        giftTab = new TextButton("Gift", skin);
        if (isPlayer && friendShipLevel < 3) {
            proposeOrQuestTab = new TextButton("Give Flower", skin);
            if(friendShipLevel < 2 || friendshipData.getFriendshipXp() < 300){
                proposeOrQuestTab.getLabel().setColor(Color.RED);
            }
        } else if (isPlayer && friendShipLevel == 3) {
            proposeOrQuestTab = new TextButton("Propose", skin);
            if(friendshipData.getFriendshipXp() < 400){
                proposeOrQuestTab.getLabel().setColor(Color.RED);
            }
        } else if (isPlayer) {
            proposeOrQuestTab = new TextButton("Go To Farm", skin);
        } else {
            proposeOrQuestTab = new TextButton("Quest", skin);
        }
        tradeTab = new TextButton("Trade", skin);
        backTab = new TextButton("Back", skin);

        // --- Message
        messageLabel = new Label("", skin);
        messageLabel.setColor(Color.RED);
        messageLabel.setAlignment(Align.center);

        if (friendShipLevel <= 1 && isPlayer) {
            giftTab.getLabel().setColor(Color.RED);
            proposeOrQuestTab.getLabel().setColor(Color.RED);
        } else if (friendShipLevel == 2 && isPlayer) {
            proposeOrQuestTab.getLabel().setColor(Color.RED);
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
        questsTable = new Table();
        questsTable.setFillParent(true);

        // --- Layouts
        mainTable = new Table();
        mainTable.setFillParent(true);

        chatForm = new Table();
        chatForm.setFillParent(true);
        giftForm = new Table();
        giftForm.setFillParent(true);
        proposeOrQuestForm = new Table();
        proposeOrQuestForm.setFillParent(true);
        tradeForm = new Table();
        tradeForm.setFillParent(true);


        Gdx.input.setInputProcessor(stage);
        stage.addActor(invActor);
        stage.addActor(mainTable);
        stage.addActor(chatForm);
        stage.addActor(giftForm);
        stage.addActor(proposeOrQuestForm);
        stage.addActor(tradeForm);
        stage.addActor(avatarImage);

        avatarImage.setPosition(screenWidth * 0.15f, screenHeight * 0.75f);

        buildUI();
    }

    public void updateChat() {
        chatTable.clearChildren(); // Keeps the same instance
        if (AppClient.getGameData() == null) return;
        if (isPlayer) {
            FriendshipData fsd = null;

            for (FriendshipData friendshipData1 : Finder.getpd().getFriendshipsData()) {
                if (friendshipData1.getPlayer1Name().equals(playerData.getUsername())
                    || friendshipData1.getPlayer2Name().equals(playerData.getUsername())) {
                    fsd = friendshipData1;
                    break;
                }
            }
            if (fsd == null) return;
            for (ArrayList<String> message : fsd.getTalks()) {
                assert AppClient.getUserData() != null;
                if (message.get(0).equals(AppClient.getUserData().getUsername())) {
                    Label label = new Label(message.get(1) + "-", GameAssetManager.getGameAssetManager().getSkin());
                    label.setWrap(true);
                    label.setAlignment(Align.right);
                    label.setColor(CustomColors.GAMEGREENCOLOR);
                    label.pack();
                    chatTable.add(label).width(380).right().padBottom(5).row();
                } else {
                    Label label = new Label(message.get(0) + ": " + message.get(1), GameAssetManager.getGameAssetManager().getSkin());
                    label.setAlignment(Align.left);
                    label.setWrap(true);
                    label.pack();
                    if (label.getText().toString().matches(".+@" + AppClient.getUserData().getUsername() + ".+")) {
                        label.setColor(Color.BLUE);
                    }
                    chatTable.add(label).width(380).left().padBottom(5).row();
                }
            }
        } else {
            NPCData npc = Finder.getnpcdatabyname(npcData.getName());
            String playername = Finder.getpd().getUsername();
            Talk talk = npc.getTalkByName(playername);
            for (Messagenpc t : talk.getTalks()) {
                if (!t.isNPC) {
                    Label label = new Label(t.message + "-", GameAssetManager.getGameAssetManager().getSkin());
                    label.setWrap(true);
                    label.setAlignment(Align.right);
                    label.setColor(CustomColors.GAMEGREENCOLOR);
                    label.pack();
                    chatTable.add(label).width(380).right().padBottom(5).row();
                } else {
                    Label label = new Label(npc.getName() + ": " + t.message, GameAssetManager.getGameAssetManager().getSkin());
                    label.setAlignment(Align.left);
                    label.setWrap(true);
                    if (label.getText().toString().matches(".+@" + AppClient.getUserData().getUsername() + ".+")) {
                        label.setColor(Color.BLUE);
                    }
                    label.pack();
                    chatTable.add(label).width(380).left().padBottom(5).row();
                }
            }
        }
        chatTable.row();
        chatTable.row().row();
        chatTable.padBottom(20);
        chatTable.invalidateHierarchy(); // Recalculate layout tree
        chatScrollPane.setActor(chatTable);
        chatScrollPane.setFadeScrollBars(false);
        chatScrollPane.setScrollingDisabled(true, false);
        Gdx.app.postRunnable(() -> {
            chatScrollPane.layout(); // Ensure layout is recalculated
            chatScrollPane.setScrollPercentY(1f);

        });
    }

    private void setupChatUI() {
        chatTable = new Table();
        if (isPlayer) {
            FriendshipData fsd = null;
            String playername = playerData.getUsername();
            System.out.println("playername is " + playername);
            for (FriendshipData friendshipsDatum : Finder.getpd().getFriendshipsData()) {
                System.out.println("checking for " + friendshipsDatum.getPlayer1Name());
                if (friendshipsDatum.getPlayer1Name().equals(playername) || friendshipsDatum.getPlayer2Name().equals(playername)) {
                    fsd = friendshipsDatum;
                    break;
                }
            }
            if (fsd == null) return;
            for (ArrayList<String> message : fsd.getTalks()) {
                assert AppClient.getUserData() != null;
                if (message.get(0).equals(AppClient.getUserData().getUsername())) {
                    Label label = new Label(message.get(1) + "-", GameAssetManager.getGameAssetManager().getSkin());
                    label.setWrap(true);
                    label.setAlignment(Align.right);
                    label.setColor(CustomColors.GAMEGREENCOLOR);
                    chatTable.add(label).width(380).right().padBottom(5).row();
                } else {
                    Label label = new Label(message.get(0) + ": " + message.get(1), GameAssetManager.getGameAssetManager().getSkin());
                    label.setAlignment(Align.left);
                    label.setWrap(true);
                    if (label.getText().toString().matches(".+@" + AppClient.getUserData().getUsername() + ".+")) {
                        label.setColor(Color.BLUE);
                    }
                    chatTable.add(label).width(380).left().padBottom(5).row();
                }
            }
        } else {
            NPCData npc = Finder.getnpcdatabyname(npcData.getName());
            String playername = Finder.getpd().getUsername();
            Talk talk = npc.getTalkByName(playername);
            for (Messagenpc t : talk.getTalks()) {
                if (!t.isNPC) {
                    Label label = new Label(t.message + "-", GameAssetManager.getGameAssetManager().getSkin());
                    label.setWrap(true);
                    label.setAlignment(Align.right);
                    label.setColor(CustomColors.GAMEGREENCOLOR);
                    chatTable.add(label).width(380).right().padBottom(5).row();
                } else {
                    Label label = new Label(npc.getName() + ": " + t.message, GameAssetManager.getGameAssetManager().getSkin());
                    label.setAlignment(Align.left);
                    label.setWrap(true);
                    if (label.getText().toString().matches(".+@" + AppClient.getUserData().getUsername() + ".+")) {
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

        container.setPosition(stage.getWidth() / 2.75f, stage.getHeight() / 3.1f);
        chatForm.addActor(container);
    }

    public void updateQuestsUI() {
        questsTable.clear();
        float width = stage.getWidth() / 3.5f, height = stage.getHeight() / 2.25f;
        if (!isPlayer) {
            NPCData npcd = Finder.getnpcdatabyname(npcData.getName());
            for (QuestData questData : npcd.getQuestsdata()) {
                Table table = new Table();

                TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(questTexture));

                table.setBackground(backgroundDrawable);

                Label questName = new Label(questData.getQuestName() + " (Ongoing)", GameAssetManager.getGameAssetManager().getSkin());
                questName.setColor(Color.YELLOW);
                if (questData.getIsFinished().get(Finder.getpd().getUsername())) {
                    questName.setColor(CustomColors.GAMEGREENCOLOR);
                    questName.setText(questData.getQuestName() + " (Finished)");
                } else if (questData.getIsLocked().get(Finder.getpd().getUsername())) {
                    questName.setColor(Color.GRAY);
                    questName.setText(questData.getQuestName() + " (Locked)");
                }
                questName.setAlignment(Align.center);
                table.add(questName).align(Align.center).center().width(width).padLeft(width / 7.5f).pad(20).row();
                if (!questData.getIsFinished().get(Finder.getpd().getUsername())) {
                    Label questPreTalk = new Label(questData.getQuestPreTalk(), GameAssetManager.getGameAssetManager().getSkin());
                    questPreTalk.setWrap(true);
                    table.add(questPreTalk).width(width).padLeft(width / 7.5f).pad(20).row();
                } else {
                    Label questPostTalk = new Label(questData.getQuestPostTalk(), GameAssetManager.getGameAssetManager().getSkin());
                    questPostTalk.setWrap(true);
                    table.add(questPostTalk).padLeft(width / 7.5f).width(width).pad(20).row();
                }
                if (!questData.getIsFinished().get(Finder.getpd().getUsername())) {
                    Label questNeededItems = new Label("  Request: " +
                        questData.getRequestdata().getQuantity() + " "
                        + questData.getRequestdata().getItemName()
                        , GameAssetManager.getGameAssetManager().getSkin());

                    Label questReward;
                    if (questData.getCookingRecipe() != null) {
                        questReward = new Label("  Reward: " + questData.getCookingRecipe().getName(), GameAssetManager.getGameAssetManager().getSkin());
                    } else if (questData.getMoneyPrize() > 0 || questData.getRewarddata() == null) {
                        questReward = new Label("  Reward: " + questData.getMoneyPrize() + " Coins", GameAssetManager.getGameAssetManager().getSkin());
                    } else {
                        questReward = new Label("  Reward: " +
                            questData.getRewarddata().getQuantity() + " " +
                            questData.getRewarddata().getItemName()
                            , GameAssetManager.getGameAssetManager().getSkin());
                    }

                    if (!questData.getIsLocked().get(Finder.getpd().getUsername())) {
                        questNeededItems.setColor(Color.CYAN);
                        questReward.setColor(CustomColors.GAMEGREENCOLOR);
                    }

                    questNeededItems.setWrap(true);
                    questReward.setWrap(true);

                    table.add(questNeededItems).width(width).pad(20).row();
                    table.add(questReward).width(width).pad(20).row();

                    if (!questData.getIsLocked().get(Finder.getpd().getUsername())) {
                        TextButton finishQuestButton = new TextButton("Finish Quest", GameAssetManager.getGameAssetManager().getSkin());
                        finishQuestButton.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                int index = npcd.getQuestsdata().indexOf(questData);
                                GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                                    new GameCommand("quests finish -i " + (index + 1) + " "+npcData.getName(),
                                        AppClient.getUserData().getUsername()));
                                AppClient.getClient().send(new Gson().toJson(msg));
                            }
                        });
                        table.add(finishQuestButton).width(300).center().pad(20).row();
                    }
                } else {
                    TextButton collectQuestButton = new TextButton("Collect Reward", GameAssetManager.getGameAssetManager().getSkin());
                    collectQuestButton.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            int index = npcd.getQuestsdata().indexOf(questData);
                            GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                                new GameCommand("quests collect -i " + (index + 1) + " "+npcData.getName(),
                                    AppClient.getUserData().getUsername()));
                            AppClient.getClient().send(new Gson().toJson(msg));
                        }
                    });
                    table.add(collectQuestButton).center().width(300).pad(20).row();
                }
                questsTable.add(table).width(width).height(height).padLeft(width / 10);
            }
        }
    }

    private void buildUI() {
        // Tab Row
        Table tabRow = new Table();
        tabRow.add(chatTab).width(200).pad(10);
        tabRow.add(giftTab).width(200).pad(10);
        tabRow.add(proposeOrQuestTab).width(200).pad(10);
        tabRow.add(tradeTab).width(200).pad(10);
        tabRow.add(backTab).width(200).pad(10);

        chatForm.clear();
        setupChatUI();

        // Login Form Layout
        giftForm.clear();

        // Hug Or Quest Form Layout
        proposeOrQuestForm.clear();
        proposeOrQuestForm.add(questsTable);
        proposeOrQuestForm.setPosition(-stage.getWidth() / 20f, -stage.getHeight() / 2.5f);

        // Security Form Layout
        tradeForm.clear();

//        formStack.clear();
//        formStack.add(chatForm);
//        formStack.add(giftForm);
//        formStack.add(proposeOrQuestForm);
//        formStack.add(tradeForm);

        mainTable.clear();
        mainTable.top();
        mainTable.add(tabRow).padTop(200).row();
        mainTable.add(messageLabel).pad(10).padBottom(200).row();
//        mainTable.add(formStack).padTop(10);

        switchForm("gift");

        setupListeners();

    }

    public void switchForm(String formName) {
        chatForm.setVisible(formName.equals("chat"));
        giftForm.setVisible(formName.equals("gift"));
        proposeOrQuestForm.setVisible(formName.equals("proposeOrQuest"));
        tradeForm.setVisible(formName.equals("trade"));
        updateQuestsUI();
        emptyFields();
        if (isPlayer && friendShipLevel < 3) {
            proposeOrQuestTab.setText("Give Flower");
            if(friendShipLevel < 2 || friendshipData.getFriendshipXp() < 300){
                proposeOrQuestTab.getLabel().setColor(Color.RED);
            }
        } else if (isPlayer && friendShipLevel == 3) {
            proposeOrQuestTab.setText("Propose");
            if(friendshipData.getFriendshipXp() < 400){
                proposeOrQuestTab.getLabel().setColor(Color.RED);
            }
        } else if (isPlayer) {
            proposeOrQuestTab.setText("Go To Farm");
        }
    }

    private void setupListeners() {
        chatTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchForm("chat");
                isChatting = true;
            }
        });

        giftTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (friendShipLevel <= 1 && isPlayer) {
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

        proposeOrQuestTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isPlayer && friendShipLevel < 3) {
                    if(friendShipLevel < 2){
                        setMessage("Your friendship level should be at least 3 to give flower.", Color.RED);
                        return;
                    }
                    if(friendshipData.getFriendshipXp() < 300){
                        setMessage("Your friendship xp is " + friendshipData.getFriendshipXp() + ", it should be at 300 xp to give flower.", Color.RED);
                        return;
                    }
                    // TODO give flower
                    return;
                } else if (isPlayer && friendShipLevel == 3) {
                    if(friendshipData.getFriendshipXp() < 400){
                        setMessage("Your friendship xp is " + friendshipData.getFriendshipXp() + ", it should be at 400 xp to give flower.", Color.RED);
                        return;
                    }
                    // TODO Propose
                    return;
                } else if (isPlayer) {
                    // TODO GO TO Farm
                    return;
                }
                switchForm("proposeOrQuest");
                updateQuestsUI();
                if(isPlayer){
                    GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                        new GameCommand("hug -u "+playerData.getUsername(), AppClient.getUserData().getUsername()));
                    AppClient.getClient().send(new Gson().toJson(msg));
                    screen.friendshipMode = false;
                    Gdx.input.setInputProcessor(screen.stage);
                    emptyFields();
                }
                //TODO proposeOrQuest
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
        if (result.code) {
            messageLabel.setColor(Color.GREEN);
        } else {
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
