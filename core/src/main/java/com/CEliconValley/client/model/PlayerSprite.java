package com.CEliconValley.client.model;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.PlayerData;
import com.CEliconValley.models.App;
import com.CEliconValley.models.ui.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class PlayerSprite {
    public Texture texture;
    public TextureRegion[][] playerActs;
    public String name;
    public Label label;
    public BitmapFont font = new BitmapFont();
    public int currentDirection;
    public float stateTime = 0f;
    public Animation<TextureRegion> currentAnimation;
    public GlyphLayout layout;
    public ShapeRenderer shapeRenderer = new ShapeRenderer();
    public boolean isActing = false;
    public boolean onRepeat = true;

    public PlayerSprite(PlayerData playerData) {
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        texture = GameAssetManager.getGameAssetManager().getHeroTexture("generalActs.png");
        playerActs = TextureRegion.split(texture, texture.getWidth() / 8, texture.getHeight() / 11);
        this.name = playerData.getUsername();
        this.label = new Label(this.name, skin);
        this.currentDirection = 3;
        this.currentAnimation = walk(false, currentDirection);
        layout = new GlyphLayout(font, this.name);

    }

    public Animation<TextureRegion> walk(boolean canWalk, int direction) {
        TextureRegion[] wantedActs=new TextureRegion[8];
        TextureRegion[] animation = new TextureRegion[1];
        currentDirection=direction;
        if(canWalk){
            switch(direction){
                case 1:
                    for(int i=0;i<playerActs[2].length;i++){
                        wantedActs[i] = playerActs[2][i];
                    }
                    return new Animation<>(0.15f,wantedActs);
                case 2:
                    for(int i=0;i<playerActs[1].length;i++){
                        wantedActs[i] = playerActs[1][i];
                    }
                    return new Animation<>(0.15f,wantedActs);
                case 3:
                    for(int i=0;i<playerActs[0].length;i++){
                        wantedActs[i] = playerActs[0][i];
                    }
                    return new Animation<>(0.15f,wantedActs);
                case 4:
                    for (int i = 0; i < playerActs[1].length; i++) {
                        TextureRegion flippedFrame = new TextureRegion(playerActs[1][i]);
                        flippedFrame.flip(true, false);
                        wantedActs[i] = flippedFrame;
                    }
                    return new Animation<>(0.15f, wantedActs);

            }
        }else {
            switch (direction) {
                case 1:
                    animation[0] = playerActs[2][0];
                    return new Animation<>(0.15f, animation);
                case 2:
                    animation[0] = playerActs[1][0];
                    return new Animation<>(0.15f, animation);
                case 4:
                    TextureRegion baseFrame = new TextureRegion(playerActs[1][0]);
                    baseFrame.flip(true, false);
                    animation[0] = baseFrame;
                    return new Animation<>(0.15f, animation);
                default:
                    animation[0] = playerActs[0][0];
                    return new Animation<>(0.15f, animation);

            }
        }
        return null;
    }


    public PlayerData getPlayerData() {
        for (PlayerData playersDatum : AppClient.getGameData().getPlayersData()) {
            if(playersDatum.getUsername().equals(name)){
                return playersDatum;
            }
        }
        return null;
    }
}
