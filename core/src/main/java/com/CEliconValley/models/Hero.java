package com.CEliconValley.models;

import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.models.locations.Location;
import com.CEliconValley.models.ui.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.bytebuddy.matcher.FailSafeMatcher;

public class Hero {
    private final Texture playerTexture;
    private final Texture toolsTexture_Front;
    private final Texture toolsTexture_Back;
    private final Texture toolsTexture_Side;
    private TextureRegion[][] playerActs;
    private TextureRegion[][] toolsActs_Front;
    private TextureRegion[][] toolsActs_Back;
    private TextureRegion[][] toolsActs_Side;
    private Farm farm;
    public int currentDirection=3;

    public int playerX;
    public int playerY;
    public int targetX;
    public int targetY;
    public float renderX;
    public float renderY;
    public boolean isMoving = false;
    public boolean isActing = false;
    public float stateTime = 0f;
    public Animation<TextureRegion> currentAnimation;


    public Hero(Location location) {
        playerTexture = GameAssetManager.getGameAssetManager().getHeroTexture("generalActs.png");
        toolsTexture_Front = GameAssetManager.getGameAssetManager().getHeroTexture("tools_Front.png");
        toolsTexture_Back = GameAssetManager.getGameAssetManager().getHeroTexture("tools_Back.png");
        toolsTexture_Side = GameAssetManager.getGameAssetManager().getHeroTexture("tools_Side.png");
        playerActs = TextureRegion.split(playerTexture, playerTexture.getWidth() / 8, playerTexture.getHeight() / 11);
        toolsActs_Front = TextureRegion.split(toolsTexture_Front, toolsTexture_Front.getWidth()/5,toolsTexture_Front.getHeight()/15 );
        toolsActs_Back = TextureRegion.split(toolsTexture_Back, toolsTexture_Back.getWidth()/5,toolsTexture_Back.getHeight()/15);
        toolsActs_Side= TextureRegion.split(toolsTexture_Side,toolsTexture_Side.getWidth()/5,toolsTexture_Side.getHeight()/15);
//        this.farm =(Farm) location;
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
                    return new Animation<>(0.05f,wantedActs);
                case 2:
                    for(int i=0;i<playerActs[1].length;i++){
                        wantedActs[i] = playerActs[1][i];
                    }
                    return new Animation<>(0.05f,wantedActs);
                case 3:
                    for(int i=0;i<playerActs[0].length;i++){
                        wantedActs[i] = playerActs[0][i];
                    }
                    return new Animation<>(0.05f,wantedActs);
                case 4:
                    for (int i = 0; i < playerActs[1].length; i++) {
                        TextureRegion flippedFrame = new TextureRegion(playerActs[1][i]);
                        flippedFrame.flip(true, false);
                        wantedActs[i] = flippedFrame;
                    }
                    return new Animation<>(0.05f, wantedActs);

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
    public Animation<TextureRegion> useTool(int stage){
        TextureRegion[] wantedActs=new TextureRegion[5];

        switch(currentDirection){
            case 1:
                for(int i=0;i<toolsActs_Back[4+stage].length;i++){
                    wantedActs[i] = toolsActs_Back[4+stage][i];
                }
                return new Animation<>(0.15f,wantedActs);
            case 2:
                for(int i=0;i<toolsActs_Side[4+stage].length;i++){
                    wantedActs[i] = toolsActs_Side[4+stage][i];
                }
                return new Animation<>(0.15f,wantedActs);
            case 3:
                for(int i=0;i<toolsActs_Front[4+stage].length;i++){
                    wantedActs[i] = toolsActs_Front[4+stage][i];
                }
                return new Animation<>(0.15f,wantedActs);
            case 4:
                for (int i = 0; i <toolsActs_Side[4+stage].length; i++) {
                    TextureRegion flippedFrame = new TextureRegion(toolsActs_Side[4+stage][i]);
                    flippedFrame.flip(true, false);
                    wantedActs[i] = flippedFrame;
                }
                return new Animation<>(0.15f, wantedActs);

        }
        return null;
    }
    public Farm getFarm() {
        return farm;
    }


}
