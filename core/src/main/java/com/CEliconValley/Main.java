package com.CEliconValley;

import com.CEliconValley.database.UserDB;
import com.CEliconValley.models.App;
import com.CEliconValley.models.Menu;
import com.CEliconValley.models.items.CookingRecipe;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private static Main main;
    private static SpriteBatch batch;
    private static Stage stage;
    private static Texture image;
    private static Image background;

    private boolean screenSet = false;

    @Override
    public void create() {
        startApp();
        graphical();
    }

    public void graphical(){
        main = this;
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport(), batch);
        image = new Texture(GameAssetManager.getGameAssetManager().CEliconValleyBackground);
        background = new Image(image);
        Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
//        Gdx.graphics.setFullscreenMode(displayMode);
        background.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(background);
        main.setScreen(App.getMenu().getScreen());
//        main.setScreen(new StartMenuView(new StartMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
//        main.setScreen(new AuthenticationMenuView(new AuthenticationMenuController()));
    }

    private static void startApp(){
        ArrayList<String> questions = new ArrayList<>();
        questions.add("What is your favorite color?");
        questions.add("What is your favorite food?");
        questions.add("What is your favorite sport?");
        questions.add("Where were you born?");
        questions.add("What is you father's name?");
        questions.add("What is you mother's name?");
        questions.add("What was the name of your first pet?");
        questions.add("What is name of the city you're living in?");
        questions.add("What is your best friend's name?");
        questions.add("What is your body count?");
        App.setQuestions(questions);
        CookingRecipe.updateRecipe();
        UserDB.connect();
        App.setupConnections();
        App.setMenu(Menu.Authentication);
    }

    @Override
    public void render() {
        super.render();
//        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
//        batch.begin();
//        batch.end();
//        stage.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }

    public static Main getMain() {
        return main;
    }

    public static SpriteBatch getBatch() {return batch;}
}
