package com.CEliconValley.client.view;

import com.CEliconValley.FakeCheckbox;
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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.Set;

public class MainMenuView implements Screen {

    private final MainMenuController controller;
    private Stage stage;

    private final Image background = new Image(new Texture(GameAssetManager.getGameAssetManager().CEliconValleyBackground));

    public final TextButton newLobbyButton, joinLobbyButton;
    public final TextButton profileButton;
    public final TextButton logoutButton;

    private final Table mainTable;
    private final Table dataTable;
    private final Table formTable;

    // Join Lobby fields
    private TextField lobbyIdField;
    private TextButton joinConfirmButton;
    private TextButton backButton;

    // New Lobby fields
    private TextField lobbyNameField;
    private FakeCheckbox isVisibleCheckBox;
    private FakeCheckbox isPrivateCheckBox;
    private TextField passwordField;
    private TextButton createLobbyButton;

    public MainMenuView(MainMenuController controller) {
        this.controller = controller;
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();

        newLobbyButton = new TextButton("New Lobby", skin);
        joinLobbyButton = new TextButton("Join Lobby", skin);
        profileButton = new TextButton("Profile", skin);
        logoutButton = new TextButton("Logout", skin);

        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        dataTable = new Table();
        dataTable.setFillParent(true);
        dataTable.right();

        formTable = new Table();
        formTable.setFillParent(true);
        formTable.center();

        controller.setView(this);
        buildUI();
    }

    private void buildUI() {
        mainTable.clear();
        formTable.clear();

        Table lobbyRow = new Table();
        lobbyRow.add(newLobbyButton).padRight(10);
        lobbyRow.add(joinLobbyButton).padRight(10);

        mainTable.add(lobbyRow).width(450).pad(20).padTop(150).row();
        mainTable.add(profileButton).width(300).pad(20).row();
        mainTable.add(logoutButton).width(300).pad(20).row();

        Set<OnlineData> onlinePlayers = AppClient.getOnlinePlayers();
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        int i = 0;
        dataTable.clear();
        for (OnlineData onlineData : onlinePlayers) {
            Label label = new Label(onlineData.username + " .~`", GameAssetManager.getGameAssetManager().getSkin());
            label.setColor(colors.get(i++ % colors.size()));
            dataTable.add(label).width(300).pad(20).row();
        }
        dataTable.setPosition(-Gdx.graphics.getWidth() / 4.5f, -Gdx.graphics.getHeight() / 20f);

        controller.setupListeners();
    }

    public void showJoinLobbyForm() {
        mainTable.setVisible(false);
        formTable.clear();
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();

        lobbyIdField = new TextField("", skin);
        lobbyIdField.setMessageText("Enter Lobby ID");
        joinConfirmButton = new TextButton("Join", skin);
        backButton = new TextButton("Back", skin);

        formTable.add(new Label("Join Lobby", skin, "title")).padBottom(20).row();
        formTable.add(lobbyIdField).width(300).pad(10).row();
        formTable.add(joinConfirmButton).width(200).pad(10).row();
        formTable.add(backButton).width(200).pad(10).row();

        joinConfirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleJoinLobby();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainTable.setVisible(true);
                formTable.clear();
            }
        });
    }

    public void showNewLobbyForm() {
        mainTable.setVisible(false);
        formTable.clear();
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();

        lobbyNameField = new TextField("", skin);
        lobbyNameField.setMessageText("Lobby Name");

        isVisibleCheckBox = new FakeCheckbox("Is Visible", skin);
        isPrivateCheckBox = new FakeCheckbox("Is Private", skin);

        passwordField = new TextField("", skin);
        passwordField.setMessageText("Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setVisible(false);

        isPrivateCheckBox.addListener(event -> {
            passwordField.setVisible(isPrivateCheckBox.isChecked());
            return false;
        });

        createLobbyButton = new TextButton("Create Lobby", skin);

        backButton = new TextButton("Back", skin);

        formTable.add(new Label("Create Lobby", skin, "title")).padBottom(20).row();
        formTable.add(lobbyNameField).width(300).pad(10).row();
        formTable.add(isVisibleCheckBox).pad(5).row();
        formTable.add(isPrivateCheckBox).pad(5).row();
        formTable.add(passwordField).width(300).pad(10).row();
        formTable.add(createLobbyButton).width(250).pad(15).row();
        formTable.add(backButton).width(250).pad(15).row();

        createLobbyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                controller.handleJoinLobby();
                controller.handleNewLobby();
            }
        });
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainTable.setVisible(true);
                formTable.clear();
            }
        });
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport(), Main.getBatch());
        Gdx.input.setInputProcessor(stage);

        background.setSize(stage.getWidth(), stage.getHeight());
        background.setPosition(0, 0);

        stage.addActor(background);
        stage.addActor(mainTable);
        stage.addActor(dataTable);
        stage.addActor(formTable);
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

    // Getters برای کنترلر
    public Stage getStage() { return stage; }
    public TextButton getJoinLobbyButton() { return joinLobbyButton; }
    public TextButton getNewLobbyButton() { return newLobbyButton; }
    public TextButton getProfileButton() { return profileButton; }
    public TextButton getLogoutButton() { return logoutButton; }

    public TextButton getJoinConfirmButton() { return joinConfirmButton; }
    public TextField getLobbyIdField() { return lobbyIdField; }

    public TextField getLobbyNameField() { return lobbyNameField; }
    public FakeCheckbox getIsVisibleCheckBox() { return isVisibleCheckBox; }
    public FakeCheckbox getIsPrivateCheckBox() { return isPrivateCheckBox; }
    public TextField getPasswordField() { return passwordField; }
    public TextButton getCreateLobbyButton() { return createLobbyButton; }
    public TextButton getBackButton() { return backButton; }
}
