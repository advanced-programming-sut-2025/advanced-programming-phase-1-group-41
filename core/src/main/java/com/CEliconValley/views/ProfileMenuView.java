package com.CEliconValley.views;

import com.CEliconValley.CustomColors;
import com.CEliconValley.GameAssetManager;
import com.CEliconValley.Main;
import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.UserData;
import com.CEliconValley.controllers.ProfileMenuController;
import com.CEliconValley.database.UserDB;
import com.CEliconValley.models.App;
import com.CEliconValley.models.User;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.awt.*;
import java.util.Scanner;

public class ProfileMenuView implements Screen, AppMenu {

    private final ProfileMenuController controller;
    private Stage stage;

    private final Image background = new Image(new Texture(GameAssetManager.getGameAssetManager().CEliconValleyBackground));

    private final Label messageLabel;

    public final TextField newUsernameField;
    public final TextButton changeUsernameButton;

    public final TextField newPasswordField;
    public final TextButton changePasswordButton;

    public final TextField newNicknameField;
    public final TextButton changeNicknameButton;

    public final TextField newEmailField;
    public final TextButton changeEmailButton;

    public final TextButton deleteAccountButton;
    public final TextButton backButton;

    private Label usernameLabel, passwordLabel, nicknameLabel, emailLabel, genderLabel;

    private final Table rootTable;
    private final Table mainTable;
    private final Table rightTable;

    public ProfileMenuView(ProfileMenuController controller) {
        this.controller = controller;
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();

        messageLabel = new Label("", skin);
        messageLabel.setColor(Color.RED);
        messageLabel.setAlignment(Align.center);

        newUsernameField = new TextField("", skin);
        newUsernameField.setMessageText("New username");

        changeUsernameButton = new TextButton("Change Username", skin);

        newPasswordField = new TextField("", skin);
        newPasswordField.setMessageText("New Password");
//        newPasswordField.setPasswordCharacter('*');
//        newPasswordField.setPasswordMode(true);

        changePasswordButton = new TextButton("Change Password", skin);

        newNicknameField = new TextField("", skin);
        newNicknameField.setMessageText("New Nickname");

        changeNicknameButton = new TextButton("Change Nickname", skin);

        newEmailField = new TextField("", skin);
        newEmailField.setMessageText("New Email");

        changeEmailButton = new TextButton("Change Email", skin);

        deleteAccountButton = new TextButton("Delete Account", skin);
        backButton = new TextButton("Back", skin);

        rootTable = new Table();
        rootTable.setFillParent(true);

        mainTable = new Table();
        mainTable.setFillParent(true);

        rightTable = new Table();
        rightTable.setFillParent(true);

        controller.setView(this);
    }

    private void buildUI() {
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();

        mainTable.clear();

        mainTable.add(changeUsernameButton).width(400).pad(10);
        mainTable.add(newUsernameField).width(400).pad(10).row();

        mainTable.add(changePasswordButton).width(400).pad(10);
        mainTable.add(newPasswordField).width(400).pad(10).row();

        mainTable.add(changeNicknameButton).width(400).pad(10);
        mainTable.add(newNicknameField).width(400).pad(10).row();

        mainTable.add(changeEmailButton).width(400).pad(10);
        mainTable.add(newEmailField).width(400).pad(10).row();

        mainTable.add(deleteAccountButton).colspan(2).width(400).padTop(40).row();
        mainTable.add(backButton).colspan(2).width(400).padTop(20).row();

        UserData userData = AppClient.getUserData();

        usernameLabel = new Label("Username: " + userData.getUsername(), skin);
        usernameLabel.setColor(CustomColors.GAMEGREENCOLOR);
        nicknameLabel = new Label("Nickname: " + userData.getNickname(), skin);
        nicknameLabel.setColor(CustomColors.GAMEGREENCOLOR);
        emailLabel = new Label("Email: " + userData.getEmail(), skin);
        emailLabel.setColor(CustomColors.GAMEGREENCOLOR);
        genderLabel = new Label("Gender: " + userData.getGender(), skin);
        genderLabel.setColor(CustomColors.GAMEGREENCOLOR);

        rightTable.add(new Label("Current Info", skin, "title")).padBottom(20).row();
        rightTable.add(usernameLabel).left().row();
        rightTable.add(nicknameLabel).left().row();
        rightTable.add(emailLabel).left().row();
        rightTable.add(genderLabel).left().row();
        rightTable.add(messageLabel).left().row();


        rootTable.add(mainTable).padRight(50);
        rootTable.add(rightTable);

        rootTable.setPosition(-stage.getWidth() / 3.5f, -stage.getHeight() / 2.3f);

        controller.setupListeners();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport(), Main.getBatch());
        Gdx.input.setInputProcessor(stage);

        background.setSize(stage.getWidth(), stage.getHeight());
        background.setPosition(0, 0);

        buildUI();

        stage.addActor(background);
        stage.addActor(rootTable);
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

        ScreenUtils.clear(0, 0, 0, 0);
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

    public void setMessage(String message, Color color) {
        messageLabel.setText(message);
        messageLabel.setColor(color);
    }

    public void updateInfo(){
        UserData userData = AppClient.getUserData();
        usernameLabel.setText("Username: " + userData.getUsername());
        nicknameLabel.setText("Nickname: " + userData.getNickname());
        emailLabel.setText("Email: " + userData.getEmail());
        genderLabel.setText("Gender: " + userData.getGender());
    }

    @Override
    public void check(Scanner scanner) {
        System.out.println("aghebat profile");
    }
}
