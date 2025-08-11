package com.CEliconValley.client.view.screen;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.FriendshipData;
import com.CEliconValley.common.NPCData;
import com.CEliconValley.common.PlayerData;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.ui.FakeCheckbox;
import com.CEliconValley.models.ui.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Objects;

public class FriendshipStageHandler {
    private Stage stage;
    private GameScreen screen;
    private final Texture backgroundTexture = new Texture(GameAssetManager.getGameAssetManager().CEliconValleyBackground);
    private final Image background = new Image(backgroundTexture);

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

    // Login form
    public final TextField loginUsername, loginPassword;
    public final FakeCheckbox stayLoggedInCheckbox;
    public final TextButton loginSubmitButton;

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
    public final Table registerForm, loginForm, forgotForm, securityQuestionForm;

    public FriendshipStageHandler(GameScreen screen, Stage stage, PlayerData playerData, NPCData npcData) {
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();


        this.stage = stage;
        this.screen = screen;

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

        // --- Login Fields
        loginUsername = new TextField("", skin);
        loginUsername.setMessageText("Username");

        loginPassword = new TextField("", skin);
        loginPassword.setPasswordCharacter('*');
        loginPassword.setPasswordMode(true);
        loginPassword.setMessageText("Password");

        stayLoggedInCheckbox = new FakeCheckbox("Stay Logged In", skin);
        loginSubmitButton = new TextButton("Login", skin);

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
        registerForm = new Table();
        loginForm = new Table();
        forgotForm = new Table();
        securityQuestionForm = new Table();

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

        registerForm.clear();
        registerForm.add(chatTextField).width(400).row();

        // Login Form Layout
        loginForm.clear();
        loginForm.add(loginUsername).width(400).row();
        loginForm.add(loginPassword).width(400).padTop(10).row();
        loginForm.add(stayLoggedInCheckbox).padTop(10).row();
        loginForm.add(loginSubmitButton).padTop(20);

        // Forgot Form Layout
        forgotForm.clear();
        forgotForm.add(securityQuestionLabel).padTop(10).row();
        forgotForm.add(forgotUsername).width(500).row();
        forgotForm.add(forgotAnswer).width(500).padTop(10).row();
        forgotForm.add(newPassword).width(500).padTop(10).row();
        forgotForm.add(forgotSubmitButton).width(500).padTop(20);

        // Security Form Layout
        securityQuestionForm.clear();
        int i = 1;
        for(String securityQuestion : AppClient.questions){
            TextButton button = new TextButton(securityQuestion, GameAssetManager.getGameAssetManager().getSkin());
            securityQuestions.add(button);
            if(i++ % 2 == 0){
                securityQuestionForm.add(button).width(800).padLeft(10).padTop(10).row();
            } else{
                securityQuestionForm.add(button).width(800).padRight(10).padTop(10);
            }
        }
        securityQuestionForm.add(securityAnswer).width(700).padTop(10);
        securityQuestionForm.add(securitySubmitButton).width(700).padTop(10);

        formStack.clear();
        formStack.add(registerForm);
        formStack.add(loginForm);
        formStack.add(forgotForm);
        formStack.add(securityQuestionForm);

        mainTable.clear();
        mainTable.top();
        mainTable.add(tabRow).padTop(200).padBottom(340).row();
        mainTable.add(messageLabel).pad(10).row();
        mainTable.add(formStack).padTop(10);

        switchForm("login");

        setupListeners();

    }

    public void switchForm(String formName) {
        registerForm.setVisible(formName.equals("register"));
        loginForm.setVisible(formName.equals("login"));
        forgotForm.setVisible(formName.equals("forgot"));
        securityQuestionForm.setVisible(formName.equals("securityQuestion"));
        emptyFields();
    }

    private void setupListeners(){
        chatTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchForm("register");
                setMessage("", Color.CLEAR);
            }
        });

        giftTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchForm("login");
                setMessage("", Color.CLEAR);
            }
        });

        hugTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchForm("forgot");
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
        loginPassword.setText("");
        loginUsername.setText("");
        forgotUsername.setText("");
        forgotAnswer.setText("");
        newPassword.setText("");
        chatTextField.setText("");
    }
}
