package com.CEliconValley.views;

import com.CEliconValley.GameAssetManager;
import com.CEliconValley.Main;
import com.CEliconValley.controllers.MainMenuController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class MainMenuView implements Screen {

    private final MainMenuController controller;
    private Stage stage;

    private final Image background = new Image(new Texture(GameAssetManager.getGameAssetManager().CEliconValleyBackground));

    public final TextButton startGameButton;
    public final TextButton profileButton;
    public final TextButton logoutButton;

    private final Table mainTable;

    public MainMenuView(MainMenuController controller) {
        this.controller = controller;
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();

        startGameButton = new TextButton("Start Game", skin);
        profileButton = new TextButton("Profile", skin);
        logoutButton = new TextButton("Logout", skin);

        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        controller.setView(this);
        buildUI();
    }

    private void buildUI() {
        mainTable.clear();

        mainTable.add(startGameButton).width(300).pad(20).padTop(150).row();
        mainTable.add(profileButton).width(300).pad(20).row();
        mainTable.add(logoutButton).width(300).pad(20).row();

        controller.setupListeners();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport(), Main.getBatch());
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

    public Stage getStage() {
        return stage;
    }

    public TextButton getStartGameButton() {
        return startGameButton;
    }

    public TextButton getProfileButton() {
        return profileButton;
    }

    public TextButton getLogoutButton() {
        return logoutButton;
    }

}
