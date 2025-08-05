package com.CEliconValley.client.view;

import com.CEliconValley.models.locations.FarmType;
import com.CEliconValley.models.ui.CustomColors;
import com.CEliconValley.models.ui.GameAssetManager;
import com.CEliconValley.Main;
import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.controller.LobbyController;
import com.CEliconValley.models.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class LobbyScreen implements Screen {

    public Lobby lobby;

    private float timer=0;
    private final LobbyController controller;
    private Stage stage;
    Image avatarImage;

    private final Image background = GameAssetManager.getGameAssetManager().getBackground("Mountain.jpg");;

    public  Label idText;
    public  Label passText;
    public  Label adminText;
    public  Label nameText;
    private Label label1;
    private Label label2;
    private Label label3;
    private Label label4;
    public TextButton startGameButton;
    public TextButton exitButton;
    private TextButton mountainButton;
    private TextButton swampButton;
    private TextButton jungleButton;
    public boolean canStart = false;

    private Table mainTable;
    private Table teamTable;
    private Table mapTable;

    private Texture teamBackgroundTexture;
    private Texture lobbyBackgorundTexture;

    public LobbyScreen(LobbyController controller) {
        this.controller=controller;
    }

    public void updatePlayers(){
        lobby = AppClient.getCurrentLobby();
        int i = 1;
        for(String playerName : lobby.getPlayerNames()) {
            if(i == 1){
                label1.setText(playerName);
            } else if(i == 2){
                label2.setText(playerName);
            } else if(i == 3){
                label3.setText(playerName);
            } else if(i == 4){
                label4.setText(playerName);
            }
            i++;
        }
        assert AppClient.getUserData() != null;
        if(AppClient.getUserData().getUsername().equals(lobby.getAdmin())){
            startGameButton.setColor(Color.GREEN);
            canStart = true;
        }else{
            startGameButton.setColor(Color.RED);
            canStart = false;
        }
    }
    public void buildUI() {
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();

        avatarImage = new Image(new Texture(AppClient.getUserData().getAvatarPath()));
        avatarImage.setScaling(Scaling.fit);
        avatarImage.setSize(200, 200);

        label1 = new Label("PLAYER1", skin);
        label2 = new Label("PLAYER2", skin);
        label3 = new Label("PLAYER3", skin);
        label4 = new Label("PLAYER4", skin);

        startGameButton = new TextButton("Start Game", skin);
        exitButton = new TextButton("Exit", skin);

        mainTable = new Table();
        teamTable = new Table();

        mainTable.setFillParent(true);
        mainTable.center();
        teamTable.clear();
        teamTable.top().left().padTop(50).padLeft(20);
        teamTable.defaults().expandX().fillX();


        teamTable.add(label1).padLeft(220);
        teamTable.add(label2).padLeft(200).padRight(50);
        teamTable.add(label3).padLeft(200).padRight(50);
        teamTable.add(label4).padLeft(-45).expandX().right();

        lobby = AppClient.getCurrentLobby();

        adminText = new Label("Admin: " + lobby.getAdmin(), skin);
        nameText = new Label("Name: " + lobby.getLobbyName(), skin);
        idText = new Label("ID: " + lobby.getLobbyID(), skin);
        if(lobby.isPrivate()) {
            passText = new Label("Pass: " + lobby.getPassword(), skin);
        }

        controller.setView(this);

        mainTable.clear();

        updatePlayers();

        Table topContent = new Table();
        topContent.add(startGameButton).width(300).pad(20).padTop(150).row();
        startGameButton.setVisible(false);
        topContent.add(adminText).width(300).pad(20).row();
        topContent.add(nameText).width(300).pad(20).row();
        topContent.add(idText).width(300).pad(20).row();
        if(lobby.isPrivate()) {
            topContent.add(passText).width(300).pad(20).row();
        }
        topContent.add(exitButton).width(300).pad(20).row();

        mainTable.top().add(topContent).expand().fill().row();
        mainTable.bottom().add(teamTable).bottom()
            .expand()
            .fill();

        mapTable = new Table();
        mapTable.center().padTop(50);

        mountainButton = new TextButton("Mountain", skin);
        swampButton = new TextButton("Swamp", skin);
        jungleButton = new TextButton("Jungle", skin);

        ButtonGroup<TextButton> mapGroup = new ButtonGroup<>(mountainButton, swampButton, jungleButton);
        mapGroup.setMinCheckCount(1);
        mapGroup.setMaxCheckCount(1);

        mapTable.add(mountainButton).pad(10).width(200).row();
        mapTable.add(swampButton).pad(10).width(200).row();
        mapTable.add(jungleButton).pad(10).width(200);

        mountainButton.setChecked(true);

        updateMapButtonStyles();
        mountainButton.addListener(event -> { if (mountainButton.isChecked()) updateMapButtonStyles(); return false; });
        swampButton.addListener(event -> { if (swampButton.isChecked()) updateMapButtonStyles(); return false; });
        jungleButton.addListener(event -> { if (jungleButton.isChecked()) updateMapButtonStyles(); return false; });

        controller.setupListeners();
    }




    @Override
    public void show() {
        buildUI();

        stage = new Stage(new ScreenViewport(), Main.getBatch());
        Gdx.input.setInputProcessor(stage);

        background.setSize(stage.getWidth(), stage.getHeight());
        background.setPosition(0, 0);

        mapTable.setPosition(stage.getWidth() / 1.5f, stage.getHeight() / 1.7f);
        avatarImage.setPosition(stage.getWidth() / 9f, stage.getHeight() / 2.1f);
        stage.addActor(background);
        stage.addActor(mainTable);
        stage.addActor(mapTable);
        stage.addActor(avatarImage);
    }


    public void render(float delta) {
        timer += delta;
        if (timer >= 0.5f) {
            if (timer >= 1f) {
                timer = 0f;
                String path = "skin/lobby/4.png";
                setVisible(true,true,true,true);
                if(lobbyBackgorundTexture != null) {
                    lobbyBackgorundTexture.dispose();
                }
                lobbyBackgorundTexture = new Texture(Gdx.files.internal(path));
                TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(lobbyBackgorundTexture));
                teamTable.setBackground(drawable);
            } else {
                Boolean a=false,b=false,c=false,d=false;
                ArrayList<Boolean> booleans;
                booleans=new ArrayList<>();
                booleans.add(a);
                booleans.add(b);
                booleans.add(c);
                booleans.add(d);
                for(int i=1;i<=lobby.getPlayerNames().size();i++) {
                    booleans.set(i-1,true);
                }
                for(int i=lobby.getPlayerNames().size()+1;i<=4;i++) {
                    booleans.set(i-1,false);
                }
                setVisible(booleans.get(0),booleans.get(1),booleans.get(2),booleans.get(3));
                String path = "skin/lobby/" + lobby.getPlayerNames().size() + ".png";
                if(teamBackgroundTexture != null) {
                    teamBackgroundTexture.dispose();
                }
                teamBackgroundTexture = new Texture(Gdx.files.internal(path));
                TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(teamBackgroundTexture));
                teamTable.setBackground(drawable);
            }
        }
        if(!lobby.getPlayerNames().isEmpty()){
            startGameButton.setVisible(true);
        }


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

    public TextButton getExitButton() {
        return exitButton;
    }
    private void setVisible(boolean a, boolean b, boolean c, boolean d) {
        label1.setVisible(a);
        label2.setVisible(b);
        label3.setVisible(c);
        label4.setVisible(d);
    }

    private void updateMapButtonStyles() {

        if (mountainButton.isChecked()) {
            mountainButton.setColor(CustomColors.MOUNTAIN_COLOR);
            mountainButton.getLabel().setColor(Color.WHITE);
        } else {
            mountainButton.setColor(0.5f, 0.5f, 0.5f, 1f);
            mountainButton.getLabel().setColor(CustomColors.MOUNTAIN_COLOR);
        }

        if (swampButton.isChecked()) {
            swampButton.setColor(CustomColors.SWAMP_COLOR);
            swampButton.getLabel().setColor(Color.WHITE);
        } else {
            swampButton.setColor(0.5f, 0.5f, 0.5f, 1f);
            swampButton.getLabel().setColor(CustomColors.SWAMP_COLOR);
        }

        if (jungleButton.isChecked()) {
            jungleButton.setColor(CustomColors.JUNGLE_COLOR);
            jungleButton.getLabel().setColor(Color.WHITE);
        } else {
            jungleButton.setColor(0.5f, 0.5f, 0.5f, 1f);
            jungleButton.getLabel().setColor(CustomColors.JUNGLE_COLOR);
        }
    }

    public FarmType getFarmType() {
        if(mountainButton.isChecked()){
            return FarmType.Mountain;
        } else if(swampButton.isChecked()){
            return FarmType.Swamp;
        } else if(jungleButton.isChecked()){
            return FarmType.Jungle;
        }
        return null;
    }


}
