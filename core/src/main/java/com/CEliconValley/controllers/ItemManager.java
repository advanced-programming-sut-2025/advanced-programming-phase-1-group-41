package com.CEliconValley.controllers;

import com.CEliconValley.models.items.Item;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ItemManager {
    private static final TextureRegion[][][] allRegions = new TextureRegion[11][][];

    private static final int[] rows = {3,11, 5, 5, 2, 5, 1, 4, 8, 7, 1};
    private static final int[] cols = {3,12, 6, 6, 3, 5, 1, 5, 8, 5, 1};

    static {
        for (int i = 0; i <= 10; i++) {
            String path = "game/inventory/items/" + (i) + ".png";
            Texture texture = new Texture(Gdx.files.internal(path));
            allRegions[i] = TextureRegion.split(texture,
                texture.getWidth() / cols[i],
                texture.getHeight() / rows[i]
            );
        }
    }

    public static TextureRegion getTexture(Item item) {
        int id = item.getID();
        int imageIndex = id / 10000;
        int row = (id / 100) % 100;
        int col = id % 100;

        if (imageIndex < 0 || imageIndex > 10) return null;
        if (row < 0 || row >= rows[imageIndex]) return null;
        if (col < 0 || col >= cols[imageIndex]) return null;


        return allRegions[imageIndex][row][col];
    }
    public static TextureRegion getTexture(int id) {

        int imageIndex = id / 10000;
        int row = (id / 100) % 100;
        int col = id % 100;

        if (imageIndex < 0 || imageIndex >= 10) return null;
        if (row < 0 || row >= rows[imageIndex]) return null;
        if (col < 0 || col >= cols[imageIndex]) return null;


        return allRegions[imageIndex][row][col];
    }
}
