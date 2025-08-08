package com.CEliconValley.client.view;

import com.CEliconValley.Main;
import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.controller.GameSelectionController;
import com.CEliconValley.models.Menu;
import com.CEliconValley.models.ui.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Scanner;

public class GameSelectionView implements AppMenu, Screen {

    private final Stage stage;
    private final Skin skin;
    private final Array<String> games;
    private final Array<TextButton> gameButtons;
    private final Image previewGame;
    private final Label messageLabel;
    private final TextButton backButton;
    private final TextButton joinButton;
    private final Image background;

    private final GameSelectionController controller;

    public GameSelectionView(GameSelectionController controller) {
        this.skin = GameAssetManager.getGameAssetManager().getSkin();
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        background = GameAssetManager.getGameAssetManager().getBackground("Mountain.jpg");;

        games = new Array<>();
        gameButtons = new Array<>();
        previewGame = new Image();
        messageLabel = new Label("", skin);
        messageLabel.setColor(Color.YELLOW);
        backButton = new TextButton("Back", skin);
        joinButton = new TextButton("Join Lobby", skin);

        this.controller = controller;

        loadGamesPreview();
        buildUI();

        controller.setView(this);
        controller.setupListeners();
    }

    private void loadGamesPreview() {
        games.clear();
        System.out.println(AppClient.getGames().size());
        for (String name : AppClient.getGames()) {
            System.out.println("adding " + name);
            games.add(name);
        }
    }

    private void buildUI() {
        Table root = new Table();
        root.setFillParent(true);
        root.top().pad(30);
        stage.addActor(background);
        stage.addActor(root);

        Label title = new Label("Choose Your Game", skin);
        title.setColor(Color.CYAN);
        root.add(title).colspan(3).center().padBottom(20);
        root.row();

        Table gameGrid = new Table();
        int col = 5;
        for (int i = 0; i < games.size; i++) {
            TextButton textButton = new TextButton(games.get(i), skin);
            gameButtons.add(textButton);

            gameGrid.add(textButton).pad(10);
            if ((i + 1) % col == 0) gameGrid.row();
        }

        root.add(gameGrid).colspan(3).center();
        root.row().padTop(30);

        root.add(previewGame).size(150).colspan(3).center();
        root.row().padTop(15);

        root.add(messageLabel).colspan(3).center();
        root.row().padTop(15);

        root.add(backButton).colspan(3).center().width(300).row();
        root.add(joinButton).colspan(3).center().width(300).padTop(20);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            goBack();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            if (Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setWindowedMode(1280, 720);
            } else {
                Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
                Gdx.graphics.setFullscreenMode(displayMode);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        background.setSize(stage.getWidth(), stage.getHeight());
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
        for (String path : games) {
            new Texture(Gdx.files.internal(path)).dispose();
        }
    }

    public Array<TextButton> getGameButtons() {
        return gameButtons;
    }

    public Array<String> getGames() {
        return games;
    }

    public void showPreviewGame() {
        for (TextButton gameButton : gameButtons) {
            gameButton.setColor(Color.YELLOW);
        }
        controller.getSelectedButton().setColor(Color.GREEN);
//        previewGame.setDrawable(new TextureRegionDrawable(new Texture(Gdx.files.internal(path))));
    }

    public void setMessage(String text) {
        messageLabel.setText(text);
        messageLabel.setColor(Color.MAGENTA);
    }

    public void goBack() {
        Menu.Main.resetMenu();
        AppClient.setMenu(Menu.Main);
        Main.getMain().setScreen(AppClient.getMenu().getScreen());
    }

    public TextButton getBackButton() {
        return backButton;
    }

    @Override
    public void check(Scanner scanner) {

    }

    @Override
    public void setMessage(String message, Color color) {

    }

    public TextButton getJoinButton() {
        return joinButton;
    }
}
