package com.CEliconValley.client.view.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TimeScreen {
    private Table hudTable;

    public Label dateLabel;
    public Label timeLabel;
    public Label goldLabel;

    public TimeScreen(Skin skin, Texture hudTexture) {
        hudTable = new Table();
        hudTable.top().right();
        hudTable.setFillParent(true);
        hudTable.pad(10);

        // Extract regions from Clock.png
        TextureRegion weatherRegion = new TextureRegion(hudTexture, 0, 0, 32, 32); // Example coords
        TextureRegion seasonRegion = new TextureRegion(hudTexture, 32, 0, 32, 32);
        TextureRegion goldRegion = new TextureRegion(hudTexture, 64, 0, 32, 32);

        Image weatherIcon = new Image(weatherRegion);
        Image seasonIcon = new Image(seasonRegion);
        Image goldIcon = new Image(goldRegion);

        // Labels
        dateLabel = new Label("Mon. 1", skin);
        timeLabel = new Label("6:50 am", skin);
        goldLabel = new Label("500", skin);

        // Add to table
        hudTable.add(dateLabel).padBottom(5).right();
        hudTable.row();
        hudTable.add(timeLabel).padBottom(5).right();
        hudTable.row();
        hudTable.add(weatherIcon).size(32).padBottom(5).right();
        hudTable.row();
        hudTable.add(seasonIcon).size(32).padBottom(5).right();
        hudTable.row();
        Table goldRow = new Table();
        goldRow.add(goldIcon).size(24).padRight(5);
        goldRow.add(goldLabel);
        hudTable.add(goldRow).right();
    }

    public Table getHudTable() {
        return hudTable;
    }
}
