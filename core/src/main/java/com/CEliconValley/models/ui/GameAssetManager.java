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

import java.util.HashMap;
import java.util.Map;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;

    public final Map<String, Map<Integer, TextureRegion[]>> npcTextures;
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

    public GameAssetManager(){
        npcTextures = new HashMap<>();

        String[] npcNames = {
            "Clint", "Willy", "Mohsen", "Morris", "Gus",
            "Marnie", "Robin", "Pierre", "Sebastien", "Leah",
            "Harvey", "Abigail"
        };
        for (String name : npcNames) {
            if(name.equals("Mohsen")){
                npcTextures.put(name, new HashMap<>());
                continue;
            }
            npcTextures.put(name, new HashMap<>());

            for (int x = 1; x <= 3; x++) {
                Texture fullTexture = new Texture(Gdx.files.internal("game/Hero/NPC/" + name + "/" + name + "Pic" + x + ".png"));

                int partWidth = fullTexture.getWidth();
                int partHeight = fullTexture.getHeight()/6;

                TextureRegion[] parts = new TextureRegion[6];
                for (int p = 0; p < 6; p++) {
                    parts[p] = new TextureRegion(fullTexture, 0, p * partWidth, partWidth, partHeight);
                }

                npcTextures.get(name).put(x, parts);
            }
        }
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

    public TextureRegion getNPCImage(String name, int friendshipLevel) {
        int x = getSeasonTextureIndex();
        TextureRegion[] parts = npcTextures.get(name).get(x);
        if (parts == null) return null;
        return parts[getFriendshipTextureIndex(friendshipLevel)];
    }
    private int getFriendshipTextureIndex(int friendshipLevel) {
        switch (friendshipLevel) {
            case 0: return 2;
            case 1: return 0;
            case 2: return 3;
            default: return 1;
        }
    }
    private int getSeasonTextureIndex() {
        Season season = AppClient.getGameData().getTime().getSeason();
        switch (season) {
            case Spring -> {
                return 1;
            }
            case Summer -> {
                return 3;
            }
            default -> {
                return 2;
            }
        }
    }
}
