package com.CEliconValley.client.view;

import com.CEliconValley.CustomColors;
import com.CEliconValley.GameAssetManager;
import com.CEliconValley.Main;
import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.controller.MainMenuController;
import com.CEliconValley.common.OnlineData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Set;

public class MainMenuView implements Screen {

    private final MainMenuController controller;
    private Stage stage;

    private final Image background = new Image(new Texture(GameAssetManager.getGameAssetManager().CEliconValleyBackground));

    public final TextButton startGameButton;
    public final TextButton profileButton;
    public final TextButton logoutButton;

    private final Table mainTable;
    private final Table dataTable;

    public MainMenuView(MainMenuController controller) {
        this.controller = controller;
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();

        startGameButton = new TextButton("Start Game", skin);
        profileButton = new TextButton("Profile", skin);
        logoutButton = new TextButton("Logout", skin);

        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        dataTable = new Table();
        dataTable.setFillParent(true);
        dataTable.right();

        controller.setView(this);
        buildUI();
    }

    private void buildUI() {
        mainTable.clear();

        mainTable.add(startGameButton).width(300).pad(20).padTop(150).row();
        mainTable.add(profileButton).width(300).pad(20).row();
        mainTable.add(logoutButton).width(300).pad(20).row();

//        dataTable.setBackground(GameAssetManager.getGameAssetManager().getDrawableBackground("Field1.png"));
        Set<OnlineData> onlinePlayers = AppClient.getOnlinePlayers();
        for(OnlineData onlineData : onlinePlayers) {
            Label label = new Label(onlineData.username + " .~`", GameAssetManager.getGameAssetManager().getSkin());
            label.setColor(Color.GOLD);
            dataTable.add(label).width(300).pad(20).row();
        }
//        dataTable.pack();
        dataTable.setPosition(- Gdx.graphics.getWidth() / 5f, Gdx.graphics.getHeight() / 25f);

        controller.setupListeners();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport(), Main.getBatch());
        Gdx.input.setInputProcessor(stage);

        background.setSize(stage.getWidth(), stage.getHeight());
        background.setPosition(0, 0);

//        dataTable.setSize(stage.getWidth()/3, stage.getHeight()/3);

        stage.addActor(background);
        stage.addActor(mainTable);
        stage.addActor(dataTable);
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
