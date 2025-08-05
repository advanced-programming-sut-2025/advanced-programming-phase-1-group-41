package com.CEliconValley.client.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TimeScreen {
    private Table hudTable;

    public Label dateLabel;
    public Label timeLabel;
    public Label goldLabel;
    public Texture pointerTexture;
    public Image pointerImage;
    public TextureRegion[][] textureRegion;

    public TimeScreen(Skin skin, Stage stage, float posX, float posY, Image hudImage) {
        pointerTexture = new Texture(Gdx.files.internal("game/Clock/Pointer.png"));
        this.pointerImage = new Image(pointerTexture);
        pointerImage.setSize(pointerTexture.getWidth()*4, pointerTexture.getHeight()*4);
        hudTable = new Table();
        hudTable.top().right();
        hudTable.setFillParent(true);
        hudTable.pad(10);
        pointerImage.setPosition(posX+hudImage.getWidth()/4,posY+hudImage.getHeight()*13/20);
        stage.addActor(pointerImage);
        Texture texture = new Texture(Gdx.files.internal("game/Clock/WeatherSign.png"));
        this.textureRegion = TextureRegion.split(texture, texture.getWidth()/4, texture.getHeight()/3);

        // Extract regions from Clock.png
        TextureRegion weatherRegion = textureRegion[0][0]; // Example coords
        TextureRegion seasonRegion = textureRegion[2][0];
//
        Image weatherIcon = new Image(weatherRegion);
        Image seasonIcon = new Image(seasonRegion);
//        Image goldIcon = new Image(goldRegion);

        // Labels
        dateLabel = new Label("Mon. 1", skin);
        timeLabel = new Label("6:50 am", skin);
        goldLabel = new Label("500", skin);
        // Add to table
        dateLabel.setPosition(posX+hudImage.getWidth()*3/5,posY*hudImage.getHeight()*4/5);
        hudTable.add(dateLabel).row();
        hudTable.row();
        hudTable.add(timeLabel).padBottom(5).right();
        float scale = (float) 19/100;
        float secondScale = (float) 7/100;
        float thirdScale = (float) 4/100;
        hudTable.add(weatherIcon).size(hudImage.getWidth()*scale,hudImage.getHeight()*scale)
                .padTop(hudImage.getHeight()*thirdScale)
        .right().padRight(hudImage.getWidth()*secondScale* 24 / 10);
        hudTable.add(seasonIcon).size(hudImage.getWidth()*scale,hudImage.getHeight()*scale)
                .padTop(hudImage.getHeight()*thirdScale)
        .right().padRight(hudImage.getWidth()*secondScale);
        hudTable.row();
        Table goldRow = new Table();
//        goldRow.add(goldIcon).size(24).padRight(5);
        goldRow.add(goldLabel);
        hudTable.add(goldRow).right();
    }

    public Table getHudTable() {
        return hudTable;
    }
}
