package com.CEliconValley.views;

import com.CEliconValley.FakeCheckbox;
import com.CEliconValley.Main;
import com.CEliconValley.controllers.authentication.AuthenticationMenuController;
import com.CEliconValley.models.App;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.CEliconValley.GameAssetManager;

import java.util.ArrayList;


public class AuthenticationMenuView implements Screen, AppMenu {

    private final AuthenticationMenuController controller;
    private Stage stage;
    private final Image background = new Image(new Texture(GameAssetManager.getGameAssetManager().CEliconValleyBackground));

    // Navigation buttons
    private final TextButton registerTab, loginTab, forgotTab, exitTab;

    // Shared
    private final Label messageLabel;


    // Register form
    public final TextField regUsername, regPassword, regConfirmPassword, regNickname, regEmail;
    public final TextButton genderMaleButton, genderFemaleButton, regSubmitButton, randomPasswordButton;

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
    private final Table registerForm, loginForm, forgotForm, securityQuestionForm;

    public AuthenticationMenuView(AuthenticationMenuController controller) {
        this.controller = controller;
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();

        // --- Tabs
        registerTab = new TextButton("Register", skin);
        loginTab = new TextButton("Login", skin);
        forgotTab = new TextButton("Forgot Password", skin);
        exitTab = new TextButton("Exit", skin);

        // --- Message
        messageLabel = new Label("", skin);
        messageLabel.setColor(Color.RED);
        messageLabel.setAlignment(Align.center);

        // --- Register Fields
        regUsername = new TextField("", skin);
        regUsername.setMessageText("Username");

        regPassword = new TextField("", skin);
//        regPassword.setPasswordCharacter('*');
        regPassword.setPasswordMode(true);
        regPassword.setMessageText("Password");

        regConfirmPassword = new TextField("", skin);
//        regConfirmPassword.setPasswordCharacter('*');
        regConfirmPassword.setPasswordMode(true);
        regConfirmPassword.setMessageText("Confirm Password");

        regNickname = new TextField("", skin);
        regNickname.setMessageText("Nickname");

        regEmail = new TextField("", skin);
        regEmail.setMessageText("Email");

        genderMaleButton = new TextButton("Male", skin);
        genderFemaleButton = new TextButton("Female", skin);
        regSubmitButton = new TextButton("Submit Registration", skin);
        randomPasswordButton = new TextButton("Generate Random Password", skin);

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

        controller.setView(this);
        buildUI();
    }

    private void buildUI() {
        // Tab Row
        Table tabRow = new Table();
        tabRow.add(registerTab).pad(10);
        tabRow.add(loginTab).pad(10);
        tabRow.add(forgotTab).pad(10);
        tabRow.add(exitTab).pad(10);

        // Register Form Layout
        Table genderRow = new Table();
        genderRow.add(genderMaleButton).width(250).pad(5);
        genderRow.add(genderFemaleButton).width(250).pad(5);

        Table passwordRow = new Table();
        passwordRow.add(regPassword).width(300).pad(5);
        passwordRow.add(regConfirmPassword).width(300).pad(5);

        registerForm.clear();
        registerForm.add(regUsername).width(400).row();
        registerForm.add(regNickname).width(400).padTop(10).row();
        registerForm.add(regEmail).width(400).padTop(10).row();
        registerForm.add(passwordRow).padTop(10).row();
        registerForm.add(randomPasswordButton).width(600).padTop(10).row();
        registerForm.add(genderRow).padTop(10).row();
        registerForm.add(regSubmitButton).width(500).padTop(20);

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
        for(String securityQuestion : App.questions){
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
        mainTable.add(tabRow).padTop(20).padBottom(340).row();
        mainTable.add(messageLabel).pad(10).row();
        mainTable.add(formStack).padTop(10);

        // Default: show register
        switchForm("register");

        // Add listeners
        controller.setupListeners();

    }

    public void switchForm(String formName) {
        registerForm.setVisible(formName.equals("register"));
        loginForm.setVisible(formName.equals("login"));
        forgotForm.setVisible(formName.equals("forgot"));
        securityQuestionForm.setVisible(formName.equals("securityQuestion"));
        controller.emptyFields();
    }

    public void setMessage(String message, Color color) {
        messageLabel.setText(message);
        messageLabel.setColor(color);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        background.setSize(stage.getWidth(), stage.getHeight());
        background.setPosition(0, 0);

        stage.addActor(background);
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            if (Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setWindowedMode(1280, 720);
            } else {
                Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
                Gdx.graphics.setFullscreenMode(displayMode);
            }
        }

        ScreenUtils.clear(0, 0, 0 ,0);
        Main.getBatch().begin();
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        background.setSize(stage.getWidth(), stage.getHeight());
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public TextButton getRegisterTab() {
        return registerTab;
    }

    public TextButton getLoginTab() {
        return loginTab;
    }

    public TextButton getForgotTab() {
        return forgotTab;
    }

    public TextButton getExitTab() {return exitTab;}
}
