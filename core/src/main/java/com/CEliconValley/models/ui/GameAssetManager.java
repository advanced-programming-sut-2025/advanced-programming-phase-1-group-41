package com.CEliconValley.models.ui;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.models.Season;
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
    public Texture getSkillTexture(String name) {return new Texture(Gdx.files.internal("game/inventory/skills/" + name));}
    public Texture getTileTexture(String name) {return new Texture(Gdx.files.internal("game/general/tiles/" + name));}
    public Texture getScreenTexture(String name){return new Texture(Gdx.files.internal("game/Buildings/Screen/" + name));}
    public Texture getNPCTexture(String name, String item){return new Texture(Gdx.files.internal("game/Hero/NPC/" + name + "/" + item));}
    public Texture getNPCAssets(String name){
        Season season=AppClient.getGameData().getTime().getSeason();
        int seasonID=0;
        switch (season){
            case Spring -> {
                seasonID=1;
            }
            case Summer -> {
                seasonID=3;
            }
            case Autumn -> {
                seasonID=2;
            }
            case Winter -> {
                if(name.equals("Morris")){
                    seasonID=4;
                }
                else{
                    seasonID=2;
                }
            }
        }
        return new Texture("game/Hero/NPC/" + name + "/"+name+seasonID+".png");
    }
    public Drawable getDrawableBackground(String name) {
        TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal("backgrounds/" + name)));
        return new TextureRegionDrawable(region);
    }
    public Texture getInventoryTexture(String name) {return new Texture(Gdx.files.internal("game/inventory/" + name));}
    public Texture getAvatarTexture(){return new Texture(AppClient.getUserData().getAvatarPath());}

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
