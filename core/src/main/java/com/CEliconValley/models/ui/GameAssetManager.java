package com.CEliconValley.models.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;
//    private final Skin skin = new Skin(Gdx.files.internal("skin2/NzSkin.json"));
    private final Skin skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));
    ///Backgrounds
    public String CEliconValleyBackground = "backgrounds/CElicon_Valley.jpg";
    public Image getBackground(String name) {
        return new Image(new Texture(Gdx.files.internal("backgrounds/" + name)));
    }
    public Texture getBackgroundTexture(String name) {
        return new Texture(Gdx.files.internal("backgrounds/" + name));
    }
    public Texture getTileTexture(String name) {return new Texture(Gdx.files.internal("game/general/tiles/" + name));}
    public Drawable getDrawableBackground(String name) {
        TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal("backgrounds/" + name)));
        return new TextureRegionDrawable(region);
    }

    public static GameAssetManager getGameAssetManager() {
        if (gameAssetManager == null) {
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;
    }
    public Skin getSkin() {return skin;}

    public Texture getHeroTexture(String name) {
        return new Texture(Gdx.files.internal("game/Hero/" + name));
    }
}
