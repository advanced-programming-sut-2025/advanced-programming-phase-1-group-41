package com.CEliconValley.views;

import com.CEliconValley.GameAssetManager;
import com.CEliconValley.Main;
import com.CEliconValley.controllers.LobbyController;
import com.CEliconValley.controllers.MainMenuController;
import com.CEliconValley.models.App;
import com.CEliconValley.models.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class Lobby implements Screen {
    private String name;
    private String ID;
    private boolean isPrivate;
    private boolean isVisible;
    private String password;
    private float timer=0;
    private ArrayList<Player> players;
    private final LobbyController controller;
    private Stage stage;

    private final Image background = new Image(new Texture(GameAssetManager.getGameAssetManager().CEliconValleyBackground));

    public  Label idText;
    public  Label passText;
    public  Label nameText;
    private Label label1;
    private Label label2;
    private Label label3;
    private Label label4;
    public TextButton startGameButton;
    public  TextButton exitButton;

    private final Table mainTable;
    private final Table teamTable;

//    public Lobby(LobbyController controller) {
//        this.controller = controller;
//        mainTable = new Table();
//    }

    public Lobby(LobbyController controller, String name,boolean isPrivate, boolean isVisible, String password) {
        this.name = name;
        this.isPrivate = isPrivate;
        this.isVisible = isVisible;
        this.password = password;
        this.controller=controller;
        players=new ArrayList<>();
        players.add(new Player(App.getCurrentUser()));

        Skin skin = GameAssetManager.getGameAssetManager().getSkin();

        label1=new Label("PLAYER1", skin);
        label2=new Label("PLAYER2", skin);
        label3=new Label("PLAYER3", skin);
        label4=new Label("PLAYER4", skin);

        startGameButton = new TextButton("Start Game", skin);
        exitButton = new TextButton("Exit", skin);

        mainTable = new Table();
        teamTable = new Table();

        mainTable.setFillParent(true);
        mainTable.center();
        teamTable.clear();
        teamTable.top().left().padTop(50).padLeft(20);
        teamTable.defaults().expandX().fillX();


        teamTable.add(label1).padLeft(500);
        teamTable.add(label2).padLeft(600);
        teamTable.add(label3).padLeft(550);
        teamTable.add(label4).padLeft(550).expandX().right();

        nameText = new Label(name, skin);
        ID=giveID();
        idText = new Label(ID, skin);
        passText = new Label(password, skin);
        App.addToLobbies(this);
        controller.setView(this);
        buildUI();
    }
    public String getID(){
        return ID;
    }

    private String giveID() {
            int number;
            number = MathUtils.random(10000, 99999);
            if(!App.lobbies.isEmpty()) {
                for (Lobby lobby : App.lobbies) {
                    String theID = String.valueOf(number);
                    if (lobby.getID().equals(theID)) {
                        return giveID();
                    }
                }
            }
            return String.valueOf(number);

    }

    private void buildUI() {
        mainTable.clear();

        Table topContent = new Table();
        topContent.add(startGameButton).width(300).pad(20).padTop(150).row();
        startGameButton.setVisible(false);
        topContent.add(nameText).width(300).pad(20).row();
        topContent.add(idText).width(300).pad(20).row();
        topContent.add(passText).width(300).pad(20).row();
        topContent.add(exitButton).width(300).pad(20).row();


        mainTable.top().add(topContent).expand().fill().row();
        mainTable.bottom().add(teamTable).bottom()
            .expand()
            .fill();



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


    public void render(float delta) {
        timer += delta;
        if (timer >= 0.5f) {
            if (timer >= 1f) {
                timer = 0f;
                String path = "skin/lobby/4.png";
                setVisible(true,true,true,true);
                Texture texture = new Texture(Gdx.files.internal(path));
                TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
                teamTable.setBackground(drawable);
            } else {
                Boolean a=false,b=false,c=false,d=false;
                ArrayList<Boolean> booleans;
                booleans=new ArrayList<>();
                booleans.add(a);
                booleans.add(b);
                booleans.add(c);
                booleans.add(d);
                for(int i=1;i<=players.size();i++) {
                    booleans.set(i-1,true);
                }
                for(int i=players.size()+1;i<=4;i++) {
                    booleans.set(i-1,false);
                }
                setVisible(booleans.get(0),booleans.get(1),booleans.get(2),booleans.get(3));
                String path = "skin/lobby/" + players.size() + ".png";
                Texture texture = new Texture(Gdx.files.internal(path));
                TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
                teamTable.setBackground(drawable);
            }
        }
        if(players.size()>=1){
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
    public ArrayList<Player> getPlayers(){
        return players;
    }


}
