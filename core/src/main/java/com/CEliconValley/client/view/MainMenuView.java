package com.CEliconValley.client.view;

import com.CEliconValley.models.Lobby;
import com.CEliconValley.models.ui.CustomColors;
import com.CEliconValley.models.ui.FakeCheckbox;
import com.CEliconValley.models.ui.GameAssetManager;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.Set;

public class MainMenuView implements Screen {

    private final MainMenuController controller;
    private Stage stage;

    private final Image background = GameAssetManager.getGameAssetManager().getBackground("Mountain.jpg");

    public final TextButton newLobbyButton, joinLobbyButton;
    public final TextButton profileButton;
    public final TextButton logoutButton;

    private final Table mainTable;
    private final Table playersDataTable;
    private final Table formTable;

    // Join Lobby fields
    private Label joinLobbyMessage;
    private TextField lobbyIdField;
    private TextField lobbyPasswordField;
    private TextButton joinConfirmButton;
    private TextButton backButton;
    private boolean joinLobby = false;

    // New Lobby fields
    private Label createLobbyMessage;
    private TextField lobbyNameField;
    private FakeCheckbox isVisibleCheckBox;
    private FakeCheckbox isPrivateCheckBox;
    private TextField passwordField;
    private TextButton createLobbyButton;

    private ScrollPane lobbiesScrollPane;
    private final ScrollPane playersScrollPane;

    Table lobbyListTable;

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

        playersDataTable = new Table();
        playersDataTable.setFillParent(true);
        playersDataTable.left();

        formTable = new Table();
        formTable.setFillParent(true);
        formTable.center();

        playersScrollPane = new ScrollPane(playersDataTable, skin);
        playersScrollPane.setFadeScrollBars(false);
        playersScrollPane.setScrollingDisabled(true, false);
        playersScrollPane.setScrollbarsOnTop(true);

        playersScrollPane.setWidth(300);
        playersScrollPane.setHeight(300);
        playersScrollPane.setPosition(Gdx.graphics.getWidth() / 1.3f, Gdx.graphics.getHeight() / 1.6f);

