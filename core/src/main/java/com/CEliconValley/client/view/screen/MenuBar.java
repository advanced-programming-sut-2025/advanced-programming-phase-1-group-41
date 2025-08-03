package com.CEliconValley.client.view.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MenuBar {
    private final Texture menuTexture = new Texture("game/Buildings/Screen/Menu_Screen.png");
    private final TextureRegion selectedTile;
    private final int tileWidth;
    private final int tileHeight;

    public MenuBar() {
        tileWidth = menuTexture.getWidth() / 3;
        tileHeight = menuTexture.getHeight() / 3;
        TextureRegion[][] split = TextureRegion.split(menuTexture, tileWidth, tileHeight);
        selectedTile = split[0][1];
    }

    public void render(Batch batch, float x, float y) {
        batch.draw(selectedTile, x, y, tileWidth, tileHeight);
    }

    public void dispose() {
        menuTexture.dispose();
    }
}