        controller.setView(this);
        buildUI();
    }

    public void onlinePlayersUpdate(){
        Set<OnlineData> onlinePlayers = AppClient.getOnlinePlayers();
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        int i = 0;
        playersDataTable.clear();
        Label label1 = new Label("Online Players", GameAssetManager.getGameAssetManager().getSkin());
        playersDataTable.add(label1).center().width(300).pad(20).row();
        for (OnlineData onlineData : onlinePlayers) {
            Label label = new Label(onlineData.username + " .~`", GameAssetManager.getGameAssetManager().getSkin());
            label.setColor(colors.get(i++ % colors.size()));
            playersDataTable.add(label).width(300).pad(20).row();
        }
        playersDataTable.setPosition(-Gdx.graphics.getWidth() / 4.5f, -Gdx.graphics.getHeight() / 20f);

    }

    public void updateLobbies(){
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        lobbyListTable = new Table();
        lobbyListTable.top().left();

        for (Lobby lobby : AppClient.getLobbies()) {
            if(lobby.isVisible()){
                Label label = new Label(lobby.getLobbyName() + " : " + lobby.getLobbyID() + " - " + lobby.getPlayerNames().size(), skin);
                label.setColor(CustomColors.GAMEGREENCOLOR);
                if(lobby.isPrivate()){
                    label.setColor(Color.RED);
                }
                lobbyListTable.add(label).pad(10).left().row();
                int i = 1;
                for(String PlayerName : lobby.getPlayerNames()){
                    Label label1 = new Label(i++ + ". " + PlayerName, skin);
                    label1.setColor(Color.WHITE);
                    lobbyListTable.add(label1).pad(5).left().row();
                }
            }
        }
        lobbiesScrollPane = new ScrollPane(lobbyListTable, skin);
        lobbiesScrollPane.setFadeScrollBars(false);
        lobbiesScrollPane.setScrollingDisabled(true, false);
        lobbiesScrollPane.setScrollbarsOnTop(true);
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

        onlinePlayersUpdate();

        controller.setupListeners();

    }

    public void showJoinLobbyForm() {
        System.out.println("tryina make the thing "+AppClient.getLobbies().size());
        joinLobby = true;
        mainTable.setVisible(false);

        formTable.clear();
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();

        joinLobbyMessage = new Label("", skin);
        joinLobbyMessage.setColor(Color.RED);

        updateLobbies();

        lobbyIdField = new TextField("", skin);
        lobbyIdField.setMessageText("Enter Lobby ID");
        lobbyPasswordField = new TextField("", skin);
        lobbyPasswordField.setMessageText("Enter Lobby Password");

        joinConfirmButton = new TextButton("Join", skin);
        backButton = new TextButton("Back", skin);

        formTable.add(new Label("Join Lobby", skin, "title")).padBottom(20).row();
        formTable.add(joinLobbyMessage).width(400).pad(10).row();
        formTable.add(lobbiesScrollPane).width(400).height(200).pad(10).row(); //Scroll Pane
        formTable.add(lobbyIdField).width(400).pad(10).row();
        formTable.add(lobbyPasswordField).width(400).pad(10).row();
        formTable.add(joinConfirmButton).width(300).pad(10).row();
        formTable.add(backButton).width(300).pad(10).row();

        joinConfirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleJoinLobby();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                joinLobby = false;
                mainTable.setVisible(true);
                formTable.clear();
            }
        });
    }


    public void showNewLobbyForm() {
        joinLobby = false;
        mainTable.setVisible(false);
        formTable.clear();
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();

        createLobbyMessage = new Label("", skin);
        createLobbyMessage.setColor(Color.RED);

        lobbyNameField = new TextField("", skin);
        lobbyNameField.setMessageText("Lobby Name");

        isVisibleCheckBox = new FakeCheckbox("Is Visible", skin);
        isPrivateCheckBox = new FakeCheckbox("Is Private", skin);

        passwordField = new TextField("", skin);
        passwordField.setMessageText("Password");
        passwordField.setVisible(false);

        isPrivateCheckBox.addListener(event -> {
            passwordField.setVisible(isPrivateCheckBox.isChecked());
            return false;
        });

        createLobbyButton = new TextButton("Create Lobby", skin);

        backButton = new TextButton("Back", skin);

        formTable.add(new Label("Create New Lobby", skin, "title")).padBottom(20).row();
        formTable.add(createLobbyMessage).width(300).pad(20).row();
        formTable.add(lobbyNameField).width(300).pad(10).row();
        formTable.add(isVisibleCheckBox).pad(5).row();
        formTable.add(isPrivateCheckBox).pad(5).row();
        formTable.add(passwordField).width(300).pad(10).row();
        formTable.add(createLobbyButton).width(300).pad(15).row();
        formTable.add(backButton).width(300).pad(15).row();

        createLobbyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                joinLobby = false;
                controller.handleNewLobby();
            }
        });
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                joinLobby = false;
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
        stage.addActor(playersScrollPane);
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
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if(joinLobby) {
                controller.handleJoinLobby();
            } else if(!mainTable.isVisible()) {
                controller.handleNewLobby();
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

    public Stage getStage() { return stage; }
    public TextButton getJoinLobbyButton() { return joinLobbyButton; }
    public TextButton getNewLobbyButton() { return newLobbyButton; }
    public TextButton getProfileButton() { return profileButton; }
    public TextButton getLogoutButton() { return logoutButton; }

    public Label getJoinLobbyMessage(){return joinLobbyMessage;}
    public TextButton getJoinConfirmButton() { return joinConfirmButton; }
    public TextField getLobbyIdField() { return lobbyIdField; }
    public TextField getLobbyPasswordField() { return lobbyPasswordField; }

    public Label getCreateLobbyMessage(){return createLobbyMessage;}
    public TextField getLobbyNameField() { return lobbyNameField; }
    public FakeCheckbox getIsVisibleCheckBox() { return isVisibleCheckBox; }
    public FakeCheckbox getIsPrivateCheckBox() { return isPrivateCheckBox; }
    public TextField getPasswordField() { return passwordField; }
    public TextButton getCreateLobbyButton() { return createLobbyButton; }
    public TextButton getBackButton() { return backButton; }
    public boolean getJoinLobby(){return joinLobby;}
}
